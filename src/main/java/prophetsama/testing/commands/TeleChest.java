package prophetsama.testing.commands;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.core.player.inventory.InventoryBasic;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.useless.serverlibe.api.event.player.inventory.InventoryServerOpenEvent;
import org.useless.serverlibe.api.gui.GuiHelper;
import org.useless.serverlibe.api.gui.ServerGuiBuilder;
import org.useless.serverlibe.mixin.accessors.EntityPlayerMPAccessor;
import prophetsama.testing.guis.TeleChestGui;


public class TeleChest extends Command {

	private final static String COMMAND = "telechest";


	public TeleChest() {
		super(COMMAND);
	}

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
		TeleChestGui serverGui = new TeleChestGui(new InventoryBasic("TeleChest®", 27), 3);
		EntityPlayerMP player = (EntityPlayerMP) commandSender.getPlayer();
		if (commandSender.getPlayer() instanceof EntityPlayerMP) {
			EntityPlayerMPAccessor accessor = (EntityPlayerMPAccessor)player;
			accessor.serverlibe$getNextWindowId();
			player.playerNetServerHandler.sendPacket(new Packet100OpenWindow(accessor.serverlibe$getCurrentWindowId(), 0, "TeleChest®", 27));
			player.craftingInventory = serverGui;
			player.craftingInventory.windowId = accessor.serverlibe$getCurrentWindowId();
			player.craftingInventory.onContainerInit(player);

			return true;
		}
		return true;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		commandSender.sendMessage("TeleChest");
	}
}
