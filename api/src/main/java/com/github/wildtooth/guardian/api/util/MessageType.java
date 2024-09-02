package com.github.wildtooth.guardian.api.util;

import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFailEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFinishEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostTryEvent;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

public enum MessageType {
  GUARD_POST_TRY(new GuardPostTryEvent(null)),
  GUARD_POST_FAIL(new GuardPostFailEvent(null)),
  GUARD_POST_SUCCESS(new GuardPostFinishEvent(null))
  ;

  private final @Nullable Event event;

  MessageType() {
    this.event = null;
  }

  MessageType(@Nullable Event event) {
    this.event = event;
  }

  public @Nullable Event getEvent() {
    return event;
  }
}
