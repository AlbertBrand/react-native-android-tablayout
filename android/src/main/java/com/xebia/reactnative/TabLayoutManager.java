package com.xebia.reactnative;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.util.Log;
import android.view.View;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.EventDispatcher;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutManager extends ViewGroupManager<TabLayout> {
  public static final String REACT_CLASS = "TabLayout";

  private EventDispatcher mEventDispatcher;
  private List<TabStub> mTabStubs = new ArrayList<>();

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  protected TabLayout createViewInstance(ThemedReactContext themedReactContext) {
    final TabLayout tabLayout = new TabLayout(themedReactContext);
    tabLayout.setOnTabSelectedListener(onTabSelectedListener);
    return tabLayout;
  }

  @Override
  public void addView(TabLayout parent, View child, int index) {
    Log.d(REACT_CLASS, "addView");
    if (!(child instanceof TabStub)) {
      throw new JSApplicationIllegalArgumentException("The TabLayout can only have Tab children");
    }

    Tab newTab = parent.newTab();

    TabStub tabStub = (TabStub) child;
    tabStub.tab = newTab;
    mTabStubs.add(tabStub);

    newTab.setText(tabStub.name);
    // TODO icon / customview
    parent.addTab(newTab);
  }

  @Override
  public void removeViewAt(TabLayout parent, int index) {
    Log.d(REACT_CLASS, "removeViewAt");
    // TODO remove tab
    super.removeViewAt(parent, index);
  }

  @Override
  protected void addEventEmitters(ThemedReactContext reactContext, TabLayout view) {
    mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
  }

  private OnTabSelectedListener onTabSelectedListener = new OnTabSelectedListener() {
    @Override
    public void onTabSelected(Tab tab) {
      TabStub tabStub = findTabStubFor(tab);
      if (tabStub != null) {
        Log.d(REACT_CLASS, "dispatchEvent");
        mEventDispatcher.dispatchEvent(new TabSelectedEvent(tabStub.getId()));
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
