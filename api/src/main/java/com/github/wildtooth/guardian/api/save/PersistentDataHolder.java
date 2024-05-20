package com.github.wildtooth.guardian.api.save;

public interface PersistentDataHolder {
  void loadSaveData(SaveData saveData);
  void save();
}
