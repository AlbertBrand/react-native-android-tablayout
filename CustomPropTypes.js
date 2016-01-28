module.exports = {
  color: function(props, propName, componentName) {
    if (!/^#([\da-f]{2})?[\da-f]{6}$/i.test(props[propName])) {
      return new Error('Color not in #rrggbb or #aarrggbb format');
    }
  }
}
