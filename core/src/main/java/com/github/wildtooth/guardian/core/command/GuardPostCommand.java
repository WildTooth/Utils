package com.github.wildtooth.guardian.core.command;

import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.util.PrisonSector;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.ArrayList;

public class GuardPostCommand extends Command {

  private final FreakyvilleConnection freakyvilleConnection;

  public GuardPostCommand(FreakyvilleConnection freakyvilleConnection) {
    super("vagtpost", "vp");
    this.freakyvilleConnection = freakyvilleConnection;
  }

  @Override
  public boolean execute(String s, String[] args) {
    if (!this.freakyvilleConnection.isOnFreakyVille()) {
      return false;
    }
    if (!freakyvilleConnection.isGuard()) {
      return false;
    }
    if (this.freakyvilleConnection.getPrison().isEmpty()) {
      return true;
    }
    if (args.length == 0) {
      sendGuardPostList(this.freakyvilleConnection.getPrison().get());
      return true;
    }
    if (args.length == 1) {
      PrisonSector sector = PrisonSector.fromString(args[0]);
      if (sector == null) {
        return true;
      }
      sendGuardPostList(sector);
      return true;
    }
    return true;
  }

  private void sendGuardPostList(PrisonSector sector) {
    GuardPost[] guardPosts = getGuardPostsOfSector(sector);
    if (guardPosts.length == 0) {
      Component text = Component.translatable("guardian.command.vagtpost.empty", NamedTextColor.RED);
      displayMessage(text);
      return;
    }
    Component text = Component.translatable("guardian.command.vagtpost.inSector", NamedTextColor.GRAY, sector.toComponent());
    displayMessage(text);
    for (GuardPost guardPost : guardPosts) {
      TextComponent.Builder builder = TextComponent.builder();
      builder.append(Component.text(" - ", NamedTextColor.DARK_GRAY))
          .append(guardPost.toComponent())
          .append(Component.text(" | ", NamedTextColor.DARK_GRAY))
          .append(cooldownComponent(guardPost));
      displayMessage(builder.build());
    }
  }

  private GuardPost[] getGuardPostsOfSector(PrisonSector sector) {
    GuardPostService guardPostService =
        RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
    if (guardPostService == null) {
      return new GuardPost[0];
    }
    ArrayList<GuardPost> guardPosts = new ArrayList<>();
    for (GuardPost guardPost : guardPostService.getGuardPostTimeMap().keySet()) {
      if (PrisonSector.fromString(guardPost.getPrisonSector()).equals(sector)) {
        guardPosts.add(guardPost);
        continue;
      }
      if (sector.equals(PrisonSector.A) && PrisonSector.fromString(guardPost.getPrisonSector()).equals(PrisonSector.A_PLUS)) {
        guardPosts.add(guardPost);
        continue;
      }
      if (sector.equals(PrisonSector.B) && PrisonSector.fromString(guardPost.getPrisonSector()).equals(PrisonSector.B_PLUS)) {
        guardPosts.add(guardPost);
      }
    }
    return guardPosts.toArray(new GuardPost[0]);
  }

  private TextComponent cooldownComponent(GuardPost guardPost) {
    GuardPostService guardPostService =
        RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
    if (guardPostService == null) {
      return Component.empty();
    }
    long cooldown = guardPostService.getCooldownTime(guardPost);
    if (cooldown <= 0) {
      return Component.text("0s", NamedTextColor.GREEN);
    }
    return Component.text(formatTime(cooldown), NamedTextColor.AQUA);
  }

  private String formatTime(long time) {
    StringBuilder builder = new StringBuilder();
    long seconds = time / 1000;
    long minutes = seconds / 60;
    long hours = minutes / 60;
    if (hours > 0) {
      builder.append(hours).append("t ");
    }
    if (minutes > 0) {
      builder.append(minutes % 60).append("m ");
    }
    if (seconds > 0) {
      builder.append(seconds % 60).append("s");
    }
    return builder.toString();
  }
}
