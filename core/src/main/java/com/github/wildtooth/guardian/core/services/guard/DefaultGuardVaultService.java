package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.api.event.vault.GuardVaultFinishEvent;
import com.github.wildtooth.guardian.api.guard.GuardVault;
import com.github.wildtooth.guardian.api.service.guard.GuardVaultService;
import com.github.wildtooth.guardian.api.util.PrisonSector;
import com.github.wildtooth.guardian.core.guard.DefaultGuardVault;
import net.labymod.api.Laby;
import net.labymod.api.util.concurrent.task.Task;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DefaultGuardVaultService implements GuardVaultService {

  private final Set<GuardVault> guardVaults;
  private final Set<GuardVault> limboVaults;

  public DefaultGuardVaultService() {
    this.guardVaults = new HashSet<>();
    this.limboVaults = new HashSet<>();
  }

  @Override
  public Optional<? extends GuardVault> getVaultBySector(PrisonSector sector) {
    for (GuardVault guardVault : this.guardVaults) {
      if (guardVault.getPrisonSector() == sector) {
        return Optional.of(guardVault);
      }
    }
    return Optional.empty();
  }

  @Override
  public void handleRobbery(PrisonSector sector, String robber) {
    Optional<? extends GuardVault> vault = getVaultBySector(sector);
    if (vault.isEmpty()) {
      return;
    }
    vault.ifPresent(this.limboVaults::add);
    Task robbingTask = Task.builder(
      () -> {
        if (this.limboVaults.contains(vault.get())) {
          removeFromLimbo(vault.get());
          Laby.fireEvent(new GuardVaultFinishEvent(sector, robber, false));
        }
      }
    ).delay(vault.get().getExpectedRobberyDuration() + 1, TimeUnit.SECONDS)
    .build();
    robbingTask.execute();
  }

  @Override
  public void removeFromLimbo(GuardVault vault) {
    this.limboVaults.remove(vault);
  }

  @Override
  public void onRegister() {
    initialize();
  }

  @Override
  public void onUnregister() {

  }

  @Override
  public void initialize() {
    this.guardVaults.addAll(Set.of(
        new DefaultGuardVault(PrisonSector.B_PLUS, 13),
        new DefaultGuardVault(PrisonSector.A, 8),
        new DefaultGuardVault(PrisonSector.A_PLUS, 10)
    ));
  }

  @Override
  public void shutdown() {

  }
}
