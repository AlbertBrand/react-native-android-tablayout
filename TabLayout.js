import React, {
  Component,
  processColor,
  PropTypes,
  requireNativeComponent,
  View,
} from 'react-native';

const AndroidTabLayout = requireNativeComponent('TabLayout', TabLayout);

class TabLayout extends Component {
  static propTypes = {
    ...View.propTypes,
    onTabSelected: PropTypes.func,
    selectedTab: PropTypes.number,
    selectedTabIndicatorColor: PropTypes.string,
    tabMode: PropTypes.oneOf(['fixed', 'scrollable']),
  };

  onTabSelected = (e) => {
    if (this.props.onTabSelected) {
      this.props.onTabSelected(e);
    }
  };

  render() {
    return (
      <AndroidTabLayout
        {...this.props}
        onTabSelected={this.onTabSelected}
        selectedTabIndicatorColor={processColor(this.props.selectedTabIndicatorColor)}
        style={[{height: 48}, this.props.style]}/>
    );
  }
}

module.exports = TabLayout;
