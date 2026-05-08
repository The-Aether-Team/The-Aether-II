package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlocksLightLeavesBlock extends AetherLeavesBlock {
    public BlocksLightLeavesBlock(Properties properties, ParticleOptions leavesParticle, Holder<Block> leavesPile) {
        super(properties, leavesParticle, leavesPile);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return false;
    }

    @Override
    public int getLightDampening(BlockState state) {
        return 15;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }
}