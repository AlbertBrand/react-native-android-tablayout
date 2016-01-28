import React, {
  Component,
  PropTypes,
  requireNativeComponent,
  View,
} from 'react-native';
import CustomPropTypes from './CustomPropTypes';

const AndroidTab = requireNativeComponent('Tab', Tab);

class Tab extends Component {
  static propTypes = {
    ...View.propTypes,
    name: PropTypes.string,
    iconUri: PropTypes.string,
    iconResId: PropTypes.string,
    iconPackage: PropTypes.string,
    iconSize: PropTypes.number,
    textColor: CustomPropTypes.color,
    onTabSelected: PropTypes.func,
  };

  _onTabSelected(e:Event) {
    this.props.onTabSelected && this.props.onTabSelected(e);
  }

  render() {
    return (
      <AndroidTab
        {...this.props}
        onTabSelected={this._onTabSelected.bind(this)}
        collapsable={false}/>
    );
  }
}

module.exports = Tab;
