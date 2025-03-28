package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class WovenSticksBlock extends Block {
    public static final EnumProperty<AetherIIBlockStateProperties.Mossy> MOSSY = AetherIIBlockStateProperties.MOSSY;

    public WovenSticksBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOSSY, AetherIIBlockStateProperties.Mossy.NONE));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        BlockState returnState = super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
        if (direction == Direction.UP) {
            if (neighborState.is(AetherIIBlocks.BRYALINN_MOSS_CARPET) || neighborState.is(AetherIIBlocks.BRYALINN_MOSS_BLOCK)) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.BRYALINN);
            } else if (neighborState.is(AetherIIBlocks.SHAYELINN_MOSS_CARPET) || neighborState.is(AetherIIBlocks.SHAYELINN_MOSS_BLOCK)) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.SHAYELINN);
            } else if (neighborState.is(AetherIIBlocks.AMBRELINN_MOSS_CARPET) || neighborState.is(AetherIIBlocks.AMBRELINN_MOSS_BLOCK)) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.AMBRELINN);
            }
        }
        return returnState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOSSY);
    }
}
