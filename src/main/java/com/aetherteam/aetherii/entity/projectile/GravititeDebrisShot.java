package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class GravititeDebrisShot extends AbstractHurtingProjectile {
    private int ticksInAir;

    public GravititeDebrisShot(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    public GravititeDebrisShot(LivingEntity owner, Level level) {
        super(AetherIIEntityTypes.GRAVITITE_DEBRIS_SHOT.get(), level);
        this.setOwner(owner);
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        if (!this.onGround()) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 400) {
            if (!this.level().isClientSide()) {
                this.discard();
            }
        }
        if (this.level().isClientSide() || (this.getOwner() == null || this.getOwner().isAlive()) && this.level().hasChunkAt(this.blockPosition())) {
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitResult)) {
                this.onHit(hitResult);
            }

            this.applyEffectsFromBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x();
            double d1 = this.getY() + vec3.y();
            double d2 = this.getZ() + vec3.z();
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                this.discard();
            }

            this.setDeltaMovement(vec3.add(vec3.normalize().scale(this.accelerationPower)).scale(f));
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                entity.hurt(this.damageSources().indirectMagic(this, entity1), 2.0F);
            }
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TicksInAir", this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("TicksInAir")) {
            this.ticksInAir = tag.getInt("TicksInAir");
        }
    }
}
