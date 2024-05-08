package com.github.wildtooth.guardian.core.util;

import com.github.wildtooth.guardian.api.Constants;
import com.github.wildtooth.guardian.api.ConstantsProvider;
import net.labymod.api.client.chat.Title;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.Notification.Type;
import net.labymod.api.util.Pair;
import java.util.Objects;

public final class Hud {

  private static Constants constants = ConstantsProvider.getConstants();

  private static final Pair<TextColor, TextColor> infoColorPair = Pair.of(NamedTextColor.GREEN, NamedTextColor.DARK_GREEN);
  private static final Pair<TextColor, TextColor> warnColorPair = Pair.of(NamedTextColor.YELLOW, NamedTextColor.GOLD);
  private static final Pair<TextColor, TextColor> errorColorPair = Pair.of(NamedTextColor.RED, NamedTextColor.DARK_RED);
  private static final Pair<TextColor, TextColor> debugColorPair = Pair.of(NamedTextColor.LIGHT_PURPLE, NamedTextColor.DARK_PURPLE);

  private Hud() {

  }

  public static void displayTitle(Pair<Component, Component> titleSubTitlePair, int fadeInTicks, int stayTicks, int fadeOutTicks) {
    Title.Builder titleBuilder = Title.builder();
    titleBuilder.title(Objects.requireNonNull(titleSubTitlePair.getFirst()));
    titleBuilder.subTitle(Objects.requireNonNull(titleSubTitlePair.getSecond()));
    titleBuilder.timing(fadeInTicks, stayTicks, fadeOutTicks);
    titleBuilder.show();
  }

  public static void info(String key, Object... args) {
    if (constants == null) {
      constants = ConstantsProvider.getConstants();
    }
    Notification.Builder info = Notification.builder()
        .title(Component.translatable(constants.i18nKeys().notifications() + ".info", coloredBold(infoColorPair.getSecond())))
        .text(translate(key, infoColorPair, args))
        .type(Type.SYSTEM)
        .icon(constants.icons().notificationInfoIcon());
    info.buildAndPush();
  }

  public static void warn(String key, Object... args) {
    if (constants == null) {
      constants = ConstantsProvider.getConstants();
    }
    Notification.Builder warn = Notification.builder()
        .title(Component.translatable(constants.i18nKeys().notifications() + ".warn", coloredBold(warnColorPair.getSecond())))
        .text(translate(key, warnColorPair, args))
        .type(Type.SYSTEM)
        .icon(constants.icons().notificationWarnIcon());
    warn.buildAndPush();
  }

  public static void error(String key, Object... args) {
    if (constants == null) {
      constants = ConstantsProvider.getConstants();
    }
    Notification.Builder error = Notification.builder()
        .title(Component.translatable(constants.i18nKeys().notifications() + ".error", coloredBold(errorColorPair.getSecond())))
        .text(translate(key, errorColorPair, args))
        .type(Type.SYSTEM)
        .icon(constants.icons().notificationErrorIcon());
    error.buildAndPush();
  }

  public static void debug(String key, Object... args) {
    if (constants == null) {
      constants = ConstantsProvider.getConstants();
    }
    Notification.Builder debug = Notification.builder()
        .title(Component.translatable(constants.i18nKeys().notifications() + ".debug", coloredBold(debugColorPair.getSecond())))
        .text(translate(key, debugColorPair, args))
        .type(Type.SYSTEM)
        .icon(constants.icons().notificationDebugIcon());
    debug.buildAndPush();
  }

  private static Component translate(String key, Pair<TextColor, TextColor> colorPair, Object... args) {
    Component[] componentArgs = new Component[args.length];
    for (int i = 0; i < componentArgs.length; i++) {
      componentArgs[i] = Component.text(args[i], colorPair.getSecond());
    }
    return Component.translatable(key, colorPair.getFirst(), componentArgs);
  }

  private static Style coloredBold(TextColor textColor) {
    return Style.builder()
        .color(textColor)
        .decorate(TextDecoration.BOLD)
        .build();
  }
}
