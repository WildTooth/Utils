package com.github.wildtooth.guardian.core;

import com.github.wildtooth.guardian.api.ConstantsProvider;
import com.github.wildtooth.guardian.api.gson.SpecializedGsonProvider;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import com.github.wildtooth.guardian.core.command.TestCommand;
import com.github.wildtooth.guardian.core.gson.DefaultSpecializedGson;
import com.github.wildtooth.guardian.core.listener.GameShutdownListener;
import com.github.wildtooth.guardian.core.listener.GuardDeathListener;
import com.github.wildtooth.guardian.core.listener.GuardShiftSwitchListener;
import com.github.wildtooth.guardian.core.services.DefaultRegistry;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardPostService;
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

    SpecializedGsonProvider.setSpecializedGson(new DefaultSpecializedGson());

    ConstantsProvider.setConstants(new DefaultConstants());

    RegistryProvider.setRegistry(new DefaultRegistry());
    RegistryProvider.getRegistry().register(GuardService.class, new DefaultGuardService(), false);
    RegistryProvider.getRegistry().register(GuardPostService.class, new DefaultGuardPostService(), false);

    registerCommand(new TestCommand());
    registerListener(new GuardShiftSwitchListener());
    registerListener(new GuardDeathListener());
    registerListener(new GameShutdownListener());

    this.logger().info(I18n.translate("guardian.log.enabled"));
  }

  @Override
  protected Class<GuardianConfiguration> configurationClass() {
    return GuardianConfiguration.class;
  }
}
