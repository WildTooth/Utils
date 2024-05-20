package com.github.wildtooth.guardian.core.guard;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.util.PrisonSector;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.Triple;

public class DefaultGuardPost implements GuardPost {
  private final String prisonSector;
  private final int numericalIdentifier;
  private final String displayName;
  private final int personalCooldown;
  private final Triple<Integer, Integer, Integer> coordinates;

  public DefaultGuardPost(String prisonSector, int numericalIdentifier, String displayName, int personalCooldown, Triple<Integer, Integer, Integer> coordinates) {
    this.prisonSector = prisonSector;
    this.numericalIdentifier = numericalIdentifier;
    this.displayName = displayName;
    this.personalCooldown = personalCooldown;
    this.coordinates = coordinates;
  }

  @Override
  public String getPrisonSector() {
    return prisonSector;
  }

  @Override
  public int getNumericalIdentifier() {
    return numericalIdentifier;
  }

  @Override
  public String displayName() {
    return displayName;
  }

  @Override
  public int getPersonalCooldown() {
    return personalCooldown;
  }

  @Override
  public Triple<Integer, Integer, Integer> getCoordinates() {
    return coordinates;
  }

  @Override
  public String combinedIdentifier() {
    return prisonSector + "_" + numericalIdentifier;
  }

  @Override
  public Component toComponent() {
    Component component = Component.empty();
    component.append(Component.text("[", NamedTextColor.DARK_GRAY));
    component.append(PrisonSector.fromString(prisonSector).toComponent());
    component.append(Component.space());
    component.append(Component.text("#" + numericalIdentifier, NamedTextColor.GRAY));
    component.append(Component.text("] ", NamedTextColor.DARK_GRAY));

    component.append(Component.text(displayName, NamedTextColor.WHITE));

    return component;
  }

  @Override
  public String toString() {
    return "DefaultGuardPost{" +
        "prisonSector=" + prisonSector +
        ", numericalIdentifier=" + numericalIdentifier +
        ", displayName='" + displayName + '\'' +
        ", coordinates=" + coordinates +
        '}';
  }
}
