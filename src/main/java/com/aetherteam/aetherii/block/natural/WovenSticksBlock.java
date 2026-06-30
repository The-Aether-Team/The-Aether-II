package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelReader, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = levelReader;
        BlockState returnState = super.updateShape(state, direction, neighborState, levelReader, pos, neighborPos);
        if (direction == Direction.UP) {
            if (neighborState.is(AetherIIBlocks.BRYALINN_MOSS_CARPET.get()) || neighborState.is(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get())) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.BRYALINN);
            } else if (neighborState.is(AetherIIBlocks.SHAYELINN_MOSS_CARPET.get()) || neighborState.is(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get())) {
                returnState = returnState.setValue(MOSSY, AetherIIBlockStateProperties.Mossy.SHAYELINN);
            } else if (neighborState.is(AetherIIBlocks.AMBRELINN_MOSS_CARPET.get()) || neighborState.is(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get())) {
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
