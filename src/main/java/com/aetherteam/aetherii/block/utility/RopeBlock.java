package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.Map;

public class RopeBlock extends Block {
    public static final BooleanProperty KNOT = AetherIIBlockStateProperties.ROPE_KNOT;
    public static final BooleanProperty SPOOL = AetherIIBlockStateProperties.ROPE_SPOOL;
    public static final BooleanProperty END = AetherIIBlockStateProperties.ROPE_END;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final int MAX_LENGTH = 16;
    public static final int DELAY = 4;

    public RopeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(KNOT, false).setValue(SPOOL, false).setValue(END, false).setValue(UP, false).setValue(DOWN, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos.below()).isAir()) {
            if (this.checkForStake(level, pos)) {
                level.setBlock(pos, state.setValue(DOWN, true).setValue(END, false), 1 | 2);
                level.setBlock(pos.below(), AetherIIBlocks.CLIMBING_ROPE.get().defaultBlockState().setValue(UP, true), 1 | 2);
                level.scheduleTick(pos.below(), this, DELAY);
            } else {
                level.setBlock(pos, state.setValue(END, true), 1 | 2);
            }
        } else {
            level.setBlock(pos, state.setValue(DOWN, true).setValue(END, false).setValue(SPOOL, true), 1 | 2);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource randomSource) {
        if (neighborState.isAir()) {
            if (direction == Direction.DOWN) {
                if (state.getValue(SPOOL)) {
                    scheduledTickAccess.scheduleTick(pos, this, DELAY);
                    state = state.setValue(SPOOL, false);
                } else {
                    state = state.setValue(DOWN, false);
                    if (!state.getValue(KNOT)) {
                        state = state.setValue(END, true);
                    }
                }
            } else if (direction == Direction.UP) { //todo this should be based on connection direction to allow for placing rope without having it break immediately when breaking smth above it. if it only has one connection?
                return Blocks.AIR.defaultBlockState();
            }
        }
        return state;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockState newState = state.cycle(KNOT);
        level.setBlock(pos, newState, 1 | 2);
        return InteractionResult.SUCCESS;
    }

    public boolean checkForStake(LevelReader levelReader, BlockPos pos) {
        for (int i = 1; i < MAX_LENGTH; ++i) {
            BlockPos abovePos = pos.above(i);
            BlockState aboveState = levelReader.getBlockState(abovePos);
            if (aboveState.is(AetherIIBlocks.CLIMBING_ROPE_STAKE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KNOT, SPOOL, END, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape spool = Block.box(4, 0, 4, 12, 2, 12);
        VoxelShape connection = Block.box(6, 6, 0, 10, 10, 8);
        Map<Direction, VoxelShape> rotated = Shapes.rotateAll(connection);
        VoxelShape finalShape = Shapes.empty();
        for (Map.Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValue(entry.getValue())) {
                finalShape = Shapes.or(finalShape, rotated.get(entry.getKey()));
            }
        }
        if (state.getValue(SPOOL)) {
            finalShape = Shapes.or(finalShape, spool);
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
    protected boolean skipRendering(BlockState state, BlockState neighborState, Direction direction) {
        return neighborState.is(this) || neighborState.is(AetherIIBlocks.CLIMBING_ROPE_STAKE) || super.skipRendering(state, neighborState, direction);
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
