package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CorroboniteOreBlock extends DropExperienceBlock {
    public CorroboniteOreBlock(IntProvider xpRange, Properties properties) {
        super(xpRange, properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, state.getBlock(), 0);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (neighborState.isAir()) {
            scheduledTickAccess.scheduleTick(pos, state.getBlock(), 0);
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
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
