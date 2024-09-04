package com.github.wildtooth.guardian.core.util;

import com.github.wildtooth.guardian.api.Constants;
import com.github.wildtooth.guardian.api.util.MessageType;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.Pair;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;

public class DefaultConstants implements Constants {

  private final I18nKeys i18nKeys = new DefaultI18nKeys();
  private final Icons icons = new DefaultIcons();
  private final Data data = new DefaultData();
  private final FreakyVilleMessages freakyVilleMessages = new DefaultFreakyVilleMessages();

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

  @Override
  public FreakyVilleMessages freakyVilleMessages() {
    return freakyVilleMessages;
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

    private final String base = "https://raw.githubusercontent.com/FreakyVille-Trademarket/.github/main/scripture/icons/";
    private final Icon notificationInfoIcon = Icon.url(base + "info.png");
    private final Icon notificationWarnIcon = Icon.url(base + "warn.png");
    private final Icon notificationErrorIcon = Icon.url(base + "error.png");
    private final Icon notificationDebugIcon = Icon.url(base + "debug.png");

    @Override
    public Icon notificationInfoIcon() {
      return notificationInfoIcon;
    }

    @Override
    public Icon notificationWarnIcon() {
      return notificationWarnIcon;
    }

    @Override
    public Icon notificationErrorIcon() {
      return notificationErrorIcon;
    }

    @Override
    public Icon notificationDebugIcon() {
      return notificationDebugIcon;
    }
  }

  private static class DefaultData implements Data {
    @Override
    public String guardPostData() {
      return "https://raw.githubusercontent.com/WildTooth/FreakyVille-General-Data/main/entities/vagtposter.csv";
    }
  }

  private static class DefaultFreakyVilleMessages implements FreakyVilleMessages {
    @Override
    public ImmutableMap<String, MessageType> messages() {
      return ImmutableMap.<String, MessageType>builder()
          .put("Stå stille i 10 sekunder for at aktivere vagtposten!", MessageType.GUARD_POST_TRY)
          .put("Stå stille i 20 sekunder for at aktivere vagtposten!", MessageType.GUARD_POST_TRY)
          .put("Du bevægede dig og har stoppet med at aktivere VagtPosten", MessageType.GUARD_POST_FAIL)
          .put("Godt gået! Du aktiverede denne vagtpost!", MessageType.GUARD_POST_SUCCESS)
          .build();
    }

    @Override
    public ImmutableMap<Pair<String, String>, MessageType> advancedMessages() {
      return ImmutableMap.<Pair<String, String>, MessageType>builder()
          .put(Pair.of("VAGT-ALARM:", "prøver at bryde pengeskabet op i banken i A+ Området!"), MessageType.A_PLUS_GUARD_VAULT_START)
          .put(Pair.of("VAGT-ALARM:", "forsøger at røve vagternes værdi-boks i A!"), MessageType.A_GUARD_VAULT_START)
          .put(Pair.of("VAGT-ALARM:", "prøver at bryde pengeskabet op i banken i B+ Området!"), MessageType.B_PLUS_GUARD_VAULT_START)

          .put(Pair.of("VAGT-ALARM: Det lykkedes", "at bryde bankens pengeskab op i A+ Området!"), MessageType.A_PLUS_GUARD_VAULT_FINISH)
          .put(Pair.of("VAGT-ALARM:", "gennemførte et røveri i vagternes værdi-boks i A!"), MessageType.A_GUARD_VAULT_FINISH)
          .put(Pair.of("VAGT-ALARM: Det lykkedes", "at bryde bankens pengeskab op i B+ Området!"), MessageType.B_PLUS_GUARD_VAULT_FINISH)

          .build();
    }
  }
}
