package com.github.wildtooth.guardian.core.services;

import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Registry;
import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import net.labymod.api.util.logging.Logging;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultRegistry implements Registry {
  private final TranslationLogger logger;
  private final Map<Class<? extends Registrable>, Registrable> registrar;

  public DefaultRegistry() {
    this.logger = new TranslationLogger(Logging.getLogger());
    this.registrar = new HashMap<>();
  }

  @Override
  public void register(Class<? extends Registrable> clazz, Registrable registrable,
      boolean override) {
    if (!registrar.containsKey(clazz)) {
      this.registrar.put(clazz, registrable);
      registrable.onRegister();
      this.logger.info("guardian.registry.logOutPut.registered", registrable.getClass().getName(), clazz.getName());
    } else if (override) {
      this.logger.warn("guardian.registry.logOutPut.performingOverride", registrable.getClass().getName(), clazz.getName());
      this.unregister(registrar.get(clazz));
      this.register(clazz, registrable, false);
    } else {
      this.logger.warn("guardian.registry.logOutPut.alreadyRegistered", registrable.getClass().getName(), clazz.getName());
    }
  }

  @Override
  public void unregister(Registrable registrable) {
    Class<? extends Registrable> clazz = registrable.getClass();
    this.registrar.remove(clazz);
    registrable.onUnregister();
    this.logger.info("guardian.registry.logOutPut.unregistered", registrable.getClass().getName(), clazz.getName());
  }

  @Override
  public <T extends Registrable> Optional<T> get(Class<T> clazz) {
    return Optional.ofNullable(clazz.cast(registrar.get(clazz)));
  }
}
