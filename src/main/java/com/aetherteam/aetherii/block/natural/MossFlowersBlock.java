package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class MossFlowersBlock extends AetherBushBlock implements BonemealableBlock, SegmentableBlock, Snowable {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = BlockStateProperties.FLOWER_AMOUNT;
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    private final Map<BlockState, VoxelShape> shapes;

    public MossFlowersBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AMOUNT, 1).setValue(SNOWY, Boolean.FALSE));
        this.shapes = this.makeShapes();
    }

    private Map<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(this.getShapeCalculator(FACING, AMOUNT));
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        if (this.isSnowy(state)) {
            level.setBlock(pos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState(), 1 | 2);
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return this.canBeReplaced(state, context, AMOUNT) || super.canBeReplaced(state, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapes.get(state);
    }

    public double getShapeHeight() {
        return 3.0;
    }

    @Override
    public IntegerProperty getSegmentAmountProperty() {
        return AMOUNT;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateForPlacement(context, this, AMOUNT, FACING);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AMOUNT, SNOWY);
    }

    @Override
    public boolean isSnowy(BlockState blockState) {
        return blockState.getValue(SNOWY);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_272968_, BlockPos p_273762_, BlockState p_273662_, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level p_272604_, RandomSource p_273609_, BlockPos p_272988_, BlockState p_273231_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_273476_, RandomSource p_273093_, BlockPos p_272601_, BlockState p_272683_) {
        int i = p_272683_.getValue(AMOUNT);
        if (i < 4) {
            p_273476_.setBlock(p_272601_, p_272683_.setValue(AMOUNT, Integer.valueOf(i + 1)), 2);
        } else {
            popResource(p_273476_, p_272601_, new ItemStack(this));
        }
    }
}
