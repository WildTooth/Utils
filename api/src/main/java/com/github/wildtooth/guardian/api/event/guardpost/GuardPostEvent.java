package com.github.wildtooth.guardian.api.event.guardpost;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class GuardPostEvent implements Event {
  private final @NotNull GuardPost guardPost;

  public GuardPostEvent(@NotNull GuardPost guardPost) {
    this.guardPost = guardPost;
  }

  public @NotNull GuardPost getGuardPost() {
    return guardPost;
  }
}
