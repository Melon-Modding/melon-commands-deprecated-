package prophetsama.testing.mixin.gamerules;

import net.minecraft.core.world.World;
import net.minecraft.core.block.BlockFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import prophetsama.testing.MelonBTACommands;

import java.util.Random;

@Mixin(value = BlockFire.class, remap = false)
public abstract class FireTickMixin {

	@Inject(at = @At("HEAD"), cancellable = true, method = "updateTick")
	private void checkFireTickRule(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
		if(!(Boolean)world.getGameRule(MelonBTACommands.FIRE_TICKS)) {
			ci.cancel();
		}
	}

}
