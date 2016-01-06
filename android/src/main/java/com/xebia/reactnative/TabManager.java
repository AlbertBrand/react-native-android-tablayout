package com.xebia.reactnative;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

public class TabManager extends SimpleViewManager<TabStub> {
  public static final String REACT_CLASS = "Tab";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  protected TabStub createViewInstance(ThemedReactContext themedReactContext) {
    return new TabStub(themedReactContext);
  }

  @ReactProp(name = "name")
  public void setName(TabStub view, String name) {
    view.name = name;
  }

  @Override
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
        TabSelectedEvent.EVENT_NAME, MapBuilder.of("registrationName", "onTabSelected")
    );
  }
}
