import React, {
  Component,
  ListView,
  StyleSheet,
  Text,
  TouchableHighlight,
  View,
} from 'react-native';

const ds = new ListView.DataSource({
  rowHasChanged: (r1, r2) => r1 !== r2
});

export default class DrawerMenuList extends Component {
  constructor(props:any) {
    super(props);
    this.state = {
      dataSource: ds.cloneWithRows(this.props.routes),
    };
  }

  render() {
    return (
      <View style={styles.listContainer}>
        <ListView
          style={styles.list}
          dataSource={this.state.dataSource}
          renderRow={this._renderRow.bind(this)}
          keyboardShouldPersistTaps={true}
          automaticallyAdjustContentInsets={false}
          keyboardDismissMode="on-drag"
        />
      </View>
    );
  }

  _renderRow(route:any, i:number) {
    return (
      <View key={i}>
        <TouchableHighlight onPress={() => this.props.onPressRow(route)}>
          <View style={styles.row}>
            <Text style={styles.rowTitleText}>
              {route.title}
            </Text>
          </View>
        </TouchableHighlight>
        <View style={styles.separator}/>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  listContainer: {
    flex: 1,
  },
  list: {
    backgroundColor: '#eeeeee',
  },
  row: {
    backgroundColor: 'white',
    paddingHorizontal: 15,
    paddingVertical: 8,
    flexDirection: 'row',
    justifyContent: 'flex-start',
  },
  rowTitleText: {
    fontSize: 17,
    fontWeight: '500',
  },
  separator: {
    height: 1,
    backgroundColor: '#bbbbbb',
  },
});
