package com.github.wildtooth.guardian.api.event.guard;

import com.github.wildtooth.guardian.api.guard.Guard;
import org.jetbrains.annotations.NotNull;

public class GuardShiftSwitchEvent extends GuardEvent {
  private final ShiftSwitch shiftSwitch;

  public GuardShiftSwitchEvent(@NotNull Guard guard, ShiftSwitch shiftSwitch) {
    super(guard);
    this.shiftSwitch = shiftSwitch;
  }

  public ShiftSwitch getShiftSwitch() {
    return this.shiftSwitch;
  }

  public enum ShiftSwitch {
    COMING_IN,
    GOING_OUT
  }
}
