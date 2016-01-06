# React Native Android TabLayout native component

<iframe src='https://gfycat.com/ifr/QualifiedFlusteredHornet' frameborder='0' scrolling='no' width='650' height='366' style='-webkit-backface-visibility: hidden;-webkit-transform: scale(1);' ></iframe>

## Installation

Install module with NPM

```bash
npm i --save react-native-android-tablayout
```
    
Edit `android/settings.gradle`:

```gradle
include ':react-native-android-tablayout'
project(':react-native-android-tablayout').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-android-tablayout/android')
```

Edit `android/app/build.gradle`:

```gradle
...
dependencies {
    ...
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

class MyView extends React.Component {

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
