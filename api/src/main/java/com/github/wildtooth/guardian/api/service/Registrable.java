package com.github.wildtooth.guardian.api.service;

/**
 * Retrievable object that can be registered in a {@link Registry}.
 */
public interface Registrable {

  /**
   * Called when the object is registered.
   */
  void onRegister();

  /**
   * Called when the object is unregistered.
   */
  void onUnregister();
}
