package com.github.wildtooth.guardian.api.service.guard;

import com.github.wildtooth.guardian.api.guard.GuardVault;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;
import com.github.wildtooth.guardian.api.util.PrisonSector;
import java.util.Optional;

public interface GuardVaultService extends Service, Registrable {

  Optional<? extends GuardVault> getVaultBySector(PrisonSector sector);

  void handleRobbery(PrisonSector sector, String robber);

  void removeFromLimbo(GuardVault vault);
}
