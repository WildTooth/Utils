package com.github.wildtooth.guardian.api.service.guard;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;
import com.github.wildtooth.guardian.api.save.PersistentDataHolder;
import java.util.Map;
import java.util.Optional;

public interface GuardPostService extends Service, Registrable, PersistentDataHolder {

  Optional<? extends GuardPost> getGuardPost(String identifier);

  long getCooldownTime(GuardPost guardPost);

  void addGuardPost(GuardPost guardPost);

  void removeGuardPost(String identifier);

  boolean hasGuardPost(GuardPost guardPost);

  boolean hasGuardPost(String identifier);

  void putGuardPostOnCooldown(GuardPost guardPost);

  boolean isGuardPostOnCooldown(GuardPost guardPost);

  Map<GuardPost, Long> getGuardPostTimeMap();

  GuardPost getNearestGuardPost();

  GuardPost createGuardPost(String prisonSector, int numericalIdentifier, String displayName, int personalCooldown, int x, int y, int z);
}
