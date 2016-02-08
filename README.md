# React Native Android TabLayout native component

A React-Native (0.19+) wrapper for the standalone Android 
[TabLayout](http://developer.android.com/reference/android/support/design/widget/TabLayout.html) component. It's fully 
native and similar in use like the [TabBarIOS](https://facebook.github.io/react-native/docs/tabbarios.html) component. 

![Image of tablayout](https://i.imgur.com/qWOWugu.gif)

## Example Project

You can find an example project in [a separate repo](https://github.com/AlbertBrand/react-native-android-tablayout-example).

## Installation

Install module with NPM:

```bash
npm install --save react-native-android-tablayout
```

If you haven't installed [RNPM](https://github.com/rnpm/rnpm):

```bash
npm install rnpm -g
```

When you have installed RNPM:

```bash
rnpm link react-native-android-tablayout
```

[Manual install guide](docs/manual_install.md).

After the configuration, run `react-native run-android` from your project root to see if there are no compilation
failures.

## Usage

### TabLayout

Prop                      | Type    | Explanation
---                       | ---     | ---
selectedTab               | number  | Use for selecting the initial tab and/or connecting to state. See the [StatefulTabLayout example](https://github.com/AlbertBrand/react-native-android-tablayout-example/blob/master/app/StatefulTabLayout.js).
selectedTabIndicatorColor | string  | Color of indicator line. Specify in [CSS color format](https://facebook.github.io/react-native/docs/colors.html).
tabGravity                | string  | Set tab gravity. Default 'fill', use 'center' when tabstrip needs to be centered.
tabMode                   | string  | Set tab mode. Default 'fixed', use 'scrollable' when tabstrip needs to scroll.
onTabSelected             | func    | Provide callback function with `e:Event` as argument. When called, the selected tab position is found in `e.nativeEvent.position` (0-based). See the [StatefulTabLayout example](https://github.com/AlbertBrand/react-native-android-tablayout-example/blob/master/app/StatefulTabLayout.js).

### Tab

Prop                | Type    | Explanation
---                 | ---     | ---
name                | string  | Tab name.
iconResId           | string  | Icon resource ID. Points to a drawable, see the [IconsOnTopTabLayout example](https://github.com/AlbertBrand/react-native-android-tablayout-example/blob/master/app/IconsOnTopTabLayout.js).
iconPackage         | string  | Icon resource package. If not provided, defaults to current package. Use 'android' for built-in icons. See the [IconsOnTopTabLayout example](https://github.com/AlbertBrand/react-native-android-tablayout-example/blob/master/app/IconsOnTopTabLayout.js).
iconUri             | string  | Icon URI. Only allows file:// URIs. See how to combine with [react-native-vector-icons](https://github.com/oblador/react-native-vector-icons) in the [IconsOnTopTabLayout example](https://github.com/AlbertBrand/react-native-android-tablayout-example/blob/master/app/IconsOnTopTabLayout.js).
iconSize            | number  | Icon size.
textColor           | string  | Text color. Specify in [CSS color format](https://facebook.github.io/react-native/docs/colors.html).
onTabSelected       | func    | Provide callback function with `e:Event` as argument. Called on the tab that was selected. When called, the selected tab position is found in `e.nativeEvent.position` (0-based). See the [StatefulTabLayout example](https://github.com/AlbertBrand/react-native-android-tablayout-example/blob/master/app/StatefulTabLayout.js).
accessibilityLabel  | string  | Accessibility label for tab. Tabs are already set as accessible.

## Todo

  * mirror Android API:
    * icon alignment (icons on side)
    * colors, fonts
    * add/remove tabs
    * etc.

PRs are welcome!
  
