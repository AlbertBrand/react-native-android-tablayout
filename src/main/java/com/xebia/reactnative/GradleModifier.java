package com.xebia.reactnative;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleModifier {
  public static final String SETTINGS_GRADLE_PATH = "android/settings.gradle";
  public static final String APP_BUILD_GRADLE_PATH = "android/app/build.gradle";

  public static final String TABLAYOUT_INCLUDE_EXPR = "include ':react-native-android-tablayout'";
  public static final String TABLAYOUT_PROJECTDIR_EXPR = "project(':react-native-android-tablayout').projectDir = new File('../node_modules/react-native-android-tablayout/android')";

  public static final String TABLAYOUT_DEPENDENCY_EXPR = "compile project(':react-native-android-tablayout')";
  public static final Pattern DEPENDENCIES_PATTERN = Pattern.compile(
      "(dependencies\\s*\\{)" + // $1
          "([^}]*)" +           // $2
          "}"
  );

  public void updateSettings() throws Exception {
    String content = new String(Files.readAllBytes(Paths.get(SETTINGS_GRADLE_PATH)));
    String newContent = null;

    if (!content.contains(TABLAYOUT_INCLUDE_EXPR)) {
      newContent = content.concat("\n\n" + TABLAYOUT_INCLUDE_EXPR + "\n" + TABLAYOUT_PROJECTDIR_EXPR + "\n");
    }

    if (newContent != null) {
      InstallerUtil.writeToDisk(SETTINGS_GRADLE_PATH, newContent);
      System.out.println("Updated includes in " + SETTINGS_GRADLE_PATH);
    } else {
      System.out.println("Includes were not updated, is the file " + SETTINGS_GRADLE_PATH + " updated already?");
    }
  }

  public void updateAppBuild() throws Exception {
    String content = new String(Files.readAllBytes(Paths.get(APP_BUILD_GRADLE_PATH)));
    String newContent = null;

    Matcher m = DEPENDENCIES_PATTERN.matcher(content);
    if (m.find() && !m.group(2).contains(TABLAYOUT_DEPENDENCY_EXPR)) {
      newContent = m.replaceFirst("$1$2    " + TABLAYOUT_DEPENDENCY_EXPR + "\n}");
    }

    if (newContent != null) {
      InstallerUtil.writeToDisk(APP_BUILD_GRADLE_PATH, newContent);
      System.out.println("Updated dependencies in " + APP_BUILD_GRADLE_PATH);
    } else {
      System.out.println("Dependencies were not updated, is the file " + APP_BUILD_GRADLE_PATH + " updated already?");
    }
  }

}
