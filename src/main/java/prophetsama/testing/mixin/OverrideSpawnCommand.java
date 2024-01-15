package prophetsama.testing.mixin;

import net.minecraft.core.net.command.commands.SpawnCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SpawnCommand.class, remap = false)
public abstract class OverrideSpawnCommand {

	@Inject(method = "opRequired", at = @At("RETURN"), cancellable = true)
	public void melon_opRequired(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
    }
}
