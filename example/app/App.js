import React, {
  Component,
  Dimensions,
  DrawerLayoutAndroid,
  StyleSheet,
  ToolbarAndroid,
  View
} from 'react-native';
import { ROUTES } from './Routes';
import DrawerMenuList from './DrawerMenuList';
import SimpleTabLayout from './SimpleTabLayout';

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      route: ROUTES[0]
    };
  }

  render() {
    return (
      <DrawerLayoutAndroid
        drawerPosition={DrawerLayoutAndroid.positions.Left}
        drawerWidth={Dimensions.get('window').width - 80}
        keyboardDismissMode="on-drag"
        ref={(drawer) => { this.drawer = drawer; }}
        renderNavigationView={this._renderNavigationView.bind(this)}>
        <ToolbarAndroid
          navIcon={require('./img/hamburger_menu.png')}
          onIconClicked={() => this.drawer.openDrawer()}
          style={styles.navBar}
          subtitle={this.state.route.title}
          title="TabLayout examples"/>
        {this._renderScene()}
      </DrawerLayoutAndroid>
    );
  }

  _renderNavigationView() {
    return (
      <DrawerMenuList
        routes={ROUTES}
        onPressRow={this._onSelectMenuItem.bind(this)}
      />
    );
  }

  _renderScene() {
    const Component = this.state.route.component;
    return (
      <View style={styles.container}>
        <Component/>
      </View>
    );
  }

  _onSelectMenuItem(route) {
    this.drawer.closeDrawer();
    this.setState({ route: route });
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  navBar: {
    backgroundColor: '#ddd',
    height: 56,
  },
  navBarText: {
    fontSize: 16,
    fontWeight: '500',
    marginVertical: 16,
  },
});
