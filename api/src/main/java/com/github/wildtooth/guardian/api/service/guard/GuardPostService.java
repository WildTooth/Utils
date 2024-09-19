package com.github.wildtooth.guardian.api.service.guard;

import com.github.wildtooth.guardian.api.guardpost.GuardPost;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;
import com.github.wildtooth.guardian.api.save.PersistentDataHolder;
import net.labymod.api.util.Triple;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing {@link GuardPost}s.
 */
public interface GuardPostService extends Service, Registrable, PersistentDataHolder {

  /**
   * Gets a {@link GuardPost} by its identifier.
   * @param identifier The identifier of the {@link GuardPost}.
   * @return           An {@link Optional} containing the {@link GuardPost} if it exists, otherwise an empty {@link Optional}.
   */
  Optional<? extends GuardPost> getGuardPost(String identifier);

  /**
   * Gets the cooldown time of a {@link GuardPost}.
   * @param guardPost The {@link GuardPost} to get the cooldown time of.
   * @return          The cooldown time of the {@link GuardPost}.
   */
  long getCooldownTime(GuardPost guardPost);

  /**
   * Adds a {@link GuardPost} to the service.
   * @param guardPost The {@link GuardPost} to add.
   */
  void addGuardPost(GuardPost guardPost);

  /**
   * Removes a {@link GuardPost} from the service.
    * @param identifier The identifier of the {@link GuardPost} to remove.
   */
  void removeGuardPost(String identifier);

  /**
   * Checks if the service has a {@link GuardPost}.
   * @param guardPost The {@link GuardPost} to check for.
   * @return         Whether the service has the {@link GuardPost}.
   */
  boolean hasGuardPost(GuardPost guardPost);

  /**
   * Checks if the service has a {@link GuardPost} by its identifier.
   * @param identifier The identifier of the {@link GuardPost} to check for.
   * @return          Whether the service has the {@link GuardPost}.
   */
  boolean hasGuardPost(String identifier);

  /**
   * Puts a {@link GuardPost} on cooldown.
   * @param guardPost The {@link GuardPost} to put on cooldown.
   */
  void putGuardPostOnCooldown(GuardPost guardPost);

  /**
   * Puts a {@link GuardPost} on cooldown with a specified last taken time.
   * @param guardPost The {@link GuardPost} to put on cooldown.
   */
  void putGuardPostOnCooldown(GuardPost guardPost, long lastTaken);

  /**
   * Checks if a {@link GuardPost} is on cooldown.
   * @param guardPost The {@link GuardPost} to check.
   * @return          Whether the {@link GuardPost} is on cooldown.
   */
  boolean isGuardPostOnCooldown(GuardPost guardPost);

  /**
   * Gets a map the time the {@link GuardPost}s was last interacted with.
   * @return A map of {@link GuardPost}s to the time they were last interacted with.
   */
  Map<GuardPost, Long> getGuardPostTimeMap();

  /**
   * Gets a {@link GuardPost} by its location.
   * @param location The location of the {@link GuardPost}.
   * @return         The {@link GuardPost} at the location.
   */
  GuardPost getGuardPostByLocation(Triple<Integer, Integer, Integer> location);

  /**
   * Gets the nearest {@link GuardPost}.
   * @return The nearest {@link GuardPost}.
   */
  GuardPost getNearestGuardPost();

  /**
   * Gets the last {@link GuardPost} interacted with.
   * @return The last {@link GuardPost} interacted with.
   */
  Optional<GuardPost> getLastInteractedGuardPost();

  /**
   * Sets the last {@link GuardPost} interacted with.
   * @param guardPost The {@link GuardPost} to set as the last interacted with.
   */
  void setLastInteractedGuardPost(GuardPost guardPost);

  /**
   * Creates a {@link GuardPost}.
   * @param prisonSector The prison sector of the {@link GuardPost}.
   * @param numericalIdentifier The numerical identifier of the {@link GuardPost}.
   * @param displayName The display name of the {@link GuardPost}.
   * @param personalCooldown The personal cooldown of the {@link GuardPost}.
   * @param x The x-coordinate of the {@link GuardPost}.
   * @param y The y-coordinate of the {@link GuardPost}.
   * @param z The z-coordinate of the {@link GuardPost}.
   * @return The created {@link GuardPost}.
   */
  GuardPost createGuardPost(String prisonSector, int numericalIdentifier, String displayName, int personalCooldown, int x, int y, int z);
}
