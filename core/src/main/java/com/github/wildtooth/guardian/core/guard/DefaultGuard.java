package com.github.wildtooth.guardian.core.guard;

import com.github.wildtooth.guardian.api.guard.Guard;
import java.util.UUID;

public class DefaultGuard implements Guard {
  private final UUID uuid;
  private final String name;

  private DefaultGuard(UUID uuid, String name) {
    this.uuid = uuid;
    this.name = name;
  }

  @Override
  public UUID getUuid() {
    return this.uuid;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Guard) {
      return this.equals((Guard) obj);
    }
    return false;
  }

  @Override
  public boolean equals(Guard guard) {
    return this.uuid.equals(guard.getUuid());
  }
}
