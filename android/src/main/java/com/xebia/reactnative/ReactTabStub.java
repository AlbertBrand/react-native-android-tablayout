package com.xebia.reactnative;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.TabLayout.Tab;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReactTabStub extends View {
  public static final String REACT_CLASS = "ReactTabStub";

  public ReactTabStub(Context context) {
    super(context);
  }

  Tab tab;
  TextView tabText;
  ImageView tabImage;

  String name;
  String iconResId;
  String iconPackage;
  String iconUri;
  int iconSize;
  String textColor;

  public void attachCustomTabView(Tab tab) {
    View customView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_view, null);
    tab.setCustomView(customView);

    this.tab = tab;
    tabText = (TextView) customView.findViewById(R.id.tabText);
    tabImage = (ImageView) customView.findViewById(R.id.tabImage);

    if (name != null) {
      nameChanged();
    }

    if (textColor != null) {
      textColorChanged();
    }

    if (iconUri != null) {
      iconUriChanged();
    } else if (iconResId != null) {
      iconResourceChanged();
    }

    if (iconSize > 0) {
      iconSizeChanged();
    }
  }

  public void nameChanged() {
    Log.d(REACT_CLASS, "nameChanged: " + name);
    if (tabText == null) return;

    tabText.setText(name);
  }

  public void iconResourceChanged() {
    Log.d(REACT_CLASS, "iconResourceChanged, id: " + iconResId + " package: " + iconPackage);
    if (tabImage == null) return;

    String packageName = iconPackage != null ? iconPackage : getContext().getPackageName();
    try {
      int resId = getContext().getResources().getIdentifier(iconResId, "drawable", packageName);
      tabImage.setImageResource(resId);
    } catch (Exception e) {
      Log.e(REACT_CLASS, "Icon resource id " + iconResId + " with package " + packageName + " not found", e);
    }
  }

  public void iconUriChanged() {
    Log.d(REACT_CLASS, "iconUriChanged: " + iconUri);
    if (tabImage == null) return;

    // iconUri only supports file:// for now
    if (iconUri.startsWith("file://")) {
      String pathName = iconUri.substring(7);
      Bitmap bm = BitmapFactory.decodeFile(pathName);
      tabImage.setImageBitmap(bm);
    }
  }

  public void iconSizeChanged() {
    Log.d(REACT_CLASS, "iconSizeChanged: " + iconSize);
    if (tabImage == null) return;

    float scale = getContext().getResources().getDisplayMetrics().density;
    int size = Math.round(iconSize * scale);
    tabImage.getLayoutParams().width = size;
    tabImage.getLayoutParams().height = size;
  }

  public void textColorChanged() {
    Log.d(REACT_CLASS, "textColorChanged: " + textColor);
    if (tabText == null) return;

    try {
      int color = Color.parseColor(textColor);
      tabText.setTextColor(color);
    } catch (Exception e) {
      Log.e(REACT_CLASS, "Can't parse textColor: " + textColor, e);
    }
  }
}
