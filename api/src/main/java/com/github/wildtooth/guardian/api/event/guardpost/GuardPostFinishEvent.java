package com.github.wildtooth.guardian.api.event.guardpost;

import com.github.wildtooth.guardian.api.guard.GuardPost;

public class GuardPostFinishEvent extends GuardPostEvent {

  private final long time = System.currentTimeMillis();

  public GuardPostFinishEvent(GuardPost guardPost) {
    super(guardPost);
  }

  public long getTime() {
    return time;
  }
}
