package com.github.wildtooth.guardian.api;

import net.labymod.api.client.gui.icon.Icon;

public interface Constants {
  I18nKeys i18nKeys();
  Icons icons();
  Data data();

  interface I18nKeys {
    String head();
    String settings();
    String log();
    String registry();
    String service();
    String notifications();
  }

  interface Icons {
    Icon notificationInfoIcon();
    Icon notificationWarnIcon();
    Icon notificationErrorIcon();
    Icon notificationDebugIcon();
  }

  interface Data {
    String guardPostData();
  }
}
