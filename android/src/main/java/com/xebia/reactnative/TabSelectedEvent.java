package com.xebia.reactnative;

import android.os.SystemClock;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class TabSelectedEvent extends Event<TabSelectedEvent> {
  public static final String EVENT_NAME = "tabSelected";

  public TabSelectedEvent(int viewTag) {
    super(viewTag, SystemClock.uptimeMillis());
  }

  @Override
  public String getEventName() {
    return EVENT_NAME;
  }

  @Override
  public void dispatch(RCTEventEmitter rctEventEmitter) {
    rctEventEmitter.receiveEvent(getViewTag(), getEventName(), Arguments.createMap());
  }
}
