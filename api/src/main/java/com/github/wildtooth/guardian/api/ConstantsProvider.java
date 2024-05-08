package com.github.wildtooth.guardian.api;

public class ConstantsProvider {
  private static Constants constants;

  public static void setConstants(Constants constants) {
    ConstantsProvider.constants = constants;
  }

  public static Constants getConstants() {
    return constants;
  }
}
