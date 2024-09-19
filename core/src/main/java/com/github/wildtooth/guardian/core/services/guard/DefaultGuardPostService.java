package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.api.ConstantsProvider;
import com.github.wildtooth.guardian.api.guardpost.GuardPost;
import com.github.wildtooth.guardian.api.refrences.LocationHelper;
import com.github.wildtooth.guardian.api.save.GuardPostSaveData;
import com.github.wildtooth.guardian.api.save.SaveData;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.util.CoordinateUtil;
import com.github.wildtooth.guardian.api.util.FileUtil;
import com.github.wildtooth.guardian.core.guard.DefaultGuardPost;
import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import com.github.wildtooth.guardian.core.util.DataOutput;
import net.labymod.api.util.Pair;
import net.labymod.api.util.Triple;
import net.labymod.api.util.logging.Logging;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultGuardPostService implements GuardPostService {
  private final TranslationLogger logger;
  private final Map<String, GuardPost> identifierGuardPostMap;
  private final Map<GuardPost, Long> guardPostLastUpdateMap;
  private GuardPost lastInteractedGuardPost;

  public DefaultGuardPostService() {
    this.logger = new TranslationLogger(Logging.getLogger());
    this.identifierGuardPostMap = new HashMap<>();
    this.guardPostLastUpdateMap = new HashMap<>();
  }

  @Override
  public void onRegister() {
    initialize();
  }

  @Override
  public void onUnregister() {
    shutdown();
  }

  @Override
  public void initialize() {
    ArrayList<String[]> guardPostData = DataOutput.csv(ConstantsProvider.getConstants().data().guardPostData());
    for (String[] guardPost : guardPostData) {
      addGuardPost(createGuardPost(guardPost[0], Integer.parseInt(guardPost[1]), guardPost[2], Integer.parseInt(guardPost[3]), Integer.parseInt(guardPost[4]), Integer.parseInt(guardPost[5]), Integer.parseInt(guardPost[6])));
    }
    try {
      GuardPostSaveData guardPostSaveData = GuardPostSaveData.loadFromSaveData(getSavePath());
      loadSaveData(guardPostSaveData);
    } catch (Exception e) {
      this.logger.warn("guardian.service.posts.saveData.loadFailed", e.getMessage());
    }
    this.logger.info("guardian.service.shared.initialize", this.logger.translate("guardian.service.posts.name"));
  }

  @Override
  public void shutdown() {
    save();
    this.logger.info("guardian.service.shared.shutdown", this.logger.translate("guardian.service.posts.name"));
  }

  @Override
  public Optional<? extends GuardPost> getGuardPost(String identifier) {
    return Optional.ofNullable(this.identifierGuardPostMap.get(identifier));
  }

  @Override
  public long getCooldownTime(GuardPost guardPost) {
    final long currentTime = System.currentTimeMillis();
    final long lastTaken = this.guardPostLastUpdateMap.getOrDefault(guardPost, 0L);
    final long cooldown = guardPost.getPersonalCooldown() * 60000L;

    return Math.max(0, cooldown - (currentTime - lastTaken));
  }

  public long getCooldownTime(GuardPost guardPost, long lastTaken) {
    final long currentTime = System.currentTimeMillis();
    final long cooldown = guardPost.getPersonalCooldown() * 60000L;

    return Math.max(0, cooldown - (currentTime - lastTaken));
  }

  @Override
  public void addGuardPost(GuardPost guardPost) {
    this.identifierGuardPostMap.put(guardPost.combinedIdentifier(), guardPost);
  }

  @Override
  public void removeGuardPost(String identifier) {
    this.identifierGuardPostMap.remove(identifier);
  }

  @Override
  public boolean hasGuardPost(GuardPost guardPost) {
    return this.identifierGuardPostMap.containsValue(guardPost);
  }

  @Override
  public boolean hasGuardPost(String identifier) {
    return this.identifierGuardPostMap.containsKey(identifier);
  }

  @Override
  public void putGuardPostOnCooldown(GuardPost guardPost) {
    this.guardPostLastUpdateMap.put(guardPost, System.currentTimeMillis());
  }

  @Override
  public void putGuardPostOnCooldown(GuardPost guardPost, long lastTaken) {
    this.guardPostLastUpdateMap.put(guardPost, lastTaken);
  }

  @Override
  public boolean isGuardPostOnCooldown(GuardPost guardPost) {
    return getCooldownTime(guardPost) > 0;
  }

  @Override
  public Map<GuardPost, Long> getGuardPostTimeMap() {
    return this.guardPostLastUpdateMap;
  }

  @Override
  public GuardPost getGuardPostByLocation(Triple<Integer, Integer, Integer> location) {
    for (GuardPost guardPost : this.identifierGuardPostMap.values()) {
      if (guardPost.getCoordinates().equals(location)) {
        return guardPost;
      }
    }
    return null;
  }

  @Override
  public GuardPost getNearestGuardPost() {
    LocationHelper locationHelper = RegistryProvider.getRegistry().get(LocationHelper.class).orElse(null);
    if (locationHelper == null) {
      throw new IllegalStateException("LocationHelper is not registered");
    }
    Pair<Double, GuardPost> nearestGuardPost = Pair.of(10.0, null);
    for (GuardPost guardPost : this.identifierGuardPostMap.values()) {
      double distance = CoordinateUtil.distance(locationHelper.getIntCoordinates(5), guardPost.getCoordinates());
      if (distance < nearestGuardPost.getFirst()) {
        nearestGuardPost = Pair.of(distance, guardPost);
      }
    }
    return nearestGuardPost.getSecond();
  }

  @Override
  public Optional<GuardPost> getLastInteractedGuardPost() {
    return lastInteractedGuardPost == null ? Optional.empty() : Optional.of(lastInteractedGuardPost);
  }

  @Override
  public void setLastInteractedGuardPost(GuardPost guardPost) {
    this.lastInteractedGuardPost = guardPost;
  }

  @Override
  public GuardPost createGuardPost(String prisonSector, int numericalIdentifier, String displayName,
      int personalCooldown, int x, int y, int z) {
    return new DefaultGuardPost(prisonSector, numericalIdentifier, displayName, personalCooldown, x, y, z);
  }

  @Override
  public void loadSaveData(SaveData saveData) {
    if (saveData instanceof GuardPostSaveData guardPostSaveData) {
      Map<GuardPost, Long> lastGuardPostVisit = guardPostSaveData.getLastGuardPostVisit();
      for (GuardPost guardPost : lastGuardPostVisit.keySet()) {
        if (!this.identifierGuardPostMap.containsKey(guardPost.combinedIdentifier())) {
          continue;
        }
        if (guardPostLastUpdateMap.containsKey(guardPost)) {
          continue;
        }
        if (getCooldownTime(guardPost, lastGuardPostVisit.get(guardPost)) == 0) {
          continue;
        }
        this.guardPostLastUpdateMap.put(guardPost, lastGuardPostVisit.get(guardPost));
      }
    } else {
      throw new IllegalArgumentException("SaveData is not of type GuardPostSaveData");
    }
  }

  @Override
  public void save() {
    GuardPostSaveData guardPostSaveData = GuardPostSaveData.create(this);
    guardPostSaveData.executeSave();
  }

  @Override
  public File getSavePath() {
    File file = FileUtil.getGuardianFile("saves", "guard_posts.json");
    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }
    } catch (Exception e) {
      this.logger.warn("guardian.service.posts.saveData.createFailed", e.getMessage());
    }
    return file;
  }
}
