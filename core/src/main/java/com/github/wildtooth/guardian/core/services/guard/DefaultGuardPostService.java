package com.github.wildtooth.guardian.core.services.guard;

import com.github.wildtooth.guardian.api.guard.GuardPost;
import com.github.wildtooth.guardian.api.service.guard.GuardPostService;
import com.github.wildtooth.guardian.core.internatiolization.TranslationLogger;
import com.github.wildtooth.guardian.api.service.Registrable;
import com.github.wildtooth.guardian.api.service.Service;
import net.labymod.api.util.logging.Logging;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultGuardPostService implements GuardPostService {
  private final TranslationLogger logger;
  private final Map<String, GuardPost> identifierGuardPostMap;

  public DefaultGuardPostService() {
    this.logger = new TranslationLogger(Logging.getLogger());
    this.identifierGuardPostMap = new HashMap<>();
  }

  @Override
  public void onRegister() {
    initialize();
  }

  @Override
  public void onUnregister() {
    shutdown();
  }

  @Override
  public void initialize() {
    this.logger.info("guardian.service.shared.initialize", this.logger.translate("guardian.service.posts.name"));
  }

  @Override
  public void shutdown() {
    this.logger.info("guardian.service.shared.shutdown", this.logger.translate("guardian.service.posts.name"));
  }

  @Override
  public Optional<? extends GuardPost> getGuardPost(String identifier) {
    return Optional.ofNullable(this.identifierGuardPostMap.get(identifier));
  }

  @Override
  public <GP extends GuardPost> void addGuardPost(GP guardPost) {
    this.identifierGuardPostMap.put(guardPost.combinedIdentifier(), guardPost);
  }

  @Override
  public void removeGuardPost(String identifier) {
    this.identifierGuardPostMap.remove(identifier);
  }

  @Override
  public <GP extends GuardPost> boolean hasGuardPost(GP guardPost) {
    return this.identifierGuardPostMap.containsValue(guardPost);
  }

  @Override
  public boolean hasGuardPost(String identifier) {
    return this.identifierGuardPostMap.containsKey(identifier);
  }
}
