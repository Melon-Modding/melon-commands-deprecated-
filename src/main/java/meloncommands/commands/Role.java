package meloncommands.commands;

import meloncommands.config.ConfigManager;
import meloncommands.config.RoleData;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class Role extends Command {

	private final static String COMMAND = "role";

	public Role(){super(COMMAND);}

	private RoleData getRoleFromArg(String arg){return ConfigManager.getRoleConfig(arg);}

	@SuppressWarnings("SameReturnValue")
	private boolean create(CommandSender sender, String[] args){
		if (args.length == 1) {
			sender.sendMessage("§eFailed to Create Role (Invalid Syntax)");
			sender.sendMessage("§8/role create <role>");
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
		sender.sendMessage("§5Created Role: " + getRoleFromArg(role).displayName);
		return true;
	}

	private boolean delete(CommandSender sender, String[] args){
		if (args.length == 1) {
			sender.sendMessage("§eFailed to Delete Role (Invalid Syntax)");
			sender.sendMessage("§8/role delete <role>");
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
			sender.sendMessage("§8/role edit <role> <mode>");
			return true;
		}

		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Edit Role (Invalid Role)");
			sender.sendMessage("§8/role edit <role> <mode>");
			return true;
		}

		if(args.length == 2){
			sender.sendMessage("§eFailed to Edit Role (Invalid Syntax)");
			sender.sendMessage("§8/role edit <role> <mode>");
			return true;
		}

		switch(args[2]){
			case "display":
				return display(sender, args);
			case "text":
				return text(sender, args);
		}

        sender.sendMessage("§eFailed to Edit Role (Invalid Mode or Syntax)");
        sender.sendMessage("§8/role edit <role> <mode>");
        return true;
    }

	private boolean display(CommandSender sender, String[] args){

		if(args.length == 3){
			sender.sendMessage("§eFailed to Edit Role Display (Invalid Syntax)");
			sender.sendMessage("§8/role edit <role> display color/underline/bold/italics/border");
			return true;
		}

		switch(args[3]){
			case "name":
				return displayName(sender, args);
			case "color":
				return displayColor(sender, args);
			case "underline":
				return displayUnderline(sender, args);
			case "bold":
				return displayBold(sender, args);
			case "italics":
				return displayItalics(sender, args);
			case "border":
				return displayBorder(sender, args);
		}

		sender.sendMessage("§eFailed to Edit Role Display (Invalid Syntax)");
		sender.sendMessage("§8/role edit <role> display color/underline/bold/italics/border");
		return true;
	}

	private boolean displayName(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		getRoleFromArg(args[1]).displayName = args[4];
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Display Name for Role " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		return true;
	}

	private boolean displayColor(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		getRoleFromArg(args[1]).displayColor = args[4];
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Display Color for Role " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		return true;
	}

	private boolean displayUnderline(CommandSender sender, String[] args){
		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).isDisplayUnderlined = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Underline for Role " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).isDisplayUnderlined = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Underline for Role " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Display Underline (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	private boolean displayBold(CommandSender sender, String[] args){
		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).isDisplayBold = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Bold for Role " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).isDisplayBold = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Bold for Role " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Display Bold (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	private boolean displayItalics(CommandSender sender, String[] args){
		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).isDisplayItalics = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Edited Display Italics for Role: " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			getRoleFromArg(args[1]).isDisplayItalics = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Edited Display Italics for Role: " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		}

		sender.sendMessage("§eFailed to Edit Display Italics (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	private boolean displayBorder(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Display Border (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role> <mode>");
			sender.sendMessage("§8    > display <style>");
			sender.sendMessage("§8      > border <style>");
			sender.sendMessage("§8        > color <color/hex>");
			sender.sendMessage("§8        > bracket/caret/curly");
			sender.sendMessage("§8        > custom <affix>");
			sender.sendMessage("§8          > prefix/suffix <custom affix>");
			return true;
		}

		switch(args[4]){
			case "color":
				return displayBorderColor(sender, args);
			case "bracket":
				return displayBorderBracket(sender, args);
			case "caret":
				return displayBorderCaret(sender, args);
			case "curly":
				return displayBorderCurly(sender, args);
			case "custom":
				return displayBorderCustom(sender, args);
		}
		sender.sendMessage("§eFailed to Edit Display Border (Invalid Syntax)");
		sender.sendMessage("§8  > /role edit <role> <mode>");
		sender.sendMessage("§8    > display <style>");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		return true;
	}

	private boolean displayBorderColor(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		getRoleFromArg(args[1]).displayBorderColor = args[5];
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Edited Display Border Color for Role: " + getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		return true;
	}

	private boolean displayBorderBracket(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		getRoleFromArg(args[1]).isDisplayBracketBorder = true;
		getRoleFromArg(args[1]).isDisplayCaretBorder = false;
		getRoleFromArg(args[1]).isDisplayCurlyBracketBorder = false;
		getRoleFromArg(args[1]).isDisplayCustomBorder = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Display to Bracket Border for Role: " + getRoleFromArg(args[1]).displayName);
		return true;
	}

	private boolean displayBorderCaret(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		getRoleFromArg(args[1]).isDisplayBracketBorder = false;
		getRoleFromArg(args[1]).isDisplayCaretBorder = true;
		getRoleFromArg(args[1]).isDisplayCurlyBracketBorder = false;
		getRoleFromArg(args[1]).isDisplayCustomBorder = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Display to Caret Border for Role: " + getRoleFromArg(args[1]).displayName);
		return true;
	}

	private boolean displayBorderCurly(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		getRoleFromArg(args[1]).isDisplayBracketBorder = false;
		getRoleFromArg(args[1]).isDisplayCaretBorder = false;
		getRoleFromArg(args[1]).isDisplayCurlyBracketBorder = true;
		getRoleFromArg(args[1]).isDisplayCustomBorder = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Display to Curly Bracket Border for Role: " + getRoleFromArg(args[1]).displayName);
		return true;
	}

	private boolean displayBorderCustom(CommandSender sender, String[] args){
		if(args.length == 5){
			sender.sendMessage("§eFailed to Edit Custom Display Border (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role> <mode>");
			sender.sendMessage("§8    > display <style>");
			sender.sendMessage("§8      > border <style>");
			sender.sendMessage("§8        > custom <affix>");
			sender.sendMessage("§8          > prefix/suffix <custom affix>");
			return true;
		}

		RoleData role = getRoleFromArg(args[1]);
		if(args[5].equals("suffix")){
			if(args.length == 6){
				sender.sendMessage("§eFailed to Edit Custom Suffix (Invalid Syntax)");
				sender.sendMessage("§8  > /role edit <role> <mode>");
				sender.sendMessage("§8    > display <style>");
				sender.sendMessage("§8      > border <style>");
				sender.sendMessage("§8        > custom <affix>");
				sender.sendMessage("§8          > suffix <custom affix>");
				return true;
			}
			ConfigManager.loadAllRoles();
			role.isDisplayBracketBorder = false;
			role.isDisplayCaretBorder = false;
			role.isDisplayCurlyBracketBorder = false;
			role.isDisplayCustomBorder = true;
			role.customBorderSuffix = args[6];
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Custom Display Border Suffix for Role: " + getRoleFromArg(args[1]).displayName + " to " + args[6]);
			return true;
		} else if(args[5].equals("prefix")){
			if(args.length == 6){
				sender.sendMessage("§eFailed to Edit Custom Prefix (Invalid Syntax)");
				sender.sendMessage("§8  > /role edit <role> <mode>");
				sender.sendMessage("§8    > display <style>");
				sender.sendMessage("§8      > border <style>");
				sender.sendMessage("§8        > custom <affix>");
				sender.sendMessage("§8          > prefix <custom affix>");
				return true;
			}
			ConfigManager.loadAllRoles();
			role.isDisplayBracketBorder = false;
			role.isDisplayCaretBorder = false;
			role.isDisplayCurlyBracketBorder = false;
			role.isDisplayCustomBorder = true;
			role.customBorderPrefix = args[6];
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Custom Display Border Prefix for Role: " + getRoleFromArg(args[1]).displayName + " to " + args[6]);
			return true;
		}

		ConfigManager.saveAllRoles();
		return true;
	}

	private boolean text(CommandSender sender, String[] args){

		if(args.length == 3){
			sender.sendMessage("§eFailed to Edit Role Text (Invalid Syntax)");
			sender.sendMessage("§8/role edit <role> text color/underline/bold/italics");
			return true;
		}

		switch(args[3]){
			case "color":
				return textColor(sender, args);
			case "underline":
				return textUnderline(sender, args);
			case "bold":
				return textBold(sender, args);
			case "italics":
				return textItalics(sender, args);
		}

		sender.sendMessage("§eFailed to Edit Role Text (Invalid Syntax)");
		sender.sendMessage("§8/role edit <role> text color/underline/bold/italics");
		return true;
	}

	private boolean textColor(CommandSender sender, String[] args){
		return true;
	}

	private boolean textUnderline(CommandSender sender, String[] args){
		return true;
	}

	private boolean textBold(CommandSender sender, String[] args){
		return true;
	}

	private boolean textItalics(CommandSender sender, String[] args){
		return true;
	}

	private boolean grant(CommandSender sender, String[] args){

		if(args.length == 1){
			sender.sendMessage("§eFailed to Grant Role (Invalid Syntax)");
			sender.sendMessage("§8/role grant <role> [<username>]");
			return true;
		}


		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Grant Role (Role doesnt exist!)");
			sender.sendMessage("§8/role grant <role> [<username>]");
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
			sender.sendMessage("§8/role grant <role> [<username>]");
			return true;
		}


		sender.sendMessage("§eFailed to Grant Role (Error)");
		return false;
	}

	private boolean revoke(CommandSender sender, String[] args){

		if(args[1] == null){
			sender.sendMessage("§eFailed to Revoke Role (Invalid Syntax)");
			sender.sendMessage("§8/role revoke <role> [<username>]");
			return true;
		}

		if(!ConfigManager.roleHashMap.containsKey(args[1])){
			sender.sendMessage("§eFailed to Revoke Role (Role doesnt exist!)");
			sender.sendMessage("§8/role revoke <role> [<username>]");
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
		}

		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		sender.sendMessage("");
		sender.sendMessage("§8< Command Syntax >");
		sender.sendMessage("§8  > /role create <role>");
		sender.sendMessage("§8  > /role delete <role>");
		sender.sendMessage("§8  > /role reload");
		sender.sendMessage("§8  > /role edit <role> <mode>");
		sender.sendMessage("§8    > display <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		sender.sendMessage("§8    > text <style>");
		sender.sendMessage("§8      > color <color/hex>");
		sender.sendMessage("§8      > underline true/false");
		sender.sendMessage("§8      > bold true/false");
		sender.sendMessage("§8      > italics true/false");
		sender.sendMessage("§8  > /role grant <role> [<username>]");
		sender.sendMessage("§8  > /role revoke <role> [<username>]");
	}
}
