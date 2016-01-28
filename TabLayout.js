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
    tabTextColor: PropTypes.string,
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
        tabTextColor={processColor(this.props.tabTextColor)}
        style={[{ height: 48 }, this.props.style]}
        onTabSelected={this._onTabSelected.bind(this)}/>
    );
  }
}

module.exports = TabLayout;
