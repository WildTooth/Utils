package com.github.wildtooth.guardian.api.util;

import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFailEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostFinishEvent;
import com.github.wildtooth.guardian.api.event.guardpost.GuardPostTryEvent;
import com.github.wildtooth.guardian.api.event.vault.GuardVaultFinishEvent;
import com.github.wildtooth.guardian.api.event.vault.GuardVaultTryEvent;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

/**
 * Enum der indeholder forskellige beskedtyper man kan møde på freakyville.
 * <p>
 * Hver beskedtype har en tilhørende event, som kan sendes når beskedtypen bliver mødt.
 * Eventsne indlæses med null argumenter hvis de bør overskrives.
 */
public enum MessageType {
  GUARD_POST_TRY(new GuardPostTryEvent(null)),
  GUARD_POST_FAIL(new GuardPostFailEvent(null)),
  GUARD_POST_SUCCESS(new GuardPostFinishEvent(null)),

  // Vagt vault beskeder der handler om afslutningen af en VV bliver kun sendt når det blev gennemført uden fejl
  // Derfor er success altid true

  B_PLUS_GUARD_VAULT_START(new GuardVaultTryEvent(PrisonSector.B_PLUS, null)),
  B_PLUS_GUARD_VAULT_FINISH(new GuardVaultFinishEvent(PrisonSector.B_PLUS, null, true)),

  A_GUARD_VAULT_START(new GuardVaultTryEvent(PrisonSector.A, null)),
  A_GUARD_VAULT_FINISH(new GuardVaultFinishEvent(PrisonSector.A, null, true)),

  A_PLUS_GUARD_VAULT_START(new GuardVaultTryEvent(PrisonSector.A_PLUS, null)),
  A_PLUS_GUARD_VAULT_FINISH(new GuardVaultFinishEvent(PrisonSector.A_PLUS, null, true)),
  ;

  private final @Nullable Event event;

  MessageType(@Nullable Event event) {
    this.event = event;
  }

  public @Nullable Event getEvent() {
    return event;
  }
}
