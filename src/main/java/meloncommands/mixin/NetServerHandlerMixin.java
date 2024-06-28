package meloncommands.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import meloncommands.MelonCommands;
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

		StringBuilder allRoleDisplays = new StringBuilder();

		for(RoleData role : ConfigManager.roleHashMap.values()){
			if(role.playersGrantedRole.contains(this.playerEntity.username)){

				String roleDisplay = "";

				String borderColor = MelonCommands.getCodeOrHex(role.displayBorderColor);
				String roleColor = MelonCommands.getCodeOrHex(role.displayColor);

				roleDisplay = roleColor + role.displayName;

				if(role.isDisplayUnderlined){
					roleDisplay = TextFormatting.UNDERLINE + roleDisplay;
				}
				if(role.isDisplayBold){
					roleDisplay = TextFormatting.BOLD + roleDisplay;
				}
				if(role.isDisplayItalics){
					roleDisplay = TextFormatting.ITALIC + roleDisplay;
				}

				if(role.isDisplayCustomBorder){
					roleDisplay = borderColor + role.borderPrefix + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + role.borderSuffix;
				} else if (role.isDisplayBracketBorder) {
					roleDisplay = borderColor + "[" + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + "]";
				} else if (role.isDisplayCaretBorder) {
					roleDisplay = borderColor + "<" + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + ">";
				} else if (role.isDisplayCurlyBracketBorder) {
					roleDisplay = borderColor + "{" + TextFormatting.RESET + roleDisplay + TextFormatting.RESET + borderColor + "}";
				}

				roleDisplay = TextFormatting.RESET + roleDisplay + TextFormatting.RESET;
				allRoleDisplays.append(roleDisplay);
			}
		}
		message = allRoleDisplays + "<" + playerEntity.getDisplayName() + TextFormatting.RESET + "> " + TextFormatting.WHITE + message;
		logger.info(message);
		this.mcServer.playerList.sendEncryptedChatToAllPlayers(message);
		ci.cancel();
	}
}
