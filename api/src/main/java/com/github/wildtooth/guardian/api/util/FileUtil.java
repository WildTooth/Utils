package com.github.wildtooth.guardian.api.util;

import net.labymod.api.Laby;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
  private FileUtil() {}

  public static File getGuardianFolder() {
    return new File(getGameDirectory() + File.separator + "labymod-neo" + File.separator + "addons" + File.separator + "guardian");
  }

  public static Path getGameDirectory() {
    return Laby.labyAPI().labyModLoader().getGameDirectory();
  }

  public static File getGuardianFile(String fileName) {
    return new File(getGuardianFolder(), fileName);
  }

  public static File getGuardianFile(String folderName, String fileName) {
    return new File(getGuardianFolder() + File.separator + folderName, fileName);
  }

  public static void writeToFile(File file, String content) {
    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }
      Files.write(file.toPath(), content.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
