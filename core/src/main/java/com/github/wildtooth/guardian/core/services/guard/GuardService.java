package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.core.guard.DefaultGuard;
import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;
import net.labymod.api.util.logging.Logging;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GuardService implements Service, Registrable {
  private final TranslationLogger logger;
  private final Map<UUID, DefaultGuard> uuidGuardMap;

  public GuardService() {
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
    this.logger.info("guardian.service.shared.initialize", this.logger.translate("guardian.service.guard.name"));
  }

  @Override
  public void shutdown() {
    this.logger.info("guardian.service.shared.shutdown", this.logger.translate("guardian.service.guard.name"));
  }

  public Optional<DefaultGuard> getGuard(UUID uuid) {
    return Optional.ofNullable(this.uuidGuardMap.get(uuid));
  }

  public void addGuard(DefaultGuard guard) {
    this.uuidGuardMap.put(guard.getUuid(), guard);
  }

  public void removeGuard(UUID uuid) {
    this.uuidGuardMap.remove(uuid);
  }

  public boolean hasGuard(UUID uuid) {
    return this.uuidGuardMap.containsKey(uuid);
  }

  public boolean hasGuard(DefaultGuard guard) {
    return this.uuidGuardMap.containsValue(guard);
  }
}
