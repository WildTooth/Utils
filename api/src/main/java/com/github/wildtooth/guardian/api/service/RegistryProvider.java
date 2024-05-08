package com.github.wildtooth.guardian.api.service;

/**
 * Provides a {@link Registry} instance.
 * This class is used to provide a {@link Registry} instance to the rest of the application and by depending addons.
 */
public class RegistryProvider {
  private static Registry registry;

  public static void setRegistry(Registry registry) {
    RegistryProvider.registry = registry;
  }

  public static Registry getRegistry() {
    return registry;
  }
}
