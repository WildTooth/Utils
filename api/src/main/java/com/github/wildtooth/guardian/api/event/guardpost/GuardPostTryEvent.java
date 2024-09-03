package com.github.wildtooth.guardian.api.event.guardpost;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import net.labymod.api.util.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuardPostTryEvent extends GuardPostEvent {

  private Triple<Integer, Integer, Integer> coordsToNearestGuardPost;

  public GuardPostTryEvent(@Nullable GuardPost guardPost) {
    super(guardPost);
    if (guardPost != null) {
      this.coordsToNearestGuardPost = guardPost.getCoordinates();
    }
  }

  @NotNull
  public Triple<Integer, Integer, Integer> getCoordsToNearestGuardPost() {
    if (this.coordsToNearestGuardPost == null) {
      GuardPostService guardPostService = RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
      if (guardPostService == null) {
        throw new IllegalStateException("GuardPostService is not registered");
      }
      this.coordsToNearestGuardPost = guardPostService.getNearestGuardPost().getCoordinates();
    }
    return this.coordsToNearestGuardPost;
  }
}
