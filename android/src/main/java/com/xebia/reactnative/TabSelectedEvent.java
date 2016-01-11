package com.xebia.reactnative;

import android.os.SystemClock;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class TabSelectedEvent extends Event<TabSelectedEvent> {
  public static final String EVENT_NAME = "tabSelected";
  private final int position;

  public TabSelectedEvent(int viewTag, int position) {
    super(viewTag, SystemClock.uptimeMillis());
    this.position = position;
  }

  @Override
  public String getEventName() {
    return EVENT_NAME;
  }

  @Override
  public void dispatch(RCTEventEmitter rctEventEmitter) {
    rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }

  private WritableMap serializeEventData() {
    WritableMap eventData = Arguments.createMap();
    eventData.putInt("position", position);
    return eventData;
  }
}
