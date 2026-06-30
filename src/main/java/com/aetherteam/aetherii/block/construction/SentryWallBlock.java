package com.aetherteam.aetherii.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SentryWallBlock extends WallBlock implements SentryBlockUpdating {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final Map<Direction, EnumProperty<WallSide>> PROPERTY_BY_DIRECTION = Map.of(
            Direction.NORTH, NORTH_WALL,
            Direction.EAST, EAST_WALL,
            Direction.SOUTH, SOUTH_WALL,
            Direction.WEST, WEST_WALL
    );
    private final Map<BlockState, VoxelShape> shapes;
    private final Map<BlockState, VoxelShape> collisionShapes;

    public SentryWallBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, true).setValue(POWERED, false).setValue(UP, true).setValue(NORTH_WALL, WallSide.NONE).setValue(EAST_WALL, WallSide.NONE).setValue(SOUTH_WALL, WallSide.NONE).setValue(WEST_WALL, WallSide.NONE).setValue(WATERLOGGED, false));
        this.shapes = this.makeShapes(16.0F, 14.0F);
        this.collisionShapes = this.makeShapes(24.0F, 24.0F);
    }

    private Map<BlockState, VoxelShape> makeShapes(float height, float width) {
        VoxelShape voxelshape = Block.box(4.0F, 0.0F, 4.0F, 12.0F, height, 12.0F);
        Map<Direction, VoxelShape> map = com.aetherteam.aetherii.block.AetherIIShapes.rotateHorizontal(com.aetherteam.aetherii.block.AetherIIShapes.boxZ(6.0F, 0.0F, width, 0.0F, 11.0F));
        Map<Direction, VoxelShape> map1 = com.aetherteam.aetherii.block.AetherIIShapes.rotateHorizontal(com.aetherteam.aetherii.block.AetherIIShapes.boxZ(6.0F, 0.0F, height, 0.0F, 11.0F));
        return this.getShapeForEachState((state) -> {
            VoxelShape voxelshape1 = state.getValue(UP) ? voxelshape : Shapes.empty();
            for (Map.Entry<Direction, EnumProperty<WallSide>> entry : PROPERTY_BY_DIRECTION.entrySet()) {
                VoxelShape var10001;
                switch (state.getValue(entry.getValue())) {
                    case NONE -> var10001 = Shapes.empty();
                    case LOW -> var10001 = map.get(entry.getKey());
                    case TALL -> var10001 = map1.get(entry.getKey());
                    default -> throw new IllegalStateException("Unknown wall side");
                }
                voxelshape1 = Shapes.or(voxelshape1, var10001);
            }
            return voxelshape1;
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapes.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.collisionShapes.get(state);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        SentryBlockUpdating.super.updateStates(state, level, pos);
        super.tick(state, level, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = level;
        SentryBlockUpdating.super.scheduleChange(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT) ? super.getLightEmission(state, level, pos) : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT, POWERED);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return level.getBlockState(pos).getValue(LIT) ? 15 : 0;
    }
}
