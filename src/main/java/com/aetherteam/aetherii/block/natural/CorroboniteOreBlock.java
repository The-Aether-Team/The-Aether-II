package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CorroboniteOreBlock extends DropExperienceBlock {
    public CorroboniteOreBlock(IntProvider xpRange, Properties properties) {
        super(properties, xpRange);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, state.getBlock(), 0);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = level;
        if (neighborState.isAir()) {
            scheduledTickAccess.scheduleTick(pos, state.getBlock(), 0);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        for (Direction direction : Direction.values()) {
            BlockPos offset = pos.relative(direction);
            BlockState offsetState = level.getBlockState(offset);
            BlockState newState = AetherIIBlocks.CORROBONITE_CLUSTER.get().defaultBlockState();
            if (offsetState.isAir()) {
                level.setBlockAndUpdate(offset, newState.trySetValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true));
            } else if (offsetState.is(newState.getBlock())) {
                level.setBlockAndUpdate(offset, offsetState.trySetValue(MultifaceBlock.getFaceProperty(direction.getOpposite()), true));
            }
        }
        super.tick(state, level, pos, random);
    }
}
