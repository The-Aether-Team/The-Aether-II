package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.AmberHourglassBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AmberHourglassBlock extends BaseEntityBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    protected static final VoxelShape SHAPE_BOTTOM = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    protected static final VoxelShape SHAPE_CENTER = Block.box(3.0, 2.0, 3.0, 13.0, 14.0, 13.0);
    protected static final VoxelShape SHAPE_COLUMN_1 = Block.box(1.0, 2.0, 1.0, 3.0, 14.0, 3.0);
    protected static final VoxelShape SHAPE_COLUMN_2 = Block.box(13.0, 2.0, 1.0, 15.0, 14.0, 3.0);
    protected static final VoxelShape SHAPE_COLUMN_3 = Block.box(1.0, 2.0, 13.0, 3.0, 14.0, 15.0);
    protected static final VoxelShape SHAPE_COLUMN_4 = Block.box(13.0, 2.0, 13.0, 15.0, 14.0, 15.0);
    protected static final VoxelShape SHAPE_TOP = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SHAPE = Shapes.or(SHAPE_BOTTOM, SHAPE_CENTER, SHAPE_COLUMN_1, SHAPE_COLUMN_2, SHAPE_COLUMN_3,SHAPE_COLUMN_4, SHAPE_TOP);

    public AmberHourglassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));
    }
@Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AmberHourglassBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTicker(level, blockEntityType, AetherIIBlockEntityTypes.AMBER_HOURGLASS.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends AmberHourglassBlockEntity> clientType) {
        if (level instanceof ServerLevel serverLevel) {
            return createTickerHelper(serverType, clientType, (tickerLevel, pos, state, blockEntity) -> AmberHourglassBlockEntity.serverTick(serverLevel, pos, state, blockEntity));
        } else {
            return null;
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
        if (blockEntity instanceof AmberHourglassBlockEntity amberHourglassBlockEntity) {
            player.openMenu(amberHourglassBlockEntity);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
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
        return SHAPE;
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
