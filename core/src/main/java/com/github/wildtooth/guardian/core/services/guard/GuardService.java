package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import com.github.wildtooth.guardian.core.services.Registrable;
import com.github.wildtooth.guardian.core.services.Service;
import net.labymod.api.util.logging.Logging;

public class GuardService implements Service, Registrable {
  private final TranslationLogger logger;

  public GuardService() {
    this.logger = new TranslationLogger(Logging.getLogger());
  }

  @Override
  public void register() {
    initialize();
  }

  @Override
  public void unregister() {
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
}
