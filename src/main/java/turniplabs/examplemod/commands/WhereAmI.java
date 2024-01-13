package turniplabs.examplemod.commands;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.ClientPlayerCommandSender;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class WhereAmI extends Command {
	public WhereAmI() {
		super("/whereami");
	}

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
		EntityPlayerMP player = (EntityPlayerMP) commandSender.getPlayer();
		int px = (int) player.x;
		int py = (int) player.y;
		int pz = (int) player.x;

		commandSender.sendMessage("You are at " + px + " " + py + " " + pz);

		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		commandSender.sendMessage("whereami");
	}
}
