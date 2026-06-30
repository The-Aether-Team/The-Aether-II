package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.SkephidWebbingBall;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Skephid extends CellingMonster implements RangedAttackMob {
    public Skephid(EntityType<? extends CellingMonster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
        //this.goalSelector.addGoal(4, new SkephidMeleeAttackGoal(this, 1.1F, true));
        this.goalSelector.addGoal(5, new Skephid.SkephidAttackGoal(this, 0.8F, 20 * 3, 20 * 5, 10.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Skephid.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        SkephidWebbingBall dart = new SkephidWebbingBall(this, this.level());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        dart.shoot(d1, d0 + d4, d3, 0.8F, 6.0F);
        this.playSound(AetherIISoundEvents.ENTITY_SKEPHID_SHOOT.get(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(dart);
    }

    public static boolean checkSkephidSpawnRules(EntityType<? extends Skephid> skephid, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.SKEPHID_SPAWNABLE_ON) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn((ServerLevelAccessor) level, pos, random) && checkMobSpawnRules(skephid, level, reason, pos, random);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 380;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_SKEPHID_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_SKEPHID_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_SKEPHID_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_SKEPHID_STEP.get(), 0.15F, 1.0F);
    }

    protected static class SkephidAttackGoal extends Goal {
        private final Mob mob;
        private final RangedAttackMob rangedAttackMob;
        private int attackTime = -1;
        private final double speedModifier;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public SkephidAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackInterval, float attackRadius) {
            this(rangedAttackMob, speedModifier, attackInterval, attackInterval, attackRadius);
        }

        public SkephidAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
            if (!(rangedAttackMob instanceof LivingEntity)) {
                throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
            } else {
                this.rangedAttackMob = rangedAttackMob;
                this.mob = (Mob) rangedAttackMob;
                this.speedModifier = speedModifier;
                this.attackIntervalMin = attackIntervalMin;
                this.attackIntervalMax = attackIntervalMax;
                this.attackRadius = attackRadius;
                this.attackRadiusSqr = attackRadius * attackRadius;
                this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            }
        }

        @Override
        public boolean canUse() {
            return this.mob.getTarget() != null;
        }

        @Override
        public void stop() {
            this.seeTime = 0;
            this.attackTime = -1;
        }

        @Override
        public void tick() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
                boolean canSee = this.mob.getSensing().hasLineOfSight(target);

                if (canSee) {
                    ++this.seeTime;
                } else {
                    this.seeTime = 0;
                }
                if (distance <= (double) this.attackRadiusSqr && this.seeTime >= 5) {
                    this.mob.getNavigation().stop();
                } else {
                    this.mob.getNavigation().moveTo(target, this.speedModifier);
                }
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

                if (canSee) {
                    float f = (float) Math.sqrt(distance) / this.attackRadius;
                    float f1 = Mth.clamp(f, 0.1F, 1.0F);
                    if (++this.attackTime >= 0) {
                        if (this.isTimeToAttack()) {
                            this.rangedAttackMob.performRangedAttack(target, f1);
                        }
                        if (this.attackTime == 20 * Mth.floor(1)) {
                            this.attackTime = -Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
                        }
                    }
                }
            }
        }

        private boolean isTimeToAttack() {
            int i = attackTime;

            return i == Mth.floor(0.25 * 20F);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}
