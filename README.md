# React Native Android TabLayout native component

A React-Native wrapper for the standalone Android 
[TabLayout](http://developer.android.com/reference/android/support/design/widget/TabLayout.html) component. It's fully 
native and similar in use like the [TabBarIOS](https://facebook.github.io/react-native/docs/tabbarios.html) component. 

![Image of tablayout](https://i.imgur.com/qWOWugu.gif)

## Installation

Install module with NPM

```bash
npm i --save react-native-android-tablayout
```

If you have a fairly default setup of your project (React-Native 0.18+), you can try out the installer task. 
It will try to automatically perform the required changes on the Gradle and Java files for you. It won't back up 
anything so make sure you can reset its changes.

```bash
gradle -b node_modules/react-native-android-tablayout/build.gradle install
```

[Manual install guide](docs/manual_install.md) (with details on how to install on older versions of React-Native).

After the configuration, run `react-native run-android` from your project root to see if there are no compilation
failures.

## Usage

### TabLayout

Prop                      | Type    | Explanation
---                       | ---     | ---
selectedTabIndicatorColor | string  | Color of indicator line. Specify as #rrggbb.
selectedTab               | number  | Use for selecting default tab or connect to state.
tabMode                   | string  | Set tab mode. Default 'fixed', use 'scrollable' when tab need to scroll.
onTabSelected             | func    | Callback function when a tab is selected.


### Tab

Prop          | Type    | Explanation
---           | ---     | ---
name          | string  | Tab name.
iconUri       | string  | Icon URI. Only allows file:// URIs. See combination with react-native-vector-icons.
iconResId     | string  | Icon resource ID. Points to src/res/drawable*/<iconResId>.*
iconPackage   | string  | Icon resource package. If not provided, defaults to current package. Use 'android' for built in icons. See example.
iconSize      | number  | Icon size.
textColor     | string  | Text color. Specify as #rrggbb.
onTabSelected | func    | Callback function when a tab is selected


## Example 

See the example project under [example](example).

[Example documentation](docs/example_docs.md)

## Todo

  * mirror Android API:
    * icon alignment (icons on side)
    * colors, fonts
    * add/remove tabs
    * etc.

PRs are welcome!
  
