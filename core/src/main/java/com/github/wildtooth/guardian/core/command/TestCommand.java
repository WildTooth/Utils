package com.github.wildtooth.guardian.core.command;

import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import com.github.wildtooth.guardian.api.freakyville.FreakyvilleServer;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;

public class TestCommand extends Command {

  private final FreakyvilleConnection freakyvilleConnection;

  public TestCommand(FreakyvilleConnection freakyvilleConnection) {
    super("test", "try", "fail", "finish");
    this.freakyvilleConnection = freakyvilleConnection;
  }

  @Override
  public boolean execute(String s, String[] strings) {
    if (!this.freakyvilleConnection.isOnFreakyVille()) {
      return false;
    }
    if (this.freakyvilleConnection.getCurrentServer() != FreakyvilleServer.PRISON) {
      return false;
    }
    if (this.freakyvilleConnection.getPrison().isEmpty()) {
      return true;
    }
    String message = switch (s) {
      case "try" -> "Stå stille i 10 sekunder for at aktivere vagtposten!";
      case "fail" -> "Du bevægede dig og har stoppet med at aktivere VagtPosten";
      case "finish" -> "Godt gået! Du aktiverede denne vagtpost!";
      default -> "";
    };
    displayMessage(message);
    return true;
  }
}
