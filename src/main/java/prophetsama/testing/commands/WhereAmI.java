package prophetsama.testing.commands;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.ClientPlayerCommandSender;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.server.entity.player.EntityPlayerMP;

/**
 * This command when run prints out information of the players current coordinates.
 ***********************************************************************************/

public class WhereAmI extends Command {
	public WhereAmI() {
		super("whereami");
	}

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
		// Get the player and cast it to EntityPlayerMP
		EntityPlayerMP player = (EntityPlayerMP) commandSender.getPlayer();

		// Get the players coords
		// casted to int to shorten them
		int px = (int) player.x;
		int py = (int) player.y;
		int pz = (int) player.z;

		// Send the info to the chat
		commandSender.sendMessage("You are at " + px + " " + py + " " + pz);

		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		// Feedback is unneeded, since execute is printing the message
		//commandSender.sendMessage("whereami");
	}
}
