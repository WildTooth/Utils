package com.github.wildtooth.guardian.core.services;

import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Registry;
import com.github.wildtooth.guardian.api.service.Service;
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

  public void register(Registrable registrable) {
    Class<? extends Registrable> clazz = registrable.getClass();
    if (!registrar.containsKey(clazz)) {
      this.registrar.put(clazz, registrable);
      registrable.onRegister();
    }
  }

  public void unregister(Registrable registrable) {
    Class<? extends Registrable> clazz = registrable.getClass();
    this.registrar.remove(clazz);
    registrable.onUnregister();
  }

  public <T extends Registrable> Optional<T> get(Class<T> clazz) {
    return Optional.ofNullable(clazz.cast(registrar.get(clazz)));
  }
}
