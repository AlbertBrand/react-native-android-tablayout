import React, {
  Component,
  StyleSheet,
  View
} from 'react-native';
import { Tab, TabLayout } from 'react-native-android-tablayout'

export default class ScrollableTabLayout extends Component {
  render() {
    return (
      <View>
        <TabLayout style={styles.tabLayout} tabMode="scrollable">
          <Tab name="Tab one"/>
          <Tab name="Tab two"/>
          <Tab name="Tab three"/>
          <Tab name="Tab four"/>
          <Tab name="Tab five"/>
          <Tab name="Tab six"/>
          <Tab name="Tab seven"/>
          <Tab name="Tab eight"/>
          <Tab name="Tab nine"/>
        </TabLayout>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  tabLayout: {
    backgroundColor: '#ddd',
  },
});
