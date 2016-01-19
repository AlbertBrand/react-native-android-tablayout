package com.xebia.reactnative;

class Installer {
  private GradleModifier groovyModifier = new GradleModifier();
  private JavaModifier javaModifier = new JavaModifier();

  public static void main(String[] args) throws Exception {
    new Installer().run();
  }

  private void run() throws Exception {
    groovyModifier.updateSettings();
    groovyModifier.updateAppBuild();
    javaModifier.updateMainActivity();
  }
}
