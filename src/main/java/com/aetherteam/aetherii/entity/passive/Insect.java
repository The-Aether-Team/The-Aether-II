package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.entity.ai.controller.FlyingMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.FleeRainGoal;
import com.aetherteam.aetherii.entity.ai.goal.FlyingLookGoal;
import com.aetherteam.aetherii.entity.ai.navigator.InsectPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Insect extends PathfinderMob {
    private static final EntityDataAccessor<Boolean> DATA_REST = SynchedEntityData.defineId(Insect.class, EntityDataSerializers.BOOLEAN);

    private float restAnimationO;
    private float restAnimation;

    public Insect(EntityType<? extends Insect> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326499_) {
        super.defineSynchedData(p_326499_);
        p_326499_.define(DATA_REST, false);
    }

    public void setRest(boolean rest) {
        this.entityData.set(DATA_REST, rest);
    }

    public boolean isRest() {
        return this.entityData.get(DATA_REST);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new InsectPathNavigation(this, level);
    }


    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0F);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getRawBrightness(pos, 0) - 8;
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    public static boolean checkInsectSpawnRules(EntityType<? extends Insect> animal, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.0, 1.1, livingEntity -> {
            return !livingEntity.isShiftKeyDown() && EntitySelector.NO_SPECTATORS.test(livingEntity);
        }) {
            @Override
            public void start() {
                super.start();
                setRest(false);
            }
        });
        this.goalSelector.addGoal(4, new FleeRainGoal(this, 1.0F));

        this.goalSelector.addGoal(6, new RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new FlyingLookGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {

            this.restAnimationO = this.restAnimation;
            if (this.isRest()) {
                this.restAnimation = Mth.clamp(this.restAnimation + 0.1F, 0.0F, 1.0F);
            } else {
                this.restAnimation = Mth.clamp(this.restAnimation - 0.1F, 0.0F, 1.0F);
            }
        }
    }

    public float getRestAnimationScale(float partialTick) {
        return Mth.lerp(partialTick, this.restAnimationO, this.restAnimation);
    }


    @Override
    public void travel(Vec3 p_415638_) {
        this.travelFlying(p_415638_, 0.02F);
    }

    @Override
    public Vec3 getDeltaMovement() {
        return this.isRest() ? Vec3.ZERO : super.getDeltaMovement();
    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity entity) {
    }

    protected void pushEntities() {
    }

    @Override
    public boolean hurtServer(ServerLevel p_376221_, DamageSource p_376460_, float p_376610_) {
        boolean flag = super.hurtServer(p_376221_, p_376460_, p_376610_);

        if (flag && this.isRest()) {
            this.setRest(false);
        }

        return flag;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.RandomFloatAroundGoal}.
     */
    public static class RandomFloatAroundGoal extends Goal {
        private final Insect insect;

        public RandomFloatAroundGoal(Insect insect) {
            this.insect = insect;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.insect.getNavigation().isDone() && !this.insect.isRest();
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            if (this.insect.isInWaterOrRain()) {
                this.checkRainAndFly();
            } else {
                this.randomFly();
            }
        }

        private void randomFly() {
            RandomSource random = this.insect.getRandom();
            double d0 = this.insect.getX() + (random.nextFloat() * 2.0F - 1.0F) * 4.0F;
            double d1 = this.insect.getY() + (random.nextFloat() * 2.0F - 1.0F) * 4.0F;
            double d2 = this.insect.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 4.0F;
            this.insect.getNavigation().moveTo(d0, d1, d2, 1.0);
        }

        private void checkRainAndFly() {
            RandomSource random = this.insect.getRandom();
            double d0 = this.insect.getX() + (random.nextFloat() * 2.0F - 1.0F) * 32.0F;
            double d1 = this.insect.getY() + (random.nextFloat() * 2.0F - 1.0F) * 32.0F;
            double d2 = this.insect.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 32.0F;
            this.insect.getNavigation().moveTo(d0, d1, d2, 1.0);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}
