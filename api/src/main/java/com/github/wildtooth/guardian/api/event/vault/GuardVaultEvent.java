package com.github.wildtooth.guardian.api.event.vault;

import com.github.wildtooth.guardian.api.util.PrisonSector;
import net.labymod.api.event.Event;

public abstract class GuardVaultEvent implements Event {
  private final PrisonSector sector;
  private final String robber;

  public GuardVaultEvent(PrisonSector sector, String robber) {
    this.sector = sector;
    this.robber = robber;
  }

  public PrisonSector getSector() {
    return sector;
  }

  public String getRobber() {
    return robber;
  }
}
