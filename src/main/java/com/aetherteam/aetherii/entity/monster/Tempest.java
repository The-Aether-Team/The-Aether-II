package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.projectile.TempestThunderball;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class Tempest extends Zephyr {
    public static final EntityDataAccessor<Integer> DATA_ATTACK_CHARGE_ID = SynchedEntityData.defineId(Tempest.class, EntityDataSerializers.INT);

    public Tempest(EntityType<? extends Tempest> type, Level level) {
        super(type, level);
        this.moveControl = new Zephyr.ZephyrMoveControl(this);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new Zephyr.RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new Zephyr.ZephyrLookGoal(this));
        this.goalSelector.addGoal(5, new Tempest.ThunderballAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }


    public static AttributeSupplier.Builder createMobAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.FOLLOW_RANGE, 50.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ATTACK_CHARGE_ID, 0);
    }

    public static boolean checkTempestSpawnRules(EntityType<? extends Tempest> tempest, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && level.getBlockState(pos.below()).is(AetherIIBlocks.STORM_AERCLOUD.get()) && Mob.checkMobSpawnRules(tempest, level, reason, pos, random) && (reason != MobSpawnType.NATURAL || random.nextInt(11) == 0) && level.canSeeSky(pos) && isNight(level);
    }

    private static boolean isNight(LevelAccessor level){
        return !(level.getSkyDarken() < 4) && !level.dimensionType().hasFixedTime();
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide() && !isNight(this.level())) {
            this.hurt(this.level().damageSources().generic(), 1);
            for (int i = 0; i < 7; ++i) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.SMOKE, this.getRandomX(0.3), this.getRandomY() - 0.1, this.getRandomZ(0.3), 1, 0, 0, 0, this.random.nextGaussian() * 0.02);
                }
            }
        }
        for (int count = 0; count < 3; ++count) {
            double xOffset = this.position().x() + (this.level().getRandom().nextDouble() * 1.5) - 0.75;
            double yOffset = this.position().y() + (this.level().getRandom().nextDouble() * 2) - 0.5;
            double zOffset = this.position().z() + (this.level().getRandom().nextDouble() * 1.5) - 0.75;
            this.level().addParticle(AetherIIParticleTypes.TEMPEST_ELECTRICITY.get(), xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
        }
        super.tick();
    }

    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_ZEPHYR_AMBIENT.get();
    }

    protected SoundEvent getHurtSound( DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_ZEPHYR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_ZEPHYR_DEATH.get();
    }


    static class ThunderballAttackGoal extends Goal {
        private final Tempest parentEntity;

        public ThunderballAttackGoal(Tempest tempest) {
            this.parentEntity = tempest;
        }

        /**
         * Returns whether execution should begin. You can also read and cache
         * any state necessary for execution in this method as well.
         */
        @Override
        public boolean canUse() {
            return this.parentEntity.getTarget() != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
            this.parentEntity.setChargeTime(0);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted
         * by another one
         */
        @Override
        public void stop() {
            this.parentEntity.setChargeTime(0);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            LivingEntity target = this.parentEntity.getTarget();
            if (target.distanceToSqr(this.parentEntity) < 40 * 40 && this.parentEntity.hasLineOfSight(target)) {
                Level level = this.parentEntity.level();
                this.parentEntity.setChargeTime(this.parentEntity.getChargeTime() + 1);
                if (this.parentEntity.getChargeTime() == 10) {
                    this.parentEntity.playSound(this.parentEntity.getAmbientSound(), 0.75F, (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F);
                } else if (this.parentEntity.getChargeTime() == 20) {
                    Vec3 look = this.parentEntity.getViewVector(1.0F);
                    double accelX = target.getX() - (this.parentEntity.getX() + look.x * 4.0);
                    double accelY = target.getY(0.5) - (0.5 + this.parentEntity.getY(0.5));
                    double accelZ = target.getZ() - (this.parentEntity.getZ() + look.z * 4.0);
                    this.parentEntity.playSound(AetherIISoundEvents.ENTITY_ZEPHYR_SHOOT.get(), 0.75F, (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F);
                    TempestThunderball thunderBall = new TempestThunderball(level, this.parentEntity, accelX, accelY, accelZ);
                    thunderBall.setPos(this.parentEntity.getX() + look.x * 4.0, this.parentEntity.getY(0.5) + 0.5, this.parentEntity.getZ() + look.z * 4.0);
                    level.addFreshEntity(thunderBall);
                    this.parentEntity.setChargeTime(-40);
                }
            } else if (this.parentEntity.getChargeTime() > 0) {
                this.parentEntity.setChargeTime(this.parentEntity.getChargeTime() - 1);
            }
        }
    }
}