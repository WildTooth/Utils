package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.api.guard.Guard;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import com.github.wildtooth.guardian.core.guard.DefaultGuard;
import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.github.wildtooth.guardian.core.util.DataOutput;
import net.labymod.api.util.logging.Logging;

public class DefaultGuardService implements GuardService {
  private final TranslationLogger logger;
  private final Map<UUID, Guard> uuidGuardMap;

  public DefaultGuardService() {
    this.logger = new TranslationLogger(Logging.getLogger());
    this.uuidGuardMap = new HashMap<>();
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
    ArrayList<String[]> guards = DataOutput.csv("https://raw.githubusercontent.com/FreakyVille-Trademarket/Public-Freakyville-Datahub/main/vagter/vagter.csv");
    guards.forEach(guard -> {
      UUID uuid = UUID.fromString(guard[0]);
      String name = guard[1];
      addGuard(new DefaultGuard(uuid, name));
    });
    this.logger.info("guardian.service.shared.initialize", this.logger.translate("guardian.service.guard.name"));
  }

  @Override
  public void shutdown() {
    this.logger.info("guardian.service.shared.shutdown", this.logger.translate("guardian.service.guard.name"));
  }

  @Override
  public Optional<Guard> getGuard(UUID uuid) {
    return Optional.ofNullable(this.uuidGuardMap.get(uuid));
  }

  @Override
  public <G extends Guard> void addGuard(G guard) {
    this.uuidGuardMap.put(guard.getUuid(), guard);
  }

  @Override
  public void removeGuard(UUID uuid) {
    this.uuidGuardMap.remove(uuid);
  }

  @Override
  public boolean hasGuard(UUID uuid) {
    return this.uuidGuardMap.containsKey(uuid);
  }

  @Override
  public <G extends Guard> boolean hasGuard(G guard) {
    return this.uuidGuardMap.containsValue(guard);
  }
}
