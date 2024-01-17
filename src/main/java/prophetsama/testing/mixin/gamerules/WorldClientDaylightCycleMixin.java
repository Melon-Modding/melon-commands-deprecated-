package prophetsama.testing.mixin.gamerules;

import prophetsama.testing.MelonBTACommands;
import net.minecraft.client.world.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WorldClient.class, remap = false)
public abstract class WorldClientDaylightCycleMixin extends WorldDaylightCycleMixin{

	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/WorldClient;setWorldTime(J)V"))
	private void checkDaylightCycleRule(WorldClient instance, long l) {
		if((Boolean)this.getGameRule(MelonBTACommands.DAYLIGHT_CYCLE)) {
			instance.setWorldTime(l);
		}
	}

}
