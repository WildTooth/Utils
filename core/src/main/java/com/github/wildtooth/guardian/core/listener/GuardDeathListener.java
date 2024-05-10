package com.github.wildtooth.guardian.core.listener;

import com.github.wildtooth.guardian.api.event.GuardDeathEvent;
import com.github.wildtooth.guardian.core.util.Hud;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.Pair;

public class GuardDeathListener {
  public GuardDeathListener() {
  }

  @Subscribe
  public void onGuardDeath(GuardDeathEvent event) {
    TranslatableComponent component = Component.translatable()
        .key("guardian.event.guardDeath")
        .arguments(Component.text(event.getGuard().getName(), NamedTextColor.YELLOW), event.getBroadLocation())
        .colorIfAbsent(NamedTextColor.RED)
        .build();
    Hud.displayTitle(Pair.of(Component.empty(), component), 10, 40, 10);
  }
}
