package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class GravititeDebrisShot extends AbstractHurtingProjectile {
    private int ticksInAir;

    public GravititeDebrisShot(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    public GravititeDebrisShot(LivingEntity owner, Level level) {
        this(AetherIIEntityTypes.GRAVITITE_DEBRIS_SHOT.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ());
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        if (this.getOwner() != null) {
            this.setXRot(this.getOwner().getXRot());
            this.setYRot(this.getOwner().getYHeadRot());
            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
        }
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

        Entity entity = this.getOwner();
        this.applyInertia();
        if (this.level().isClientSide() || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            boolean impacted = hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult);
            Vec3 vec3;
            if (impacted) {
                vec3 = hitresult.getLocation();
            } else {
                vec3 = this.position().add(this.getDeltaMovement());
            }

            if (hitresult.getType() != HitResult.Type.MISS && this.isAlive() && impacted) {
                this.onHit(hitresult);
            }

            this.setPos(vec3);
            this.checkInsideBlocks();

            this.baseTick();

        } else {
            this.discard();
        }
    }

    private void applyInertia() {
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec31 = this.position();
        float f;
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.BUBBLE, vec31.x - vec3.x * (double) 0.25F, vec31.y - vec3.y * (double) 0.25F, vec31.z - vec3.z * (double) 0.25F, vec3.x, vec3.y, vec3.z);
            }
            f = 0.8F;
        } else {
            f = this.getInertia();
        }
        this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale(f));
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
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.level() instanceof ServerLevel serverLevel) {
                entity.hurt(this.damageSources().mobProjectile(this, this.getOwner() instanceof LivingEntity owner ? owner : null), 6.0F);
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
        this.ticksInAir = tag.getInt("TicksInAir");
    }
}
