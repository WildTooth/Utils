package com.github.wildtooth.guardian.core;

import com.github.wildtooth.guardian.api.ConstantsProvider;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.generated.ReferenceStorage;
import com.github.wildtooth.guardian.api.gson.SpecializedGsonProvider;
import com.github.wildtooth.guardian.api.refrences.LocationHelper;
import com.github.wildtooth.guardian.api.service.Registry;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import com.github.wildtooth.guardian.api.service.guard.GuardVaultService;
import com.github.wildtooth.guardian.core.command.GuardPostCommand;
import com.github.wildtooth.guardian.core.command.TestCommand;
import com.github.wildtooth.guardian.core.gson.DefaultSpecializedGson;
import com.github.wildtooth.guardian.core.listener.GameShutdownListener;
import com.github.wildtooth.guardian.core.listener.GuardDeathListener;
import com.github.wildtooth.guardian.core.listener.GuardPostListener;
import com.github.wildtooth.guardian.core.listener.GuardShiftSwitchListener;
import com.github.wildtooth.guardian.core.listener.GuardVaultListener;
import com.github.wildtooth.guardian.core.listener.convertions.ChatMessageListener;
import com.github.wildtooth.guardian.core.listener.convertions.PlayerInfoListener;
import com.github.wildtooth.guardian.core.listener.internals.PrisonNavigationListener;
import com.github.wildtooth.guardian.core.listener.internals.ScoreBoardListener;
import com.github.wildtooth.guardian.core.listener.internals.ServerNavigationListener;
import com.github.wildtooth.guardian.core.services.DefaultRegistry;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardPostService;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardService;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardVaultService;
import com.github.wildtooth.guardian.core.util.DataOutput;
import com.github.wildtooth.guardian.core.util.DefaultConstants;
import com.github.wildtooth.guardian.core.widgets.WidgetUpdater;
import com.github.wildtooth.guardian.core.widgets.Widgets;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.I18n;
import net.labymod.api.util.version.SemanticVersion;
import java.util.ArrayList;

@AddonMain
public class GuardianAddon extends LabyAddon<GuardianConfiguration> {



  @Override
  protected void enable() {
    this.registerSettingCategory();

    // Køres først
    boolean needsUpdate = shouldUpdate();

    LocationHelper locationHelper = ((ReferenceStorage) this.referenceStorageAccessor()).locationHelper();

    SpecializedGsonProvider.setSpecializedGson(new DefaultSpecializedGson());

    ConstantsProvider.setConstants(new DefaultConstants());

    Registry registry = new DefaultRegistry();

    registry.register(GuardService.class, new DefaultGuardService(), false);
    registry.register(GuardPostService.class, new DefaultGuardPostService(), false);
    registry.register(GuardVaultService.class, new DefaultGuardVaultService(), false);
    registry.register(LocationHelper.class, locationHelper, false);

    RegistryProvider.setRegistry(registry);

    FreakyvilleConnection freakyvilleConnection = new FreakyvilleConnection(
        this.labyAPI().serverController(),
        this.labyAPI().minecraft().getClientPlayer()
    );

    HudWidgetRegistry hudWidgetRegistry = this.labyAPI().hudWidgetRegistry();

    hudWidgetRegistry.register(Widgets.A_PLUS_TIMER);
    hudWidgetRegistry.register(Widgets.A_TIMER);
    hudWidgetRegistry.register(Widgets.B_PLUS_TIMER);

    hudWidgetRegistry.register(Widgets.A_PLUS_ROBBER_WIDGET);
    hudWidgetRegistry.register(Widgets.A_ROBBER_WIDGET);
    hudWidgetRegistry.register(Widgets.B_PLUS_ROBBER_WIDGET);

    registerListener(new WidgetUpdater(hudWidgetRegistry));

    registerCommand(new TestCommand(freakyvilleConnection));
    registerCommand(new GuardPostCommand(freakyvilleConnection));

    registerListener(new ChatMessageListener());
    registerListener(new PlayerInfoListener());

    registerListener(new ServerNavigationListener(needsUpdate, freakyvilleConnection));
    registerListener(new ScoreBoardListener(freakyvilleConnection));
    registerListener(new PrisonNavigationListener(freakyvilleConnection));

    registerListener(new GuardVaultListener());

    registerListener(new GuardPostListener(freakyvilleConnection));
    registerListener(new GuardShiftSwitchListener());
    registerListener(new GuardDeathListener());
    registerListener(new GameShutdownListener());

    this.logger().info(I18n.translate("guardian.log.enabled"));
  }

  @Override
  protected Class<GuardianConfiguration> configurationClass() {
    return GuardianConfiguration.class;
  }

  private boolean shouldUpdate() {
    ArrayList<String[]> lines = DataOutput.csv("https://raw.githubusercontent.com/WildTooth/FreakyVille-General-Data/main/tools/versions.csv");
    for (String[] line : lines) {
      if (!line[0].equals("GUARDIAN")) {
        continue;
      }
      SemanticVersion ourVersion = new SemanticVersion(this.addonInfo().getVersion());
      SemanticVersion readVersion = new SemanticVersion(line[1]);
      if (ourVersion.isLowerThan(readVersion)) {
        return true;
      }
    }
    return false;
  }
}
