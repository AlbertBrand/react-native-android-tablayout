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

Register module in `MainActivity.java` for React-Native 0.19+:

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
