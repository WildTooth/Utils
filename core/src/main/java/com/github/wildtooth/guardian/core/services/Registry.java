package com.github.wildtooth.guardian.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Registry {
  private static Registry instance;
  private final List<Registrable> registrar;

  private Registry() {
    this.registrar = new ArrayList<>();
  }

  public static Registry getInstance() {
    if (instance == null) {
      instance = new Registry();
    }
    return instance;
  }

  public void register(Registrable registrable) {
    for (Registrable element : registrar) {
      if (element.getClass() == registrable.getClass()) {
        return;
      }
    }
    this.registrar.add(registrable);
    registrable.register();
  }

  public void unregister(Registrable registrable) {
    this.registrar.remove(registrable);
    registrable.unregister();
  }

  public Optional<Registrable> get(Class<? extends Registrable> clazz) {
    for (Registrable element : registrar) {
      if (element.getClass() == clazz) {
        return Optional.of(element);
      }
    }
    return Optional.empty();
  }
}
