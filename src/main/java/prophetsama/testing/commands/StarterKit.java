package prophetsama.testing.commands;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import prophetsama.testing.ExampleMod;

public class StarterKit extends Command {
	private final static String COMMAND = "starterkit";
	private final static String NAME = "Starter Kit";

	public StarterKit() { super(COMMAND); }

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {

		for (int i : ExampleMod.starterKitIDs ) {
			commandSender.getPlayer().inventory.insertItem(new ItemStack(i, 1, 1), false);
		}
		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		// Feedback to the player that it executed
		commandSender.sendMessage("Given 1x " + NAME + " to " + commandSender.getPlayer().username);
	}
}
