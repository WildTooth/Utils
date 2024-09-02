package com.github.wildtooth.guardian.core.listener.convertions;

import com.github.wildtooth.guardian.api.Constants;
import com.github.wildtooth.guardian.api.Constants.FreakyVilleMessages;
import com.github.wildtooth.guardian.api.ConstantsProvider;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFailEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFinishEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostTryEvent;
import com.github.wildtooth.guardian.api.refrences.LocationHelper;
import com.github.wildtooth.guardian.api.service.Registry;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Event;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

public class ChatMessageListener {

  private final FreakyVilleMessages freakyVilleMessages;

  public ChatMessageListener() {
    Constants constants = ConstantsProvider.getConstants();
    this.freakyVilleMessages = constants.freakyVilleMessages();
  }

  @Subscribe
  public void onMessageReceived(ChatReceiveEvent event) {
    ChatMessage chatMessage = event.chatMessage();
    String message = chatMessage.getPlainText().trim();
    if (freakyVilleMessages.messages().containsKey(message)) {
      fireEvent(freakyVilleMessages.messages().get(message).getEvent());
    }
  }

  private <E extends Event> void fireEvent(E event) {
    Registry registry = RegistryProvider.getRegistry();
    GuardPostService guardPostService = registry.get(GuardPostService.class).orElse(null);
    LocationHelper locationHelper = registry.get(LocationHelper.class).orElse(null);
    if (guardPostService == null) {
      throw new IllegalStateException("GuardPostService is not registered.");
    }
    if (locationHelper == null) {
      throw new IllegalStateException("LocationHelper is not registered.");
    }
    Event e = null;
    if (event instanceof GuardPostTryEvent) {
      e = new GuardPostTryEvent(guardPostService.getGuardPostByLocation(locationHelper.getIntCoordinates(5)));
    } else if (event instanceof GuardPostFailEvent) {
      e = new GuardPostFailEvent(guardPostService.getLastInteractedGuardPost().orElse(null));
    } else if (event instanceof GuardPostFinishEvent) {
      e = new GuardPostFinishEvent(guardPostService.getLastInteractedGuardPost().orElse(null));
    }
    if (e != null) {
      Laby.fireEvent(e);
    }
  }
}
