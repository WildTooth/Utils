package com.github.wildtooth.guardian.api.save;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import java.util.HashMap;
import java.util.Map;

public class GuardPostSaveData implements SaveData {
  private final Map<GuardPost, Long> lastGuardPostVisit;
  private final long saveTime;

  private GuardPostSaveData(Map<GuardPost, Long> lastGuardPostVisit, long saveTime) {
    this.lastGuardPostVisit = lastGuardPostVisit;
    this.saveTime = saveTime;
  }

  public Map<GuardPost, Long> getLastGuardPostVisit() {
    return this.lastGuardPostVisit;
  }

  @Override
  public long getSaveTime() {
    return this.saveTime;
  }

  public static GuardPostSaveData create(GuardPostService guardPostService) {
    Map<GuardPost, Long> lastGuardPostVisit = guardPostService.getGuardPostTimeMap();
    Map<GuardPost, Long> lastGuardPostVisitCopy = new HashMap<>(Map.copyOf(lastGuardPostVisit));
    for (GuardPost guardPost : lastGuardPostVisit.keySet()) {
      if (guardPostService.getCooldownTime(guardPost) >= 0) {
        lastGuardPostVisitCopy.remove(guardPost);
      }
    }
    return new GuardPostSaveData(lastGuardPostVisitCopy, System.currentTimeMillis());
  }

  public Map<GuardPost, Long> handleUnload(GuardPostService guardPostService) {
    Map<GuardPost, Long> lastGuardPostVisitCopy = new HashMap<>(Map.copyOf(this.lastGuardPostVisit));
    for (GuardPost guardPost : this.lastGuardPostVisit.keySet()) {
      if (guardPostService.getCooldownTime(guardPost) >= 0) {
        lastGuardPostVisitCopy.remove(guardPost);
      }
    }
    return lastGuardPostVisitCopy;
  }
}
