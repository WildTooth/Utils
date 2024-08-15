package com.github.wildtooth.guardian.core.command;

import com.github.wildtooth.guardian.api.event.guard.GuardDeathEvent;
import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent;
import com.github.wildtooth.guardian.api.event.guard.GuardShiftSwitchEvent.ShiftSwitch;
import com.github.wildtooth.guardian.api.guard.Guard;
import com.github.wildtooth.guardian.core.guard.DefaultGuard;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.Objects;
import java.util.UUID;

public class TestCommand extends Command {

  private final Guard guard = new DefaultGuard(UUID.randomUUID(), "TestGuard");

  public TestCommand() {
    super("test", "damn");
  }

  @Override
  public boolean execute(String s, String[] strings) {
    if (Objects.equals(s, "test")) {
      Laby.fireEvent(new GuardShiftSwitchEvent(guard, ShiftSwitch.COMING_IN));
    } else {
      Laby.fireEvent(new GuardDeathEvent(guard, Component.text("B", NamedTextColor.AQUA)));
    }
    return true;
  }
}
