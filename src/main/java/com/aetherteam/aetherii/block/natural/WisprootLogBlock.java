package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class WisprootLogBlock extends RotatedPillarBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty MOSSY = AetherIIBlockStateProperties.MOSSY;

    public WisprootLogBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(MOSSY, Boolean.FALSE));
    }

    @SuppressWarnings("deprecation")
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState originalState, @NotNull LevelAccessor accessor, @NotNull BlockPos pos, @NotNull BlockPos originalPos) {
        return direction == Direction.DOWN ? state.setValue(MOSSY, isMossy(state)) : super.updateShape(state, direction, originalState, accessor, pos, originalPos);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos().below());
        return defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(MOSSY, isMossy(state));
    }

    private boolean isMossy(BlockState state) {
        return state.is(BlockTags.DIRT);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState > builder) {
        builder.add(AXIS).add(MOSSY);
    }
}