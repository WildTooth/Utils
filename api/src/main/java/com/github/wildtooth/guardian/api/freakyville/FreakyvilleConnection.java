package com.github.wildtooth.guardian.api.freakyville;

import com.github.wildtooth.guardian.api.util.PrisonSector;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.network.server.ServerController;
import java.util.Objects;
import java.util.Optional;

public class FreakyvilleConnection {
  private final ServerController serverController;
  private ClientPlayer clientPlayer;
  private FreakyvilleServer currentServer;
  private PrisonSector prison;
  private boolean hasUpdatedToCurrentServer;

  public FreakyvilleConnection(ServerController serverController, ClientPlayer clientPlayer) {
    this.serverController = serverController;
    this.clientPlayer = clientPlayer;
    this.currentServer = FreakyvilleServer.NONE;
    this.prison = null;
    this.hasUpdatedToCurrentServer = false;
  }

  public boolean isOnFreakyVille() {
    if (!this.serverController.isConnected()) return false;
    return isValidServerAddress(
        Objects.requireNonNull(this.serverController.getCurrentServerData()).address().getHost());
  }

  public Optional<ClientPlayer> getClientPlayer() {
    return Optional.ofNullable(clientPlayer);
  }

  public void setClientPlayer(ClientPlayer clientPlayer) {
    this.clientPlayer = clientPlayer;
  }

  public FreakyvilleServer getCurrentServer() {
    return currentServer;
  }

  public void setCurrentServer(FreakyvilleServer currentServer) {
    this.currentServer = currentServer;
  }

  public Optional<PrisonSector> getPrison() {
    return Optional.ofNullable(prison);
  }

  public void setPrison(PrisonSector prison) {
    this.prison = prison;
  }

  public boolean isUpdatedToCurrentServer() {
    return hasUpdatedToCurrentServer;
  }

  public void setHasUpdatedToCurrentServer(boolean hasUpdatedToCurrentServer) {
    this.hasUpdatedToCurrentServer = hasUpdatedToCurrentServer;
  }

  private boolean isValidServerAddress(String address) {
    address = address.toLowerCase();
    return address.endsWith(".freakyville.dk") || address.endsWith(".freakyville.net");
  }
}
