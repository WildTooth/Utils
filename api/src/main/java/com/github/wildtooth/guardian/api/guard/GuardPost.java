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

  default boolean equalsOther(GuardPost obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof GuardPost other)) {
      return false;
    }
    String format = "%s:%s - %s, %s, %s";
    String thisCheckValue = String.format(format
        , this.getPrisonSector()
        , this.getNumericalIdentifier()
        , this.getCoordinates().getLeft()
        , this.getCoordinates().getMiddle()
        , this.getCoordinates().getRight()
    );
    String otherCheckValue = String.format(format
        , other.getPrisonSector()
        , other.getNumericalIdentifier()
        , other.getCoordinates().getLeft()
        , other.getCoordinates().getMiddle()
        , other.getCoordinates().getRight()
    );
    return thisCheckValue.equals(otherCheckValue);
  }
}
