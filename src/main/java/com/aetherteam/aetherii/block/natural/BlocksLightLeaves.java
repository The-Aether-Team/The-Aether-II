package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BlocksLightLeaves extends AetherLeavesBlock {
    public BlocksLightLeaves(Properties properties, Supplier<SimpleParticleType> leavesParticle, Supplier<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter getter, BlockPos pos) {
        return getter.getMaxLightLevel();
    }
}