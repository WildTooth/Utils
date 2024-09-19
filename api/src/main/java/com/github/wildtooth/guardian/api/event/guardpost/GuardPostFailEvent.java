package com.github.wildtooth.guardian.api.event.guardpost;

import com.github.wildtooth.guardian.api.guardpost.GuardPost;
import org.jetbrains.annotations.Nullable;

public class GuardPostFailEvent extends GuardPostEvent {
  public GuardPostFailEvent(@Nullable GuardPost guardPost) {
    super(guardPost);
  }
}
