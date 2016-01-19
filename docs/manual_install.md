# Manual install guide

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

Register module in `MainActivity.java` for React-Native 0.18+:

```java
// ...

import com.xebia.reactnative.TabLayoutPackage;    // <--- import

public class MainActivity extends ReactActivity {
    // ...
    
    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
            new TabLayoutPackage()                // <--- add package
        );
    }
    
    // ...
}
```

For React-Native 0.17:

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
