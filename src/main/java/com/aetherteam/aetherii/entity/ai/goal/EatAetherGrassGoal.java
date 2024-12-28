package com.aetherteam.aetherii.entity.ai.goal;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.neoforge.event.EventHooks;

import java.util.EnumSet;
import java.util.function.Predicate;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.ai.goal.EatBlockGoal}.
 * Changed checks for Grass Blocks to Aether Grass Blocks.
 */
public class EatAetherGrassGoal extends Goal {
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.SHORT_GRASS);
    private final Mob mob;
    private int eatAnimationTick;

    public EatAetherGrassGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.mob.level().getBlockState(blockpos))) {
                return true;
            } else {
                return this.mob.level().getBlockState(blockpos.below()).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
            }
        }
    }

    @Override
    public void start() {
        this.eatAnimationTick = this.adjustedTickDelay(40);
        this.mob.level().broadcastEntityEvent(this.mob, (byte) 10);
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.eatAnimationTick = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }

    public int getEatAnimationTick() {
        return this.eatAnimationTick;
    }

    @Override
    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
            BlockPos blockPos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.mob.level().getBlockState(blockPos))) {
                if (EventHooks.canEntityGrief((ServerLevel) this.mob.level(), this.mob)) {
                    this.mob.level().destroyBlock(blockPos, false);
                }
                this.mob.ate();
            } else {
                BlockPos blockPos1 = blockPos.below();
                if (this.mob.level().getBlockState(blockPos1).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get())) {
                    if (EventHooks.canEntityGrief((ServerLevel) this.mob.level(), this.mob)) {
                        this.mob.level().levelEvent(2001, blockPos1, Block.getId(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState()));
                        this.mob.level().setBlock(blockPos1, AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(), 2);
                    }
                    this.mob.ate();
                }
            }
        }
    }
}
