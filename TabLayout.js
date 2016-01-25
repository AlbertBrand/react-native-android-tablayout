import React from 'react-native';
const {
  PropTypes,
  requireNativeComponent,
  UIManager,
  View,
  } = React;

class TabLayout extends React.Component {
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

var AndroidTabLayout = requireNativeComponent('TabLayout', TabLayout);

module.exports = TabLayout;
