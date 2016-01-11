package com.xebia.reactnative;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.EventDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabLayoutManager extends ViewGroupManager<TabLayout> {
  public static final String REACT_CLASS = "TabLayout";

  private TabLayout mTabLayout;
  private List<TabStub> mTabStubs = new ArrayList<>();
  private EventDispatcher mEventDispatcher;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  protected TabLayout createViewInstance(ThemedReactContext themedReactContext) {
    mTabLayout = new TabLayout(themedReactContext);
    mTabLayout.setOnTabSelectedListener(mOnTabSelectedListener);
    return mTabLayout;
  }

  @Override
  public void addView(TabLayout parent, View child, int index) {
    Log.d(REACT_CLASS, "addView");
    if (!(child instanceof TabStub)) {
      throw new JSApplicationIllegalArgumentException("The TabLayout can only have Tab children");
    }

    Context context = mTabLayout.getContext();

    Tab newTab = parent.newTab();

    TabStub tabStub = (TabStub) child;
    tabStub.tab = newTab;
    mTabStubs.add(tabStub);

    // TODO check if custom view is really necessary
    View customView = LayoutInflater.from(context).inflate(R.layout.custom_tab_view, null);
    TextView tabText = (TextView) customView.findViewById(R.id.tabText);
    ImageView tabImage = (ImageView) customView.findViewById(R.id.tabImage);
    tabText.setText(tabStub.name);

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
  }

  @Override
  public void removeViewAt(TabLayout parent, int index) {
    Log.d(REACT_CLASS, "removeViewAt");
    // TODO remove tab
    super.removeViewAt(parent, index);
  }

  @ReactProp(name = "selectedTab", defaultInt = 0)
  public void setSelectedTab(TabLayout view, int selectedTab) {
    Log.d(REACT_CLASS, "selectedTab " + selectedTab);
    // TODO selectTab does not work until child components are rendered
    selectTab(selectedTab);
  }

  @Override
  protected void addEventEmitters(ThemedReactContext reactContext, TabLayout view) {
    mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
  }

  private void selectTab(int position) {
    if (position < 0 || position > mTabLayout.getTabCount() - 1) {
      Log.w(REACT_CLASS, "Tried to select out of bounds tab");
      return;
    }
    Tab tab = mTabLayout.getTabAt(position);
    if (tab != null) {
      tab.select();
    }
  }

  private OnTabSelectedListener mOnTabSelectedListener = new OnTabSelectedListener() {
    @Override
    public void onTabSelected(Tab tab) {
      TabStub tabStub = findTabStubFor(tab);
      if (tabStub != null) {
        Log.d(REACT_CLASS, "dispatchEvent");
        int position = mTabStubs.indexOf(tabStub);
        mEventDispatcher.dispatchEvent(new TabSelectedEvent(tabStub.getId(), position));
        mEventDispatcher.dispatchEvent(new TabSelectedEvent(mTabLayout.getId(), position));
      }
    }

    @Override
    public void onTabUnselected(Tab tab) {
    }

    @Override
    public void onTabReselected(Tab tab) {
    }

    private TabStub findTabStubFor(Tab tab) {
      for (TabStub tabStub : mTabStubs) {
        if (tabStub.tab.equals(tab)) {
          return tabStub;
        }
      }
      return null;
    }
  };
}
