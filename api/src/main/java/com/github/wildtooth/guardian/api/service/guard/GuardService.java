package com.github.wildtooth.guardian.api.service.guard;

import com.github.wildtooth.guardian.api.guard.Guard;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;
import java.util.Optional;
import java.util.UUID;

public interface GuardService extends Service, Registrable {
  Optional<? extends Guard> getGuard(UUID uniqueId);

  <G extends Guard> void addGuard(G guard);

  void removeGuard(UUID uniqueId);

  boolean hasGuard(UUID uniqueId);

  <G extends Guard> boolean hasGuard(G guard);
}
