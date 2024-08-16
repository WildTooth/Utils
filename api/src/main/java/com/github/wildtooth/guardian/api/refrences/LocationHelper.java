package com.github.wildtooth.guardian.api.refrences;

import com.github.wildtooth.guardian.api.service.Registrable;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Triple;
import javax.inject.Singleton;

@Singleton
@Referenceable
public interface LocationHelper extends Registrable {
  Triple<Integer, Integer, Integer> getIntCoordinates(double range);
}
