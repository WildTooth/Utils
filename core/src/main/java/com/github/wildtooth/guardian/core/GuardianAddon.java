package com.github.wildtooth.guardian.core;

import com.github.wildtooth.guardian.api.ConstantsProvider;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.generated.ReferenceStorage;
import com.github.wildtooth.guardian.api.gson.SpecializedGsonProvider;
import com.github.wildtooth.guardian.api.refrences.LocationHelper;
import com.github.wildtooth.guardian.api.service.RegistryProvider;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.api.service.guard.GuardService;
import com.github.wildtooth.guardian.core.command.GuardPostCommand;
import com.github.wildtooth.guardian.core.command.TestCommand;
import com.github.wildtooth.guardian.core.gson.DefaultSpecializedGson;
import com.github.wildtooth.guardian.core.listener.GameShutdownListener;
import com.github.wildtooth.guardian.core.listener.GuardDeathListener;
import com.github.wildtooth.guardian.core.listener.GuardPostListener;
import com.github.wildtooth.guardian.core.listener.GuardShiftSwitchListener;
import com.github.wildtooth.guardian.core.listener.convertions.ChatMessageListener;
import com.github.wildtooth.guardian.core.listener.convertions.PlayerInfoListener;
import com.github.wildtooth.guardian.core.listener.internals.PrisonNavigationListener;
import com.github.wildtooth.guardian.core.listener.internals.ScoreBoardListener;
import com.github.wildtooth.guardian.core.listener.internals.ServerNavigationListener;
import com.github.wildtooth.guardian.core.services.DefaultRegistry;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardPostService;
import com.github.wildtooth.guardian.core.services.guard.DefaultGuardService;
import com.github.wildtooth.guardian.core.util.DefaultConstants;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.util.I18n;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.version.SemanticVersion;
import java.util.ArrayList;

@AddonMain
public class GuardianAddon extends LabyAddon<GuardianConfiguration> {

  private static final SemanticVersion VERSION = new SemanticVersion("1.0.0");
  private boolean needsUpdate = false;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    // Køres først
    checkForUpdate();

    LocationHelper locationHelper = ((ReferenceStorage) this.referenceStorageAccessor()).locationHelper();

    SpecializedGsonProvider.setSpecializedGson(new DefaultSpecializedGson());

    ConstantsProvider.setConstants(new DefaultConstants());

    RegistryProvider.setRegistry(new DefaultRegistry());
    RegistryProvider.getRegistry().register(GuardService.class, new DefaultGuardService(), false);
    RegistryProvider.getRegistry().register(GuardPostService.class, new DefaultGuardPostService(), false);
    RegistryProvider.getRegistry().register(LocationHelper.class, locationHelper, false);

    FreakyvilleConnection freakyvilleConnection = new FreakyvilleConnection(
        this.labyAPI().serverController(),
        this.labyAPI().minecraft().getClientPlayer()
    );

    registerCommand(new TestCommand(freakyvilleConnection));
    registerCommand(new GuardPostCommand(freakyvilleConnection));

    registerListener(new ChatMessageListener());
    registerListener(new PlayerInfoListener());

    registerListener(new ServerNavigationListener(needsUpdate, freakyvilleConnection));
    registerListener(new ScoreBoardListener(freakyvilleConnection));
    registerListener(new PrisonNavigationListener(freakyvilleConnection));

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

  private void checkForUpdate() {
    Response<String> response = Request.ofString()
        .url("https://raw.githubusercontent.com/WildTooth/FreakyVille-General-Data/main/tools/versions.csv")
        .executeSync();
    ArrayList<String[]> lines = new ArrayList<>();
    for (String line : response.get().split("\n")) {
      lines.add(line.split(","));
    }
    for (String[] line : lines) {
      if (!line[0].equals("GUARDIAN")) {
        continue;
      }
      SemanticVersion readVersion = new SemanticVersion(line[1]);
      if (GuardianAddon.VERSION.isLowerThan(readVersion)) {
        this.needsUpdate = true;
      }
    }
  }
}
