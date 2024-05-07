package com.github.wildtooth.guardian.api.service.guard;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;
import java.util.Optional;

public interface GuardPostService extends Service, Registrable {

  Optional<? extends GuardPost> getGuardPost(String identifier);

  <GP extends GuardPost> void addGuardPost(GP guardPost);

  void removeGuardPost(String identifier);

  <GP extends GuardPost> boolean hasGuardPost(GP guardPost);

  boolean hasGuardPost(String identifier);
}
