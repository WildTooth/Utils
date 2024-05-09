package com.github.wildtooth.guardian.core.listener;

import com.github.wildtooth.guardian.api.event.GuardShiftSwitchEvent;
import com.github.wildtooth.guardian.core.util.Hud;
import net.labymod.api.event.Subscribe;

public class GuardShiftSwitchListener {

  @Subscribe
  public void onGuardShiftSwitch(GuardShiftSwitchEvent event) {
      if (event.getShiftSwitch() == GuardShiftSwitchEvent.ShiftSwitch.COMING_IN) {
        Hud.info("guardian.event.shiftSwitch.join", event.getGuard().getName());
      } else if (event.getShiftSwitch() == GuardShiftSwitchEvent.ShiftSwitch.GOING_OUT) {
        Hud.info("guardian.event.shiftSwitch.leave", event.getGuard().getName());
      }
  }
}
