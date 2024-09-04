package com.github.wildtooth.guardian.api.event.guardpost;

import com.github.wildtooth.guardian.api.guard.GuardPost;

public class GuardPostFinishEvent extends GuardPostEvent {

  private final long time;

  public GuardPostFinishEvent(GuardPost guardPost) {
    super(guardPost);
    this.time = System.currentTimeMillis();
  }

  public long getTime() {
    return time;
  }
}
