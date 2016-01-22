import React from 'react-native';
const {
  PropTypes,
  requireNativeComponent,
  View,
  } = React;

class Tab extends React.Component {
  static propTypes = {
    ...View.propTypes,
    name: PropTypes.string,
    iconUri: PropTypes.string,
    iconResId: PropTypes.string,
    iconPackage: PropTypes.string,
    textColor: PropTypes.string,
    iconSize: PropTypes.number,
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

const AndroidTab = requireNativeComponent('Tab', Tab);

module.exports = Tab;
