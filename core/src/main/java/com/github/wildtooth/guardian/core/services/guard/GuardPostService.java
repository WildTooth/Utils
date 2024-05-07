package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;

public class GuardPostService implements Service, Registrable {
  private final TranslationLogger logger;

  public GuardPostService(TranslationLogger logger) {
    this.logger = logger;
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
    this.logger.info("guardian.service.shared.initialize", this.logger.translate("guardian.service.posts.name"));
  }

  @Override
  public void shutdown() {
    this.logger.info("guardian.service.shared.shutdown", this.logger.translate("guardian.service.posts.name"));
  }
}
