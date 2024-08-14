package com.github.wildtooth.guardian.api.service;

import java.util.Optional;

/**
 * Interface for a registry that can be used to register and unregister {@link Registrable} objects.
 */
public interface Registry {

  /**
   * Registers a {@link Registrable} object.
   * @param clazz The class of the object to register.
   * @param registrable The object to register.
   * @param override Whether to override the registration if the class is already registered.
   */
  void register(Class<? extends Registrable> clazz, Registrable registrable, boolean override);

  /**
   * Unregisters a {@link Registrable} object.
   * @param registrable The object to unregister.
   */
  void unregister(Registrable registrable);

  /**
   * Unregisters a {@link Registrable} of the specified class.
   * @param clazz The class of the object to unregister.
   */
  void unregister(Class<? extends Registrable> clazz);

  /**
   * Gets a {@link Registrable} object of the specified class.
   * @param clazz The class of the object to get.
   * @param <T> The type of the object to get.
   * @return An {@link Optional} containing the object if it exists, otherwise an empty {@link Optional}.
   */
  <T extends Registrable> Optional<T> get(Class<T> clazz);

  /**
   * Closes the registry.
   * This should be called when the registry is no longer needed.
   * This will unregister all registered objects.
   */
  void close();
}
