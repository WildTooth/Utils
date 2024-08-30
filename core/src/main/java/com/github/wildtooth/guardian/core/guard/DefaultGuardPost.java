package com.github.wildtooth.guardian.core.guard;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.util.PrisonSector;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.Triple;

public class DefaultGuardPost implements GuardPost {
  private final String prisonSector;
  private final int numericalIdentifier;
  private final String displayName;
  private final int personalCooldown;
  private final int x;
  private final int y;
  private final int z;

  public DefaultGuardPost(String prisonSector, int numericalIdentifier, String displayName, int personalCooldown, int x, int y, int z) {
    this.prisonSector = prisonSector;
    this.numericalIdentifier = numericalIdentifier;
    this.displayName = displayName;
    this.personalCooldown = personalCooldown;
    this.x = x;
    this.y = y;
    this.z = z;
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
    return new Triple<>(x, y, z);
  }

  @Override
  public String combinedIdentifier() {
    return prisonSector + "_" + numericalIdentifier;
  }

  @Override
  public Component toComponent() {
    return TextComponent.builder()
        .append(Component.text("[", NamedTextColor.DARK_GRAY))
        .append(PrisonSector.fromString(prisonSector).toComponent())
        .append(Component.space())
        .append(Component.text("#" + numericalIdentifier, NamedTextColor.GRAY))
        .append(Component.text("] ", NamedTextColor.DARK_GRAY))
        .append(Component.text(displayName, NamedTextColor.WHITE))
        .build();
  }

  @Override
  public String toString() {
    return "DefaultGuardPost{" +
        "prisonSector='" + prisonSector + '\'' +
        ", numericalIdentifier=" + numericalIdentifier +
        ", displayName='" + displayName + '\'' +
        ", personalCooldown=" + personalCooldown +
        ", x=" + x +
        ", y=" + y +
        ", z=" + z +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    DefaultGuardPost that = (DefaultGuardPost) obj;

    if (numericalIdentifier != that.numericalIdentifier) return false;
    if (personalCooldown != that.personalCooldown) return false;
    if (!prisonSector.equals(that.prisonSector)) return false;
    if (!displayName.equals(that.displayName)) return false;
    return getCoordinates().equals(that.getCoordinates());
  }

  @Override
  public int hashCode() {
    int result = prisonSector.hashCode();
    result = 31 * result + numericalIdentifier;
    result = 31 * result + displayName.hashCode();
    result = 31 * result + personalCooldown;
    result = 31 * result + getCoordinates().hashCode();
    return result;
  }
}
