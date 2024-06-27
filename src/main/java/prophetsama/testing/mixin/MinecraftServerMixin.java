package prophetsama.testing.mixin;

import net.minecraft.server.MinecraftServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import prophetsama.testing.TpaManager;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MinecraftServerMixin {
	@Inject(at = @At("HEAD"), method = "doTick")
	private void doTick(CallbackInfo ci) {
		TpaManager.tick();
	}
}