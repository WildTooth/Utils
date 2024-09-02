package com.github.wildtooth.guardian.api;

import com.github.wildtooth.guardian.api.util.MessageType;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Map;

/**
 * Constants used throughout the addon to provide a consistent set of values for both the addon itself and depending addons.
 * <p>
 *   This interface provides access to the following sub-interfaces:
 *   <ul>
 *     <li>{@link I18nKeys} - Contains keys for internationalization.</li>
 *     <li>{@link Icons} - Contains icons used throughout the addon.</li>
 *     <li>{@link Data} - Contains data used throughout the addon.</li>
 *   </ul>
 *   Implementations of this interface should provide the actual values to return in these methods.
 */
public interface Constants {
  I18nKeys i18nKeys();
  Icons icons();
  Data data();
  FreakyVilleMessages freakyVilleMessages();

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

  interface FreakyVilleMessages {
    Map<String, MessageType> messages();
  }
}
