package com.github.wildtooth.guardian.core.listener.internals;

import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleServer;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;

public class ServerNavigationListener {
  private final FreakyvilleConnection clientInfo;

  public ServerNavigationListener(FreakyvilleConnection clientInfo) {
    this.clientInfo = clientInfo;
  }

  @Subscribe
  public void onServerJoin(ServerJoinEvent event) {
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
    if (this.clientInfo.getCurrentServer() != FreakyvilleServer.NONE) {
      this.clientInfo.setCurrentServer(FreakyvilleServer.NONE);
      this.clientInfo.setPrison(null);
      this.clientInfo.setHasUpdatedToCurrentServer(false);
      Laby.labyAPI().thirdPartyService().discord().displayDefaultActivity();
    }
  }

  @Subscribe
  public void onSubServerSwitch(SubServerSwitchEvent event) {
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
}