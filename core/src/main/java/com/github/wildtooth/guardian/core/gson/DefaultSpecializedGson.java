package com.github.wildtooth.guardian.core.gson;

import com.github.wildtooth.guardian.api.gson.deserialization.GuardPostDeserializer;
import com.github.wildtooth.guardian.api.gson.SpecializedGson;
import com.github.wildtooth.guardian.core.gson.deserialization.DefaultGuardPostDeserializer;

public class DefaultSpecializedGson implements SpecializedGson {
  @Override
  public GuardPostDeserializer getGuardPostDeserializer() {
    return new DefaultGuardPostDeserializer();
  }
}
