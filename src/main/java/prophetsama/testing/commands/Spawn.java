package prophetsama.testing.commands;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class Spawn extends Command {
	private final static String COMMAND = "spawn";
	private final static String NAME = "Spawn";

	public Spawn() { super(COMMAND); }

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
		EntityPlayer player = commandSender.getPlayer();
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			playerMP.playerNetServerHandler.teleport(-42.5, 119, -2.5);
		}
		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler handler, CommandSender sender) {
		// Feedback to the player that it executed
		handler.sendCommandFeedback(sender, "Teleported " + sender.getPlayer().username + " to " + NAME);
	}
}
