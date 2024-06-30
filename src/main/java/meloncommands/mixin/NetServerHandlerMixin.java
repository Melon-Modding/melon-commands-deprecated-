package meloncommands.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import meloncommands.MelonCommands;
import meloncommands.RoleBuilder;
import meloncommands.config.ConfigManager;
import meloncommands.config.RoleData;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.apache.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Shadow
	public static Logger logger;

	@Shadow
	private MinecraftServer mcServer;

	@Inject(at = @At(shift = At.Shift.AFTER, value = "INVOKE", target = "Lnet/minecraft/core/net/ChatEmotes;process(Ljava/lang/String;)Ljava/lang/String;"), method = "handleChat", cancellable = true)
	public void handleChat(Packet3Chat packet, CallbackInfo ci, @Local String message) {

		StringBuilder roleDisplays = new StringBuilder();
		String roleUsername = "" + TextFormatting.RESET + TextFormatting.WHITE + "<" + this.playerEntity.username + "> ";
		String roleTextFormatting = "" + TextFormatting.WHITE;
		ArrayList<RoleData> rolesGranted = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			rolesGranted.add(null);
		}

		for(RoleData role : ConfigManager.roleHashMap.values()){
			if(role.playersGrantedRole.contains(this.playerEntity.username)){
				rolesGranted.add(role.priority, role);
			}
		}

		for(RoleData role : rolesGranted){
            if (role != null) {
				roleUsername = RoleBuilder.buildRoleUsername(role, this.playerEntity.username);
				roleTextFormatting = RoleBuilder.buildRoleTextFormat(role);
				break;
			}
        }

		for(RoleData role : rolesGranted){if(role != null){roleDisplays.append(RoleBuilder.buildRoleDisplay(role));}}

		message = roleDisplays + roleUsername + roleTextFormatting + message;
		logger.info(message);
		this.mcServer.playerList.sendEncryptedChatToAllPlayers(message);
		ci.cancel();

	}
}
