package com.github.wildtooth.guardian.api.mission.goal;

public class CheckGoal extends AbstractGoal {
  private final boolean[] checks;

  public CheckGoal(int checkCount) {
    this.checks = new boolean[checkCount];
  }

  public int getCheckCount() {
    return this.checks.length;
  }

  public boolean getCheck(int index) {
    return this.checks[index];
  }

  public void setCheck(int index, boolean value) {
    this.checks[index] = value;
  }
}
