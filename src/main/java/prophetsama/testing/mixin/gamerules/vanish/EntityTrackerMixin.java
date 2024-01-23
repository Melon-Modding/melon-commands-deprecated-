package prophetsama.testing.mixin.gamerules.vanish;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.EntityTrackerEntry;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet28EntityVelocity;
import net.minecraft.core.net.packet.Packet31RelEntityMove;
import net.minecraft.core.net.packet.Packet32EntityLook;
import net.minecraft.core.net.packet.Packet33RelEntityMoveLook;
import net.minecraft.core.net.packet.Packet34EntityTeleport;
import net.minecraft.core.net.packet.Packet35EntityNickname;
import net.minecraft.core.net.packet.Packet40EntityMetadata;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.data.SynchedEntityData;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import prophetsama.testing.mixininterfaces.IVanish;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityTrackerEntry.class, remap = false)
public abstract class EntityTrackerMixin {
	@Shadow
	public Entity trackedEntity;

	@Shadow
	public abstract void sendPacketToTrackedPlayers(Packet packet);

	@Shadow
	public boolean playerEntitiesUpdated;

	@Shadow
	private boolean firstUpdateDone;

	@Shadow
	private double lastTrackedEntityPosX;

	@Shadow
	private double lastTrackedEntityPosY;

	@Shadow
	private double lastTrackedEntityPosZ;

	@Shadow
	public abstract void updatePlayerEntities(List list);

	@Shadow
	private int field_28165_t;

	@Shadow
	public int updateCounter;

	@Shadow
	public int packetDelay;

	@Shadow
	public int encodedPosX;

	@Shadow
	public int encodedPosY;

	@Shadow
	public int encodedPosZ;

	@Shadow
	public int encodedRotationYaw;

	@Shadow
	public int encodedRotationPitch;

	@Shadow
	private boolean shouldSendMotionUpdates;

	@Shadow
	public double lastTrackedEntityMotionX;

	@Shadow
	public double lastTrackedEntityMotionY;

	@Shadow
	public double lastTrackedEntityMotionZ;

	@Shadow
	public abstract void sendPacketToTrackedPlayersAndTrackedEntity(Packet packet);

	@Inject(method = "tick(Ljava/util/List;)V", at = @At("HEAD"), cancellable = true)
	private void cancelVanishedUpdates(List<EntityPlayer> players, CallbackInfo ci){
		if (trackedEntity instanceof EntityPlayerMP){
			EntityPlayerMP client = (EntityPlayerMP)trackedEntity;
			if (((IVanish)client).melonbta_commands$isVanished()) {
				ci.cancel();
				List<EntityPlayer> ops = new ArrayList<>();
				for (EntityPlayer player : players){
					if (player instanceof EntityPlayerMP && ((EntityPlayerMP) player).isOperator()){
						ops.add(player);
					}
				}
				updateOps(ops);
//				sendPacketToTrackedPlayers(new Packet34EntityTeleport(this.trackedEntity.id, 0, -1000, 0, (byte)0, (byte)0));
			}
		}
	}
	@Unique
	private void updateOps(List<EntityPlayer> players) {
		this.playerEntitiesUpdated = false;
		if (!this.firstUpdateDone || this.trackedEntity.distanceToSqr(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0) {
			this.lastTrackedEntityPosX = this.trackedEntity.x;
			this.lastTrackedEntityPosY = this.trackedEntity.y;
			this.lastTrackedEntityPosZ = this.trackedEntity.z;
			this.firstUpdateDone = true;
			this.playerEntitiesUpdated = true;
			this.updatePlayerEntities(players);
		}
		++this.field_28165_t;
		if (++this.updateCounter % this.packetDelay == 0) {
			SynchedEntityData datawatcher;
			double d3;
			double d2;
			double d1;
			double d;
			double d4;
			boolean hasLooked;
			int entityX = MathHelper.floor_double(this.trackedEntity.x * 32.0);
			int entityY = MathHelper.floor_double(this.trackedEntity.y * 32.0);
			int entityZ = MathHelper.floor_double(this.trackedEntity.z * 32.0);
			int entityYaw = MathHelper.floor_float(this.trackedEntity.yRot * 256.0f / 360.0f);
			int entityPitch = MathHelper.floor_float(this.trackedEntity.xRot * 256.0f / 360.0f);
			int dx = entityX - this.encodedPosX;
			int dy = entityY - this.encodedPosY;
			int dz = entityZ - this.encodedPosZ;
			Packet obj = null;
			boolean hasMoved = Math.abs(entityX) >= 8 || Math.abs(entityY) >= 8 || Math.abs(entityZ) >= 8;
			boolean bl = hasLooked = Math.abs(entityYaw - this.encodedRotationYaw) >= 8 || Math.abs(entityPitch - this.encodedRotationPitch) >= 8;
			if (dx < -128 || dx >= 128 || dy < -128 || dy >= 128 || dz < -128 || dz >= 128 || this.field_28165_t > 400) {
				this.field_28165_t = 0;
				this.trackedEntity.x = (double)entityX / 32.0;
				this.trackedEntity.y = (double)entityY / 32.0;
				this.trackedEntity.z = (double)entityZ / 32.0;
				obj = new Packet34EntityTeleport(this.trackedEntity.id, entityX, entityY, entityZ, (byte)entityYaw, (byte)entityPitch);
			} else if (hasMoved && hasLooked) {
				obj = new Packet33RelEntityMoveLook(this.trackedEntity.id, (byte)dx, (byte)dy, (byte)dz, (byte)entityYaw, (byte)entityPitch);
			} else if (hasMoved) {
				obj = new Packet31RelEntityMove(this.trackedEntity.id, (byte)dx, (byte)dy, (byte)dz);
			} else if (hasLooked) {
				obj = new Packet32EntityLook(this.trackedEntity.id, (byte)entityYaw, (byte)entityPitch);
			}
			if (this.shouldSendMotionUpdates && ((d4 = (d = this.trackedEntity.xd - this.lastTrackedEntityMotionX) * d + (d1 = this.trackedEntity.yd - this.lastTrackedEntityMotionY) * d1 + (d2 = this.trackedEntity.zd - this.lastTrackedEntityMotionZ) * d2) > (d3 = 0.02) * d3 || d4 > 0.0 && this.trackedEntity.xd == 0.0 && this.trackedEntity.yd == 0.0 && this.trackedEntity.zd == 0.0)) {
				this.lastTrackedEntityMotionX = this.trackedEntity.xd;
				this.lastTrackedEntityMotionY = this.trackedEntity.yd;
				this.lastTrackedEntityMotionZ = this.trackedEntity.zd;
				this.sendPacketToTrackedPlayers(new Packet28EntityVelocity(this.trackedEntity.id, this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.lastTrackedEntityMotionZ));
			}
			if (obj != null) {
				this.sendPacketToTrackedPlayers(obj);
			}
			if ((datawatcher = this.trackedEntity.getEntityData()).isDirty()) {
				this.sendPacketToTrackedPlayersAndTrackedEntity(new Packet40EntityMetadata(this.trackedEntity.id, datawatcher));
			}
			if (hasMoved) {
				this.encodedPosX = entityX;
				this.encodedPosY = entityY;
				this.encodedPosZ = entityZ;
			}
			if (hasLooked) {
				this.encodedRotationYaw = entityYaw;
				this.encodedRotationPitch = entityPitch;
			}
		}
		if (this.trackedEntity.hurtMarked) {
			this.sendPacketToTrackedPlayersAndTrackedEntity(new Packet28EntityVelocity(this.trackedEntity));
			this.trackedEntity.hurtMarked = false;
		}
		if (this.trackedEntity.hadNicknameSet) {
			this.sendPacketToTrackedPlayersAndTrackedEntity(new Packet35EntityNickname(this.trackedEntity.id, ((EntityLiving)this.trackedEntity).nickname, ((EntityLiving)this.trackedEntity).chatColor));
			this.trackedEntity.hadNicknameSet = false;
		}
	}
}
