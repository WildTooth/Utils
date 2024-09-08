package com.github.wildtooth.guardian.core.widgets;

import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine.State;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.I18n;
import java.time.Duration;

public class TimerWidget extends TextHudWidget<TextHudWidgetConfig> {

  private TextLine textLine;
  private final String timerName;
  private final Icon associatedIcon;

  private long startTime;
  private final Duration duration;

  private boolean isRunning = false;

  public TimerWidget(String id, String timerName, Icon associatedIcon, long startTime, Duration duration) {
    super(id);
    this.timerName = timerName;
    this.associatedIcon = associatedIcon;
    this.startTime = startTime;
    this.duration = duration;
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);
    String currentTimeLeft = this.getTimeLeft();
    boolean shouldShow = !currentTimeLeft.isEmpty();
    if (currentTimeLeft.isEmpty()) {
      currentTimeLeft = I18n.translate("guardian.widgets.timer.timeLeft.unavailable");
    }
    this.textLine = super.createLine(this.timerName, currentTimeLeft);
    if (shouldShow) {
      this.textLine.setState(State.VISIBLE);
    } else {
      this.textLine.setState(State.HIDDEN);
    }
    this.setIcon(this.associatedIcon);
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    if (!this.getTimeLeft().isEmpty()) {
      this.textLine.update(this.getTimeLeft());
      this.textLine.setState(State.VISIBLE);
    } else if (this.textLine == null) {
      this.textLine = this.createLine(this.timerName, " ");
      this.textLine.setState(State.HIDDEN);
    } else if (this.getTimeLeft().isEmpty()) {
      this.textLine.update(I18n.translate("guardian.widgets.timer.timeLeft.unavailable"));
      this.textLine.setState(State.HIDDEN);
    }
  }

  private String getTimeLeft() {
    if (!this.isRunning) {
      return "";
    }
    StringBuilder timeLeft = new StringBuilder();
    long timeLeftMillis = this.startTime + this.duration.toMillis() - System.currentTimeMillis();
    if (timeLeftMillis >= 0) {
      Duration timeLeftDuration = Duration.ofMillis(timeLeftMillis);
      if (timeLeftDuration.toHours() > 0) {
        timeLeft.append(timeLeftDuration.toHours()).append("t ");
      }
      if (timeLeftDuration.toMinutes() > 0) {
        timeLeft.append(timeLeftDuration.toMinutes() % 60).append("m ");
      }
      timeLeft.append(timeLeftDuration.getSeconds() % 60).append("s");
    } else {
      this.isRunning = false;
      return "";
    }
    return timeLeft.toString();
  }

  public void startTimer() {
    this.startTime = System.currentTimeMillis();
    this.isRunning = true;
  }

  public void stopTimer() {
    this.isRunning = false;
  }
}
