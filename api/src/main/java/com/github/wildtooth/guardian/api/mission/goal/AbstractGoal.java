package com.github.wildtooth.guardian.api.mission.goal;

public abstract class AbstractGoal {
  protected boolean completed;

  public AbstractGoal() {
    this.completed = false;
  }

  public boolean isCompleted() {
    return this.completed;
  }
}
