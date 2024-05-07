package com.github.wildtooth.guardian.api.event;

import com.github.wildtooth.guardian.api.guard.Guard;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;

public class GuardDeathEvent extends GuardEvent {
  private final Component broadLocation;

  public GuardDeathEvent(@NotNull Guard guard, Component broadLocation) {
    super(guard);
    this.broadLocation = broadLocation;
  }

  public Component getBroadLocation() {
    return this.broadLocation;
  }
}
