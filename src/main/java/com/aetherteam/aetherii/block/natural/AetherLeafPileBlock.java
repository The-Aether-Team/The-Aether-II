package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AetherLeafPileBlock extends FallingBlock {
    public static final MapCodec<AetherLeafPileBlock> CODEC = simpleCodec(AetherLeafPileBlock::new);
    public static final IntegerProperty PILES = AetherIIBlockStateProperties.PILES;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[] {
            Shapes.empty(),
            Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 11.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    @Override
    public MapCodec<AetherLeafPileBlock> codec() {
        return CODEC;
    }

    public AetherLeafPileBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PILES, 1).setValue(PERSISTENT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PILES, PERSISTENT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(PILES)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[0];
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE_BY_LAYER[0];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[0];
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(PILES) == 16 ? 0.2F : 1.0F;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state) {
        return 1;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos currentPos, BlockState currentState, RandomSource randomSource) {
        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, scheduledTickAccess, blockPos, direction, currentPos, currentState, randomSource);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return !level.isEmptyBlock(pos.below());
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof FallingBlockEntity fallingBlock && fallingBlock.getBlockState().is(this)) {
            fallingBlock.discard();
            level.setBlock(pos, level.getBlockState(pos).setValue(PILES, Math.min(16, state.getValue(PILES) + 1)), 2);
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(PERSISTENT) && random.nextInt(5) == 0) {
            int i = state.getValue(PILES);
            if (i > 1) {
                state.setValue(PILES, Math.max(1, i - 1));
            } else {
                level.removeBlock(pos, false);
            }
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        int i = state.getValue(PILES);
        if (!context.getItemInHand().is(this.asItem()) || i >= 16) {
            return i == 1;
        } else if (context.replacingClickedOnBlock()) {
            return context.getClickedFace() == Direction.UP;
        } else {
            return true;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        boolean persistent = context.getPlayer() != null;
        if (blockstate.is(this)) {
            int i = blockstate.getValue(PILES);
            return blockstate.setValue(PILES, Math.min(16, i + 1)).setValue(PERSISTENT, persistent);
        } else {
            BlockState state = super.getStateForPlacement(context);
            if (state != null) {
                state = state.setValue(PERSISTENT, persistent);
            }
            return state;
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        if (type == PathComputationType.LAND || type == PathComputationType.AIR) {
            return true;
        } else {
            return super.isPathfindable(state, type);
        }
    }
}
