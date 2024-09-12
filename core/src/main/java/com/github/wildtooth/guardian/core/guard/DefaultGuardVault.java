package com.github.wildtooth.guardian.core.guard;

import com.github.wildtooth.guardian.api.guard.GuardVault;
import com.github.wildtooth.guardian.api.util.PrisonSector;

public class DefaultGuardVault implements GuardVault {

  private final PrisonSector prisonSector;
  private final int expectedRobberyDuration;

  public DefaultGuardVault(PrisonSector prisonSector, int expectedRobberyDuration) {
    this.prisonSector = prisonSector;
    this.expectedRobberyDuration = expectedRobberyDuration;
  }

  @Override
  public PrisonSector getPrisonSector() {
    return this.prisonSector;
  }

  @Override
  public int getExpectedRobberyDuration() {
    return this.expectedRobberyDuration;
  }
}
