package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.AlkahestPurifierBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AlkahestPurifierBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty LEVEL = AetherIIBlockStateProperties.PURIFIER_LEVEL;
    protected static final VoxelShape BASE_Z = Block.box(0.0, 0.0, 1.0, 16.0, 2.0, 15.0);
    protected static final VoxelShape BASE_X = Block.box(1.0, 0.0, 0.0, 15.0, 2.0, 16.0);
    protected static final VoxelShape MIDDLE = Block.box(4.0, 2.0, 4.0, 12.0, 14.0, 12.0);
    protected static final VoxelShape FRONT_RIDGE_Z = Block.box(5.0, 0.0, 12.0, 11.0, 4.0, 16.0);
    protected static final VoxelShape BACK_RIDGE_Z = Block.box(5.0, 0.0, 0.0, 11.0, 4.0, 4.0);
    protected static final VoxelShape FRONT_RIDGE_X = Block.box(12.0, 0.0, 5.0, 16.0, 4.0, 11.0);
    protected static final VoxelShape BACK_RIDGE_X = Block.box(0.0, 0.0, 5.0, 4.0, 4.0, 11.0);
    protected static final VoxelShape VIALS_SIDE_1_Z = Block.box(0.5, 3.0, 6.5, 4.0, 14.0, 9.5);
    protected static final VoxelShape VIALS_SIDE_2_Z = Block.box(11.0, 3.0, 6.5, 15.5, 14.0, 9.5);
    protected static final VoxelShape VIALS_SIDE_1_X = Block.box(6.5, 3.0, 0.5, 9.5, 14.0, 4.0);
    protected static final VoxelShape VIALS_SIDE_2_X = Block.box(6.5, 3.0, 11.0, 9.5, 14.0, 15.5);
    protected static final VoxelShape TOP = Block.box(3.0, 14.0, 3.0, 13.0, 16.0, 13.0);
    protected static final VoxelShape SHAPE_Z = Shapes.or(BASE_Z, MIDDLE, FRONT_RIDGE_Z, BACK_RIDGE_Z, VIALS_SIDE_1_Z, VIALS_SIDE_2_Z, TOP);
    protected static final VoxelShape SHAPE_X = Shapes.or(BASE_X, MIDDLE, FRONT_RIDGE_X, BACK_RIDGE_X, VIALS_SIDE_1_X, VIALS_SIDE_2_X, TOP);

    public AlkahestPurifierBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEVEL, 0));
    }
@Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlkahestPurifierBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTicker(level, blockEntityType, AetherIIBlockEntityTypes.ALKAHEST_PURIFIER.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends AlkahestPurifierBlockEntity> clientType) {
        if (level instanceof ServerLevel serverLevel) {
            return createTickerHelper(serverType, clientType, (tickerLevel, pos, state, blockEntity) -> AlkahestPurifierBlockEntity.serverTick(serverLevel, pos, state, blockEntity));
        } else {
            return createTickerHelper(serverType, clientType, AlkahestPurifierBlockEntity::lidAnimateTick);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AlkahestPurifierBlockEntity alkahestPurifierBlockEntity) {
            player.openMenu(alkahestPurifierBlockEntity);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof AlkahestPurifierBlockEntity alkahestPurifierBlockEntity) {
            alkahestPurifierBlockEntity.recheckOpen();
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
        builder.add(FACING, LEVEL);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
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
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}
