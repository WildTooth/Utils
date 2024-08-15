package com.github.wildtooth.guardian.api.event.guardpost;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import org.jetbrains.annotations.NotNull;

public class GuardPostTryEvent extends GuardPostEvent {

  

  public GuardPostTryEvent(@NotNull GuardPost guardPost) {
    super(guardPost);
  }
}
