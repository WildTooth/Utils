package com.github.wildtooth.guardian.core;

import com.github.wildtooth.guardian.core.services.Registry;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.I18n;

@AddonMain
public class GuardianAddon extends LabyAddon<GuardianConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    Registry registry = Registry.getInstance();

    this.logger().info(I18n.translate("guardian.log.enabled"));
  }

  @Override
  protected Class<GuardianConfiguration> configurationClass() {
    return GuardianConfiguration.class;
  }
}
