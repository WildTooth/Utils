package com.github.wildtooth.guardian.core.listener;

import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFailEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFinishEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostTryEvent;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleServer;
import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import net.labymod.api.event.Subscribe;

public class GuardPostListener {

  private final FreakyvilleConnection freakyvilleConnection;

  public GuardPostListener(FreakyvilleConnection freakyvilleConnection) {
    this.freakyvilleConnection = freakyvilleConnection;
  }

  @Subscribe
  public void onGuardPostTry(GuardPostTryEvent event) {
    GuardPost guardPost = event.getGuardPost();
    if (!freakyvilleConnection.isOnFreakyVille()) {
      return;
    }
    if (freakyvilleConnection.getCurrentServer() != FreakyvilleServer.PRISON) {
      return;
    }
    if (freakyvilleConnection.getPrison().isEmpty()) {
      return;
    }
    GuardPostService guardPostService = RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
    if (guardPostService == null) {
      throw new IllegalStateException("GuardPostService is not registered.");
    }
    if (guardPost == null) {
      guardPost = guardPostService.getGuardPostByLocation(event.getCoordsToNearestGuardPost());
    }
    if (guardPost == null) {
      throw new IllegalStateException("GuardPost does not exist???????");
    }
    guardPostService.setLastInteractedGuardPost(guardPost);
  }

  @Subscribe
  public void onGuardPostFail(GuardPostFailEvent event) {
    GuardPostService guardPostService = RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
    if (guardPostService == null) {
      throw new IllegalStateException("GuardPostService is not registered.");
    }
    guardPostService.setLastInteractedGuardPost(null);
  }

  @Subscribe
  public void onGuardPostFinish(GuardPostFinishEvent event) {
    GuardPostService guardPostService = RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
    if (guardPostService == null) {
      throw new IllegalStateException("GuardPostService is not registered.");
    }
    if (event.getGuardPost() == null) {
      throw new IllegalStateException("GuardPost does not exist???????");
    }
    guardPostService.putGuardPostOnCooldown(event.getGuardPost());
    guardPostService.setLastInteractedGuardPost(null);
  }

}
