package com.aetherteam.aetherii.entity.ai.goal.boss;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * Set the path up to avoid an unbreakable block.
 */
public class AvoidObstaclesGoal extends Goal {
    private final Slider slider;

    public AvoidObstaclesGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        if (!this.slider.isAwake() || this.slider.isDeadOrDying() || this.slider.getMoveDelay() != 1) {
            return false;
        }

        Direction direction = this.slider.getMoveDirection();
        return direction != null;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        Vec3 targetPos = this.slider.findTargetPoint();
        if (targetPos == null) {
            return;
        }
        Direction direction = Slider.calculateDirection(this.slider, targetPos.x() - this.slider.getX(), targetPos.y() - this.slider.getY(), targetPos.z() - this.slider.getZ());
        AABB collisionBox = Slider.calculateAdjacentBox(this.slider.getBoundingBox(), direction);
        BlockPos min = new BlockPos(Mth.floor(collisionBox.minX), Mth.floor(collisionBox.minY), Mth.floor(collisionBox.minZ));
        BlockPos max = new BlockPos(Mth.ceil(collisionBox.maxX - 1), Mth.ceil(collisionBox.maxY - 1), Mth.ceil(collisionBox.maxZ - 1));

        boolean isNeedMoveDifferent = false;
        boolean wallState = true;
        Direction horizonalDirection = null;
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (this.slider.level().getBlockState(pos).is(AetherIITags.Blocks.SLIDER_UNBREAKABLE)) {
                isNeedMoveDifferent = true;
                break;
            }
        }
        if (isNeedMoveDifferent) {
            List<Direction> directions = Arrays.stream(Direction.values()).toList();
            List<Direction> directionsCannotMove = Lists.newArrayList();

            directions = directions.stream().sorted((comparator, comparator2) -> {
                return (int) -targetPos.distanceTo(this.slider.position().add(comparator.getStepX() * 2, comparator.getStepY() * 2, comparator.getStepZ() * 2));
            }).toList();

            double distance = 300.0D;

            //check which one is nearest
            for (Direction direction1 : directions) {
                Direction horizonalDirectionSelect = null;

                AABB collisionBox2 = Slider.calculateAdjacentBox(this.slider.getBoundingBox(), direction1);
                BlockPos min2 = new BlockPos(Mth.floor(collisionBox2.minX), Mth.floor(collisionBox2.minY), Mth.floor(collisionBox2.minZ));
                BlockPos max2 = new BlockPos(Mth.ceil(collisionBox2.maxX - 1), Mth.ceil(collisionBox2.maxY - 1), Mth.ceil(collisionBox2.maxZ - 1));
                for (BlockPos pos : BlockPos.betweenClosed(min2, max2)) {

                    if (distance > targetPos.distanceTo(pos.getBottomCenter()) && !this.slider.level().getBlockState(pos).is(AetherIITags.Blocks.SLIDER_UNBREAKABLE)) {
                        distance = targetPos.distanceTo(pos.getBottomCenter());
                        horizonalDirectionSelect = direction1;
                    } else if (this.slider.level().getBlockState(pos).is(AetherIITags.Blocks.SLIDER_UNBREAKABLE)) {
                        horizonalDirectionSelect = null;
                        break;
                    }
                }
                if (horizonalDirectionSelect != null) {
                    horizonalDirection = horizonalDirectionSelect;
                }
            }
        }
        if (horizonalDirection != null && horizonalDirection.getAxis() == Direction.Axis.Y) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            boolean down = horizonalDirection == Direction.DOWN;
            int y = Mth.floor(collisionBox.minY);
            while (wallState) {
                if (down) {
                    y--;
                } else {
                    y++;
                }
                wallState = false;
                for (int x = Mth.floor(collisionBox.minX); x < collisionBox.maxX; x++) {
                    for (int z = Mth.floor(collisionBox.minZ); z < collisionBox.maxZ; z++) {
                        if (this.slider.level().getBlockState(pos.set(x, y, z)).is(AetherIITags.Blocks.SLIDER_UNBREAKABLE)) {
                            wallState = true;
                        }
                    }
                }
            }
            Vec3 currentPos = this.slider.position();
            this.slider.setTargetPoint(new Vec3(currentPos.x(), y, currentPos.z()));
            this.slider.setMoveDirection(horizonalDirection);
            this.slider.needMoveState = horizonalDirection;
        } else if (horizonalDirection != null) {
            Vec3 currentPos = this.slider.position();
            this.slider.setTargetPoint(new Vec3(currentPos.x() + horizonalDirection.getStepX() * 3, currentPos.y(), currentPos.z() + horizonalDirection.getStepZ() * 3));
            this.slider.setMoveDirection(horizonalDirection);
            this.slider.needMoveState = horizonalDirection;
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}