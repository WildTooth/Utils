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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PlayerInfoListener {
  private final FreakyvilleConnection freakyvilleConnection;
  private GuardService guardService;
  private final BlockingQueue<Runnable> eventQueue = new LinkedBlockingQueue<>();
  private static final int MAX_EVENTS = 3;
  private static final long TIME_WINDOW_MS = 2000;

  public PlayerInfoListener(FreakyvilleConnection freakyvilleConnection) {
    this.freakyvilleConnection = freakyvilleConnection;
    this.guardService = RegistryProvider.getRegistry().get(GuardService.class).orElse(null);
    startQueueProcessor();
  }

  @Subscribe
  public void onPlayerInfoAdd(PlayerInfoAddEvent event) {
    if (!freakyvilleConnection.isOnFreakyVille()) {
      return;
    }
    if (!freakyvilleConnection.isGuard()) {
      return;
    }
    eventQueue.offer(() -> checkAndFireShiftSwitch(event.playerInfo().profile(), ShiftSwitch.COMING_IN));
  }

  @Subscribe
  public void onPlayerInfoRemove(PlayerInfoRemoveEvent event) {
    if (!freakyvilleConnection.isOnFreakyVille()) {
      return;
    }
    if (!freakyvilleConnection.isGuard()) {
      return;
    }
    eventQueue.offer(() -> checkAndFireShiftSwitch(event.playerInfo().profile(), ShiftSwitch.GOING_OUT));
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

  private void startQueueProcessor() {
    CompletableFuture.runAsync(() -> {
      while (true) {
        try {
          TimeUnit.MILLISECONDS.sleep(TIME_WINDOW_MS);
          if (eventQueue.size() > MAX_EVENTS) {
            eventQueue.clear();
          } else {
            while (!eventQueue.isEmpty()) {
              eventQueue.poll().run();
            }
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
  }
}