package prophetsama.testing.mixin.vanish;

import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet138PlayerList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.util.helper.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import prophetsama.testing.commands.Vanish;
import prophetsama.testing.mixininterfaces.IVanish;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = PlayerList.class, remap = false)
public class PlayerListMixin {
	@Redirect(method = "updateList()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;sendPacketToAllPlayers(Lnet/minecraft/core/net/packet/Packet;)V"))
	private static void hideVanishedPlayers(net.minecraft.server.net.PlayerList instance, Packet originalPacket){
		sendToAllOps();
		sentToAllNonOps();
	}
	@Unique
	private static void sendToAllOps(){
		MinecraftServer server = MinecraftServer.getInstance();
		int playerCount = server.playerList.playerEntities.size();
		List<String> players = new ArrayList<>();
		List<String> scores = new ArrayList<>();
		for (int i = 0; i < playerCount; ++i) {
			EntityPlayerMP player = server.playerList.playerEntities.get(i);
			players.add(Vanish.getVanishName(player));
			scores.add(String.valueOf(player.getScore()));
		}
		Packet packet = new Packet138PlayerList(players.size(), players.toArray(new String[0]), scores.toArray(new String[0]));
        Vanish.sendPacketToAllOps(packet);
	}
	@Unique
	private static void sentToAllNonOps(){
		MinecraftServer server = MinecraftServer.getInstance();
		int playerCount = server.playerList.playerEntities.size();
		List<String> players = new ArrayList<>();
		List<String> scores = new ArrayList<>();
		for (int i = 0; i < playerCount; ++i) {
			EntityPlayerMP player = server.playerList.playerEntities.get(i);
			if (((IVanish)player).melonbta_commands$isVanished()) continue;
			players.add(player.getDisplayName());
			scores.add(String.valueOf(player.getScore()));
		}
		Packet packet = new Packet138PlayerList(players.size(), players.toArray(new String[0]), scores.toArray(new String[0]));
        Vanish.sendPacketToAllNonOps(packet);
	}
}
