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

  @ReactProp(name = "iconResId")
  public void setIconResId(TabStub view, String iconResId) {
    view.iconResId = iconResId;
  }

  @ReactProp(name = "iconPackage")
  public void setIconPackage(TabStub view, String iconPackage) {
    view.iconPackage = iconPackage;
  }

  @ReactProp(name = "iconUri")
  public void setIconUri(TabStub view, String iconUri) {
    view.iconUri = iconUri;
  }

  @ReactProp(name = "iconSize")
  public void setIconSize(TabStub view, int iconSize) {
    view.iconSize = iconSize;
  }

  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
        TabSelectedEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onTabSelected")
    );
  }
}
