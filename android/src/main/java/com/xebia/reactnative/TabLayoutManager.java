package com.xebia.reactnative;

import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.util.Log;
import android.view.View;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
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
  public void addView(ReactTabLayout tabLayout, View child, int index) {
    Log.d(REACT_CLASS, "addView");
    if (!(child instanceof ReactTabStub)) {
      throw new JSApplicationIllegalArgumentException("The TabLayout can only have Tab children");
    }

    Tab tab = tabLayout.newTab();
    tabLayout.addTab(tab);

    ReactTabStub tabStub = (ReactTabStub) child;
    tabStub.attachCustomTabView(tab);

    tabLayout.tabStubs.add(tabStub);

    // set accessibilityLabel on parent TabView, which is now available after addTab call
    if (tabStub.getContentDescription() != null) {
      tabStub.accessibilityLabelChanged();
    }

    // when initial position was stored, update tab selection
    if (tabLayout.initialState == InitialState.TAB_POSITION_SET &&
        tabLayout.initialTabPosition == index) {
      tabLayout.initialState = InitialState.TAB_SELECTED;
      tab.select();
    }
  }

  @ReactProp(name = "selectedTab", defaultInt = 0)
  public void setSelectedTab(ReactTabLayout view, int selectedTab) {
    Log.d(REACT_CLASS, "selectedTab " + selectedTab);
    selectTab(view, selectedTab);
  }

  @ReactProp(name = "selectedTabIndicatorColor")
  public void setSelectedTabIndicatorColor(ReactTabLayout view, int indicatorColor) {
    Log.d(REACT_CLASS, "selectedTabIndicatorColor " + indicatorColor);
    view.setSelectedTabIndicatorColor(indicatorColor);
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

  @ReactProp(name = "tabGravity")
  public void setTabGravity(ReactTabLayout view, String gravity) {
    Log.d(REACT_CLASS, "tabGravity " + gravity);
    try {
      TabGravity tabGravity = TabGravity.fromString(gravity);
      view.setTabGravity(tabGravity.gravity);
    } catch (IllegalArgumentException e) {
      Log.w(REACT_CLASS, "No valid tabGravity: " + gravity);
    }
  }

  @Override
  public boolean needsCustomLayoutForChildren() {
    // don't bother to layout the child tab stub views
    return true;
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
