package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.goal.FakeMeleeAttackGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class DetonationSentry extends PathfinderMob {
    private static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(DetonationSentry.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(DetonationSentry.class, EntityDataSerializers.BOOLEAN);

    private static final float EXPLOSION_RADIUS = 2.0F;
    public static final int MAX_TIMER = 60;
    private int oldTimer;
    private int timer;
    private float timeSpotted = 0.0F;
    private boolean playSound;
    public AnimationState explosionAnimationState = new AnimationState();

    public DetonationSentry(EntityType<? extends DetonationSentry> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SentryFloatGoal(this));
        this.goalSelector.addGoal(2, new SwellGoal(this));
        this.goalSelector.addGoal(3, new FakeMeleeAttackGoal(this, 0.6F, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.6)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.FOLLOW_RANGE, 10.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_AWAKE_ID, false);
        this.entityData.define(DATA_IS_IGNITED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("ignited", this.isIgnited());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ignited") && tag.getBoolean("ignited")) {
            this.ignite();
        }
    }

    /**
     * Handles waking the Sentry up if a target is spotted for long enough.
     */
    @Override
    public void tick() {
        if (this.isAlive()) {
            this.oldTimer = this.timer;
            if (this.isIgnited()) {
                this.timer += 1;
                float flickerInterval = Mth.sin(Mth.square(this.timer) / 50.0F);
                if (flickerInterval < 0) {
                    if (this.playSound) {
                        this.playSound(AetherIISoundEvents.ENTITY_DETONATION_SENTRY_BEEP.get(), 1.0F, 1.0F);
                        this.playSound = false;
                    }
                } else {
                    this.playSound = true;
                }
            }
            if (this.timer < 0) {
                this.timer = 0;
            }
            if (this.timer > MAX_TIMER - 12) {
                this.explosionAnimationState.startIfStopped(this.tickCount);
            }

            if (this.timer >= MAX_TIMER) {
                this.timer = MAX_TIMER;
                this.explodeAt();
            }
        }

        if (this.getTarget() != null) {
            if (!this.isAwake()) {
                if (this.timeSpotted >= 24) {
                    this.setAwake(true);
                }
                this.timeSpotted++;
            }

        } else if (!this.isIgnited()) {
            this.setAwake(false);
        }
        super.tick();
    }

    public int getTimer() {
        return timer;
    }

    /**
     * Handles explosion behavior if the Sentry is close enough to an entity.
     *
     */
    protected void explodeAt() {
        if (this.isAwake() && this.isAlive()) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), EXPLOSION_RADIUS, Level.ExplosionInteraction.NONE);
            this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.2F * (this.getRandom().nextFloat() - this.getRandom().nextFloat()) + 1);
            if (this.level() instanceof ServerLevel level) {
                level.broadcastEntityEvent(this, (byte) 70);
                level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.5);
            }
            this.discard();
        }
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    public float getTimer(float partialTicks) {
        return Mth.lerp(partialTicks, (float) this.oldTimer, (float) this.timer);
    }

    /**
     * @return Whether the Sentry is awake, as a {@link Boolean}.
     */
    public boolean isAwake() {
        return this.getEntityData().get(DATA_AWAKE_ID);
    }

    /**
     * Sets whether the Sentry is awake.
     *
     * @param awake The {@link Boolean} value.
     */
    public void setAwake(boolean awake) {
        this.getEntityData().set(DATA_AWAKE_ID, awake);
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_DETONATION_SENTRY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_DETONATION_SENTRY_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_DETONATION_SENTRY_AMBIENT.get();
    }


    @Override
    public boolean shouldDropExperience() {
        return true;
    }

    @Override
    protected boolean shouldDropLoot() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 70) {
            for (int i = 0; i < 40; i++) {
                double x = this.getX() + (this.getRandom().nextFloat() * 0.25);
                double y = this.getY() + 0.5;
                double z = this.getZ() + (this.getRandom().nextFloat() * 0.25);
                float f1 = this.getRandom().nextFloat() * 360.0F;
                this.level().addParticle(ParticleTypes.POOF, x, y, z, -Math.sin(Mth.DEG_TO_RAD * f1) * 0.75, 0.125, Math.cos(Mth.DEG_TO_RAD * f1) * 0.75);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    public boolean isIgnited() {
        return this.entityData.get(DATA_IS_IGNITED);
    }

    public void ignite() {
        this.entityData.set(DATA_IS_IGNITED, true);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        Entity damageEntity = damageSource.getEntity();
        if (damageEntity != null) {
            return damageEntity.getType().builtInRegistryHolder().is(AetherIITags.EntityTypes.SENTRY_RUINS_MOBS) || super.isInvulnerableTo(damageSource);
        }
        return super.isInvulnerableTo(damageSource);
    }

    static class SentryFloatGoal extends FloatGoal {
        private final DetonationSentry detonationSentry;

        public SentryFloatGoal(DetonationSentry detonationSentry) {
            super(detonationSentry);
            this.detonationSentry = detonationSentry;
        }

        @Override
        public boolean canUse() {
            return this.detonationSentry.isAwake() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.detonationSentry.isAwake() && super.canContinueToUse();
        }
    }

    public static class SwellGoal extends Goal {
        private final DetonationSentry detonationSentry;

        public SwellGoal(DetonationSentry detonationSentry) {
            this.detonationSentry = detonationSentry;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingentity = this.detonationSentry.getTarget();
            return this.detonationSentry.isIgnited() || this.detonationSentry.isAwake() && livingentity != null && this.detonationSentry.distanceToSqr(livingentity) < 6.0;
        }

        @Override
        public void start() {
//            this.detonationSentry.getNavigation().stop();
            this.detonationSentry.setAwake(true);
        }

        @Override
        public void stop() {
            this.detonationSentry.setAwake(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (!this.detonationSentry.isIgnited()) {
                this.detonationSentry.ignite();
            } else {
                if (this.detonationSentry.getTimer() > MAX_TIMER - 12) {
                    this.detonationSentry.getNavigation().stop();
                } else if (this.detonationSentry.getTarget() != null) {
                    this.detonationSentry.getNavigation().moveTo(this.detonationSentry.getTarget(), 0.6F);
                }
            }
        }
    }

}
