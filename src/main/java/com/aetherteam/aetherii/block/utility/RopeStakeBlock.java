package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeStakeBlock extends Block {
    public static final EnumProperty<Direction> CONNECTION = BlockStateProperties.FACING;
    public static final EnumProperty<AetherIIBlockStateProperties.StakeSpoolState> SPOOL = AetherIIBlockStateProperties.STAKE_SPOOL;

    public RopeStakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONNECTION, Direction.NORTH).setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos.below()).isAir()) {
            level.setBlock(pos.below(), AetherIIBlocks.CLIMBING_ROPE.get().defaultBlockState().setValue(RopeBlock.UP, true), 1 | 2);
            level.scheduleTick(pos.below(), AetherIIBlocks.CLIMBING_ROPE.get(), RopeBlock.DELAY);
        } else {
            level.setBlock(pos, state.setValue(RopeStakeBlock.SPOOL, AetherIIBlockStateProperties.StakeSpoolState.FLOOR), 1 | 2);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (neighborState.isAir()) {
            if (direction == Direction.DOWN) {
                if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED) {
                    state = state.setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE);
                }
            } else if (direction == state.getValue(CONNECTION)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return state;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.CENTER) {
            BlockState newState = state.setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED);
            level.setBlock(pos, newState, 1 | 2);
            level.scheduleTick(pos, this, RopeBlock.DELAY);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(CONNECTION, context.getClickedFace().getOpposite()).setValue(SPOOL, AetherIIBlockStateProperties.StakeSpoolState.CENTER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONNECTION, SPOOL);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        VoxelShape center = Block.box(5, 5, 5, 11, 11, 11);
        VoxelShape stake = Block.box(6, 6, 0, 10, 10, 13);
        VoxelShape finalShape = Shapes.rotateAll(stake).get(state.getValue(CONNECTION));
        if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.NONE_CONNECTED || state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.FLOOR) {
            VoxelShape connection = Block.box(6, 0, 6, 10, 6, 10);
            finalShape = Shapes.or(finalShape, connection);
        }
        if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.FLOOR) {
            VoxelShape spool = Block.box(4, 0, 4, 12, 2, 12);
            finalShape = Shapes.or(finalShape, spool);
        } else if (state.getValue(SPOOL) == AetherIIBlockStateProperties.StakeSpoolState.CENTER) {
            VoxelShape spool = Block.box(4, 4, 7, 12, 12, 9);
            finalShape = Shapes.or(finalShape, Shapes.rotateAll(spool).get(state.getValue(CONNECTION)));

        }
        return finalShape;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
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
}
