package com.github.wildtooth.guardian.api.mission;

public enum MissionType {
  RANSACK_GUARD_POST,
  PATROL_LOCATIONS,
  KILL_INMATES,
  JAIL_INMATES,
  EAT_SPECIFIED_FOOD,
  JUMP_AT_LOCATION,
  RECEIVE_SALARY,
  SELL_SPECIFIED_ITEM,
  SELL_FOR_AMOUNT,
  SEARCH_INMATE,
  CHECK_PORTALS,
  ;

  MissionType() {
  }

  @Override
  public String toString() {
    return this.name();
  }
}
