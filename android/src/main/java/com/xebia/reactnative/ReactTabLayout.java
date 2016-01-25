package com.xebia.reactnative;

import android.content.Context;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ReactTabLayout extends TabLayout {

  public ReactTabLayout(Context context) {
    super(context);
  }

  List<ReactTabStub> tabStubs = new ArrayList<>();

  InitialState initialState = InitialState.TAB_POSITION_UNSET;
  int initialTabPosition;

  enum InitialState {
    TAB_POSITION_UNSET,
    TAB_POSITION_SET,
    TAB_SELECTED
  }
}
