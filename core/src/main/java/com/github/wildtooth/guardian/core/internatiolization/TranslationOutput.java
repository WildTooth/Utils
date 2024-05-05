package com.github.wildtooth.guardian.core.internatiolization;

import net.labymod.api.util.I18n;
import net.labymod.api.util.logging.Logging;

public abstract class TranslationOutput {
  private final Logging logger;

  protected TranslationOutput() {
    this.logger = Logging.getLogger();
  }

  protected void info(String key, Object... args) {
    this.logger.info(translate(key, args));
  }

  protected void warn(String key, Object... args) {
    this.logger.warn(translate(key, args));
  }

  protected void error(String key, Object... args) {
    this.logger.error(translate(key, args));
  }

  protected void debug(String key, Object... args) {
    this.logger.debug(translate(key, args));
  }

  protected String translate(String key, Object... args) {
    return I18n.translate(key, args);
  }
}
