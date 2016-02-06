package com.xebia.reactnative;

import android.support.design.widget.TabLayout;

enum TabGravity {
  FILL(TabLayout.GRAVITY_FILL),
  CENTER(TabLayout.GRAVITY_CENTER);

  public int gravity;

  TabGravity(int gravity) {
    this.gravity = gravity;
  }

  public static TabGravity fromString(String text) {
    return text != null ? Enum.valueOf(TabGravity.class, text.trim().toUpperCase()) : null;
  }
}
