package com.github.wildtooth.guardian.api.guardvault;

import com.github.wildtooth.guardian.api.util.PrisonSector;

public interface GuardVault {
  PrisonSector getPrisonSector();

  int getExpectedRobberyDuration();
}
