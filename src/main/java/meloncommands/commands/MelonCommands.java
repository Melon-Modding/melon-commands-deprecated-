package meloncommands.commands;

import meloncommands.config.ConfigManager;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class MelonCommands extends Command {

	private final static String COMMAND = "meloncommands";
	private final static String NAME = "MelonCommands";

	public MelonCommands(){super(COMMAND);}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {
		if (args.length == 0) {
			return false;
		}

		if (args[0].equals("reload")) {
			ConfigManager.loadAllKits();
			ConfigManager.loadAllRoles();
			sender.sendMessage("§5Reloading " + NAME + "...");
			sender.sendMessage("§5Reloaded " + ConfigManager.kitHashMap.size() + " Kit(s)!");
			sender.sendMessage("§5Reloaded " + ConfigManager.roleHashMap.size() + " Role(s)!");
			return true;
		}

		sender.sendMessage("§e " + NAME + " Error: (Invalid Syntax)");
        return false;
    }

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {

	}
}
