package prophetsama.testing.commands;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import prophetsama.testing.config.ConfigManager;
import prophetsama.testing.config.KitData;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Kit extends Command {
	private final static String COMMAND = "kit";
	private final static String NAME = "Kit";
	public static HashMap<String, Long> cooldowns = new HashMap<>();

private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

public boolean isNumeric(String strNum) {
	if (strNum == null) {
		return false;
	}
	return pattern.matcher(strNum).matches();
}

public static String hmsConversion(long millis) {

	Duration duration = Duration.ofMillis(millis);

	long h = duration.toHours();
	long m = duration.toMinutes() % 60;
	long s = duration.getSeconds() % 60;

    return String.format("%02d:%02d:%02d [h:m:s]", h, m, s);
}

	public Kit() {
		super(COMMAND);
	}

	static int listIndexOf(ItemStack[] items, ItemStack target) {
		List<ItemStack> list = Arrays.asList(items);
		return list.indexOf(target);
	}
	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] args) {
		subcommands:
		{
			if (args.length == 0) {
				return false;
			}

			if (args[0].equals("give")) {
				if (args.length == 1) {
					sender.sendMessage("§eFailed to Give Kit (Invalid Syntax)");
					sender.sendMessage("§8/kit give <kit> §a[<player>]");
					return true;
				}

				if(ConfigManager.configHashMap.containsKey(args[1])) {

					String kit = args[1];
					KitData kitdata = ConfigManager.getConfig(kit);
					long kitCooldown = kitdata.kitCooldown * 1000L;

					if (System.currentTimeMillis() - cooldowns.getOrDefault(sender.getPlayer().username, 0L) > kitCooldown) {
						cooldowns.put(sender.getPlayer().username, System.currentTimeMillis());

						int counter = 0;
						for (ItemStack item : kitdata.kitItems) {
							if(sender.getPlayer().inventory.getStackInSlot(counter) != null){
								return true;
							}
							sender.getPlayer().inventory.setInventorySlotContents(kitdata.kitItemsSlots.get(counter), item);
							counter+=1;
						}

						ConfigManager.saveAll();
						sender.sendMessage("§5Given Kit: '" + kit + "' to " + sender.getPlayer().username);
						return true;
					}
					if (!ConfigManager.configHashMap.containsKey(kit)) {
						sender.sendMessage("§eFailed to Give Kit: '" + kit + "' to " + sender.getPlayer().username + " (Kit Doesn't Exist)");
						sender.sendMessage("");
					} else {
						sender.sendMessage("§1You've already used this kit... time left until next kit: ");
						sender.sendMessage("§1" + hmsConversion(kitCooldown - (System.currentTimeMillis() - cooldowns.getOrDefault(sender.getPlayer().username, 0L))));
						return true;
					}
				}
			}

			if (args[0].equals("reset")) {
				if (args.length == 1) {
					sender.sendMessage("§eFailed to Reset Kit Cooldown (Invalid Syntax)");
					sender.sendMessage("§8/kit reset <kit> [<player>]");
					return true;
				}
				if (args.length > 2) {
					String kit = args[1];
					String player = args[2];
					if (handler.playerExists(player)) {
						cooldowns.put(handler.getPlayer(player).username, 0L);
						sender.sendMessage("§5" + handler.getPlayer(player).username + "'s Kit: '" + kit + "' Cooldown Reset");
						return true;
					} else {
						sender.sendMessage("§eFailed to Reset " + player + "'s Cooldown for Kit: " + kit);
						sender.sendMessage("§e(Player Doesn't Exist)");
						return true;
					}
				}
				String kit = args[1];
				if(ConfigManager.configHashMap.containsKey(kit)) {
					cooldowns.put(sender.getPlayer().username, 0L);
					sender.sendMessage("§5Kit: '" + kit + "' Cooldown Reset!");
					return true;
				}

				return true;
			}


			if (args[0].equals("reload")) {
				ConfigManager.loadAll();
				sender.sendMessage("§5Reloaded All " + ConfigManager.configHashMap.size() + " Kit(s)!");
				return true;
			}


			if (args[0].equals("setcooldown")) {

				if (args.length == 1) {
					sender.sendMessage("§eFailed to Set Kit Cooldown (Invalid Syntax)");
					sender.sendMessage("§8/kit setcooldown <kit> <cooldown>");
					return true;
				}

				String kit = args[1];

				if (args.length > 2 && ConfigManager.configHashMap.containsKey(kit) && isNumeric(args[2])) {
					KitData kitdata = ConfigManager.getConfig(kit);
					kitdata.kitCooldown = Long.parseLong(args[2]);
					ConfigManager.saveAll();
					sender.sendMessage("§5Set Cooldown for Kit: '" + kit + "' to: " + args[2]);
					return true;
				}

				return true;
			}


			if (args[0].equals("list")) {

				if(args.length > 1 && ConfigManager.configHashMap.containsKey(args[1])){

					KitData kitdata = ConfigManager.getConfig(args[1]);


					sender.sendMessage("§8< Kit: '" + args[1] + "' List >");
					sender.sendMessage("§8< Cooldown: " + hmsConversion(kitdata.kitCooldown * 1000) + " >");
					for (ItemStack item : kitdata.kitItems){
						sender.sendMessage("§8> " + item.getDisplayName() + " * " + item.stackSize);
					}
					return true;
				}

				if (ConfigManager.configHashMap.isEmpty()) {
					sender.sendMessage("§8< Kit List >");
					sender.sendMessage("§8-No Kits Created-");
					return true;
				}

				sender.sendMessage("§8< Kit List >");

				for (String kit : ConfigManager.configHashMap.keySet()) {
					sender.sendMessage("§8> " + kit);
				}

				return true;
			}

			if (args[0].equals("create")) {

				if (args.length == 1) {
					sender.sendMessage("§eFailed to Create Kit (Invalid Syntax)");
					sender.sendMessage("§8/kit create <kit> [<cooldown>] [inv]");
					return true;
				}

				String kit = args[1];

				if (ConfigManager.configHashMap.containsKey(kit)) {
					sender.sendMessage("§eFailed to Create Kit: '" + kit + "' (Kit Already Exists)");
					return true;
				}


				if (args.length > 2 && isNumeric(args[2])) {

					if (args.length > 3 && args[3].equals("inv")) {
						return true;
					}

					KitData kitdata = ConfigManager.getConfig(kit);
					kitdata.kitCooldown = Long.parseLong(args[2]);
					ConfigManager.saveAll();
					sender.sendMessage("§5Created Kit: '" + kit + "' with Cooldown: " + args[2]);
					return true;
				}

				ConfigManager.getConfig(kit);
				ConfigManager.saveAll();
				sender.sendMessage("§5Created Kit: '" + kit + "' with Cooldown: 0");
				return true;
			}

			if (args[0].equals("addto")) {

				if (args.length == 1) {
					sender.sendMessage("§eFailed to Add To Kit (Invalid Syntax)");
					sender.sendMessage("§8/kit addto <kit> item/row/armor [head/chest/legs/boots]");
					return true;
				}

				String kit = args[1];

				if (!ConfigManager.configHashMap.containsKey(kit)) {
					sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Kit Doesn't Exist)");
					sender.sendMessage("§8*Tip: Double Check your Spelling*");
					return true;
				}

				KitData kitdata = ConfigManager.getConfig(kit);

				if (args[2].equals("item")){

					if (sender.getPlayer().getHeldItem() == null) {
						sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Held Item is Null)");
						sender.sendMessage("§8*Tip: Hold an item in your hand*");
						return true;
					}

					kitdata.kitItems.add(new ItemStack(sender.getPlayer().getHeldItem()));
					kitdata.kitItemsSlots.add(listIndexOf(sender.getPlayer().inventory.mainInventory, sender.getPlayer().getHeldItem()));
					sender.sendMessage("§5Added [" + sender.getPlayer().getHeldItem() + "] to Kit: '" + kit + "'");
					ConfigManager.saveAll();
					return true;
				}
				if (args[2].equals("row")){
					return true;
				}
				if (args[2].equals("armor")){
					return true;
				}

				return true;
			}

			if (args[0].equals("delete")) {

				if (args.length == 1) {
					sender.sendMessage("§eFailed to Delete Kit (Invalid Syntax)");
					sender.sendMessage("§8/kit delete <kit>");
					return true;
				}

				String kit = args[1];

				switch (ConfigManager.removeKit(kit)) {
					case 0:
						sender.sendMessage("§1Deleted Kit: '" + kit + "'");
						return true;
					case 1:
						sender.sendMessage("§eFailed to Delete Kit: '" + kit + "' (Kit Doesn't Exist)");
						return true;
					case 2:
						sender.sendMessage("§eFailed to Delete Kit: '" + kit + "' (IO Error)");
						return true;
				}
			}
		}

		ConfigManager.loadAll();
		sender.sendMessage("§e Kit Error: (Invalid Syntax)");
		return false;
	}

	private static final List<String> opList = new ArrayList<>();
	static{
		opList.add("create");
		opList.add("reset");
		opList.add("reload");
		opList.add("setcooldown");
		opList.add("delete");
		opList.add("addto");
	}
	@Override
	public boolean opRequired(String[] args) {
		if(args == null) return false;
		if(args.length == 0) return false;
		if(opList.contains(args[0])) return true;
		if(args[0].equals("give") && args.length > 2) return true;
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		// Feedback to the player that it executed [ * = To Do ]
		if(sender.isAdmin()) {
			sender.sendMessage("§8> /kit give <kit> *[<player>]");
			sender.sendMessage("§8> /kit create <kit> [<cooldown>] *[inv]");
			sender.sendMessage("§8> /kit delete <kit>");
			sender.sendMessage("§8> /kit setcooldown <kit> <cooldown>");
			sender.sendMessage("§8> /kit addto <kit> item/*row/*armor *[head/chest/legs/boots]");
			sender.sendMessage("§8> /kit reset <kit> [<player>]");
			sender.sendMessage("§8> /kit list [<kit>]");
			sender.sendMessage("§8> /kit reload");
		}
		else{
			sender.sendMessage("§8> /kit give <kit>");
			sender.sendMessage("§8> /kit list [<kit>]");
		}
	}



}
