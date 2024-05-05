package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.core.internatiolization.TranslationOutput;
import com.github.wildtooth.guardian.core.services.Registrable;
import com.github.wildtooth.guardian.core.services.Service;

public class GuardService extends TranslationOutput implements Service, Registrable {

  public GuardService() {
    super();
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
    info("");
  }

  @Override
  public void shutdown() {

  }
}
