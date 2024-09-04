package com.github.wildtooth.guardian.api.event.vault;

import com.github.wildtooth.guardian.api.util.PrisonSector;

public class GuardVaultFinishEvent extends GuardVaultEvent {

  private final boolean success;

  public GuardVaultFinishEvent(PrisonSector sector, String robber, boolean success) {
    super(sector, robber);
    this.success = success;
  }

  public boolean wasSuccessFull() {
    return success;
  }
}
