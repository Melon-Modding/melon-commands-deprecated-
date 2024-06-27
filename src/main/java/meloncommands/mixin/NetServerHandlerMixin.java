package meloncommands.mixin;

import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin {
	@Inject(at = @At("TAIL"), method = "handleChat")
	public void handleChat(Packet3Chat packet, CallbackInfo ci) {

	}
}
