package com.github.wildtooth.guardian.api.freakyville;

public enum FreakyvilleServer {
  PRISON(),
  CREATIVE(),
  SKY_BLOCK(),
  THE_CITY(),
  KIT_PVP(),
  HUB(),
  NONE(),

  /**
   * @deprecated Ekspeditionen er ikke længere tilgængelig. Den kan blive genaktiveret i fremtiden.
   */
  @Deprecated(forRemoval = false)
  THE_EXPEDITION(),

  /**
   * @deprecated Factions er ikke længere tilgængelig. Den kan blive genaktiveret i fremtiden.
   */
  @Deprecated(forRemoval = false)
  FACTIONS(),

  /**
   * @deprecated OP-Prison er ikke længere tilgængelig. Den kan blive genaktiveret i fremtiden.
   */
  @Deprecated(forRemoval = false)
  OP_PRISON(),

  /**
   * @deprecated Rumrejsen er ikke længere tilgængelig. Den kan blive genaktiveret i fremtiden.
   */
  @Deprecated(forRemoval = false)
  SPACE_JOURNEY(),
  ;

  public static FreakyvilleServer fromString(String scoreBoardTitle) {
    return switch (scoreBoardTitle) {
      case "FV Skyblock" -> FreakyvilleServer.SKY_BLOCK;
      case "FV PRISON" -> FreakyvilleServer.PRISON;
      case "FRIHEDEN" -> FreakyvilleServer.THE_CITY;
      case "FV Creative" -> FreakyvilleServer.CREATIVE;
      case "FreakyVille" -> FreakyvilleServer.HUB;
      /*
       * KitPvP har flere forskellige navne, ifølge det "scoreboard" klienten læser.
       * Derfor tjekker vi for flere navne. Det skifter engang i mellem?
       */
      case "FV KitPvP", "HP", "mvdwan" -> FreakyvilleServer.KIT_PVP;
      default -> FreakyvilleServer.NONE;
    };
  }
}
