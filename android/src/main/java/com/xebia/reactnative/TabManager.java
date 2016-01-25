package com.xebia.reactnative;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

public class TabManager extends SimpleViewManager<ReactTabStub> {
  public static final String REACT_CLASS = "Tab";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  protected ReactTabStub createViewInstance(ThemedReactContext themedReactContext) {
    return new ReactTabStub(themedReactContext);
  }

  @ReactProp(name = "name")
  public void setName(ReactTabStub view, String name) {
    view.name = name;
    view.nameChanged();
  }

  @ReactProp(name = "iconResId")
  public void setIconResId(ReactTabStub view, String iconResId) {
    view.iconResId = iconResId;
    view.iconResourceChanged();
  }

  @ReactProp(name = "iconPackage")
  public void setIconPackage(ReactTabStub view, String iconPackage) {
    view.iconPackage = iconPackage;
    view.iconResourceChanged();
  }

  @ReactProp(name = "iconUri")
  public void setIconUri(ReactTabStub view, String iconUri) {
    view.iconUri = iconUri;
    view.iconUriChanged();
  }

  @ReactProp(name = "iconSize")
  public void setIconSize(ReactTabStub view, int iconSize) {
    view.iconSize = iconSize;
    view.iconSizeChanged();
  }

  @ReactProp(name = "textColor")
  public void setTextColor(ReactTabStub view, String textColor) {
    view.textColor = textColor;
    view.textColorChanged();
  }

  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
        TabSelectedEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onTabSelected")
    );
  }
}
