package com.github.wildtooth.guardian.core;

import com.github.wildtooth.guardian.api.ConstantsProvider;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import com.github.wildtooth.guardian.core.services.DefaultRegistry;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardService;
import com.github.wildtooth.guardian.core.util.DefaultConstants;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.I18n;

@AddonMain
public class GuardianAddon extends LabyAddon<GuardianConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    RegistryProvider.setRegistry(new DefaultRegistry());
    RegistryProvider.getRegistry().register(GuardService.class, new DefaultGuardService(), false);

    ConstantsProvider.setConstants(new DefaultConstants());

    this.logger().info(I18n.translate("guardian.log.enabled"));
  }

  @Override
  protected Class<GuardianConfiguration> configurationClass() {
    return GuardianConfiguration.class;
  }
}
