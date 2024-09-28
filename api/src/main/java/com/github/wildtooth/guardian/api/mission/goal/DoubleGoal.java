package com.github.wildtooth.guardian.api.mission.goal;

public class DoubleGoal extends AbstractGoal {
  private double currentValue;
  private final double expectedValue;

  public DoubleGoal(double currentValue, double expectedValue) {
    this.currentValue = currentValue;
    this.expectedValue = expectedValue;
  }

  public double getCurrentValue() {
    return this.currentValue;
  }

  public void increaseValue(double byAmount) {
    this.currentValue = currentValue + byAmount;
    if (this.currentValue >= this.expectedValue) {
      this.completed = true;
    }
  }

  public double getExpectedValue() {
    return this.expectedValue;
  }
}
