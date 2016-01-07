package com.xebia.reactnative;

import android.content.Context;
import android.support.design.widget.TabLayout.Tab;
import android.view.View;

public class TabStub extends View {
  public TabStub(Context context) {
    super(context);
  }

  public Tab tab;

  public String name;
  public String iconResId;
  public String iconPackage;
  public String iconUri;
  public int iconSize;
}
