package com.github.wildtooth.guardian.core.listener.convertions;

import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent;
import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent.ShiftSwitch;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.mojang.GameProfile;

public class PlayerInfoListener {
  private GuardService guardService;

  public PlayerInfoListener() {
    this.guardService = RegistryProvider.getRegistry().get(GuardService.class).orElse(null);
  }

  @Subscribe
  public void onPlayerInfoAdd(PlayerInfoAddEvent event) {
    GameProfile profile = event.playerInfo().profile();
    if (this.guardService == null) {
      this.guardService = RegistryProvider.getRegistry().get(GuardService.class).orElse(null);
      return;
    }
    if (this.guardService.hasGuard(profile.getUniqueId())) {
      this.guardService.getGuard(profile.getUniqueId()).ifPresent(guard -> {
        Laby.fireEvent(new GuardShiftSwitchEvent(guard, ShiftSwitch.COMING_IN));
      });
    }
  }

  @Subscribe
  public void onPlayerInfoRemove(PlayerInfoRemoveEvent event) {
    GameProfile profile = event.playerInfo().profile();
    if (this.guardService == null) {
      this.guardService = RegistryProvider.getRegistry().get(GuardService.class).orElse(null);
      return;
    }
    if (this.guardService.hasGuard(profile.getUniqueId())) {
      this.guardService.getGuard(profile.getUniqueId()).ifPresent(guard -> {
        Laby.fireEvent(new GuardShiftSwitchEvent(guard, ShiftSwitch.GOING_OUT));
      });
    }
  }
}
