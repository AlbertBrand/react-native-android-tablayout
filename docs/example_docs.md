# Example documentation

## Basic setup

```js
// ...

import { Tab, TabLayout } from 'react-native-android-tablayout'

// ...

class MyTabView extends React.Component {

  render() {
    return (
      <View>
        <TabLayout>
          <Tab name="Tab 1" onTabSelected={()=>{ console.log('Tab 1 pressed') }}/>
          <Tab name="Tab 2" onTabSelected={()=>{ console.log('Tab 2 pressed') }}/>
          <Tab name="Tab 3" onTabSelected={()=>{ console.log('Tab 3 pressed') }}/>
        </TabLayout>
      </View>
    )
  }

  // ...
}

// ...

```

## With icons on top

```js
// ...

import { Tab, TabLayout } from 'react-native-android-tablayout'

// ...

class MyTabIconView extends React.Component {

  render() {
    return (
      <View>
        <TabLayout 
          style={{height: 72}}>         // design standard for icons on top
          <Tab 
            name="Tab 1" 
            iconSize="24"                                                  // design standard for icons on top
            iconUri="file:///data/data/com.example/cache/abcdef_24@3x.png" // only file:// support for local paths
            />
          <Tab 
            name="Tab 2"
            iconSize="24"
            iconResId="my_custom_icon"  // points to src/res/drawable*/my_custom_icon.*
            />
          <Tab 
            name="Tab 3"
            iconSize="24"
            iconPackage="android"       // specify if not in own package; use 'android' for platform packaged resources
            iconResId="emo_im_cool"     // see [Android Drawables](http://androiddrawables.com)
            />
        </TabLayout>
      </View>
    )
  }

  // ...
}

// ...

```

## Selected tab state

```js
// ...

import { Tab, TabLayout } from 'react-native-android-tablayout'

// ...

const MyStatefulTabView = React.createClass({

  getInitialState() {
    return {
      pagePosition: 0,                          // create position state, position is 0-based
    };
  },

  render() {
    return (
      <View>
        <TabLayout
          selectedTab={this.state.pagePosition} // wire up state to component
          onTabSelected={(e:Event) => {         // called when native component changes state
            this.setState({ pagePosition: e.nativeEvent.position }); 
          }}>
          <Tab name="Tab 1"/>
          <Tab name="Tab 2"/>
          <Tab name="Tab 3"/>
        </TabLayout>
        <TouchableOpacity onPress={() => { this.setState({ pagePosition: 1 }); }}>
          <Text>Switch to second tab</Text>     // change of state propagates to component
        </TouchableOpacity>
      </View>
    )
  }

  // ...
});

// ...

```

## Together with [ViewPagerAndroid](https://facebook.github.io/react-native/docs/viewpagerandroid.html)

```js
// ...

import { Tab, TabLayout } from 'react-native-android-tablayout'

// ...

const MyViewPagerTabView = React.createClass({

  getInitialState() {
    return {
      pagePosition: 0,
    };
  },

  render() {
    return (
      <View>
        <TabLayout
          selectedTab={this.state.pagePosition}
          onTabSelected={this.setPagePosition}>
          <Tab name="Tab 1"/>
          <Tab name="Tab 2"/>
          <Tab name="Tab 3"/>
        </TabLayout>
        <ViewPagerAndroid
          style={{flex: 1}}
          ref={viewPager => { this.viewPager = viewPager; }}
          onPageSelected={this.setPagePosition}>
          <View>
            <Text>Tab 1 content</Text>
          </View>
          <View>
            <Text>Tab 2 content</Text>
          </View>
          <View>
            <Text>Tab 3 content</Text>
          </View>
        </ViewPagerAndroid>
      </View>
    )
  },
  
  setPagePosition(event:Event) {
    const pagePosition = e.nativeEvent.position;
    this.setState({ pagePosition });
    // too bad ViewPagerAndroid doesn't support prop updates,
    // work around by forwarding changes using exposed API
    this.viewPager.setPage(pagePosition);
  }

  // ...
});

// ...

```
