package meloncommands.commands;

import meloncommands.config.ConfigManager;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class Role extends Command {

	private final static String COMMAND = "role";
	private final static String NAME = "Role";

	public Role(){super(COMMAND);}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {

		if (args.length == 0) {
			return false;
		}

		if (args[0].equals("create")) {

			if (args.length == 1) {
				sender.sendMessage("§eFailed to Create " + NAME + " (Invalid Syntax)");
				sender.sendMessage("§8/" + COMMAND + " create <" + COMMAND + ">");
				return true;
			}

			String role = args[1];

			if (ConfigManager.roleHashMap.containsKey(role)) {
				sender.sendMessage("§eFailed to Create " + NAME + ": '" + role + "' (" + NAME + " Already Exists)");
				return true;
			}

			ConfigManager.getRoleConfig(role);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Created " + NAME + ": '" + role + "'");
			return true;
		}

		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("§8< Command Syntax >");
		sender.sendMessage("§8  > /" + COMMAND + " create <" + COMMAND + ">");
		sender.sendMessage("§8  > /" + COMMAND + " delete <" + COMMAND + ">");
		sender.sendMessage("§8  > /" + COMMAND + " reload");
		sender.sendMessage("§8  > /" + COMMAND + " edit <" + COMMAND + "> <mode>");
		sender.sendMessage("§8    > display");
		sender.sendMessage("§8      > color <color>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8      > border bracket/caret/curly");
		sender.sendMessage("§8      > customborder prefix/suffix <border>");
		sender.sendMessage("§8    > text");
		sender.sendMessage("§8      > color <color>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8  > /" + COMMAND + " grant <" + COMMAND + "> <username>");
		sender.sendMessage("§8  > /" + COMMAND + " revoke <" + COMMAND + "> <username>");
	}
}
