package com.github.wildtooth.guardian.api.service;

/**
 * Service that can be initialized and shutdown.
 */
public interface Service {

  /**
   * Initializes the service.
   */
  void initialize();

  /**
   * Shuts down the service.
   */
  void shutdown();
}
