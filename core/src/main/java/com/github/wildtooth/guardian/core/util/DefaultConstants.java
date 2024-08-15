package com.github.wildtooth.guardian.core.util;

import com.github.wildtooth.guardian.api.Constants;
import net.labymod.api.client.gui.icon.Icon;

public class DefaultConstants implements Constants {

  private final I18nKeys i18nKeys = new DefaultI18nKeys();
  private final Icons icons = new DefaultIcons();
  private final Data data = new DefaultData();

  @Override
  public I18nKeys i18nKeys() {
    return i18nKeys;
  }

  @Override
  public Icons icons() {
    return icons;
  }

  @Override
  public Data data() {
    return data;
  }

  private static class DefaultI18nKeys implements I18nKeys {
    @Override
    public String head() {
      return "guardian";
    }

    @Override
    public String settings() {
      return head() + ".settings";
    }

    @Override
    public String log() {
      return head() + ".log";
    }

    @Override
    public String registry() {
      return head() + ".registry";
    }

    @Override
    public String service() {
      return head() + ".service";
    }

    @Override
    public String notifications() {
      return head() + ".notifications";
    }
  }

  private static class DefaultIcons implements Icons {
    @Override
    public Icon notificationInfoIcon() {
      return Icon.url("https://raw.githubusercontent.com/FreakyVille-Trademarket/.github/main/scripture/icons/info.png");
    }

    @Override
    public Icon notificationWarnIcon() {
      return Icon.url("https://raw.githubusercontent.com/FreakyVille-Trademarket/.github/main/scripture/icons/warn.png");
    }

    @Override
    public Icon notificationErrorIcon() {
      return Icon.url("https://raw.githubusercontent.com/FreakyVille-Trademarket/.github/main/scripture/icons/error.png");
    }

    @Override
    public Icon notificationDebugIcon() {
      return Icon.url("https://raw.githubusercontent.com/FreakyVille-Trademarket/.github/main/scripture/icons/debug.png");
    }
  }

  private static class DefaultData implements Data {
    @Override
    public String guardPostData() {
      return "https://raw.githubusercontent.com/WildTooth/FreakyVille-General-Data/main/entities/vagtposter.csv";
    }
  }
}
