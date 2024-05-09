package com.github.wildtooth.guardian.core.listener;

import com.github.wildtooth.guardian.api.event.GuardDeathEvent;
import com.github.wildtooth.guardian.core.util.Hud;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.Pair;

public class GuardDeathListener {
  public GuardDeathListener() {
  }

  @Subscribe
  public void onGuardDeath(GuardDeathEvent event) {
    Hud.displayTitle(
        Pair.of(
            Component.empty(),
            Component.translatable("guardian.event.guardDeath",
                Component.text(event.getGuard().getName(), NamedTextColor.YELLOW),
                event.getBroadLocation()
            ).colorIfAbsent(NamedTextColor.RED)
        ),
        10, 40, 10
    );
  }
}
