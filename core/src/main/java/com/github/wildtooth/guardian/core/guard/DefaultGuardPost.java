package com.github.wildtooth.guardian.core.guard;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import net.labymod.api.util.Triple;

public class DefaultGuardPost implements GuardPost {
  private final char[] prisonSector;
  private final int numericalIdentifier;
  private final String displayName;
  private final Triple<Integer, Integer, Integer> coordinates;

  public DefaultGuardPost(char[] prisonSector, int numericalIdentifier, String displayName, Triple<Integer, Integer, Integer> coordinates) {
    this.prisonSector = prisonSector;
    this.numericalIdentifier = numericalIdentifier;
    this.displayName = displayName;
    this.coordinates = coordinates;
  }

  @Override
  public char[] getPrisonSector() {
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
  public Triple<Integer, Integer, Integer> getCoordinates() {
    return coordinates;
  }

  @Override
  public String combinedIdentifier() {
    return new String(prisonSector) + "_" + numericalIdentifier;
  }
}
