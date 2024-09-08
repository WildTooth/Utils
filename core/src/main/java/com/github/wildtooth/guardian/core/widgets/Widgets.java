package com.github.wildtooth.guardian.core.widgets;

import net.labymod.api.client.gui.icon.Icon;
import java.time.Duration;

public class Widgets {
  public static final TimerWidget B_PLUS_TIMER = new TimerWidget("b_plus_timer", "B+ Ledetid", Icon.head("hey"), 0L, Duration.ofSeconds(30));
  public static final TimerWidget A_PLUS_TIMER = new TimerWidget("a_plus_timer", "A+ Ledetid", Icon.head("hey"), 0L, Duration.ofMinutes(5));
  public static final TimerWidget A_TIMER = new TimerWidget("a_timer", "A Ledetid", Icon.head("hey"), 0L, Duration.ofMinutes(5));

  public static final RobberWidget B_PLUS_ROBBER_WIDGET = new RobberWidget("b_plus_robber_widget", "B+ Røver", "");
  public static final RobberWidget A_PLUS_ROBBER_WIDGET = new RobberWidget("a_plus_robber_widget", "A+ Røver", "");
  public static final RobberWidget A_ROBBER_WIDGET = new RobberWidget("a_robber_widget", "A Røver", "");
}
