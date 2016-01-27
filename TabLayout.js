import React, {
  Component,
  PropTypes,
  requireNativeComponent,
  View,
} from 'react-native';

const AndroidTabLayout = requireNativeComponent('TabLayout', TabLayout);

class TabLayout extends Component {
  static propTypes = {
    ...View.propTypes,
    selectedTabIndicatorColor: PropTypes.string,
    selectedTab: PropTypes.number,
    tabMode: PropTypes.string,
    onTabSelected: PropTypes.func,
  };

  _onTabSelected(e:Event) {
    this.props.onTabSelected && this.props.onTabSelected(e);
  }

  render() {
    return (
      <AndroidTabLayout
        {...this.props}
        style={[{ height: 48 }, this.props.style]}
        onTabSelected={this._onTabSelected.bind(this)}/>
    );
  }
}

module.exports = TabLayout;
