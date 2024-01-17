package prophetsama.testing.mixin.gamerules;

import prophetsama.testing.MelonBTACommands;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WorldServer.class, remap = false)
public abstract class WorldServerDaylightCycle extends WorldDaylightCycleMixin{
	@Redirect(method = "updateSleepingPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/save/LevelData;setWorldTime(J)V"))
	private void fixSleeping(LevelData instance, long l) {
		if((Boolean)this.getGameRule(MelonBTACommands.DAYLIGHT_CYCLE)) {
			instance.setWorldTime(l);
		}
	}
}
