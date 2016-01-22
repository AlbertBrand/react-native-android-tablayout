import React, {
  Component,
  StyleSheet,
  View
} from 'react-native';
import { Tab, TabLayout } from 'react-native-android-tablayout'

export default class IconsOnTopTabLayout extends Component {
  render() {
    return (
      <View>
        <TabLayout style={styles.tabLayout}>
          <Tab
            name="Tab 1"
            iconSize={24}
            iconUri="file:///data/data/com.example/cache/abcdef_24@3x.png"/>
          <Tab
            name="Tab 2"
            iconSize={24}
            iconResId="custom_icon"/>
          <Tab
            name="Tab 3"
            iconSize={24}
            iconPackage="android"
            iconResId="emo_im_cool"/>
        </TabLayout>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  tabLayout: {
    backgroundColor: '#ddd',
    height: 72,
  },
});
