package com.github.wildtooth.guardian.api.mission.goal;

public class IntegerGoal extends AbstractGoal {
  private int currentValue;
  private final int expectedValue;

  public IntegerGoal(int currentValue, int expectedValue) {
    this.currentValue = currentValue;
    this.expectedValue = expectedValue;
  }

  public int getCurrentValue() {
    return this.currentValue;
  }

  public void setCurrentValue(int currentValue) {
    this.currentValue = currentValue;
  }

  public void incrementCurrentValue() {
    this.currentValue++;
    if (this.currentValue >= this.expectedValue) {
      this.completed = true;
    }
  }

  public int getExpectedValue() {
    return this.expectedValue;
  }
}
