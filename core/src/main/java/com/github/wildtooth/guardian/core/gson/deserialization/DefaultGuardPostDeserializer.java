package com.github.wildtooth.guardian.core.gson.deserialization;

import com.github.wildtooth.guardian.api.gson.deserialization.GuardPostDeserializer;
import com.github.wildtooth.guardian.core.guard.DefaultGuardPost;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class DefaultGuardPostDeserializer implements GuardPostDeserializer {

  @Override
  public DefaultGuardPost deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String jsonString = json.getAsString();
    jsonString = jsonString.replace("DefaultGuardPost{", "").replace("}", "");

    String[] parts = jsonString.split(",(?![^\\[\\\"]*[\\]\\\"])");

    String prisonSector = parts[0].split("=")[1].replace("'", "");
    int numericalIdentifier = Integer.parseInt(parts[1].split("=")[1]);
    String displayName = parts[2].split("=")[1].replace("'", "");
    int personalCooldown = Integer.parseInt(parts[3].split("=")[1]);
    int x = Integer.parseInt(parts[4].split("=")[1]);
    int y = Integer.parseInt(parts[5].split("=")[1]);
    int z = Integer.parseInt(parts[6].split("=")[1]);

    return new DefaultGuardPost(prisonSector, numericalIdentifier, displayName, personalCooldown, x, y, z);
  }
}
