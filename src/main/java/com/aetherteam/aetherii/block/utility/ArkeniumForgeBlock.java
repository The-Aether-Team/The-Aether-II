package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ArkeniumForgeBlock extends BaseEntityBlock {
    public static final MapCodec<ArkeniumForgeBlock> CODEC = simpleCodec(ArkeniumForgeBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");
    protected static final VoxelShape CORNER_1 = Block.box(0.0, 0.0, 0.0, 3.0, 6.0, 3.0);
    protected static final VoxelShape CORNER_2 = Block.box(13.0, 0.0, 13.0, 16.0, 6.0, 16.0);
    protected static final VoxelShape CORNER_3 = Block.box(13.0, 0.0, 0.0, 16.0, 6.0, 3.0);
    protected static final VoxelShape CORNER_4 = Block.box(0.0, 0.0, 13.0, 3.0, 6.0, 16.0);
    protected static final VoxelShape BASE_1 = Block.box(1.0, 0.0, 1.0, 15.0, 4.0, 15.0);
    protected static final VoxelShape RIM_1 = Block.box(2.0, 4.0, 2.0, 4.0, 8.0, 14.0);
    protected static final VoxelShape RIM_2 = Block.box(4.0, 4.0, 2.0, 12.0, 8.0, 4.0);
    protected static final VoxelShape RIM_3 = Block.box(12.0, 4.0, 2.0, 14.0, 8.0, 14.0);
    protected static final VoxelShape RIM_4 = Block.box(4.0, 4.0, 12.0, 12.0, 8.0, 14.0);
    protected static final VoxelShape CENTER_Z = Block.box(5.5, 4.0, 6.0, 10.5, 11.0, 10.0);
    protected static final VoxelShape TOP_1_Z = Block.box(5.5, 11.0, 3.0, 10.5, 16.0, 13.0);
    protected static final VoxelShape TOP_2_Z = Block.box(1.5, 11.0, 5.0, 14.5, 16.0, 11.0);
    protected static final VoxelShape CENTER_X = Block.box(6.0, 4.0, 5.5, 10.0, 11.0, 10.5);
    protected static final VoxelShape TOP_1_X = Block.box(3.0, 11.0, 5.5, 13.0, 16.0, 10.5);
    protected static final VoxelShape TOP_2_X = Block.box(5.0, 11.0, 1.5, 11.0, 16.0, 14.5);
    protected static final VoxelShape SHAPE_Z = Shapes.or(BASE_1, RIM_1, RIM_2, RIM_3, RIM_4, CENTER_Z, CORNER_1, CORNER_2, CORNER_3, CORNER_4, TOP_1_Z, TOP_2_Z);
    protected static final VoxelShape SHAPE_X = Shapes.or(BASE_1, RIM_1, RIM_2, RIM_3, RIM_4, CENTER_X, CORNER_1, CORNER_2, CORNER_3, CORNER_4, TOP_1_X, TOP_2_X);

    public ArkeniumForgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CHARGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArkeniumForgeBlockEntity(pos, state);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTicker(level, blockEntityType, AetherIIBlockEntityTypes.ARKENIUM_FORGE.get());
    }

    @javax.annotation.Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends ArkeniumForgeBlockEntity> clientType) {
        return level.isClientSide() ? null : createTickerHelper(serverType, clientType, ArkeniumForgeBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ArkeniumForgeBlockEntity arkeniumForgeBlockEntity) {
            player.openMenu(arkeniumForgeBlockEntity);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {
            return SHAPE_Z;
        } else {
            return SHAPE_X;
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(CHARGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
