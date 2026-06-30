package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.entity.ai.goal.ClosedAnimationMeleeAttackGoal;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Cockatrice extends Monster implements RangedAttackMob, Blighted {
    public static int HIDE_ANIMATION_START = 200;
    public static int HIDE_ANIMATION_LENGTH = 50;
    public static int HIDE_PARTICLE_START = HIDE_ANIMATION_START + 5;
    public static int HIDE_LENGTH = HIDE_ANIMATION_START + HIDE_ANIMATION_LENGTH;

    public static int CLAW_ATTACK_EVENT = 100;
    public static int DART_ATTACK_EVENT = 101;
    public static int DIG_EVENT = 102;

    public static final EntityDataAccessor<Integer> DATA_HIDE_ID = SynchedEntityData.defineId(Cockatrice.class, EntityDataSerializers.INT);

    public AnimationState clawAttackAnimationState = new AnimationState();
    public AnimationState dartAttackAnimationState = new AnimationState();
    public AnimationState digAnimationState = new AnimationState();

    public Cockatrice(EntityType<? extends Cockatrice> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.5));
        this.goalSelector.addGoal(4, new Cockatrice.CockatriceMeleeAttackGoal(this, 1.075F, true, 6.0F));
        this.goalSelector.addGoal(5, new Cockatrice.CockatriceRangedAttackGoal(this, 1.15F, 200, 300, 15.0F, 6.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Cockatrice.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.ATTACK_DAMAGE, 3.5);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HIDE_ID, 0);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == CLAW_ATTACK_EVENT) {
            this.dartAttackAnimationState.stop();
            this.clawAttackAnimationState.start(this.tickCount);
        } else if (id == DART_ATTACK_EVENT) {
            this.clawAttackAnimationState.stop();
            this.dartAttackAnimationState.start(this.tickCount);
        } else if (id == DIG_EVENT) {
            this.digAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.isInvulnerable() && (Blighted.super.inSunlight(this) || this.getHideTime() >= HIDE_ANIMATION_START)) {
            if (this.getHideTime() <= HIDE_LENGTH) {
                Blighted.super.weaken(this, this.getRandom(), this.tickCount, 0.65F);

                if (this.getHideTime() == HIDE_ANIMATION_START) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.broadcastEntityEvent(this, (byte) DIG_EVENT);
                    }
                }

                if (this.getHideTime() >= HIDE_PARTICLE_START) {
                    this.spawnDigParticles();
                    this.getNavigation().stop();
                    this.setDeltaMovement(Vec3.ZERO);
                    this.removeAllGoals((goal) -> true);

                    if (this.getHideTime() == HIDE_LENGTH) {
                        this.discard();
                    }
                }
            }
            this.setHideTime(this.getHideTime() + 1);
        } else {
            this.setHideTime(0);
        }
    }

    private void spawnDigParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < Math.max(0, (this.getHideTime() - (HIDE_PARTICLE_START)) * 2); ++i) {
                BlockParticleOption blockParticles = new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn());
                serverLevel.sendParticles(blockParticles,
                        this.getRandomX(1.0F), this.getY() + 0.25, this.getRandomZ(1.0F), 1,
                        0, 0, 0, this.getRandom().nextGaussian() * 0.02);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(livingEntity, this, this, EffectBuildupPresets.VENOM, 250);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        VenomousDart dart = new VenomousDart(this, this.level());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.05F;
        dart.shoot(d1, d0 + d4, d3, 1.25F, 6.0F);
        this.playSound(AetherIISoundEvents.ENTITY_COCKATRICE_SHOOT.get(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(dart);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != AetherIIMobEffects.VENOM.get() && super.canBeAffected(effect);
    }

    @Override
    public int getHideTime() {
        return this.getEntityData().get(DATA_HIDE_ID);
    }

    @Override
    public void setHideTime(int hideTime) {
        this.getEntityData().set(DATA_HIDE_ID, hideTime);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 360;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_COCKATRICE_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_COCKATRICE_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_COCKATRICE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_COCKATRICE_STEP.get(), 0.15F, 1.0F);
    }

    protected static class CockatriceMeleeAttackGoal extends ClosedAnimationMeleeAttackGoal {

        public CockatriceMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, float attackThreshold) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen, 7, 12, attackThreshold);
        }

        @Override
        public void attackAnimation() {
            this.mob.level().broadcastEntityEvent(this.mob, (byte) CLAW_ATTACK_EVENT);
        }

        @Override
        public void attackAction() {
            super.attackAction();
            this.mob.setZza(0.3F);
        }
    }

    protected static class CockatriceRangedAttackGoal extends Goal {
        private final Cockatrice cockatrice;
        private int attackTime = -1;
        private final double speedModifier;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;
        private final float attackThresholdSqr;
        private LivingEntity trackedTarget;

        public CockatriceRangedAttackGoal(Cockatrice cockatrice, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius, float attackThreshold) {
            this.cockatrice = cockatrice;
            this.speedModifier = speedModifier;
            this.attackIntervalMin = attackIntervalMin;
            this.attackIntervalMax = attackIntervalMax;
            this.attackRadius = attackRadius;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.attackThresholdSqr = attackThreshold * attackThreshold;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.cockatrice.getTarget() != null && this.cockatrice.getTarget().isAlive() && this.cockatrice.distanceToSqr(this.cockatrice.getTarget()) >= this.attackThresholdSqr;
        }

        @Override
        public boolean canContinueToUse() {
            return this.trackedTarget != null;
        }

        @Override
        public void start() {
            this.trackedTarget = this.cockatrice.getTarget();
        }

        @Override
        public void stop() {
            this.seeTime = 0;
            this.attackTime = -1;
            this.trackedTarget = null;
        }

        @Override
        public void tick() {
            if (this.trackedTarget != null) {
                double distance = this.cockatrice.distanceToSqr(this.trackedTarget);
                boolean canSee = this.cockatrice.getSensing().hasLineOfSight(this.trackedTarget);

                if (canSee) {
                    ++this.seeTime;
                } else {
                    this.seeTime = 0;
                }
                if (distance <= (double) this.attackRadiusSqr && this.seeTime >= 5) {
                    this.cockatrice.getNavigation().stop();
                }
                if (distance > (double) this.attackRadiusSqr || this.seeTime < 5 || !this.isTimeToAttack()) {
                    this.cockatrice.getNavigation().moveTo(this.trackedTarget, this.speedModifier);
                }
                this.cockatrice.getLookControl().setLookAt(this.trackedTarget, 30.0F, 30.0F);

                float f = (float) Math.sqrt(distance) / this.attackRadius;
                float f1 = Mth.clamp(f, 0.1F, 1.0F);
                if (++this.attackTime >= 0) {
                    if (this.attackTime == 0) {
                        this.cockatrice.level().broadcastEntityEvent(this.cockatrice, (byte) DART_ATTACK_EVENT);
                    }
                    if (this.isTimeToAttack()) {
                        this.cockatrice.performRangedAttack(this.trackedTarget, f1);
                    }
                    if (this.attackTime == 20 * Mth.floor(3.375)) {
                        this.attackTime = -Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
                    }
                    if (!canSee || this.cockatrice.getTarget() == null || !this.cockatrice.getTarget().isAlive() || this.cockatrice.distanceToSqr(this.trackedTarget) < this.attackThresholdSqr) {
                        this.trackedTarget = null;
                    }
                }
            }
        }

        private boolean isTimeToAttack() {
            int i = this.attackTime;
            return i == Mth.floor(1.25 * 20) || i == Mth.floor(1.85 * 20) || i == Mth.floor(2.42 * 20) || i == Mth.floor(2.67 * 20);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}
