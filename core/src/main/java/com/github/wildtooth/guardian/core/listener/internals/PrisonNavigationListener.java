package com.github.wildtooth.guardian.core.listener.internals;

import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleServer;
import com.github.wildtooth.guardian.api.util.PrisonSector;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.Priority;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.I18n;
import net.labymod.api.util.Pair;
import net.labymod.api.util.logging.Logging;
import java.util.Objects;

public class PrisonNavigationListener {
  private final FreakyvilleConnection clientInfo;

  public PrisonNavigationListener(FreakyvilleConnection clientInfo) {
    this.clientInfo = clientInfo;
  }

  @Subscribe(Priority.FIRST)
  public void onChatReceive(ChatReceiveEvent event) {
    if (!this.clientInfo.isOnFreakyVille()) {
      return;
    }
    if (this.clientInfo.getCurrentServer() != FreakyvilleServer.PRISON) {
      return;
    }
    if (this.clientInfo.getPrison().isPresent()) {
      return;
    }
    String plainMessage = event.chatMessage().getPlainText().trim();
    if (plainMessage.startsWith(Objects.requireNonNull(headerDecoration().getFirst())) &&
        plainMessage.endsWith(Objects.requireNonNull(headerDecoration().getSecond()))) {
      try {
        this.clientInfo.setPrison(prisonFromHeader(plainMessage));
      } catch (IllegalArgumentException e) {
        this.clientInfo.setPrison(null);
        Logging.getLogger().error(I18n.translate("fvplus.logging.error.findingPrison"), e);
        Laby.references().chatExecutor().displayClientMessage(Component.translatable("fvplus.logging.error.findingPrison", NamedTextColor.RED));
      }
    }
  }

  private PrisonSector prisonFromHeader(String header) {
    String restProduct = header
        .replace(Objects.requireNonNull(headerDecoration().getFirst()), "")
        .replace(Objects.requireNonNull(headerDecoration().getSecond()), "")
        .trim();
    return switch (restProduct.toUpperCase()) {
      case "C" -> PrisonSector.C;
      case "B" -> PrisonSector.B;
      case "A" -> PrisonSector.A;
      default -> throw new IllegalArgumentException(I18n.translate("fvplus.logging.error.unexpectedValue", restProduct.toUpperCase()));
    };
  }

  private Pair<String, String> headerDecoration() {
    return Pair.of("----- Online Fanger i", "----");
  }
}
