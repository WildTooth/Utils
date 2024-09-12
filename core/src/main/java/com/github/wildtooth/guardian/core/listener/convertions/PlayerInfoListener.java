package com.github.wildtooth.guardian.core.listener.convertions;

import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent;
import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent.ShiftSwitch;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.mojang.GameProfile;
import java.util.concurrent.CompletableFuture;

public class PlayerInfoListener {
  private final FreakyvilleConnection freakyvilleConnection;
  private GuardService guardService;

  public PlayerInfoListener(FreakyvilleConnection freakyvilleConnection) {
    this.freakyvilleConnection = freakyvilleConnection;
    this.guardService = RegistryProvider.getRegistry().get(GuardService.class).orElse(null);
  }

  @Subscribe
  public void onPlayerInfoAdd(PlayerInfoAddEvent event) {
    if (!freakyvilleConnection.isGuard()) {
      return;
    }
    CompletableFuture.runAsync(() -> checkAndFireShiftSwitch(event.playerInfo().profile(), ShiftSwitch.COMING_IN));
  }

  @Subscribe
  public void onPlayerInfoRemove(PlayerInfoRemoveEvent event) {
    if (!freakyvilleConnection.isGuard()) {
      return;
    }
    CompletableFuture.runAsync(() -> checkAndFireShiftSwitch(event.playerInfo().profile(), ShiftSwitch.GOING_OUT));
  }

  private void checkAndFireShiftSwitch(GameProfile profile, ShiftSwitch shiftSwitch) {
    if (this.guardService == null) {
      this.guardService = RegistryProvider.getRegistry().get(GuardService.class).orElse(null);
      return;
    }
    if (this.guardService.hasGuard(profile.getUniqueId())) {
      this.guardService.getGuard(profile.getUniqueId()).ifPresent(guard -> {
        Laby.fireEvent(new GuardShiftSwitchEvent(guard, shiftSwitch));
      });
    }
  }
}
