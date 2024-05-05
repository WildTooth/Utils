package com.github.wildtooth.guardian.core.services.guard;

import java.util.UUID;

public class Guard {
  private final UUID uuid;

  Guard(UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getUuid() {
    return this.uuid;
  }
}
