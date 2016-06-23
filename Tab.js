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

export default class Tab extends Component {
  static propTypes = {
    ...View.propTypes,
    iconPackage: PropTypes.string,
    iconResId: PropTypes.string,
    iconSize: PropTypes.number,
    iconUri: PropTypes.string,
    name: PropTypes.string,
    onTabSelected: PropTypes.func,
    textColor: ColorPropType
  };

  onTabSelected: Function = (e) => {
    if (this.props.onTabSelected) {
      this.props.onTabSelected(e);
    }
  };

  render() {
    const {style, children, ...otherProps} = this.props;
    const wrappedChildren = children ?
      <View
        children={children}
        collapsable={false}
        pointerEvents={'none'}
        style={style}
      /> : null;

    return (
      <AndroidTab
        {...otherProps}
        children={wrappedChildren}
        collapsable={false}
        onTabSelected={this.onTabSelected}
        textColor={processColor(this.props.textColor)}
      />
    );
  }
}

const AndroidTab = requireNativeComponent('Tab', Tab);
