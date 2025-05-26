package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.ai.goal.CockatriceMeleeAttackGoal;
import com.aetherteam.aetherii.entity.ai.goal.CockatriceRangedAttackGoal;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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

import javax.annotation.Nullable;

public class Cockatrice extends Monster implements RangedAttackMob, Blighted {
    public static int HIDE_ANIMATION_START = 200; //todo values
    public static int HIDE_ANIMATION_LENGTH = 50;
    public static int HIDE_PARTICLE_START = HIDE_ANIMATION_START + 5;
    public static int HIDE_LENGTH = HIDE_ANIMATION_START + HIDE_ANIMATION_LENGTH;

    public static final EntityDataAccessor<Integer> DATA_HIDE_ID = SynchedEntityData.defineId(Cockatrice.class, EntityDataSerializers.INT);

    //todo dig animation.
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();
    public AnimationState digAnimationState = new AnimationState();
    private int attackTick;

    public Cockatrice(EntityType<? extends Cockatrice> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.5));
        this.goalSelector.addGoal(4, new CockatriceMeleeAttackGoal(this, 1.5F, true));
        this.goalSelector.addGoal(5, new CockatriceRangedAttackGoal(this, 0.8F, 20 * 10, 20 * 15, 10.0F));
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
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HIDE_ID, 0);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 61) {
            this.attackAnimationState.start(this.tickCount);
            this.attackTick = 0;
            this.shootAnimationState.stop();
        } else if (pId == 62) {
            this.shootAnimationState.start(this.tickCount);
            this.attackAnimationState.stop();
        } else if (pId == 64) {
            this.shootAnimationState.stop(); //todo unused?
        } else if (pId == 65) {
            this.digAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            //handle the attack animation
            if (this.attackAnimationState.isStarted()) {
                if (this.attackTick >= 40) {
                    this.attackAnimationState.stop();
                } else {
                    ++this.attackTick;
                }
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (Blighted.super.inSunlight(this) || this.getHideTime() >= HIDE_ANIMATION_START) {
            if (this.getHideTime() <= HIDE_LENGTH) {
                Blighted.super.weaken(this, this.getRandom(), this.tickCount, 0.65F);

                if (this.getHideTime() == HIDE_ANIMATION_START) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.broadcastEntityEvent(this, (byte) 65);
                        this.removeAllGoals((goal) -> true);
                    }
                }

                if (this.getHideTime() >= HIDE_PARTICLE_START) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < Math.max(0, (this.getHideTime() - (HIDE_PARTICLE_START)) * 2); ++i) {
                            BlockParticleOption blockParticles = new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn());
                            serverLevel.sendParticles(blockParticles,
                                    this.getRandomX(1.0F), this.getY() + 0.25, this.getRandomZ(1.0F), 1,
                                    0, 0, 0, this.getRandom().nextGaussian() * 0.02);
                        }
                    }
                    this.stopInPlace();

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

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        VenomousDart dart = new VenomousDart(this, this.level());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        dart.shoot(d1, d0 + d4, d3, 0.8F, 6.0F);
        this.playSound(AetherIISoundEvents.ENTITY_COCKATRICE_SHOOT.value(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(dart);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != AetherIIEffects.VENOM.get() && super.canBeAffected(effect);
    }

    @Override
    public int getHideTime() {
        return this.getEntityData().get(DATA_HIDE_ID);
    }

    @Override
    public void setHideTime(int hideTime) {
        this.getEntityData().set(DATA_HIDE_ID, hideTime);
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
}
