package prophetsama.testing.mixin.gamerules;

import net.minecraft.core.world.World;
import net.minecraft.core.block.BlockFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import prophetsama.testing.MelonBTACommands;

import java.util.Random;

@Mixin(value = BlockFire.class, remap = false)
public abstract class FireTickMixin {

	@Inject(at = @At("HEAD"), cancellable = true, method = "setBurnResult(Lnet/minecraft/core/world/World;III)V")
	private void checkFireTickRule(World world, int x, int y, int z, CallbackInfo ci) {
		if(!(Boolean)world.getGameRule(MelonBTACommands.FIRE_TICKS)) {
			ci.cancel();
		}
	}
	@Inject(at = @At("HEAD"), cancellable = true, method = "canNeighborCatchFire(Lnet/minecraft/core/world/World;III)Z")
	private void checkFireTickRuleForSpread(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		if(!(Boolean)world.getGameRule(MelonBTACommands.FIRE_TICKS)) {
			cir.setReturnValue(false);
		}
	}


}
