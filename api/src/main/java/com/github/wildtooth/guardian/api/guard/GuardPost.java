package com.github.wildtooth.guardian.api.guard;

import net.labymod.api.util.Triple;

public interface GuardPost {

  char[] getPrisonSector();

  int getNumericalIdentifier();

  String displayName();

  Triple<Integer, Integer, Integer> getCoordinates();

  String combinedIdentifier();
}
