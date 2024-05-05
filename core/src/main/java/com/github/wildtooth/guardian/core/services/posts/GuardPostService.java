package com.github.wildtooth.guardian.core.services.posts;

import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import com.github.wildtooth.guardian.core.services.Registrable;
import com.github.wildtooth.guardian.core.services.Service;

public class GuardPostService implements Service, Registrable {
  private final TranslationLogger logger;

  public GuardPostService(TranslationLogger logger) {
    this.logger = logger;
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
    this.logger.info("guardian.service.shared.initialize", this.logger.translate("guardian.service.posts.name"));
  }

  @Override
  public void shutdown() {
    this.logger.info("guardian.service.shared.shutdown", this.logger.translate("guardian.service.posts.name"));
  }
}
