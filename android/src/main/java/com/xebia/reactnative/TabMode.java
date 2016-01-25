package com.xebia.reactnative;

import android.support.design.widget.TabLayout;

enum TabMode {
  SCROLLABLE(TabLayout.MODE_SCROLLABLE),
  FIXED(TabLayout.MODE_FIXED);

  public int mode;

  TabMode(int mode) {
    this.mode = mode;
  }

  public static TabMode fromString(String text) {
    return text != null ? Enum.valueOf(TabMode.class, text.trim().toUpperCase()) : null;
  }
}
