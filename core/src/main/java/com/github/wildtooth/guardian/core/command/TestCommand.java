package com.github.wildtooth.guardian.core.command;

import com.github.wildtooth.guardian.api.event.guard.GuardDeathEvent;
import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent;
import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent.ShiftSwitch;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.guard.Guard;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.util.PrisonSector;
import com.github.wildtooth.guardian.core.guard.DefaultGuard;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.Objects;
import java.util.UUID;

public class TestCommand extends Command {

  private final Guard guard = new DefaultGuard(UUID.randomUUID(), "TestGuard");
  private final FreakyvilleConnection freakyvilleConnection;

  public TestCommand(FreakyvilleConnection freakyvilleConnection) {
    super("test", "damn");
    this.freakyvilleConnection = freakyvilleConnection;
  }

  @Override
  public boolean execute(String s, String[] strings) {
    if (Objects.equals(s, "test")) {
      freakyvilleConnection.setPrison(PrisonSector.A);
      Laby.fireEvent(new GuardShiftSwitchEvent(guard, ShiftSwitch.COMING_IN));
      GuardPostService guardPostService =
          RegistryProvider.getRegistry().get(GuardPostService.class).orElse(null);
      if (guardPostService == null) {
        return false;
      }
      guardPostService.putGuardPostOnCooldown(guardPostService.getGuardPost("C_1").orElse(null));
      guardPostService.putGuardPostOnCooldown(guardPostService.getGuardPost("C_2").orElse(null));
      guardPostService.putGuardPostOnCooldown(guardPostService.getGuardPost("B_1").orElse(null));
      guardPostService.putGuardPostOnCooldown(guardPostService.getGuardPost("B_2").orElse(null));
      guardPostService.putGuardPostOnCooldown(guardPostService.getGuardPost("A_1").orElse(null));
      guardPostService.putGuardPostOnCooldown(guardPostService.getGuardPost("A+_6").orElse(null));
      displayMessage(Component.text("Test successful", NamedTextColor.GREEN));
    } else {
      freakyvilleConnection.setPrison(PrisonSector.A);
      Laby.fireEvent(new GuardDeathEvent(guard, Component.text("B", NamedTextColor.AQUA)));
    }
    return true;
  }
}
