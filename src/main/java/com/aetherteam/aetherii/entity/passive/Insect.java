package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.entity.ai.controller.FlyingMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.FlyingLookGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Insect extends AmbientCreature {
    public Insect(EntityType<? extends Insect> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0F);
    }

    public static boolean checkInsectSpawnRules(EntityType<? extends Insect> animal, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(6, new FlyingLookGoal(this));
    }

    @Override
    public void travel(Vec3 p_415638_) {
        this.travelFlying(p_415638_, 0.02F);
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
            MoveControl moveControl = this.insect.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double d0 = moveControl.getWantedX() - this.insect.getX();
                double d1 = moveControl.getWantedY() - this.insect.getY();
                double d2 = moveControl.getWantedZ() - this.insect.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0 || d3 > 3600.0;
            }
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
            this.insect.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
        }

        private void checkRainAndFly() {
            RandomSource random = this.insect.getRandom();
            double d0 = this.insect.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
            double d1 = this.insect.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
            double d2 = this.insect.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
            if (!this.insect.level().isRainingAt(BlockPos.containing(d0, d1, d2)) && this.insect.level().getFluidState(BlockPos.containing(d0, d1, d2)).isEmpty()) {
                this.insect.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
            } else {
                this.randomFly();
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}
