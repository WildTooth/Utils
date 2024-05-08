package com.github.wildtooth.guardian.core;

import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import com.github.wildtooth.guardian.core.guard.DefaultGuard;
import com.github.wildtooth.guardian.core.services.DefaultRegistry;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardService;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.I18n;
import java.util.UUID;

@AddonMain
public class GuardianAddon extends LabyAddon<GuardianConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    RegistryProvider.setRegistry(new DefaultRegistry());

    RegistryProvider.getRegistry().register(GuardService.class, new DefaultGuardService(), false);

    RegistryProvider.getRegistry().get(GuardService.class).get().addGuard(new DefaultGuard(UUID.fromString("81f49df3-94a5-4f94-8000-c5c18b6261ba"), "CV1"));

    this.logger().info(I18n.translate("guardian.log.enabled"));
  }

  @Override
  protected Class<GuardianConfiguration> configurationClass() {
    return GuardianConfiguration.class;
  }
}
