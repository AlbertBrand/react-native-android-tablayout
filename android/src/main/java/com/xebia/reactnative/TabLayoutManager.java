package com.xebia.reactnative;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.xebia.reactnative.ReactTabLayout.InitialState;

import java.util.Map;

public class TabLayoutManager extends ViewGroupManager<ReactTabLayout> {
  public static final String REACT_CLASS = "TabLayout";

  private EventDispatcher mEventDispatcher;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  protected ReactTabLayout createViewInstance(ThemedReactContext themedReactContext) {
    Log.d(REACT_CLASS, "createViewInstance");
    ReactTabLayout tabLayout = new ReactTabLayout(themedReactContext);
    tabLayout.setOnTabSelectedListener(new TabLayoutOnTabSelectedListener(tabLayout));
    return tabLayout;
  }

  @Override
  public void addView(ReactTabLayout parent, View child, int index) {
    Log.d(REACT_CLASS, "addView");
    if (!(child instanceof ReactTabStub)) {
      throw new JSApplicationIllegalArgumentException("The TabLayout can only have Tab children");
    }

    Context context = parent.getContext();

    Tab newTab = parent.newTab();

    ReactTabStub tabStub = (ReactTabStub) child;
    tabStub.tab = newTab;
    parent.tabStubs.add(tabStub);

    // TODO check if custom view is really necessary
    View customView = LayoutInflater.from(context).inflate(R.layout.custom_tab_view, null);
    TextView tabText = (TextView) customView.findViewById(R.id.tabText);
    ImageView tabImage = (ImageView) customView.findViewById(R.id.tabImage);
    tabText.setText(tabStub.name);

    if (tabStub.textColor != null) {
      try {
        Log.d(REACT_CLASS, "textColor: " + tabStub.textColor);
        int textColor = Color.parseColor(tabStub.textColor);
        tabText.setTextColor(textColor);
      } catch (Exception e) {
        Log.e(REACT_CLASS, "Can't parse textColor '" + tabStub.textColor + "'", e);
      }
    }

    if (tabStub.iconUri != null) {
      Log.d(REACT_CLASS, "iconUri: " + tabStub.iconUri);
      // iconUri only supports file:// for now
      if (tabStub.iconUri.startsWith("file://")) {
        String pathName = tabStub.iconUri.substring(7);
        Bitmap bm = BitmapFactory.decodeFile(pathName);
        tabImage.setImageBitmap(bm);
      }
    } else if (tabStub.iconResId != null) {
      try {
        String iconPackage = tabStub.iconPackage != null ? tabStub.iconPackage : context.getPackageName();
        Log.d(REACT_CLASS, "iconResId: " + tabStub.iconResId + " iconPackage: " + iconPackage);
        int resId = context.getResources().getIdentifier(tabStub.iconResId, "drawable", iconPackage);
        tabImage.setImageResource(resId);
      } catch (Exception e) {
        Log.e(REACT_CLASS, "Icon resource id '" + tabStub.iconResId + "' not found", e);
      }
    }

    if (tabStub.iconSize > 0) {
      float scale = context.getResources().getDisplayMetrics().density;
      int iconSize = Math.round(tabStub.iconSize * scale);
      tabImage.getLayoutParams().width = iconSize;
      tabImage.getLayoutParams().height = iconSize;
    }

    newTab.setCustomView(customView);
    parent.addTab(newTab);

    if (parent.initialState == InitialState.TAB_POSITION_SET &&
        parent.initialTabPosition == index) {
      newTab.select();
      parent.initialState = InitialState.TAB_SELECTED;
    }
  }

  @ReactProp(name = "selectedTab", defaultInt = 0)
  public void setSelectedTab(ReactTabLayout view, int selectedTab) {
    Log.d(REACT_CLASS, "selectedTab " + selectedTab);
    selectTab(view, selectedTab);
  }

  @ReactProp(name = "selectedTabIndicatorColor")
  public void setSelectedTabIndicatorColor(ReactTabLayout view, String indicatorColor) {
    Log.d(REACT_CLASS, "selectedTabIndicatorColor " + indicatorColor);
    try {
      int highlightColor = Color.parseColor(indicatorColor);
      view.setSelectedTabIndicatorColor(highlightColor);
    } catch (IllegalArgumentException e) {
      Log.w(REACT_CLASS, "Unparseable color: " + indicatorColor);
    }
  }

  @ReactProp(name = "tabMode")
  public void setTabMode(ReactTabLayout view, String mode) {
    Log.d(REACT_CLASS, "tabMode " + mode);
    try {
      TabMode tabMode = TabMode.fromString(mode);
      view.setTabMode(tabMode.mode);
    } catch (IllegalArgumentException e) {
      Log.w(REACT_CLASS, "No valid tabMode: " + mode);
    }
  }

  @Override
  protected void addEventEmitters(ThemedReactContext reactContext, ReactTabLayout view) {
    mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
  }

  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
        TabSelectedEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onTabSelected")
    );
  }

  private void selectTab(ReactTabLayout tabLayout, int position) {
    if (position < 0 || position > tabLayout.getTabCount() - 1) {
      if (tabLayout.initialState == InitialState.TAB_POSITION_UNSET) {
        // store initial position until tab is added
        tabLayout.initialTabPosition = position;
        tabLayout.initialState = InitialState.TAB_POSITION_SET;
      } else {
        Log.w(REACT_CLASS, "Tried to select out of bounds tab");
      }
      return;
    }
    Tab tab = tabLayout.getTabAt(position);
    if (tab != null) {
      tab.select();
    }
  }

  class TabLayoutOnTabSelectedListener implements OnTabSelectedListener {
    private final ReactTabLayout mTabLayout;

    TabLayoutOnTabSelectedListener(ReactTabLayout tabLayout) {
      this.mTabLayout = tabLayout;
    }

    @Override
    public void onTabSelected(Tab tab) {
      if (mTabLayout.initialState == InitialState.TAB_POSITION_SET) {
        // don't send tabSelected events when initial tab is set but not selected yet
        return;
      }
      ReactTabStub tabStub = findTabStubFor(tab);
      if (tabStub == null) {
        return;
      }
      Log.d(REACT_CLASS, "dispatchEvent");
      int position = mTabLayout.tabStubs.indexOf(tabStub);
      mEventDispatcher.dispatchEvent(new TabSelectedEvent(tabStub.getId(), position));
      mEventDispatcher.dispatchEvent(new TabSelectedEvent(mTabLayout.getId(), position));
    }

    @Override
    public void onTabUnselected(Tab tab) {
    }

    @Override
    public void onTabReselected(Tab tab) {
    }

    private ReactTabStub findTabStubFor(Tab tab) {
      for (ReactTabStub tabStub : mTabLayout.tabStubs) {
        if (tabStub.tab.equals(tab)) {
          return tabStub;
        }
      }
      return null;
    }
  }
}
