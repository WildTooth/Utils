package com.github.wildtooth.guardian.api.guard;

import com.github.wildtooth.guardian.api.util.PrisonSector;

public interface GuardVault {
  PrisonSector getPrisonSector();

  int getExpectedRobberyDuration();
}
