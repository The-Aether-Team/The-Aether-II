package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class FleeRainGoal extends Goal {
    protected final PathfinderMob mob;
    private double wantedX;
    private double wantedY;
    private double wantedZ;
    private final double speedModifier;
    private final Level level;

    public FleeRainGoal(PathfinderMob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.level = mob.level();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.level.isRaining()) {
            return false;
        } else if (!this.mob.isOnFire()) {
            return false;
        } else if (!this.level.canSeeSky(this.mob.blockPosition())) {
            return false;
        } else {
            return this.setWantedPos();
        }
    }

    protected boolean setWantedPos() {
        Vec3 vec3 = this.getHidePos();
        if (vec3 == null) {
            return false;
        } else {
            this.wantedX = vec3.x;
            this.wantedY = vec3.y;
            this.wantedZ = vec3.z;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    @Nullable
    protected Vec3 getHidePos() {
        RandomSource randomsource = this.mob.getRandom();
        BlockPos blockpos = this.mob.blockPosition();

        for (int i = 0; i < 10; i++) {
            BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(20) - 10, randomsource.nextInt(6) - 3, randomsource.nextInt(20) - 10);
            if (!this.level.isRainingAt(blockpos1) && this.mob.level().getBlockState(blockpos1).isAir()) {
                return Vec3.atBottomCenterOf(blockpos1);
            }
        }

        return null;
    }
}
