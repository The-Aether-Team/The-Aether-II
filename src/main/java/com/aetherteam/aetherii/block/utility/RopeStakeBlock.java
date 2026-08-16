package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Function;

public class RopeStakeBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> CONNECTION = BlockStateProperties.FACING;
    public static final EnumProperty<AetherIIBlockStateProperties.StakeSpoolState> SPOOL = AetherIIBlockStateProperties.STAKE_SPOOL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Map<Direction, VoxelShape> SHAPE_STAKES =  Shapes.rotateAll(Block.box(6, 6, 0, 10, 10, 13));
    public static final Map<Direction, VoxelShape> SHAPE_OCCLUSION_STAKES =  Shapes.rotateAll(Block.box(7, 7, 0, 9, 9, 13));
    public static final Map<Direction, VoxelShape> SHAPE_CENTER_SPOOLS = Shapes.rotateAll(Block.box(4, 4, 7, 12, 12, 9));
    public static final VoxelShape SHAPE_CONNECTION = Block.box(6, 0, 6, 10, 8, 10);
    public static final VoxelShape SHAPE_FLOOR_SPOOL = Block.box(4, 0, 4, 12, 2, 12);
    public static final int MAX_ROPE_LENGTH = 16;
    private final Function<BlockState, VoxelShape> shapes;

    public RopeStakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONNECTION, Direction.NORTH).setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE).setValue(WATERLOGGED, false));
        this.shapes = this.makeShapes();
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState((state) -> {
            Direction connection = state.getValue(CONNECTION);
            VoxelShape shape = SHAPE_STAKES.get(connection);
            if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.CENTER) {
                shape = Shapes.or(shape, SHAPE_CENTER_SPOOLS.get(connection));
            } else if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.FLOOR) {
                shape = Shapes.or(shape, SHAPE_FLOOR_SPOOL);
                shape = Shapes.or(shape, SHAPE_CONNECTION);
            } else if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED) {
                shape = Shapes.or(shape, SHAPE_CONNECTION);
            }
            return shape;
        }, WATERLOGGED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) { //todo respool behavior
        if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.CENTER) {
            BlockState newState = state.setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED);
            level.setBlock(pos, newState, 1 | 2);
            level.scheduleTick(pos, this, RopeBlock.DELAY);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
            RopeBlock.placeRope(level, belowPos, AetherIIBlocks.BRETTL_ROPE.get().defaultBlockState().setValue(RopeBlock.UP, true));
        } else {
            level.setBlock(pos, state.setValue(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR), 1 | 2);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        if (direction == state.getValue(CONNECTION) && !neighborState.isFaceSturdy(levelReader, pos.relative(direction.getOpposite()), direction.getOpposite(), SupportType.CENTER)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (direction == Direction.DOWN && neighborState.isAir()) {
            if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.FLOOR) {
                state = state.setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED);
                scheduledTickAccess.scheduleTick(pos, this, RopeBlock.DELAY);
            }
        }
        return state;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(CONNECTION);
        BlockPos relativePos = pos.relative(direction);
        BlockState relativeState = level.getBlockState(relativePos);
        return relativeState.isFaceSturdy(level, relativePos, direction.getOpposite(), SupportType.CENTER);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState replacedFluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(CONNECTION, context.getClickedFace().getOpposite()).setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER).setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONNECTION, SPOOL, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapes.apply(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = SHAPE_STAKES.get(state.getValue(CONNECTION));
        if (context.isAbove(shape, pos, true) && !context.isDescending()) {
            return shape;
        } else {
            return Shapes.empty();
        }
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return SHAPE_OCCLUSION_STAKES.get(state.getValue(CONNECTION));
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean isScaffolding(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    public static boolean checkForStake(LevelReader levelReader, BlockPos pos) {
        for (int i = 1; i < MAX_ROPE_LENGTH; ++i) {
            BlockPos abovePos = pos.above(i);
            BlockState aboveState = levelReader.getBlockState(abovePos);
            if (aboveState.is(AetherIIBlocks.BRETTL_ROPE_STAKE)) {
                return true;
            }
        }
        return false;
    }
}
