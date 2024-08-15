package com.github.wildtooth.guardian.api.gson;

import com.github.wildtooth.guardian.api.gson.deserialization.GuardPostDeserializer;

public interface SpecializedGson {
  GuardPostDeserializer getGuardPostDeserializer();
}
