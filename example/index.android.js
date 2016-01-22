import React, { AppRegistry, Component } from 'react-native';
import App from './app/App';

class TabLayoutExample extends Component {
  render() {
    return (
      <App/>
    );
  }
}

AppRegistry.registerComponent('tabLayoutExample', () => TabLayoutExample);
