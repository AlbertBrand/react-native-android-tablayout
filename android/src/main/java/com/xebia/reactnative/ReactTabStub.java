package com.xebia.reactnative;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.TabLayout.Tab;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReactTabStub extends View {
  public static final String TAG = "ReactTabStub";

  public ReactTabStub(Context context) {
    super(context);
  }

  Tab tab;

  private TextView tabText;
  private ImageView tabImage;

  private String name;
  private String iconResId;
  private String iconPackage;
  private String iconUri;
  private int iconSize;
  private String textColor;

  public void attachCustomTabView(Tab tab) {
    tab.setCustomView(R.layout.custom_tab_view);
    View customView = tab.getCustomView();
    assert customView != null;

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

  public void setName(String name) {
    this.name = name;
    nameChanged();
  }

  public void setIconResId(String iconResId) {
    this.iconResId = iconResId;
    iconResourceChanged();
  }

  public void setIconPackage(String iconPackage) {
    this.iconPackage = iconPackage;
    iconResourceChanged();
  }

  public void setIconUri(String iconUri) {
    this.iconUri = iconUri;
    iconUriChanged();
  }

  public void setIconSize(int iconSize) {
    this.iconSize = iconSize;
    iconSizeChanged();
  }

  public void setTextColor(String textColor) {
    this.textColor = textColor;
    textColorChanged();
  }

  private void nameChanged() {
    if (tabText == null) return;
    Log.d(TAG, "nameChanged: " + name);

    tabText.setText(name);
  }

  private void iconResourceChanged() {
    if (tabImage == null) return;
    Log.d(TAG, "iconResourceChanged, id: " + iconResId + " package: " + iconPackage);

    String packageName = iconPackage != null ? iconPackage : getContext().getPackageName();
    try {
      int resId = getContext().getResources().getIdentifier(iconResId, "drawable", packageName);
      tabImage.setImageResource(resId);
    } catch (Exception e) {
      Log.e(TAG, "Icon resource id " + iconResId + " with package " + packageName + " not found", e);
    }
  }

  private void iconUriChanged() {
    if (tabImage == null) return;
    Log.d(TAG, "iconUriChanged: " + iconUri);

    // iconUri only supports file:// for now
    if (iconUri.startsWith("file://")) {
      String pathName = iconUri.substring(7);
      Bitmap bm = BitmapFactory.decodeFile(pathName);
      tabImage.setImageBitmap(bm);
    }
  }

  private void iconSizeChanged() {
    if (tabImage == null) return;
    Log.d(TAG, "iconSizeChanged: " + iconSize);

    float scale = getContext().getResources().getDisplayMetrics().density;
    int size = Math.round(iconSize * scale);
    tabImage.getLayoutParams().width = size;
    tabImage.getLayoutParams().height = size;
  }

  private void textColorChanged() {
    if (tabText == null) return;
    Log.d(TAG, "textColorChanged: " + textColor);

    try {
      int color = Color.parseColor(textColor);
      tabText.setTextColor(color);
    } catch (Exception e) {
      Log.e(TAG, "Can't parse textColor: " + textColor, e);
    }
  }
}
