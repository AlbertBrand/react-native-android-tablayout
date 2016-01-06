const React = require('react-native');
const { requireNativeComponent, PropTypes, View } = React;

class Tab extends React.Component {
  static propTypes = {
    ...View.propTypes,
    name: PropTypes.string,
    onTabSelected: PropTypes.func,
  };

  _onTabSelected(e: Event) {
    if (this.props.onTabSelected) {
      this.props.onTabSelected(e);
    }
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
