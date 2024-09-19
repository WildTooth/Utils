package com.github.wildtooth.guardian.api.event.guardpost;

import com.github.wildtooth.guardian.api.guardpost.GuardPost;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

public abstract class GuardPostEvent implements Event {
  private final @Nullable GuardPost guardPost;

  public GuardPostEvent(@Nullable GuardPost guardPost) {
    this.guardPost = guardPost;
  }

  public @Nullable GuardPost getGuardPost() {
    return guardPost;
  }
}
