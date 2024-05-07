package com.github.wildtooth.guardian.api.service;

import java.util.Optional;

/**
 * Interface for a registry that can be used to register and unregister {@link Registrable} objects.
 */
public interface Registry {

  /**
   * Registers a {@link Registrable} object.
   * @param registrable The object to register.
   */
  void register(Registrable registrable);

  /**
   * Unregisters a {@link Registrable} object.
   * @param registrable The object to unregister.
   */
  void unregister(Registrable registrable);

  /**
   * Gets a {@link Registrable} object of the specified class.
   * @param clazz The class of the object to get.
   * @param <T> The type of the object to get.
   * @return An {@link Optional} containing the object if it exists, otherwise an empty {@link Optional}.
   */
  <T extends Registrable> Optional<T> get(Class<T> clazz);
}
