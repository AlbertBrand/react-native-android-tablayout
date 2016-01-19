package com.xebia.reactnative;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class InstallerUtil {
  static void writeToDisk(String path, String content) throws IOException {
    Files.write(Paths.get(path), content.getBytes(), StandardOpenOption.CREATE);
  }
}
