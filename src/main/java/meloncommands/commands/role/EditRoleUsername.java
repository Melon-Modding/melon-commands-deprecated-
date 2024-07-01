package meloncommands.commands.role;

import meloncommands.config.ConfigManager;
import meloncommands.config.RoleData;
import net.minecraft.core.net.command.CommandSender;

public class EditRoleUsername {

	public static boolean usernameColor(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).usernameColor = args[4];
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Username Color for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	public static boolean usernameUnderline(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Username Underline (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > username <style>");
			sender.sendMessage("§8      > underline true/false");
			return true;
		}

		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isUsernameUnderlined = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Username Underline for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isUsernameUnderlined = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Username Underline for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Username Underline (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	public static boolean usernameBold(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Username Bold (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > username <style>");
			sender.sendMessage("§8      > bold true/false");
			return true;
		}

		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isUsernameBold = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Username Bold for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isUsernameBold = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Username Bold for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Username Bold (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	public static boolean usernameItalics(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Username Italics (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > username <style>");
			sender.sendMessage("§8      > italics true/false");
			return true;
		}

		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isUsernameItalics = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Username Italics for role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isUsernameItalics = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Username Italics for role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Username Italics (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	public static boolean usernameBorder(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Username Border (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > username <style>");
			sender.sendMessage("§8      > border <style>");
			sender.sendMessage("§8        > color <color/hex>");
			sender.sendMessage("§8        > none/bracket/caret/curly");
			sender.sendMessage("§8        > custom <affix>");
			sender.sendMessage("§8          > prefix/suffix <custom affix>");
			return true;
		}

		switch(args[4]){
			case "color":
				return usernameBorderColor(sender, args);
			case "none":
				return usernameBorderNone(sender, args);
			case "bracket":
				return usernameBorderBracket(sender, args);
			case "caret":
				return usernameBorderCaret(sender, args);
			case "curly":
				return usernameBorderCurly(sender, args);
			case "custom":
				return usernameBorderCustom(sender, args);
		}
		sender.sendMessage("§eFailed to Edit Username Border (Invalid Syntax)");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > username <style>");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > none/bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		return true;
	}

	private static boolean usernameBorderColor(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).usernameBorderColor = args[5];
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Username Border Color for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		return true;
	}

	private static boolean usernameBorderNone(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderNone = true;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderBracket = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCaret = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCurly = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Username Border to None for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	private static boolean usernameBorderBracket(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderNone = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderBracket = true;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCaret = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCurly = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Username Border to [Bracket] for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	private static boolean usernameBorderCaret(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderNone = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderBracket = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCaret = true;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCurly = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Username Border to <Caret> for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	private static boolean usernameBorderCurly(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderNone = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderBracket = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCaret = false;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCurly = true;
		RoleCommand.getRoleFromArg(args[1]).isUsernameBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Username Border to {Curly} for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	private static boolean usernameBorderCustom(CommandSender sender, String[] args){
		if(args.length == 5){
			ConfigManager.loadAllRoles();
			RoleData role = RoleCommand.getRoleFromArg(args[1]);
			role.isUsernameBorderNone = false;
			role.isUsernameBorderBracket = false;
			role.isUsernameBorderCaret = false;
			role.isUsernameBorderCurly = false;
			role.isUsernameBorderCustom = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Username Border to ?Custom? for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
			return true;
		}
		if(args[5].equals("suffix")){
			if(args.length == 6){
				sender.sendMessage("§eFailed to Edit Custom Suffix (Invalid Syntax)");
				sender.sendMessage("§8  > /role edit <role id> <mode>");
				sender.sendMessage("§8    > username <style>");
				sender.sendMessage("§8      > border <style>");
				sender.sendMessage("§8        > custom <affix>");
				sender.sendMessage("§8          > suffix <custom affix>");
				return true;
			}
			ConfigManager.loadAllRoles();
			RoleData role = RoleCommand.getRoleFromArg(args[1]);
			role.isUsernameBorderBracket = false;
			role.isUsernameBorderCaret = false;
			role.isUsernameBorderCurly = false;
			role.isUsernameBorderCustom = true;
			role.customUsernameBorderSuffix = args[6];
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Custom Username Border Suffix for Role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to " + args[6]);
			return true;
		} else if(args[5].equals("prefix")){
			if(args.length == 6){
				sender.sendMessage("§eFailed to Edit Custom Prefix (Invalid Syntax)");
				sender.sendMessage("§8  > /role edit <role id> <mode>");
				sender.sendMessage("§8    > username <style>");
				sender.sendMessage("§8      > border <style>");
				sender.sendMessage("§8        > custom <affix>");
				sender.sendMessage("§8          > prefix <custom affix>");
				return true;
			}
			ConfigManager.loadAllRoles();
			RoleData role = RoleCommand.getRoleFromArg(args[1]);
			role.isUsernameBorderBracket = false;
			role.isUsernameBorderCaret = false;
			role.isUsernameBorderCurly = false;
			role.isUsernameBorderCustom = true;
			role.customUsernameBorderPrefix = args[6];
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Custom Username Border Prefix for Role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to " + args[6]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Custom Username Border (Invalid Syntax)");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > username <style>");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		return true;
	}
}