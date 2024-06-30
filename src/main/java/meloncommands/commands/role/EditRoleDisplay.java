package meloncommands.commands.role;

import meloncommands.config.ConfigManager;
import meloncommands.config.RoleData;
import net.minecraft.core.net.command.CommandSender;

public class EditRoleDisplay {

	public static boolean displayName(CommandSender sender, String[] args){
		sender.sendMessage("§5Set Display Name for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).displayName = args[4];
		ConfigManager.saveAllRoles();
		return true;
	}

	public static boolean displayColor(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).displayColor = args[4];
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Display Color for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	public static boolean displayUnderline(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Display Underline (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > display <style>");
			sender.sendMessage("§8      > underline true/false");
			return true;
		}

		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isDisplayUnderlined = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Underline for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isDisplayUnderlined = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Underline for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Display Underline (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	public static boolean displayBold(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Display Bold (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > display <style>");
			sender.sendMessage("§8      > bold true/false");
			return true;
		}

		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isDisplayBold = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Bold for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isDisplayBold = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Bold for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Display Bold (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	public static boolean displayItalics(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Display Italics (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > display <style>");
			sender.sendMessage("§8      > italics true/false");
			return true;
		}

		if(args[4].equalsIgnoreCase("true")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isDisplayItalics = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Italics for role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		} else if(args[4].equalsIgnoreCase("false")){
			ConfigManager.loadAllRoles();
			RoleCommand.getRoleFromArg(args[1]).isDisplayItalics = false;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Display Italics for role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Display Italics (Invalid Boolean)");
		sender.sendMessage("§8(Tip: Use true/false)");
		return true;
	}

	public static boolean displayBorder(CommandSender sender, String[] args){

		if(args.length == 4){
			sender.sendMessage("§eFailed to Edit Display Border (Invalid Syntax)");
			sender.sendMessage("§8  > /role edit <role id> <mode>");
			sender.sendMessage("§8    > display <style>");
			sender.sendMessage("§8      > border <style>");
			sender.sendMessage("§8        > color <color/hex>");
			sender.sendMessage("§8        > none/bracket/caret/curly");
			sender.sendMessage("§8        > custom <affix>");
			sender.sendMessage("§8          > prefix/suffix <custom affix>");
			return true;
		}

		switch(args[4]){
			case "color":
				return displayBorderColor(sender, args);
			case "none":
				return displayBorderNone(sender, args);
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
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > display <style>");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > color <color/hex>");
		sender.sendMessage("§8        > none/bracket/caret/curly");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		return true;
	}

	private static boolean displayBorderColor(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).displayBorderColor = args[5];
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Border Color for role " + RoleCommand.getRoleFromArg(args[1]).displayName + " to: " + args[4]);
		return true;
	}

	private static boolean displayBorderNone(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderNone = true;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderBracket = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCaret = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCurly = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Border to None for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	private static boolean displayBorderBracket(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderNone = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderBracket = true;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCaret = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCurly = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Border to [Bracket] for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	private static boolean displayBorderCaret(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderNone = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderBracket = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCaret = true;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCurly = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Border to <Caret> for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	private static boolean displayBorderCurly(CommandSender sender, String[] args){
		ConfigManager.loadAllRoles();
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderNone = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderBracket = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCaret = false;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCurly = true;
		RoleCommand.getRoleFromArg(args[1]).isDisplayBorderCustom = false;
		ConfigManager.saveAllRoles();
		sender.sendMessage("§5Set Border to {Curly} for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
		return true;
	}

	@SuppressWarnings("SameReturnValue")
	private static boolean displayBorderCustom(CommandSender sender, String[] args){
		if(args.length == 5){
			ConfigManager.loadAllRoles();
			RoleData role = RoleCommand.getRoleFromArg(args[1]);
			role.isDisplayBorderNone = false;
			role.isDisplayBorderBracket = false;
			role.isDisplayBorderCaret = false;
			role.isDisplayBorderCurly = false;
			role.isDisplayBorderCustom = true;
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Border to ?Custom? for role " + RoleCommand.getRoleFromArg(args[1]).displayName);
			return true;
		}
		if(args[5].equals("suffix")){
			if(args.length == 6){
				sender.sendMessage("§eFailed to Edit Custom Suffix (Invalid Syntax)");
				sender.sendMessage("§8  > /role edit <role id> <mode>");
				sender.sendMessage("§8    > display <style>");
				sender.sendMessage("§8      > border <style>");
				sender.sendMessage("§8        > custom <affix>");
				sender.sendMessage("§8          > suffix <custom affix>");
				return true;
			}
			ConfigManager.loadAllRoles();
			RoleData role = RoleCommand.getRoleFromArg(args[1]);
			role.isDisplayBorderBracket = false;
			role.isDisplayBorderCaret = false;
			role.isDisplayBorderCurly = false;
			role.isDisplayBorderCustom = true;
			role.customDisplayBorderSuffix = args[6];
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Custom Display Border Suffix for Role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to " + args[6]);
			return true;
		} else if(args[5].equals("prefix")){
			if(args.length == 6){
				sender.sendMessage("§eFailed to Edit Custom Prefix (Invalid Syntax)");
				sender.sendMessage("§8  > /role edit <role id> <mode>");
				sender.sendMessage("§8    > display <style>");
				sender.sendMessage("§8      > border <style>");
				sender.sendMessage("§8        > custom <affix>");
				sender.sendMessage("§8          > prefix <custom affix>");
				return true;
			}
			ConfigManager.loadAllRoles();
			RoleData role = RoleCommand.getRoleFromArg(args[1]);
			role.isDisplayBorderBracket = false;
			role.isDisplayBorderCaret = false;
			role.isDisplayBorderCurly = false;
			role.isDisplayBorderCustom = true;
			role.customDisplayBorderPrefix = args[6];
			ConfigManager.saveAllRoles();
			sender.sendMessage("§5Set Custom Display Border Prefix for Role: " + RoleCommand.getRoleFromArg(args[1]).displayName + " to " + args[6]);
			return true;
		}

		sender.sendMessage("§eFailed to Edit Custom Display Border (Invalid Syntax)");
		sender.sendMessage("§8  > /role edit <role id> <mode>");
		sender.sendMessage("§8    > display <style>");
		sender.sendMessage("§8      > border <style>");
		sender.sendMessage("§8        > custom <affix>");
		sender.sendMessage("§8          > prefix/suffix <custom affix>");
		return true;
	}
}
