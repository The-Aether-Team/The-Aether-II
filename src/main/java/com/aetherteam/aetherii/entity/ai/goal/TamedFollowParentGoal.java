package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TamedFollowParentGoal extends Goal {
    public static final int HORIZONTAL_SCAN_RANGE = 8;
    public static final int VERTICAL_SCAN_RANGE = 4;
    public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
    private final TamableAnimal animal;
    private Animal parent;
    private final double speedModifier;
    private int timeToRecalcPath;

    public TamedFollowParentGoal(TamableAnimal animal, double speedModifier) {
        this.animal = animal;
        this.speedModifier = speedModifier;
    }

    public boolean canUse() {
        if (this.animal.getAge() >= 0 || this.animal.isInSittingPose()) {
            return false;
        } else {
            List<? extends Animal> parents = this.animal.level().getEntitiesOfClass(this.animal.getClass(), this.animal.getBoundingBox().inflate((double) 8.0F, (double) 4.0F, (double) 8.0F));
            Animal closest = null;
            double closestDistSqr = Double.MAX_VALUE;

            for (Animal parent : parents) {
                if (parent.getAge() >= 0) {
                    double distSqr = this.animal.distanceToSqr(parent);
                    if (!(distSqr > closestDistSqr)) {
                        closestDistSqr = distSqr;
                        closest = parent;
                    }
                }
            }

            if (closest == null) {
                return false;
            } else if (closestDistSqr < (double) 9.0F) {
                return false;
            } else {
                UUID owner = this.animal.getOwnerUUID();
                UUID closestOwner = closest instanceof TamableAnimal closestTamed ? closestTamed.getOwnerUUID() : null;
                if (owner == null || Objects.equals(owner, closestOwner)) {
                this.parent = closest;
                return true;
                }
            }
            return false;
        }
    }

    public boolean canContinueToUse() {
        if (this.animal.getAge() >= 0) {
            return false;
        } else if (!this.parent.isAlive()) {
            return false;
        } else {
            double distSqr = this.animal.distanceToSqr(this.parent);
            return !(distSqr < (double) 9.0F) && !(distSqr > (double) 256.0F);
        }
    }

    public void start() {
        this.timeToRecalcPath = 0;
    }

    public void stop() {
        this.parent = null;
    }

    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.animal.getNavigation().moveTo(this.parent, this.speedModifier);
        }

    }
}
