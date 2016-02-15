# React Native Android TabLayout native component

A React-Native (0.19+) wrapper for the standalone Android 
[TabLayout](http://developer.android.com/reference/android/support/design/widget/TabLayout.html) component. It's fully 
native and similar in use like the [TabBarIOS](https://facebook.github.io/react-native/docs/tabbarios.html) component. 

![Animated example](https://i.imgur.com/nKFVnu4.gif)

## Example Project

You can find an example project in [a separate repo](https://github.com/AlbertBrand/react-native-android-tablayout-example).

## Installation

Install module with NPM:

```bash
npm install --save react-native-android-tablayout
```

If you haven't installed [RNPM](https://github.com/rnpm/rnpm), run:

```bash
npm install -g rnpm
```

After RNPM is installed:

```bash
rnpm link react-native-android-tablayout
```

If you want to setup the project manually, see the [manual install guide](docs/manual_install.md).

After setting up your project, run `react-native run-android` from the root to see if there are no compilation failures.

## Usage

Make sure to import the `Tab` and `TabLayout` component in your script: 

```javascript
import { Tab, TabLayout } from 'react-native-android-tablayout';
```

Then, create a tab layout as follows:

```javascript
export default class MyComponent extends Component {
  render() {
    return (
      <View>
        <TabLayout>
          <Tab name="Tab 1"/>
          <Tab name="Tab 2"/>
          <Tab name="Tab 3"/>
        </TabLayout>
      </View>
    );
  }
}
```

The `TabLayout` and `Tab` accept the following properties:

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

Usage of these properties can be seen by example in [the example repo](https://github.com/AlbertBrand/react-native-android-tablayout-example).

## Custom views

Since v0.2, you can define a custom view for a tab by adding child components to a `Tab` element:

```javascript
export default class MyComponent extends Component {
  render() {
    return (
      <View>
        <TabLayout>
          <Tab style={{width: 110, height: 48}}>
            <Text>Tab 1</Text>
            <Text>Hey, multiline!</Text>
          </Tab>
          <Tab style={{width: 110, height: 48}}>
            <Image source={require('./image/tabImage.png')}/>
          </Tab>
          <Tab style={{width: 110, height: 48}}>
            <Icon name="user"/>
          </Tab>
        </TabLayout>
      </View>
    );
  }
}
```

You need to specify the width and height of the tab contents, else no contents will show up. This might be improved in the future.

See the [CustomViewTabLayout example](https://github.com/AlbertBrand/react-native-android-tablayout-example/blob/master/app/CustomViewTabLayout.js) for a working example.

## Todo

  * add/remove tabs not implemented
  * custom views need a width and height to work

PRs are welcome!
