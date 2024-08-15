package com.github.wildtooth.guardian.api.event.guard;

import com.github.wildtooth.guardian.api.guard.Guard;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class GuardEvent implements Event {
  private final @NotNull Guard guard;

  public GuardEvent(@NotNull Guard guard) {
    this.guard = guard;
  }

  public @NotNull Guard getGuard() {
    return this.guard;
  }
}
