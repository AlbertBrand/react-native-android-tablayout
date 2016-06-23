/* @flow */
import React, {
  Component,
  PropTypes
} from 'react';
import {
  ColorPropType,
  processColor,
  requireNativeComponent,
  View
} from 'react-native';

export default class TabLayout extends Component {
  static propTypes = {
    ...View.propTypes,
    onTabSelected: PropTypes.func,
    selectedTab: PropTypes.number,
    selectedTabIndicatorColor: ColorPropType,
    tabGravity: PropTypes.oneOf(['fill', 'center']),
    tabMode: PropTypes.oneOf(['fixed', 'scrollable'])
  };

  onTabSelected: Function = (e) => {
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

const AndroidTabLayout = requireNativeComponent('TabLayout', TabLayout);
