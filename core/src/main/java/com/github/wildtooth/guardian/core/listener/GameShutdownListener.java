package com.github.wildtooth.guardian.core.listener;

import com.github.wildtooth.guardian.api.service.Registry;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;

public class GameShutdownListener {
  public GameShutdownListener() {
  }

  @Subscribe
  public void onGameShutdown(GameShutdownEvent event) {
    Registry registry = RegistryProvider.getRegistry();
    registry.close();
  }
}
