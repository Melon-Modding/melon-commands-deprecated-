package meloncommands.commands;

import meloncommands.config.ConfigManager;
import meloncommands.config.RoleData;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class Role extends Command {

	private final static String COMMAND = "role";
	private final static String NAME = "Role";

	public Role(){super(COMMAND);}

	private RoleData getRoleFromArg(String arg){return ConfigManager.getRoleConfig(arg);}

	@SuppressWarnings("SameReturnValue")
	private boolean create(CommandHandler handler, CommandSender sender, String[] args){
		if (args.length == 1) {
			sender.sendMessage("§eFailed to Create Role (Invalid Syntax)");
			sender.sendMessage("§8/role create <role>");
			return true;
		}

		String role = args[1];

		if (ConfigManager.roleHashMap.containsKey(role)) {
			sender.sendMessage("§eFailed to Create Role: '" + role + "' (Role Already Exists)");
			return true;
		}

		ConfigManager.getRoleConfig(role);
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Created Role: '" + role + "'");
		return true;
	}

	private boolean delete(CommandHandler handler, CommandSender sender, String[] args){
		if (args.length == 1) {
			sender.sendMessage("§eFailed to Delete Role (Invalid Syntax)");
			sender.sendMessage("§8/role delete <role>");
			return true;
		}

		String role = args[1];

		switch (ConfigManager.removeRoleConfig(role)) {
			case 0:
				sender.sendMessage("§1Deleted Role: '" + role + "'");
				return true;
			case 1:
				sender.sendMessage("§eFailed to Delete Role: '" + role + "' (Role Doesn't Exist)");
				return true;
			case 2:
				sender.sendMessage("§eFailed to Delete Role: '" + role + "' (IO Error)");
				return true;
		}
        return false;
    }

	private boolean reload(CommandHandler handler, CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		sender.sendMessage("§5Reloaded " + ConfigManager.roleHashMap.size() + " Role(s)!");
		return true;
	}

	private boolean edit(CommandHandler handler, CommandSender sender, String[] args){
		return false;
	}

	private boolean grant(CommandHandler handler, CommandSender sender, String[] args){

		if(args.length == 1){
			sender.sendMessage("§eFailed to Grant Role (Invalid Syntax)");
			sender.sendMessage("§8/role grant <role> [<username>]");
			return true;
		}

		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Grant Role (Role doesn't exist!)");
			sender.sendMessage("§8/role grant <role> [<username>]");
			return true;
		}

		RoleData roleData = getRoleFromArg(args[1]);

		if(args.length == 2 && !roleData.playersGrantedRole.contains(sender.getPlayer().username)){
			ConfigManager.loadAllRoles();
			roleData.playersGrantedRole.add(sender.getPlayer().username);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Granted Role: '" + args[1] + "' to player: §0" + sender.getPlayer().username);
			return true;
		} else if (args.length == 3 && !roleData.playersGrantedRole.contains(args[2])){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).playersGrantedRole.add(args[2]);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Granted Role: '" + args[1] + "' to player: §0" + args[2]);
			return true;
		} else if (roleData.playersGrantedRole.contains(sender.getPlayer().username) || roleData.playersGrantedRole.contains(args[2])) {
			sender.sendMessage("§eFailed to Grant Role (Player already has Role!)");
			sender.sendMessage("§8/role grant <role> [<username>]");
			return true;
		}


		sender.sendMessage("§eFailed to Grant Role (Error)");
		return false;
	}

	private boolean revoke(CommandHandler handler, CommandSender sender, String[] args){

		if(args[1] == null){
			sender.sendMessage("§eFailed to Revoke Role (Invalid Syntax)");
			sender.sendMessage("§8/role revoke <role> [<username>]");
			return true;
		}

		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Revoke Role (Role doesn't exist!)");
			sender.sendMessage("§8/role revoke <role> [<username>]");
			return true;
		}

		RoleData roleData = getRoleFromArg(args[1]);

		if(args.length == 2 && roleData.playersGrantedRole.contains(sender.getPlayer().username)){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).playersGrantedRole.remove(sender.getPlayer().username);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§1Revoked Role: '" + args[1] + "' from player: §0" + sender.getPlayer().username);
			return true;
		} else if (args.length == 3 && roleData.playersGrantedRole.contains(sender.getPlayer().username)){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).playersGrantedRole.remove(args[2]);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§1Revoked Role: '" + args[1] + "' from player: §0" + args[2]);
			return true;
		} else if (!roleData.playersGrantedRole.contains(sender.getPlayer().username) || !roleData.playersGrantedRole.contains(args[2])) {
			sender.sendMessage("§eFailed to Revoke Role (Player does not have Role!)");
			sender.sendMessage("§8/role revoke <role> [<username>]");
			return true;
		}

		sender.sendMessage("§eFailed to Revoke Role (Error)");
		return false;
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args){

		if (args.length == 0) {
			return false;
		}

		switch(args[0]){
			case "create" :
				return create(handler, sender, args);
			case "delete" :
				return delete(handler, sender, args);
			case "reload" :
				return reload(handler, sender, args);
			case "edit" :
				return edit(handler, sender, args);
			case "grant" :
				return grant(handler, sender, args);
			case "revoke" :
				return revoke(handler, sender, args);
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
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8      > border bracket/caret/curly");
		sender.sendMessage("§8      > border custom prefix/suffix <border>");
		sender.sendMessage("§8    > text");
		sender.sendMessage("§8      > color <color>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8  > /role grant <role> <username>");
		sender.sendMessage("§8  > /role revoke <role> <username>");
	}
}
