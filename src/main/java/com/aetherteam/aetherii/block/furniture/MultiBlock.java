package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.mixin.mixins.common.accessor.BlockAccessor;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public abstract class MultiBlock extends BaseEntityBlock {
    public static final DirectionProperty X_DIRECTION_FROM_ORIGIN = DirectionProperty.create("x_direction_from_origin", Direction.WEST, Direction.EAST);
    public static final DirectionProperty Y_DIRECTION_FROM_ORIGIN = DirectionProperty.create("y_direction_from_origin", Direction.DOWN, Direction.UP);
    public static final DirectionProperty Z_DIRECTION_FROM_ORIGIN = DirectionProperty.create("z_direction_from_origin", Direction.NORTH, Direction.SOUTH);
    public final IntegerProperty X_OFFSET_FROM_ORIGIN;
    public final IntegerProperty Y_OFFSET_FROM_ORIGIN;
    public final IntegerProperty Z_OFFSET_FROM_ORIGIN;

    private final Vec3i scale;

    public MultiBlock(int width, int depth, int height, Properties properties) {
        super(properties);
        this.scale = new Vec3i(depth, height, width);
        X_OFFSET_FROM_ORIGIN = IntegerProperty.create("x_offset_from_origin", 0, Math.max(this.scale.getX() - 1, 1));
        Y_OFFSET_FROM_ORIGIN = IntegerProperty.create("y_offset_from_origin", 0, Math.max(this.scale.getY() - 1, 1));
        Z_OFFSET_FROM_ORIGIN = IntegerProperty.create("z_offset_from_origin", 0, Math.max(this.scale.getZ() - 1, 1));
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createPostBlockStateDefinition(builder);
        ((BlockAccessor) this).aether_ii$setStateDefinition(builder.create(Block::defaultBlockState, BlockState::new));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(X_DIRECTION_FROM_ORIGIN, Direction.WEST)
                .setValue(Y_DIRECTION_FROM_ORIGIN, Direction.DOWN)
                .setValue(Z_DIRECTION_FROM_ORIGIN, Direction.NORTH)
                .setValue(X_OFFSET_FROM_ORIGIN, 0)
                .setValue(Y_OFFSET_FROM_ORIGIN, 0)
                .setValue(Z_OFFSET_FROM_ORIGIN, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(X_DIRECTION_FROM_ORIGIN).add(Y_DIRECTION_FROM_ORIGIN).add(Z_DIRECTION_FROM_ORIGIN);
    }

    protected void createPostBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        this.createBlockStateDefinition(builder);
        builder.add(X_OFFSET_FROM_ORIGIN).add(Y_OFFSET_FROM_ORIGIN).add(Z_OFFSET_FROM_ORIGIN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Pair<Direction, Direction> directions = getDirectionAndOffsetDirection(context.getHorizontalDirection(), context.getRotation());
        Direction direction = directions.getFirst();
        Direction offsetDirection = directions.getSecond();
        if (this.multiBlockPositions(direction, offsetDirection).allMatch((loopedPos) -> context.getLevel().isEmptyBlock(loopedPos.offset(context.getClickedPos())))) {
            return this.defaultBlockState();
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null) { //todo; default if placer null; dont let this overwrite other blocks;
            Pair<Direction, Direction> directions = this.getDirectionAndOffsetDirection(placer.getDirection(), placer.getYRot());
            Direction direction = directions.getFirst();
            Direction offsetDirection = directions.getSecond();
            this.multiBlockPositions(direction, offsetDirection).forEach((loopedPos) -> {
                BlockState modifiedState = state;
                Direction xDirection = direction.getAxis() == Direction.Axis.X ? direction : offsetDirection;
                Direction zDirection = direction.getAxis() == Direction.Axis.Z ? direction : offsetDirection;
                modifiedState = modifiedState.setValue(X_DIRECTION_FROM_ORIGIN, xDirection).setValue(this.X_OFFSET_FROM_ORIGIN, Mth.abs(loopedPos.getX()));
                modifiedState = modifiedState.setValue(Y_DIRECTION_FROM_ORIGIN, Direction.UP).setValue(this.Y_OFFSET_FROM_ORIGIN, Mth.abs(loopedPos.getY()));
                modifiedState = modifiedState.setValue(Z_DIRECTION_FROM_ORIGIN, zDirection).setValue(this.Z_OFFSET_FROM_ORIGIN, Mth.abs(loopedPos.getZ()));
                level.setBlock(loopedPos.offset(pos), modifiedState, 3);
            });
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) { //todo drop block check this.isOrigin(level.getBlockState(loopedPos.offset(origin)))
        BlockPos origin = this.locateOriginFrom(state, pos);
        this.multiBlockPositions(state.getValue(X_DIRECTION_FROM_ORIGIN), state.getValue(Z_DIRECTION_FROM_ORIGIN)).forEach((loopedPos) -> level.destroyBlock(loopedPos.offset(origin), false));
        return super.playerWillDestroy(level, pos, state, player);
    }

    public Pair<Direction, Direction> getDirectionAndOffsetDirection(Direction initialDirection, float initialDegrees) {
//            Direction.Axis axis = state.getValue(HORIZONTAL_AXIS); //todo i dont need this i can just get it from the player direction
//            int segmentation = 0;
//            if (axis == Direction.Axis.X) {
//                segmentation = this.scale.getZ();
//            } else {
//                segmentation = this.scale.getX();
//            } //todo 90 / segmentation

        int degrees = Math.round(Mth.wrapDegrees(initialDegrees) + 180); //todo may need to be abstracted so its usable for getStateForPlacement placeable checking
        int degreesOffset = degrees - 45;
        if (degreesOffset < 0) degreesOffset += 360;
        int rangedDegrees = degreesOffset % 90;

        Direction offsetDirection;
        if (rangedDegrees < 45) { //todo this is where segmentation will factor in later i think. 45 is the midpoint. i need to account for more than just a midpoint for 2<
            offsetDirection = initialDirection.getCounterClockWise();
        } else {
            offsetDirection = initialDirection.getClockWise();
        }
        return Pair.of(initialDirection, offsetDirection);
    }

    public Stream<BlockPos> multiBlockPositions(Direction depthDirection, Direction widthDirection) {
        BlockPos origin = BlockPos.ZERO;
        BlockPos corner = origin.relative(depthDirection, this.getScale().getX() - 1).relative(Direction.UP, this.getScale().getY() - 1).relative(widthDirection, this.getScale().getZ() - 1);
        return Streams.stream(BlockPos.betweenClosed(origin, corner));
    }

    public BlockPos locateOriginFrom(BlockState state, BlockPos pos) {
        int xOffset = state.getValue(this.X_OFFSET_FROM_ORIGIN);
        int yOffset = state.getValue(this.Y_OFFSET_FROM_ORIGIN);
        int zOffset = state.getValue(this.Z_OFFSET_FROM_ORIGIN);
        if (this.isOrigin(state)) {
            return pos;
        } else {
            Direction xDirection = state.getValue(X_DIRECTION_FROM_ORIGIN);
            Direction yDirection = state.getValue(Y_DIRECTION_FROM_ORIGIN);
            Direction zDirection = state.getValue(Z_DIRECTION_FROM_ORIGIN);
            return pos.relative(xDirection.getOpposite(), xOffset).relative(yDirection.getOpposite(), yOffset).relative(zDirection.getOpposite(), zOffset);
        }
    }

    public boolean isOrigin(BlockState state) {
        int xOffset = state.getValue(this.X_OFFSET_FROM_ORIGIN);
        int yOffset = state.getValue(this.Y_OFFSET_FROM_ORIGIN);
        int zOffset = state.getValue(this.Z_OFFSET_FROM_ORIGIN);
        return xOffset == 0 && yOffset == 0 && zOffset == 0;
    }

    public boolean isFarthest(BlockState state) {
        return state.getValue(this.X_OFFSET_FROM_ORIGIN) == this.getScale().getX() - 1 && state.getValue(this.Y_OFFSET_FROM_ORIGIN) == 0 && state.getValue(this.Z_OFFSET_FROM_ORIGIN) == this.getScale().getZ() - 1;
    }

    public Vec3i getScale() {
        return this.scale;
    }
}
