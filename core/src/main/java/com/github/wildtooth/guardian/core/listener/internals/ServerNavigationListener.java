package com.github.wildtooth.guardian.core.listener.internals;

import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleServer;
import com.github.wildtooth.guardian.core.util.Hud;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.util.Pair;
import net.labymod.api.util.concurrent.task.Task;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerNavigationListener {
  private final boolean addonNeedsUpdate;
  private final FreakyvilleConnection clientInfo;

  private Task updateTask = Task.builder(() -> {}).build();

  public ServerNavigationListener(boolean needsUpdate, FreakyvilleConnection clientInfo) {
    this.addonNeedsUpdate = needsUpdate;
    this.clientInfo = clientInfo;
  }

  @Subscribe
  public void onServerJoin(ServerJoinEvent event) {
    if (this.updateTask.isRunning()) {
      this.updateTask.cancel();
    }
    warnUpdatedNeeded();
    if (!this.isValidServerAddress(event.serverData().address().getHost())) {
      this.clientInfo.setCurrentServer(FreakyvilleServer.NONE);
      this.clientInfo.setPrison(null);
      this.clientInfo.setHasUpdatedToCurrentServer(false);
      return;
    }
    this.clientInfo.setClientPlayer(Laby.labyAPI().minecraft().getClientPlayer());
    this.clientInfo.setCurrentServer(FreakyvilleServer.HUB);
    this.clientInfo.setPrison(null);
  }

  @Subscribe
  public void onServerDisconnect(ServerDisconnectEvent event) {
    if (this.updateTask.isRunning()) {
      this.updateTask.cancel();
    }
    if (this.clientInfo.getCurrentServer() != FreakyvilleServer.NONE) {
      this.clientInfo.setCurrentServer(FreakyvilleServer.NONE);
      this.clientInfo.setPrison(null);
      this.clientInfo.setHasUpdatedToCurrentServer(false);
      Laby.labyAPI().thirdPartyService().discord().displayDefaultActivity();
    }
  }

  @Subscribe
  public void onSubServerSwitch(SubServerSwitchEvent event) {
    if (this.updateTask.isRunning()) {
      this.updateTask.cancel();
    }
    warnUpdatedNeeded();
    if (!this.clientInfo.isOnFreakyVille()) {
      return;
    }
    this.clientInfo.setPrison(null);
    this.clientInfo.setHasUpdatedToCurrentServer(false);
  }

  private boolean isValidServerAddress(String address) {
    address = address.toLowerCase();
    return address.endsWith(".freakyville.dk") || address.endsWith(".freakyville.net");
  }

  private void warnUpdatedNeeded() {
    if (this.addonNeedsUpdate) {
      Hud.warn("guardian.update.notification");
      AtomicInteger mode = new AtomicInteger(0);
      AtomicInteger count = new AtomicInteger(0);
      final int[] fadeIn = { 10 };
      this.updateTask = Task.builder(
          () -> {
            if (mode.get() == 0) {
              Hud.displayTitle(
                  Pair.of(
                      warning(NamedTextColor.RED, NamedTextColor.YELLOW),
                      Component.translatable("guardian.update.title.contact", NamedTextColor.RED)
                  ),
                  fadeIn[0], 10, 20
              );
              mode.set(1);
            } else {
              Hud.displayTitle(
                  Pair.of(
                      warning(NamedTextColor.YELLOW, NamedTextColor.RED),
                      Component.translatable("guardian.update.title.contact", NamedTextColor.RED)
                  ),
                  fadeIn[0], 10, 20
              );
              mode.set(0);
            }
            fadeIn[0] = 0;
            if (count.getAndIncrement() >= 5) {
              this.updateTask.cancel();
            }
          }
      ).repeat(500, TimeUnit.MILLISECONDS).build();
      this.updateTask.execute();
    }
  }

  private Component warning(TextColor first, TextColor second) {
    return Component.empty()
        .append(Component.text("⚠", first))
        .append(Component.space())
        .append(Component.translatable("guardian.update.title.header", second))
        .append(Component.space())
        .append(Component.text("⚠", first));
  }
}