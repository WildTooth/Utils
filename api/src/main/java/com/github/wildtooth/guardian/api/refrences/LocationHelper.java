package com.github.wildtooth.guardian.api.refrences;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.IntIntTriple;

@Referenceable
public interface LocationHelper {
  IntIntTriple<Integer> getIntCoordinates(double range);
}
