package com.github.wildtooth.guardian.api.guard;

import net.labymod.api.client.component.Component;
import net.labymod.api.util.Triple;

public interface GuardPost {

  String getPrisonSector();

  int getNumericalIdentifier();

  String displayName();

  int getPersonalCooldown();

  Triple<Integer, Integer, Integer> getCoordinates();

  String combinedIdentifier();

  Component toComponent();

  @Override
  String toString();

  @Override
  boolean equals(Object obj);

  @Override
  int hashCode();
}
