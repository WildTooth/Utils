package com.github.wildtooth.guardian.api.guard;

import java.util.UUID;

public interface Guard {
  UUID getUuid();

  String getName();

  boolean equals(Object obj);

  boolean equals(Guard guard);
}
