import React, {
  Component,
  StyleSheet,
  Text,
  View,
  ViewPagerAndroid,
} from 'react-native';
import { Tab, TabLayout } from 'react-native-android-tablayout'

export default class ViewPagerTabLayout extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pagePosition: 0,
    };
  }

  render() {
    return (
      <View>
        <TabLayout
          style={styles.tabLayout}
          selectedTab={this.state.pagePosition}
          onTabSelected={this._setPagePosition.bind(this)}>
          <Tab name="Tab 1"/>
          <Tab name="Tab 2"/>
          <Tab name="Tab 3"/>
        </TabLayout>
        <ViewPagerAndroid
          style={styles.viewPager}
          ref={viewPager => { this.viewPager = viewPager; }}
          onPageSelected={this._setPagePosition.bind(this)}>
          <View style={styles.content}>
            <Text>Tab 1 content</Text>
          </View>
          <View style={styles.content}>
            <Text>Tab 2 content</Text>
          </View>
          <View style={styles.content}>
            <Text>Tab 3 content</Text>
          </View>
        </ViewPagerAndroid>
      </View>
    )
  }

  _setPagePosition(e:Event) {
    const pagePosition = e.nativeEvent.position;
    this.setState({ pagePosition });
    // too bad ViewPagerAndroid doesn't support prop updates,
    // work around by forwarding changes using exposed API
    this.viewPager.setPage(pagePosition);
  }
}

const styles = StyleSheet.create({
  tabLayout: {
    flex: 1,
    backgroundColor: '#ddd'
  },
  viewPager: {
    height: 200,
  },
  content: {
    padding: 10,
  }
});
