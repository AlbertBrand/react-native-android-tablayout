const React = require('react-native');
const { requireNativeComponent, View } = React;

class TabLayout extends React.Component {
  static propTypes = {
    ...View.propTypes,
  };

  render() {
    return (
      <AndroidTabLayout
        style={[{ height: 48 }, this.props.style]}>
        {this.props.children}
      </AndroidTabLayout>
    );
  }
}

var AndroidTabLayout = requireNativeComponent('TabLayout', TabLayout);

module.exports = TabLayout;
