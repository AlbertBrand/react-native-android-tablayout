package com.xebia.reactnative;

import android.content.Context;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ReactTabLayout extends TabLayout {

  public ReactTabLayout(Context context) {
    super(context);
  }

  public List<ReactTabStub> tabStubs = new ArrayList<>();
}
