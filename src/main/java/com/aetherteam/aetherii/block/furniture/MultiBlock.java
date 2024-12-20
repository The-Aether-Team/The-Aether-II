package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.blockentity.MultiBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MultiBlock extends BaseEntityBlock {
    public MultiBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (levelReader.getBlockEntity(pos) instanceof MultiBlockEntity multiblock) {
            if (multiblock.getLevelOriginPos() != null) {
                if (!levelReader.getBlockState(multiblock.getLevelOriginPos()).is(state.getBlock())) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof MultiBlockEntity multiblock) {
            if (multiblock.getLevelOriginPos() != null && multiblock.getLevelOriginPos() != pos) {
                level.destroyBlock(multiblock.getLevelOriginPos(), false);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
