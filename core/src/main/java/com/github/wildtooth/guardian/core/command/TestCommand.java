package com.github.wildtooth.guardian.core.command;

import com.github.wildtooth.guardian.api.freakyville.FreakyvilleConnection;
import net.labymod.api.client.chat.command.Command;

public class TestCommand extends Command {

  public TestCommand(FreakyvilleConnection freakyvilleConnection) {
    super("test", "try", "fail", "finish");
  }

  @Override
  public boolean execute(String s, String[] strings) {
    switch (s) {
      case "try":
        displayMessage("VAGT-ALARM: Daniel_Kongen prøver at bryde pengeskabet op i banken i A+ Området!");
        break;
      case "fail":

        break;
      case "finish":
        displayMessage("VAGT-ALARM: Det lykkedes Daniel_Kongen at bryde bankens pengeskab op i A+ Området!");
        break;
    }
    return true;
  }
}
