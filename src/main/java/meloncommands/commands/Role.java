package meloncommands.commands;

import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class Role extends Command {

	private final static String COMMAND = "role";

	public Role(){super(COMMAND);}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {
		if (args.length == 0) {
			return false;
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
		sender.sendMessage("§8  > /role create <role>");
		sender.sendMessage("§8  > /role delete <role>");
		sender.sendMessage("§8  > /role reload");
		sender.sendMessage("§8  > /role edit <role> <mode>");
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
		sender.sendMessage("§8  > /role grant <role> <username>");
		sender.sendMessage("§8  > /role revoke <role> <username>");
	}
}
