package com.github.wildtooth.guardian.api.gson;

public class SpecializedGsonProvider {
  private static SpecializedGson specializedGson;

  public static void setSpecializedGson(SpecializedGson specializedGson) {
    SpecializedGsonProvider.specializedGson = specializedGson;
  }

  public static SpecializedGson getSpecializedGson() {
    return specializedGson;
  }
}
