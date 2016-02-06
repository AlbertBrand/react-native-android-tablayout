import React, {
  Component,
  processColor,
  PropTypes,
  requireNativeComponent,
  View,
} from 'react-native';

const AndroidTab = requireNativeComponent('Tab', Tab);

class Tab extends Component {
  static propTypes = {
    ...View.propTypes,
    iconPackage: PropTypes.string,
    iconResId: PropTypes.string,
    iconSize: PropTypes.number,
    iconUri: PropTypes.string,
    name: PropTypes.string,
    onTabSelected: PropTypes.func,
    textColor: PropTypes.string,
  };

  onTabSelected = (e) => {
    if (this.props.onTabSelected) {
      this.props.onTabSelected(e);
    }
  };

  render() {
    return (
      <AndroidTab
        {...this.props}
        collapsable={false}
        onTabSelected={this.onTabSelected}
        textColor={processColor(this.props.textColor)}/>
    );
  }
}

module.exports = Tab;
