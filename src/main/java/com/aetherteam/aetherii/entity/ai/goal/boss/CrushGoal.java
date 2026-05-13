package com.aetherteam.aetherii.entity.ai.goal.boss;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class CrushGoal extends Goal {
    private final Slider slider;

    public CrushGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        return this.slider.isAwake() && (slider.horizontalCollision || this.slider.verticalCollision || this.blocksBetween(this.slider));
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        boolean crushed = false;
        if (this.slider.level() instanceof ServerLevel serverLevel) {
            if (EventHooks.canEntityGrief(serverLevel, this.slider)) {
                if (this.slider.getMoveDirection() != null) {
                    AABB crushBox = this.slider.getBoundingBox().expandTowards(this.slider.getMoveDirection().getUnitVec3().scale(0.1));
                    for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(crushBox.minX), Mth.floor(crushBox.minY), Mth.floor(crushBox.minZ), Mth.floor(crushBox.maxX), Mth.floor(crushBox.maxY), Mth.floor(crushBox.maxZ))) {
                        if (this.slider.getDungeon() == null || this.slider.getDungeon().roomBounds().contains(pos.getCenter())) {
                            BlockState blockState = this.slider.level().getBlockState(pos);
                            if (this.isBreakable(blockState, pos)) {
                                crushed = this.slider.level().destroyBlock(pos, !blockState.is(AetherIITags.Blocks.NOT_DROPPED_BY_SLIDER_COLLISION), this.slider) || crushed;
                                double a = pos.getX() + 0.5 + (double) (serverLevel.getRandom().nextFloat() - serverLevel.getRandom().nextFloat()) * 0.375;
                                double b = pos.getY() + 0.5 + (double) (serverLevel.getRandom().nextFloat() - serverLevel.getRandom().nextFloat()) * 0.375;
                                double c = pos.getZ() + 0.5 + (double) (serverLevel.getRandom().nextFloat() - serverLevel.getRandom().nextFloat()) * 0.375;
                                serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
                            }
                        }
                    }
                }
            }
        }
        if (crushed) {
            this.slider.playSound(SoundEvents.GENERIC_EXPLODE.value(), 3.0F, (0.625F + (this.slider.getRandom().nextFloat() - this.slider.getRandom().nextFloat()) * 0.2F) * 0.7F);
            this.slider.playSound(this.slider.getCollideSound(), 2.5F, 1.0F / (this.slider.getRandom().nextFloat() * 0.2F + 0.9F));
            this.slider.setMoveDelay(this.slider.calculateMoveDelay() / 2);
            this.slider.setDeltaMovement(Vec3.ZERO);
        }
    }

    /**
     * Checks if there are blocks between a target and the Slider.
     * @param slider The {@link Slider} that the brain belongs to.
     * @return Whether there are blocks, as a {@link Boolean}.
     */
    private boolean blocksBetween(Slider slider) {
        LivingEntity target = slider.getTarget();
        if (target == null) {
            return false;
        }
        for (BlockPos pos : BlockPos.betweenClosed(AABB.of(BoundingBox.fromCorners(target.blockPosition(), slider.blockPosition())))) {
            BlockState state = slider.level().getBlockState(pos);
            if (this.isBreakable(state, pos)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBreakable(BlockState blockState, BlockPos pos) {
        return !blockState.isAir() && !blockState.is(AetherIITags.Blocks.SLIDER_UNBREAKABLE) && blockState.getBlock().defaultDestroyTime() >= 0.0F && blockState.getBlock().defaultDestroyTime() < 100.0F && blockState.getBlock().canEntityDestroy(blockState, this.slider.level(), pos, this.slider);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
