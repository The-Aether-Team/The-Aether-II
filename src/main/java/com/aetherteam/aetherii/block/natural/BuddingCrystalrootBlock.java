package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BuddingCrystalrootBlock extends Block {
    public BuddingCrystalrootBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(15) == 0 && level.getBlockState(pos.below()).isAir()) {
            level.setBlock(pos.below(), AetherIIBlocks.CRYSTAL_APPLE.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
