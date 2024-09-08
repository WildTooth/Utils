package com.github.wildtooth.guardian.core.widgets;

import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine.State;

public class RobberWidget extends TextHudWidget<TextHudWidgetConfig> {

  private TextLine textLine;
  private String widgetName;
  private String robberName;

  public RobberWidget(String id, String widgetName, String robberName) {
    super(id);
    this.widgetName = widgetName;
    this.robberName = robberName;
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);
    if (this.robberName.isEmpty()) {
      this.textLine = super.createLine(this.widgetName, " ");
      this.textLine.setState(State.HIDDEN);
    } else {
      this.textLine = super.createLine(this.widgetName, this.robberName);
      this.textLine.setState(State.VISIBLE);
    }
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    if (this.robberName.isEmpty()) {
      this.textLine.update(" ");
      this.textLine.setState(State.HIDDEN);
    } else {
      this.textLine.update(this.robberName);
      this.textLine.setState(State.VISIBLE);
    }
  }
  
  public void setRobberName(String robberName) {
    this.robberName = robberName;
  }

  public String getRobberName() {
    return this.robberName;
  }
}
