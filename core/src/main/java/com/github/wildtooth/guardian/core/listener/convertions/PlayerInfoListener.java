package com.github.wildtooth.guardian.core.listener.convertions;

import com.github.wildtooth.guardian.api.event.GuardShiftSwitchEvent;
import com.github.wildtooth.guardian.api.event.GuardShiftSwitchEvent.ShiftSwitch;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.core.services.guard.GuardService;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.mojang.GameProfile;
import java.util.Optional;

public class PlayerInfoListener {
  @Subscribe
  public void onPlayerInfoAdd(PlayerInfoAddEvent event) {
    GameProfile profile = event.playerInfo().profile();
    Optional<GuardService> optionalGuardService = RegistryProvider.getRegistry().get(GuardService.class);
    if (optionalGuardService.isEmpty()) {
      return;
    }
    GuardService guardService = optionalGuardService.get();
    if (guardService.hasGuard(profile.getUniqueId())) {
      guardService.getGuard(profile.getUniqueId()).ifPresent(guard -> {
        Laby.fireEvent(new GuardShiftSwitchEvent(guard, ShiftSwitch.COMING_IN));
      });
    }
  }
}
