package prophetsama.testing.commands;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandError;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet20NamedEntitySpawn;
import net.minecraft.core.net.packet.Packet29DestroyEntity;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.core.net.packet.Packet72UpdatePlayerProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import prophetsama.testing.mixininterfaces.IVanish;

import java.util.Arrays;

public class Vanish extends Command {
	private final static String COMMAND = "vanish";
	private final static String NAME = "Vanish";
	public Vanish() {
		super(COMMAND);
	}

	@Override
	public boolean execute(CommandHandler handler, CommandSender sender, String[] strings) {
		if (!sender.isPlayer()){
			throw new CommandError("Must be used by a player!");
		}
		EntityPlayerMP player = (EntityPlayerMP)sender.getPlayer();
		if (strings.length == 0){
			sender.sendMessage(String.format("Vanish for player %s is set to %s", sender.getName(), ((IVanish)sender.getPlayer()).melonbta_commands$isVanished()));
			return true;
		}
		if (strings.length == 1){
			try {
				boolean originalVanish = ((IVanish)sender.getPlayer()).melonbta_commands$isVanished();
				boolean vanishState = Boolean.parseBoolean(strings[0]);
				((IVanish)sender.getPlayer()).melonbta_commands$setVanished(vanishState);
				sender.sendMessage(String.format("Vanish for player %s set to %s", sender.getName(), vanishState));
				player.mcServer.playerList.sendPacketToAllPlayers(new Packet72UpdatePlayerProfile(player.username, player.nickname, player.score, player.chatColor, !vanishState, player.isOperator()));

				if (originalVanish != vanishState){
					if (vanishState){
						sendPacketToAllNonOps(new Packet3Chat(player.getDisplayName() + TextFormatting.YELLOW + " left the game."));
						sendPacketToAllNonOps(new Packet29DestroyEntity(player.id));
					} else {
						sendPacketToAllNonOps(new Packet3Chat(TextFormatting.YELLOW + player.getDisplayName() + TextFormatting.YELLOW + " joined the game."));
						sendPacketToAllNonOps(new Packet20NamedEntitySpawn(player));
					}
				}
				return true;
			} catch (Exception e){
				sender.sendMessage(Arrays.toString(e.getStackTrace()));
			}
		}
		return false;
	}
	public static String getVanishName(EntityPlayer player){
		if (((IVanish)player).melonbta_commands$isVanished()) {
			return (player.getDisplayName() + TextFormatting.RESET + " (Hidden)");
		} else {
			return (player.getDisplayName());
		}
	}
	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		commandSender.sendMessage("/vanish <true/false>");
	}
	public static void sendPacketToAllOps(Packet packet){
		MinecraftServer server = MinecraftServer.getInstance();
		for (EntityPlayerMP entityplayermp : server.playerList.playerEntities) {
			if (!entityplayermp.isOperator()) continue;
			entityplayermp.playerNetServerHandler.sendPacket(packet);
		}
	}
	public static void sendPacketToAllNonOps(Packet packet){
		MinecraftServer server = MinecraftServer.getInstance();
		for (EntityPlayerMP entityplayermp : server.playerList.playerEntities) {
			if (entityplayermp.isOperator()) continue;
			entityplayermp.playerNetServerHandler.sendPacket(packet);
		}
	}
}
