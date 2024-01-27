package prophetsama.testing.mixin.vanish;

import net.minecraft.core.net.packet.Packet72UpdatePlayerProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.PlayerList;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import prophetsama.testing.mixininterfaces.IVanish;

@Mixin(value = PlayerList.class, remap = false)
public class OtherPlayerListMixin {
	@Redirect(method = "playerLoggedIn(Lnet/minecraft/server/entity/player/EntityPlayerMP;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/handler/NetServerHandler;handleSendInitialPlayerList()V"))
	private void cloakVanishedUsersOnJoin(NetServerHandler instance, EntityPlayerMP player){
		if (!player.isOperator()){
			MinecraftServer server = MinecraftServer.getInstance();
			for (EntityPlayerMP entityPlayerMP : server.playerList.playerEntities) {
				if (((IVanish)entityPlayerMP).melonbta_commands$isVanished()) continue;
				instance.sendPacket(new Packet72UpdatePlayerProfile(entityPlayerMP.username, entityPlayerMP.nickname, entityPlayerMP.score, entityPlayerMP.chatColor, true, entityPlayerMP.isOperator()));
			}
			return;
		}
		instance.handleSendInitialPlayerList();
	}
}
