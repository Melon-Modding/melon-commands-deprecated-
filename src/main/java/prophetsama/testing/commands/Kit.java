package prophetsama.testing.commands;

import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.player.inventory.InventoryPlayer;
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

	public static int[] evalInventory(InventoryPlayer inventory, ItemStack comparisonStack){

		int[] slotEval = new int[36];

		/* slotEval Key
		* 0 = null
		* 1 = can merge into slot
		* 2 = merge and split
		* 3 = cannot go into slot
		*/
		for(int i=0; i < 36; i++){
			ItemStack stackInSlot = inventory.getStackInSlot(i);

			if(stackInSlot == null){
				slotEval[i] = 0;
				continue;
			}

			if(stackInSlot.stackSize >= stackInSlot.getMaxStackSize()){
				slotEval[i] = 3;
				continue;
			}

			if(stackInSlot.isItemEqual(comparisonStack)){

				if(stackInSlot.stackSize + comparisonStack.stackSize > stackInSlot.getMaxStackSize()){
					slotEval[i] = 2;
					continue;
				}

				slotEval[i] = 1;
				continue;
			}

			slotEval[i] = 3;
			continue;
		}
		return slotEval;
	}

	public static void insertItemAtSlot(int idealSlot, ItemStack item, CommandSender sender){

		int[] slotEval = evalInventory(sender.getPlayer().inventory, item);


		for (int i = 0; i < 36; i++) {

			int currentID = (idealSlot + i) % 36;
			int slot = slotEval[currentID];

			if(slot == 3){
				continue;
			}

			if(slot == 0){
				sender.getPlayer().inventory.setInventorySlotContents(currentID, new ItemStack(item));
				return;
			}

			ItemStack inventoryStack = sender.getPlayer().inventory.getStackInSlot(currentID);

			if(slot == 1){
				inventoryStack.stackSize += item.stackSize;
				return;
			}

			if(slot == 2){

				int stackSum = inventoryStack.stackSize + item.stackSize;

				inventoryStack.stackSize = item.getMaxStackSize();
				ItemStack newStack = new ItemStack(item);
				newStack.stackSize = stackSum - item.getMaxStackSize();
				insertItemAtSlot((currentID + 1) % 36, newStack, sender);
				return;
			}
		}
		EntityItem itemToDrop = new EntityItem(sender.getPlayer().world, sender.getPlayer().x, sender.getPlayer().y + 1, sender.getPlayer().z, new ItemStack(item));
		itemToDrop.delayBeforeCanPickup = 10;
		sender.getPlayer().world.entityJoinedWorld(itemToDrop);
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
					sender.sendMessage("§8/kit give <kit> [<overwrite?>]");
					return true;
				}

				if (ConfigManager.configHashMap.containsKey(args[1])) {

					String kit = args[1];
					KitData kitdata = ConfigManager.getConfig(kit);
					long kitCooldown = kitdata.kitCooldown * 1000L;

					if (args.length > 2 && args[2].equals("true")) {

						if (System.currentTimeMillis() - cooldowns.getOrDefault(sender.getPlayer().username, 0L) > kitCooldown) {
							cooldowns.put(sender.getPlayer().username, System.currentTimeMillis());

							int counter = 0;

							for (ItemStack item : kitdata.kitItemStacks) {
								if (kitdata.kitItemNames.get(counter) != null) {
									item.setCustomName(kitdata.kitItemNames.get(counter));
								}
								sender.getPlayer().inventory.setInventorySlotContents(kitdata.kitItemSlots.get(counter++), new ItemStack(item));
							}
							//give items ^

							counter = 0;

							for (ItemStack armor : kitdata.kitArmorStacks) {
								if (kitdata.kitArmorNames.get(counter) != null) {
									armor.setCustomName(kitdata.kitArmorNames.get(counter));
								}
								sender.getPlayer().inventory.setInventorySlotContents(kitdata.kitArmorSlots.get(counter++), new ItemStack(armor));
							}
							//give armor ^

							ConfigManager.saveAll();
							sender.sendMessage("§5Given Kit: '" + kit + "' to " + sender.getPlayer().username);
							return true;
						}
					}

					if (System.currentTimeMillis() - cooldowns.getOrDefault(sender.getPlayer().username, 0L) > kitCooldown) {
						cooldowns.put(sender.getPlayer().username, System.currentTimeMillis());

						int counter = 0;


						for (ItemStack item : kitdata.kitItemStacks) {
							if (kitdata.kitItemNames.get(counter) != null) {
								item.setCustomName(kitdata.kitItemNames.get(counter));
							}
							insertItemAtSlot(kitdata.kitItemSlots.get(counter++), item, sender);
						}
						//give items ^

						counter = 0;

						for (ItemStack armor : kitdata.kitArmorStacks) {
							if (kitdata.kitArmorNames.get(counter) != null) {
								armor.setCustomName(kitdata.kitArmorNames.get(counter));
							}
							if (sender.getPlayer().inventory.getStackInSlot(39 - counter) != null) {
								insertItemAtSlot(0, armor, sender);
								counter++;
								continue;
							}
							sender.getPlayer().inventory.setInventorySlotContents(kitdata.kitArmorSlots.get(counter++), new ItemStack(armor));
						}
						//give armor ^

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
				if (ConfigManager.configHashMap.containsKey(kit)) {
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

				if (args.length > 1 && ConfigManager.configHashMap.containsKey(args[1])) {

					KitData kitdata = ConfigManager.getConfig(args[1]);

					sender.sendMessage("§8< Kit: '" + args[1] + "' List >");
					sender.sendMessage("§8< Cooldown: " + hmsConversion(kitdata.kitCooldown * 1000) + " >");
					sender.sendMessage("§8< Armor: >");
					for (ItemStack armor : kitdata.kitArmorStacks) {
						sender.sendMessage("§8  > " + armor.getDisplayName());
					}
					sender.sendMessage("§8< Items: >");
					for (ItemStack item : kitdata.kitItemStacks) {
						sender.sendMessage("§8  > " + item.getDisplayName() + " * " + item.stackSize);
					}


					return true;

				}

				if (ConfigManager.configHashMap.isEmpty()) {
					sender.sendMessage("§8< Kits: >");
					sender.sendMessage("§8  -No Kits Created-");
					return true;
				}

				sender.sendMessage("§8< Kits: >");

				for (String kit : ConfigManager.configHashMap.keySet()) {
					sender.sendMessage("§8  > " + kit);
				}

				return true;
			}

			if (args[0].equals("create")) {

				if (args.length == 1) {
					sender.sendMessage("§eFailed to Create Kit (Invalid Syntax)");
					sender.sendMessage("§8/kit create <kit> [<cooldown>]");
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
					sender.sendMessage("§8/kit addto <kit> item/row/all/armor ->");
					sender.sendMessage("§8(if armor) [head/chest/legs/boots/all]");
					return true;
				}

				String kit = args[1];

				if (!ConfigManager.configHashMap.containsKey(kit)) {
					sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Kit Doesn't Exist)");
					sender.sendMessage("§8*Tip: Double Check your Spelling*");
					return true;
				}

				KitData kitdata = ConfigManager.getConfig(kit);

				if (args[2].equals("item")) {

					if (sender.getPlayer().getHeldItem() == null) {
						sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Held Item is Null)");
						sender.sendMessage("§8*Tip: Hold an item in your hand*");
						return true;
					}

					kitdata.additem(new ItemStack(sender.getPlayer().getHeldItem()), listIndexOf(sender.getPlayer().inventory.mainInventory, sender.getPlayer().getHeldItem()));
					sender.sendMessage("§5Added [" + sender.getPlayer().getHeldItem() + "] to Kit: '" + kit + "'");
					ConfigManager.saveAll();
					return true;
				}
				if (args[2].equals("row")) {
					int row = sender.getPlayer().inventory.hotbarOffset;
					for (int i = 0; i < 9; i++) {

						if (sender.getPlayer().inventory.getStackInSlot(i + row) == null) {
							continue;
						}

						kitdata.additem(new ItemStack(sender.getPlayer().inventory.getStackInSlot(i + row)), listIndexOf(sender.getPlayer().inventory.mainInventory, sender.getPlayer().inventory.getStackInSlot(i + row)));

					}

					ConfigManager.saveAll();
					sender.sendMessage("§5Added Row to Kit: '" + kit + "'");

					return true;
				}
				if (args[2].equals("armor")) {
					if (args[3].equals("head")) {
						if (sender.getPlayer().inventory.getStackInSlot(39) == null) {
							sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Equipped Armor is Null)");
							sender.sendMessage("§8*Tip: Equip armor in your " + args[3] + " slot*");
							return true;
						}
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(39), 39);
						sender.sendMessage("§5Added " + sender.getPlayer().inventory.getStackInSlot(39) + " to Kit: '" + kit + "'");
						return true;
					}
					if (args[3].equals("chest")) {
						if (sender.getPlayer().inventory.getStackInSlot(38) == null) {
							sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Equipped Armor is Null)");
							sender.sendMessage("§8*Tip: Equip armor in your " + args[3] + " slot*");
							return true;
						}
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(38), 38);
						sender.sendMessage("§5Added " + sender.getPlayer().inventory.getStackInSlot(38) + " to Kit: '" + kit + "'");
						return true;
					}
					if (args[3].equals("legs")) {
						if (sender.getPlayer().inventory.getStackInSlot(37) == null) {
							sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Equipped Armor is Null)");
							sender.sendMessage("§8*Tip: Equip armor in your " + args[3] + " slot*");
							return true;
						}
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(37), 37);
						sender.sendMessage("§5Added " + sender.getPlayer().inventory.getStackInSlot(37) + " to Kit: '" + kit + "'");
						return true;
					}
					if (args[3].equals("boots")) {
						if (sender.getPlayer().inventory.getStackInSlot(36) == null) {
							sender.sendMessage("§eFailed to Add To Kit: '" + kit + "' (Equipped Armor is Null)");
							sender.sendMessage("§8*Tip: Equip armor in your " + args[3] + " slot*");
							return true;
						}
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(36), 36);
						sender.sendMessage("§5Added " + sender.getPlayer().inventory.getStackInSlot(36) + " to Kit: '" + kit + "'");
						return true;
					}
					if (args[3].equals("all")) {
						if (sender.getPlayer().inventory.getStackInSlot(39) != null) {
							kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(39), 39);
						}
						if (sender.getPlayer().inventory.getStackInSlot(38) != null) {
							kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(38), 38);
						}
						if (sender.getPlayer().inventory.getStackInSlot(37) != null) {
							kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(37), 37);
						}
						if (sender.getPlayer().inventory.getStackInSlot(36) != null) {
							kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(36), 36);
						}
						sender.sendMessage("§5Added All Armor to Kit: '" + kit + "'");
						return true;
					}
					return true;
				}
				if (args[2].equals("all")) {
					for (int i = 0; i < 4; i++) {
						int row = i * 9;
						for (int j = 0; j < 9; j++) {

							if (sender.getPlayer().inventory.getStackInSlot(j + row) == null) {
								continue;
							}

							kitdata.additem(new ItemStack(sender.getPlayer().inventory.getStackInSlot(j + row)), listIndexOf(sender.getPlayer().inventory.mainInventory, sender.getPlayer().inventory.getStackInSlot(j + row)));

						}
					}
					if (sender.getPlayer().inventory.getStackInSlot(39) != null) {
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(39), 39);
					}
					if (sender.getPlayer().inventory.getStackInSlot(38) != null) {
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(38), 38);
					}
					if (sender.getPlayer().inventory.getStackInSlot(37) != null) {
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(37), 37);
					}
					if (sender.getPlayer().inventory.getStackInSlot(36) != null) {
						kitdata.addarmor(sender.getPlayer().inventory.getStackInSlot(36), 36);
					}

					sender.sendMessage("§5Added All Items and Armor to Kit: " + kit);
					ConfigManager.saveAll();
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

		if (args == null) return false;
		if (args.length == 0) return false;
		if (opList.contains(args[0])) return true;
		if (args[0].equals("give") && args.length > 2) return true;
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		// Feedback to the player that it executed [ * = To Do ]

		if (sender.isAdmin()) {
			sender.sendMessage("§8< Command Syntax >");
			sender.sendMessage("§8  > /kit give <kit> [<overwrite?>]");
			sender.sendMessage("§8  > /kit create <kit> [<cooldown>]");
			sender.sendMessage("§8  > /kit delete <kit>");
			sender.sendMessage("§8  > /kit setcooldown <kit> <cooldown>");
			sender.sendMessage("§8  > /kit addto <kit> item/row/all/armor ->");
			sender.sendMessage("§8    (if armor) [head/chest/legs/boots/all]");
			sender.sendMessage("§8  > /kit reset <kit> [<player>]");
			sender.sendMessage("§8  > /kit list [<kit>]");
			sender.sendMessage("§8  > /kit reload");
		} else {
			sender.sendMessage("§8< Command Syntax >");
			sender.sendMessage("§8  > /kit give <kit> [<overwrite?>]");
			sender.sendMessage("§8  > /kit list [<kit>]");
			}
	}
}
