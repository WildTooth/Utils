package com.github.wildtooth.guardian.api.save;

import java.io.File;

public interface PersistentDataHolder {
  void loadSaveData(SaveData saveData);
  void save();
  File getSavePath();
}
