package com.github.wildtooth.guardian.core.services.guard;

import java.util.UUID;

public class Guard {
  private final UUID uuid;
  private final String name;

  private Guard(UUID uuid, String name) {
    this.uuid = uuid;
    this.name = name;
  }

  public UUID getUuid() {
    return this.uuid;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Guard guard = (Guard) obj;
    return this.uuid.equals(guard.uuid);
  }
}
