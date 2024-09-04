package com.github.wildtooth.guardian.api.event.vault;

import com.github.wildtooth.guardian.api.util.PrisonSector;
import java.time.Instant;

public class GuardVaultTryEvent extends GuardVaultEvent {

  private final Instant time = Instant.now();

  public GuardVaultTryEvent(PrisonSector sector, String robber) {
    super(sector, robber);
  }

  public Instant getTime() {
    return time;
  }
}
