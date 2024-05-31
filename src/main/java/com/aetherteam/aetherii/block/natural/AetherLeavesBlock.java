package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class AetherLeavesBlock extends LeavesBlock {
    private final Supplier<SimpleParticleType> leavesParticle;

    public AetherLeavesBlock(Properties properties, Supplier<SimpleParticleType> leavesParticle) {
        super(properties);
        this.leavesParticle = leavesParticle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextInt(200) == 0) {
            this.spawnLeavesParticles(level, pos, random);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!entity.isCrouching()) {
            if (level.getRandom().nextInt(10) == 0) {
                this.spawnLeavesParticles(level, pos, level.getRandom());
            }
        }
    }

    private void spawnLeavesParticles(Level level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) {
            ParticleUtils.spawnParticleBelow(level, pos, random, this.leavesParticle.get());
        }
    }
}
