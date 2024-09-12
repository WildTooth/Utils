package com.github.wildtooth.guardian.core.listener.convertions;

import com.github.wildtooth.guardian.api.Constants;
import com.github.wildtooth.guardian.api.Constants.FreakyVilleMessages;
import com.github.wildtooth.guardian.api.ConstantsProvider;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFailEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFinishEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostTryEvent;
import com.github.wildtooth.guardian.api.event.vault.GuardVaultFinishEvent;
import com.github.wildtooth.guardian.api.event.vault.GuardVaultTryEvent;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.refrences.LocationHelper;
import com.github.wildtooth.guardian.api.service.Registry;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Event;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.Pair;
import java.util.Objects;

public class ChatMessageListener {

  private final FreakyvilleConnection freakyvilleConnection;
  private final FreakyVilleMessages freakyVilleMessages;

  public ChatMessageListener(FreakyvilleConnection freakyvilleConnection) {
    Constants constants = ConstantsProvider.getConstants();
    this.freakyvilleConnection = freakyvilleConnection;
    this.freakyVilleMessages = constants.freakyVilleMessages();
  }

  @Subscribe
  public void onMessageReceived(ChatReceiveEvent event) {
    if (!freakyvilleConnection.isOnFreakyVille()) {
      return;
    }
    if (!freakyvilleConnection.isGuard()) {
      return;
    }
    ChatMessage chatMessage = event.chatMessage();
    String message = chatMessage.getPlainText().trim();
    if (freakyVilleMessages.messages().containsKey(message)) {
      fireEvent(freakyVilleMessages.messages().get(message).getEvent());
    }
  }

  @Subscribe
  public void advancedOnMessageReceived(ChatReceiveEvent event) {
    if (!freakyvilleConnection.isOnFreakyVille()) {
      return;
    }
    if (!freakyvilleConnection.isGuard()) {
      return;
    }
    ChatMessage chatMessage = event.chatMessage();
    String message = chatMessage.getPlainText().trim();
    for (Pair<String, String> pair : this.freakyVilleMessages.advancedMessages().keySet()) {
      String stringedPair = pairToString(pair);
      String player = getPlayerFromString(pair, message);
      if (message.replace(player, "").equals(stringedPair)) {
        fireEvent(this.freakyVilleMessages.advancedMessages().get(pair).getEvent(), player);
      }
    }
  }

  private <E extends Event> void fireEvent(E event, Object... args) {
    Registry registry = RegistryProvider.getRegistry();
    GuardPostService guardPostService = registry.get(GuardPostService.class).orElse(null);
    //LocationHelper locationHelper = registry.get(LocationHelper.class).orElse(null);
    if (guardPostService == null) {
      throw new IllegalStateException("GuardPostService is not registered.");
    }
    //if (locationHelper == null) {
    //  throw new IllegalStateException("LocationHelper is not registered.");
    //}
    Event e = null;
    if (event instanceof GuardPostTryEvent) {
      //e = new GuardPostTryEvent(guardPostService.getGuardPostByLocation(locationHelper.getIntCoordinates(5)));
    } else if (event instanceof GuardPostFailEvent) {
      //e = new GuardPostFailEvent(guardPostService.getLastInteractedGuardPost().orElse(null));
    } else if (event instanceof GuardPostFinishEvent) {
      //e = new GuardPostFinishEvent(guardPostService.getLastInteractedGuardPost().orElse(null));
    } else if (event instanceof GuardVaultTryEvent) {
      if (args.length > 0 && args[0] instanceof String) {
        e = new GuardVaultTryEvent(((GuardVaultTryEvent) event).getSector(), (String) args[0]);
      }
    } else if (event instanceof GuardVaultFinishEvent) {
      if (args.length > 0 && args[0] instanceof String) {
        e = new GuardVaultFinishEvent(((GuardVaultFinishEvent) event).getSector(),
            (String) args[0], ((GuardVaultFinishEvent) event).wasSuccessFull());
      }
    }
    if (e != null) {
      Laby.fireEvent(e);
    }
  }

  private String getPlayerFromString(Pair<String, String> pair, String message) {
    String player = "";
    if (message.startsWith(Objects.requireNonNull(pair.getFirst())) && message.endsWith(Objects.requireNonNull(pair.getSecond()))) {
      player = message.substring(pair.getFirst().length() + 1, message.length() - pair.getSecond().length());
    }
    return player;
  }

  private String pairToString(Pair<String, String> pair) {
    return pair.getFirst() + " " + pair.getSecond();
  }
}
