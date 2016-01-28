import React, {
  Component,
  PropTypes,
  requireNativeComponent,
  View,
} from 'react-native';
import CustomPropTypes from './CustomPropTypes';

const AndroidTabLayout = requireNativeComponent('TabLayout', TabLayout);

class TabLayout extends Component {
  static propTypes = {
    ...View.propTypes,
    selectedTabIndicatorColor: CustomPropTypes.color,
    selectedTab: PropTypes.number,
    tabMode: PropTypes.oneOf(['fixed', 'scrollable']),
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
