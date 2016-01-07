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
    
Edit `android/settings.gradle`:

```gradle
// ...

include ':react-native-android-tablayout'
project(':react-native-android-tablayout').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-android-tablayout/android')
```

Edit `android/app/build.gradle`:

```gradle
// ...

dependencies {
  // ...
  compile project(':react-native-android-tablayout')
}
```

Register module in `MainActivity.java`:

```java
// ...

import com.xebia.reactnative.TabLayoutPackage;    // <--- import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // ...
    mReactInstanceManager = ReactInstanceManager.builder()
      .setApplication(getApplication())
      .setBundleAssetName("index.android.bundle")
      .setJSMainModuleName("index.android")
      .addPackage(new MainReactPackage())
      .addPackage(new TabLayoutPackage())         // <--- add package
      .setUseDeveloperSupport(BuildConfig.DEBUG)
      .setInitialLifecycleState(LifecycleState.RESUMED)
      .build();
    // ...
  }

  // ...
}
```

Run `react-native run-android` from your project root.

## Usage

```js
// ...

import { Tab, TabLayout } from 'react-native-android-tablayout'

// ...

class MyTabView extends React.Component {

  render() {
    return (
      <View>
        <TabLayout>
          <Tab name="Tab 1" onTabSelected={()=>{ console.log('Tab 1 pressed') }}/>
          <Tab name="Tab 2" onTabSelected={()=>{ console.log('Tab 2 pressed') }}/>
          <Tab name="Tab 3" onTabSelected={()=>{ console.log('Tab 3 pressed') }}/>
        </TabLayout>
      </View>
    )
  }

  // ...
});

// ...

```

With icons on top:

```js
// ...

import { Tab, TabLayout } from 'react-native-android-tablayout'

// ...

class MyTabIconView extends React.Component {

  render() {
    return (
      <View>
        <TabLayout 
          style={{height: 72}}>         // design standard for icons on top
          <Tab 
            name="Tab 1" 
            iconSize="24"                                                  // design standard for icons on top
            iconUri="file:///data/data/com.example/cache/abcdef_24@3x.png" // only file:// support for local paths
            />
          <Tab 
            name="Tab 2"
            iconSize="24"
            iconResId="my_custom_icon"  // points to src/res/drawable*/my_custom_icon.*
            />
          <Tab 
            name="Tab 3"
            iconSize="24"
            iconPackage="android"       // specify if not in own package; use 'android' for platform packaged resources
            iconResId="emo_im_cool"     // see [Android Drawables](http://androiddrawables.com)
            />
        </TabLayout>
      </View>
    )
  }

  // ...
});

// ...


```

## Todo

  * mirror Android API:
    * icon alignment (icons on side)
    * colors, fonts
    * add/remove tabs
    * set selected tab
    * etc.
  * work together with [ViewPagerAndroid](https://facebook.github.io/react-native/docs/viewpagerandroid.html)

PRs are welcome!
  
