package com.aetherteam.aetherii.entity.projectile;

import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ElectricField;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class TempestThunderball extends AbstractHurtingProjectile {
    private int ticksInAir;

    public TempestThunderball(EntityType<? extends TempestThunderball> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public TempestThunderball(Level level, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(AetherIIEntityTypes.TEMPEST_THUNDERBALL.get(), shooter, new Vec3(accelX, accelY, accelZ), level);
        this.setNoGravity(true);
    }

    /**
     * [CODE COPY] - {@link AbstractHurtingProjectile#tick()}.<br><br>
     * Remove code for setting the projectile on fire.
     * Warning for "deprecation" is suppressed because vanilla calls {@link Level#hasChunkAt(BlockPos)} just fine.
     */
    @SuppressWarnings("deprecation")
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

            this.checkInsideBlocks();
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
            if (level().isClientSide()) {
                this.level().addParticle(this.getTrailParticle(), d0, d1, d2, 0.0, 0.0, 0.0);
                this.level().addParticle(this.getTrailParticle(), d0 - random.nextFloat(), d1 + random.nextFloat() - 0.1, d2 - random.nextFloat(), 0, 0, 0);
                this.level().addParticle(this.getTrailParticle(), d0 + random.nextFloat(), d1 + random.nextFloat() - 0.1, d2 + random.nextFloat(), 0, 0, 0);
                this.level().addParticle(this.getTrailParticle(), d0 + random.nextFloat(), d1 + random.nextFloat() - 0.1, d2 - random.nextFloat(), 0, 0, 0);
                this.level().addParticle(this.getTrailParticle(), d0 - random.nextFloat(), d1 + random.nextFloat() - 0.1, d2 + random.nextFloat(), 0, 0, 0);
            }
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

        this.spawnElectricCircle(result.getLocation().x(), result.getLocation().z());
    }

    private void spawnElectricCircle(double x, double z) {
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, Level.ExplosionInteraction.NONE);
        ElectricField electricCircle = new ElectricField(this.level(), x, this.getY(), z);

        electricCircle.setParticle(this.getTrailParticle());
        electricCircle.setOwner((LivingEntity) this.getOwner());
        electricCircle.setRadius(2.5F);
        electricCircle.setRadiusOnUse(-0.5F);
        electricCircle.setWaitTime(10);
        electricCircle.setDuration(electricCircle.getDuration() / 5);

        electricCircle.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0, false, false, false));
        electricCircle.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 1, false, false, false));

        this.level().addFreshEntity(electricCircle);
        electricCircle.playSound(SoundEvents.AMETHYST_BLOCK_CHIME);
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

    /**
     * Prevents this projectile from being on fire.
     */
    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.ELECTRIC_SPARK;
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