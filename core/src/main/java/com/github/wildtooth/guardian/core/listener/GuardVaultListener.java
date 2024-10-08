package com.github.wildtooth.guardian.core.listener;

import com.github.wildtooth.guardian.api.event.vault.GuardVaultFinishEvent;
import com.github.wildtooth.guardian.api.event.vault.GuardVaultTryEvent;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardVaultService;
import com.github.wildtooth.guardian.core.widgets.Widgets;
import net.labymod.api.event.Subscribe;

public class GuardVaultListener {

  public GuardVaultListener() {

  }

  @Subscribe
  public void onGuardVaultTry(GuardVaultTryEvent event) {
    switch (event.getSector()) {
      case B_PLUS:
        Widgets.B_PLUS_TIMER.startTimer();
        Widgets.B_PLUS_ROBBER_WIDGET.setRobberName(event.getRobber());
        break;
      case A_PLUS:
        Widgets.A_PLUS_TIMER.startTimer();
        Widgets.A_PLUS_ROBBER_WIDGET.setRobberName(event.getRobber());
        break;
      case A:
        Widgets.A_TIMER.startTimer();
        Widgets.A_ROBBER_WIDGET.setRobberName(event.getRobber());
        break;
      default:
        return;
    }
    GuardVaultService guardVaultService = RegistryProvider.getRegistry().get(GuardVaultService.class).orElse(null);
    if (guardVaultService != null) {
      guardVaultService.handleRobbery(event.getSector(), event.getRobber());
    }
  }

  @Subscribe
  public void onGuardVaultFinish(GuardVaultFinishEvent event) {
    if (!event.wasSuccessFull()) {
      switch (event.getSector()) {
        case B_PLUS:
          Widgets.B_PLUS_TIMER.stopTimer();
          Widgets.B_PLUS_ROBBER_WIDGET.setRobberName("");
          break;
        case A_PLUS:
          Widgets.A_PLUS_TIMER.stopTimer();
          Widgets.A_PLUS_ROBBER_WIDGET.setRobberName("");
          break;
        case A:
          Widgets.A_TIMER.stopTimer();
          Widgets.A_ROBBER_WIDGET.setRobberName("");
          break;
      }
    }
    RegistryProvider.getRegistry().get(GuardVaultService.class).ifPresent(
        guardVaultService -> guardVaultService.removeFromLimbo(
            guardVaultService.getVaultBySector(event.getSector()).orElse(null)
        )
    );
  }
}
