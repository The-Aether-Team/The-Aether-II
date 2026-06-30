package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class LassoLoop extends ThrowableProjectile {
    private static final double LEASH_SNAP_DISTANCE = 12.0;

    public LassoLoop(EntityType<? extends LassoLoop> entityType, Level level) {
        super(entityType, level);
    }

    public LassoLoop(Level level) {
        super(AetherIIEntityTypes.LASSO_LOOP.get(), level);
    }

    public LassoLoop(Level level, LivingEntity owner) {
        super(AetherIIEntityTypes.LASSO_LOOP.get(), owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), level);
        this.setOwner(owner);
    }

    public LassoLoop(Level level, double x, double y, double z) {
        super(AetherIIEntityTypes.LASSO_LOOP.get(), x, y, z, level);
    }

    @Override
    protected void defineSynchedData() { }

    @Override
    public void tick() {
        super.tick();
        Entity owner = this.getOwner();
        if (!this.level().isClientSide() && owner != null && (owner.level() != this.level() || this.distanceToSqr(owner) > LEASH_SNAP_DISTANCE * LEASH_SNAP_DISTANCE)) {
            this.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.LEASH_KNOT_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleoptions = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(AetherIIItems.BRETTL_LASSO.get()));
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (this.getOwner() instanceof Player player && entity != player) {
            if (entity.isAlive() && entity instanceof Mob mob) {
                if (!(mob.getLeashHolder() instanceof Player)) {
                    if (!this.level().isClientSide() && mob.canBeLeashed(player)) {
                        if (mob.isLeashed()) {
                            mob.dropLeash(true, true);
                        }

                        mob.setLeashedTo(player, true);
                        AetherIIDataAttachments.set(entity, AetherIIDataAttachments.LASSO_CONNECTION, true);
                        AetherIIDataAttachments.sync(entity, AetherIIDataAttachments.LASSO_CONNECTION);
                        this.playSound(SoundEvents.LEASH_KNOT_PLACE);
                    }
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
    }
}
