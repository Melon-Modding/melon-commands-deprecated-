package prophetsama.testing.mixin.vanish;

import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import prophetsama.testing.mixininterfaces.IVanish;

@Mixin(value = EntityPlayer.class, remap = false)
public class EntityPlayerMixin implements IVanish {
	@Unique
	public boolean vanished;
	@Override
	public boolean melonbta_commands$isVanished() {
		return vanished;
	}

	@Override
	public void melonbta_commands$setVanished(boolean flag) {
		this.vanished = flag;
	}
}
