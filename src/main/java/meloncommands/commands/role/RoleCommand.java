package meloncommands.commands.role;

import meloncommands.RoleBuilder;
import meloncommands.config.ConfigManager;
import meloncommands.config.RoleData;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

@SuppressWarnings("SameReturnValue")
public class RoleCommand extends Command {

	private final static String COMMAND = "role";

	public RoleCommand(){super(COMMAND);}

	public static RoleData getRoleFromArg(String arg){return ConfigManager.getRoleConfig(arg);}

	private boolean create(CommandSender sender, String[] args){

		if (args.length == 1) {
			sender.sendMessage("§eFailed to Create Role (Invalid Syntax)");
			sender.sendMessage("§8/role create <role id>");
			return true;
		}

		String role = args[1];

		if (ConfigManager.roleHashMap.containsKey(role)) {
			sender.sendMessage("§eFailed to Create Role: " + role + " (Role Already Exists)");
			return true;
		}

		ConfigManager.getRoleConfig(role);
		ConfigManager.loadAllRoles();
		getRoleFromArg(role).displayName = role;
		ConfigManager.saveAllRoles();

		if (args.length == 3){
			getRoleFromArg(role).priority = Integer.parseInt(args[2]);
		}

		sender.sendMessage("§5Created Role: " + getRoleFromArg(role).displayName + " with Priority: " + getRoleFromArg(role).priority);
		return true;
	}

	private boolean delete(CommandSender sender, String[] args){
		if (args.length == 1) {
			sender.sendMessage("§eFailed to Delete Role (Invalid Syntax)");
			sender.sendMessage("§8/role delete <role id>");
			return true;
		}

		String role = args[1];

		switch (ConfigManager.removeRoleConfig(role)) {
			case 0:
				sender.sendMessage("§1Deleted Role: " + role);
				return true;
			case 1:
				sender.sendMessage("§eFailed to Delete Role: " + role + " (Role Doesn't Exist)");
				return true;
			case 2:
				sender.sendMessage("§eFailed to Delete Role: " + role + " (IO Error)");
				return true;
		}
        return false;
    }

	private boolean reload(CommandSender sender){
		ConfigManager.loadAllRoles();
		sender.sendMessage("§5Reloaded " + ConfigManager.roleHashMap.size() + " Role(s)!");
		return true;
	}

	private boolean edit(CommandSender sender, String[] args){

		if(args.length == 1){
			sender.sendMessage("§eFailed to Edit Role (Invalid Syntax)");
			sender.sendMessage("§8/role edit <role id> <mode>");
			return true;
		}

		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Edit Role (Invalid Role)");
			sender.sendMessage("§8/role edit <role id> <mode>");
			return true;
		}

		if(args.length == 2){
			sender.sendMessage("§eFailed to Edit Role (Invalid Syntax)");
			sender.sendMessage("§8/role edit <role id> <mode>");
			return true;
		}

		switch(args[2]){
			case "display":
				return display(sender, args);
			case "username":
				return username(sender, args);
			case "text":
				return text(sender, args);
		}

        sender.sendMessage("§eFailed to Edit Role (Invalid Mode or Syntax)");
        sender.sendMessage("§8/role edit <role id> <mode>");
        return true;
    }

	private boolean display(CommandSender sender, String[] args){

		if(args.length == 3){
			sender.sendMessage("§eFailed to Edit Role Display (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > display <style>");
			sender.sendMessage("§8      > color <color/hex>");
			sender.sendMessage("§8      > underline true/false");
			sender.sendMessage("§8      > bold true/false");
			sender.sendMessage("§8      > italics true/false");
			sender.sendMessage("§8      > border <style>");
			sender.sendMessage("§8        > color <color/hex>");
			sender.sendMessage("§8        > none/bracket/caret/curly");
			sender.sendMessage("§8        > custom <affix>");
			sender.sendMessage("§8          > prefix/suffix <custom affix>");
			return true;
		}

		switch(args[3]){
			case "name":
				return EditRoleDisplay.displayName(sender, args);
			case "color":
				return EditRoleDisplay.displayColor(sender, args);
			case "underline":
				return EditRoleDisplay.displayUnderline(sender, args);
			case "bold":
				return EditRoleDisplay.displayBold(sender, args);
			case "italics":
				return EditRoleDisplay.displayItalics(sender, args);
			case "border":
				return EditRoleDisplay.displayBorder(sender, args);
		}

		sender.sendMessage("§eFailed to Edit Role Display (Invalid Syntax)");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > display <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > none/bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		return true;
	}

	private boolean username(CommandSender sender, String[] args){

		if(args.length == 3){
			sender.sendMessage("§eFailed to Edit Role Username (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > username <style>");
			sender.sendMessage("§8      > color <color/hex>");
			sender.sendMessage("§8      > underline true/false");
			sender.sendMessage("§8      > bold true/false");
			sender.sendMessage("§8      > italics true/false");
			sender.sendMessage("§8      > border <style>");
			sender.sendMessage("§8        > color <color/hex>");
			sender.sendMessage("§8        > none/bracket/caret/curly");
			sender.sendMessage("§8        > custom <affix>");
			sender.sendMessage("§8          > prefix/suffix <custom affix>");
			return true;
		}

		switch(args[3]){
			case "color":
				return EditRoleUsername.usernameColor(sender, args);
			case "underline":
				return EditRoleUsername.usernameUnderline(sender, args);
			case "bold":
				return EditRoleUsername.usernameBold(sender, args);
			case "italics":
				return EditRoleUsername.usernameItalics(sender, args);
			case "border":
				return EditRoleUsername.usernameBorder(sender, args);
		}

		sender.sendMessage("§eFailed to Edit Role Username (Invalid Syntax)");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > username <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > none/bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		return true;
	}

	private boolean text(CommandSender sender, String[] args){

		if(args.length == 3){
			sender.sendMessage("§eFailed to Edit Role Text (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > text <style>");
			sender.sendMessage("§8      > color <color/hex>");
			sender.sendMessage("§8      > underline true/false");
			sender.sendMessage("§8      > bold true/false");
			sender.sendMessage("§8      > italics true/false");
			return true;
		}

		switch(args[3]){
			case "color":
				return EditRoleText.textColor(sender, args);
			case "underline":
				return EditRoleText.textUnderline(sender, args);
			case "bold":
				return EditRoleText.textBold(sender, args);
			case "italics":
				return EditRoleText.textItalics(sender, args);
		}

		sender.sendMessage("§eFailed to Edit Role Text (Invalid Syntax)");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > text <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		return true;
	}

	private boolean grant(CommandSender sender, String[] args){

		if(args.length == 1){
			sender.sendMessage("§eFailed to Grant Role (Invalid Syntax)");
			sender.sendMessage("§8/role grant <role id> [<username>]");
			return true;
		}


		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Grant Role (Role doesnt exist!)");
			sender.sendMessage("§8/role grant <role id> [<username>]");
			return true;
		}

		RoleData roleData = getRoleFromArg(args[1]);

		if(args.length == 2 && !roleData.playersGrantedRole.contains(sender.getPlayer().username)){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).playersGrantedRole.add(sender.getPlayer().username);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Granted Role: " + args[1] + " to player: §0" + sender.getPlayer().username);
			return true;
		} else if (args.length == 3 && !roleData.playersGrantedRole.contains(args[2])){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).playersGrantedRole.add(args[2]);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Granted Role: " + args[1] + " to player: §0" + args[2]);
			return true;
		} else if (roleData.playersGrantedRole.contains(sender.getPlayer().username) || roleData.playersGrantedRole.contains(args[2])) {
			sender.sendMessage("§eFailed to Grant Role (Player already has Role!)");
			sender.sendMessage("§8/role grant <role id> [<username>]");
			return true;
		}


		sender.sendMessage("§eFailed to Grant Role (Error)");
		return false;
	}

	private boolean revoke(CommandSender sender, String[] args){

		if(args.length == 1){
			sender.sendMessage("§eFailed to Revoke Role (Invalid Syntax)");
			sender.sendMessage("§8/role revoke <role id> [<username>]");
			return true;
		}

		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Revoke Role (Role doesn't exist!)");
			sender.sendMessage("§8/role revoke <role id> [<username>]");
			return true;
		}

		RoleData roleData = getRoleFromArg(args[1]);

		if(args.length == 2 && roleData.playersGrantedRole.contains(sender.getPlayer().username)){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).playersGrantedRole.remove(sender.getPlayer().username);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§1Revoked Role: " + args[1] + " from player: §0" + sender.getPlayer().username);
			return true;
		} else if (args.length == 3 && roleData.playersGrantedRole.contains(sender.getPlayer().username)){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).playersGrantedRole.remove(args[2]);
			ConfigManager.saveAllRoles();
			sender.sendMessage("§1Revoked Role: " + args[1] + " from player: §0" + args[2]);
			return true;
		} else if (!roleData.playersGrantedRole.contains(sender.getPlayer().username) || !roleData.playersGrantedRole.contains(args[2])) {
			sender.sendMessage("§eFailed to Revoke Role (Player does not have Role!)");
			sender.sendMessage("§8/role revoke <role id> [<username>]");
			return true;
		}

		sender.sendMessage("§eFailed to Revoke Role (Error)");
		return false;
	}

	private boolean list(CommandSender sender) {
		if (ConfigManager.roleHashMap.isEmpty()) {
			sender.sendMessage("§8< Roles: >");
			sender.sendMessage("§8  -No Roles Created-");
			return true;
		}

		sender.sendMessage("§8< Roles: >");

		for (String role : ConfigManager.roleHashMap.keySet()) {
			sender.sendMessage("§8  > " + role );
			sender.sendMessage("§8    > Display:");
			sender.sendMessage("§8      > " + RoleBuilder.buildRoleDisplay(ConfigManager.roleHashMap.get(role)));
			sender.sendMessage("§8    > Username:");
			sender.sendMessage("§8      > " + RoleBuilder.buildRoleUsername(ConfigManager.roleHashMap.get(role), sender.getPlayer().username));
			sender.sendMessage("§8    > Text:");
			sender.sendMessage("§8      > " + RoleBuilder.buildRoleTextFormat(ConfigManager.roleHashMap.get(role)) + "text");
		}

		return true;
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args){

		if (args.length == 0) {
			return false;
		}

		switch(args[0]){
			case "create" :
				return create(sender, args);
			case "delete" :
				return delete(sender, args);
			case "reload" :
				return reload(sender);
			case "edit" :
				return edit(sender, args);
			case "grant" :
				return grant(sender, args);
			case "revoke" :
				return revoke(sender, args);
			case "list" :
				return list(sender);
		}


		sender.sendMessage("§eRole Command Failed (Invalid Syntax)");
		sender.sendMessage("§8  > /role create <role id>");
		sender.sendMessage("§8  > /role delete <role id>");
		sender.sendMessage("§8  > /role reload");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8  > /role grant <role id> [<username>]");
		sender.sendMessage("§8  > /role revoke <role id> [<username>]");
		sender.sendMessage("§8  > /role list");
		return true;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("");
		sender.sendMessage("§8< Command Syntax >");
		sender.sendMessage("§8  > /role create <role id> [<priority>]");
		sender.sendMessage("§8  > /role delete <role id>");
		sender.sendMessage("§8  > /role reload");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > priority <priority value>");
		sender.sendMessage("§8    > display <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > none/bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		sender.sendMessage("§8    > username <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > none/bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		sender.sendMessage("§8    > text <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8  > /role grant <role id> [<username>]");
		sender.sendMessage("§8  > /role revoke <role id> [<username>]");
		sender.sendMessage("§8  > /role list");
	}
}
