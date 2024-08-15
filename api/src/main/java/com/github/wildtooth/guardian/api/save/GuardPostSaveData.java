package com.github.wildtooth.guardian.api.save;

import com.github.wildtooth.guardian.api.gson.SpecializedGsonProvider;
import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class GuardPostSaveData implements SaveData {
  private final Map<GuardPost, Long> lastGuardPostVisit;
  private final long saveTime;

  private GuardPostSaveData(Map<GuardPost, Long> lastGuardPostVisit, long saveTime) {
    this.lastGuardPostVisit = lastGuardPostVisit;
    this.saveTime = saveTime;
  }

  public Map<GuardPost, Long> getLastGuardPostVisit() {
    return this.lastGuardPostVisit;
  }

  @Override
  public long getSaveTime() {
    return this.saveTime;
  }

  @Override
  public void executeSave() {
    Gson gson = new Gson();
    String json = gson.toJson(this);

    GuardPostService guardPostService =
        RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
    if (guardPostService == null) {
      return;
    }

    File file = guardPostService.getSavePath();
    FileUtil.writeToFile(file, json);
  }

  public static GuardPostSaveData loadFromSaveData(File file) throws FileNotFoundException {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(GuardPost.class, SpecializedGsonProvider.getSpecializedGson().getGuardPostDeserializer());
    Gson gson = gsonBuilder.create();
    return gson.fromJson(new FileReader(file), GuardPostSaveData.class);
  }

  public static GuardPostSaveData create(GuardPostService guardPostService) {
    Map<GuardPost, Long> lastGuardPostVisit = guardPostService.getGuardPostTimeMap();
    Map<GuardPost, Long> lastGuardPostVisitCopy = new HashMap<>(Map.copyOf(lastGuardPostVisit));
    for (GuardPost guardPost : lastGuardPostVisit.keySet()) {
      if (guardPostService.getCooldownTime(guardPost) == 0) {
        lastGuardPostVisitCopy.remove(guardPost);
      }
    }
    return new GuardPostSaveData(lastGuardPostVisitCopy, System.currentTimeMillis());
  }

  @Deprecated(forRemoval = true)
  public Map<GuardPost, Long> handleUnload(GuardPostService guardPostService) {
    Map<GuardPost, Long> lastGuardPostVisitCopy = new HashMap<>(Map.copyOf(this.lastGuardPostVisit));
    for (GuardPost guardPost : this.lastGuardPostVisit.keySet()) {
      if (guardPostService.getCooldownTime(guardPost) == 0) {
        lastGuardPostVisitCopy.remove(guardPost);
      }
    }
    return lastGuardPostVisitCopy;
  }
}
