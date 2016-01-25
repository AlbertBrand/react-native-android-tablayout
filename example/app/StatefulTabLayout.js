import React, {
  Component,
  StyleSheet,
  Text,
  TouchableOpacity,
  View
} from 'react-native';
import { Tab, TabLayout } from 'react-native-android-tablayout'

export default class StatefulTabLayout extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pagePosition: 2, // start on third tab
    };
  }

  render() {
    return (
      <View>
        <TabLayout
          style={styles.tabLayout}
          selectedTab={this.state.pagePosition}
          onTabSelected={(e:Event) => {
            this.setState({ pagePosition: e.nativeEvent.position });
          }}>
          <Tab name="Tab 1"/>
          <Tab name="Tab 2"/>
          <Tab name="Tab 3"/>
        </TabLayout>
        <TouchableOpacity
          style={styles.button}
          onPress={() => {
            this.setState({ pagePosition: 1 });
          }}>
          <Text>Switch to second tab</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  tabLayout: {
    backgroundColor: '#ddd'
  },
  button: {
    borderWidth: 1,
    borderColor: '#888',
    margin: 10,
    padding: 10,
  },
});
