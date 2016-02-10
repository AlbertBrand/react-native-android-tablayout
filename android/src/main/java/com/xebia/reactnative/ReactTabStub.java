package com.xebia.reactnative;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.TabLayout.Tab;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.stetho.common.StringUtil;

public class ReactTabStub extends ViewGroup {
  public static final String TAG = "ReactTabStub";

  public ReactTabStub(Context context) {
    super(context);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    // never called, needsCustomLayoutForChildren for parent is true
  }

  Tab tab;

  private View customView;
  private TextView tabText;
  private ImageView tabImage;

  private String name;
  private String iconResId;
  private String iconPackage;
  private String iconUri;
  private int iconSize;
  private int textColor;

  public void attachCustomTabView(Tab tab) {
    Log.d(TAG, "attachCustomTabView");

    this.tab = tab;

    if (customView == null) {
      tab.setCustomView(R.layout.custom_tab_view);
      customView = tab.getCustomView();
      assert customView != null;

      tabText = (TextView) customView.findViewById(R.id.tabText);
      tabImage = (ImageView) customView.findViewById(R.id.tabImage);

      if (name != null) {
        nameChanged();
      }
      if (textColor != 0) {
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
    } else {
      customViewChanged();
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

  public void setTextColor(int textColor) {
    this.textColor = textColor;
    textColorChanged();
  }

  public void setCustomView(View customView) {
    this.customView = customView;
    customViewChanged();
  }

  public void setAccessibilityLabel(String accessibilityLabel) {
    setContentDescription(accessibilityLabel);
    accessibilityLabelChanged();
  }

  private void nameChanged() {
    if (tabText == null) return;
    Log.d(TAG, "nameChanged: " + name);

    tabText.setText(name);

    updateLayout();
  }

  private void iconResourceChanged() {
    if (tabImage == null) return;
    String packageName = iconPackage != null ? iconPackage : getContext().getPackageName();
    Log.d(TAG, "iconResourceChanged, id: " + iconResId + " package: " + packageName);

    if (!TextUtils.isEmpty(iconResId)) {
      try {
        int resId = getContext().getResources().getIdentifier(iconResId, "drawable", packageName);
        tabImage.setImageResource(resId);
        tabImage.setVisibility(View.VISIBLE);
      } catch (Exception e) {
        Log.e(TAG, "Icon resource id " + iconResId + " with package " + packageName + " not found", e);
      }
    } else {
      tabImage.setVisibility(View.GONE);
    }

    updateLayout();
  }

  private void iconUriChanged() {
    if (tabImage == null) return;
    Log.d(TAG, "iconUriChanged: " + iconUri);

    if (iconUri.startsWith("file://")) {
      String pathName = iconUri.substring(7);
      Bitmap bm = BitmapFactory.decodeFile(pathName);
      tabImage.setImageBitmap(bm);
      tabImage.setVisibility(View.VISIBLE);
    } else if (TextUtils.isEmpty(iconUri)) {
      tabImage.setVisibility(View.GONE);
    } else {
      Log.e(TAG, "Icon uri only supports file:// for now, saw " + iconUri);
    }

    updateLayout();
  }

  private void iconSizeChanged() {
    if (tabImage == null) return;
    Log.d(TAG, "iconSizeChanged: " + iconSize);

    float scale = getContext().getResources().getDisplayMetrics().density;
    int size = Math.round(iconSize * scale);
    tabImage.getLayoutParams().width = size;
    tabImage.getLayoutParams().height = size;

    updateLayout();
  }

  private void textColorChanged() {
    if (tabText == null) return;
    Log.d(TAG, "textColorChanged: " + textColor);

    tabText.setTextColor(textColor);
  }

  public void accessibilityLabelChanged() {
    if (customView == null || customView.getParent() == null) return;
    CharSequence contentDescription = getContentDescription();
    Log.d(TAG, "accessibilityLabelChanged: " + contentDescription);

    ViewGroup parent = (ViewGroup) customView.getParent();
    parent.setContentDescription(contentDescription);
  }

  public void customViewChanged() {
    if (tab == null) return;
    Log.d(TAG, "customViewChanged");

    FrameLayout wrapperView = new FrameLayout(getContext()) {
      @Override
      protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // set measurements based on the TabView dimensions
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
      }
    };

    wrapperView.addView(customView);
    tab.setCustomView(wrapperView);
    updateLayout();
  }

  private void updateLayout() {
    if (customView == null || customView.getParent() == null) return;

    View tabView = (View) customView.getParent();
    tabView.measure(
        View.MeasureSpec.makeMeasureSpec(tabView.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(tabView.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
    tabView.layout(tabView.getLeft(), tabView.getTop(), tabView.getRight(), tabView.getBottom());
  }
}
