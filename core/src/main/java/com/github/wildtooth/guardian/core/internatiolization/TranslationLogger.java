package com.github.wildtooth.guardian.core.internatiolization;

import net.labymod.api.util.I18n;
import net.labymod.api.util.logging.Logging;

public final class TranslationLogger {
  private final Logging logger;

  public TranslationLogger(Logging logging) {
    this.logger = logging;
  }

  public void info(String key, Object... args) {
    this.logger.info(translate(key, args));
  }

  public void warn(String key, Object... args) {
    this.logger.warn(translate(key, args));
  }

  public void error(String key, Object... args) {
    this.logger.error(translate(key, args));
  }

  public void debug(String key, Object... args) {
    this.logger.debug(translate(key, args));
  }

  public String translate(String key, Object... args) {
    return I18n.translate(key, args);
  }
}
