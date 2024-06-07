package com.aetherteam.aetherii.block.miscellaneous;

import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class SkyplaneLeavesBlock extends AetherLeavesBlock {
    public SkyplaneLeavesBlock(Properties properties, Supplier<SimpleParticleType> leavesParticle, Supplier<Block> leavesPile) {
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