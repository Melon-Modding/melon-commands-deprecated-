package prophetsama.testing.mixin.gamerules.vanish;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityTrackerEntry;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet34EntityTeleport;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import prophetsama.testing.mixininterfaces.IVanish;

import java.util.List;

@Mixin(value = EntityTrackerEntry.class, remap = false)
public abstract class EntityTrackerMixin {
	@Shadow
	public Entity trackedEntity;

	@Shadow
	public abstract void sendPacketToTrackedPlayers(Packet packet);

	@Inject(method = "tick(Ljava/util/List;)V", at = @At("HEAD"), cancellable = true)
	private void cancelVanishedUpdates(List<EntityPlayer> players, CallbackInfo ci){
		if (trackedEntity instanceof EntityPlayerMP){
			EntityPlayerMP client = (EntityPlayerMP)trackedEntity;
			if (((IVanish)client).melonbta_commands$isVanished()) {
				ci.cancel();
//				sendPacketToTrackedPlayers(new Packet34EntityTeleport(this.trackedEntity.id, 0, -1000, 0, (byte)0, (byte)0));
			}
		}
	}
}
