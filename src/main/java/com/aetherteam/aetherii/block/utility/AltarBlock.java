package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.AltarBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AltarBlock extends BaseEntityBlock {
    public static final MapCodec<AltarBlock> CODEC = simpleCodec(AltarBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty CHARGING = AetherIIBlockStateProperties.ALTAR_CHARGING;
    public static final BooleanProperty BLASTING = AetherIIBlockStateProperties.ALTAR_BLASTING;
    protected static final VoxelShape SHAPE_BASE = Block.box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0);
    protected static final VoxelShape SHAPE_COLUMN = Block.box(5.0, 2.0, 5.0, 11.0, 8.0, 11.0);
    protected static final VoxelShape SHAPE_TOP = Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SHAPE = Shapes.or(SHAPE_BASE, SHAPE_COLUMN, SHAPE_TOP);

    public AltarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CHARGING, false).setValue(BLASTING, false));
    }

    @Override
    public MapCodec<AltarBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AltarBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTicker(level, blockEntityType, AetherIIBlockEntityTypes.ALTAR.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends AltarBlockEntity> clientType) {
        if (level instanceof ServerLevel serverLevel) {
            return createTickerHelper(serverType, clientType, (tickerLevel, pos, state, blockEntity) -> AltarBlockEntity.serverTick(serverLevel, pos, state, blockEntity));
        } else {
            return null;
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AltarBlockEntity altarBlockEntity) {
            player.openMenu(altarBlockEntity);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(BLASTING)) {
            double x = (double) pos.getX() + 0.5;
            double y = (double) pos.getY() + 0.65;
            double z = (double) pos.getZ() + 0.5;

            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();

            for (int i = 0; i < 5; i++) {
                double multiplier = random.nextDouble() * 0.2 - 0.1;

                double xOffset = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52 : multiplier;
                double yOffset = random.nextDouble() * 4.0 / 16.0;
                double zOffset = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52 : multiplier;

                int color = random.nextBoolean() ? 14403138 : 9721330;

                level.addParticle(new DustParticleOptions(color, 1.0F), x + xOffset + (i * xOffset * 0.1), y + yOffset, z + zOffset + (i * zOffset * 0.1), xOffset * 0.5, 0.0, zOffset * 0.5);
                level.addParticle(new DustParticleOptions(color, 1.0F), x - xOffset - (i * xOffset * 0.1), y + yOffset, z - zOffset - (i * zOffset * 0.1), -xOffset * 0.5, 0.0, -zOffset * 0.5);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AltarBlockEntity altarBlockEntity) {
                if (level instanceof ServerLevel serverLevel) {
                    Containers.dropContents(level, pos, altarBlockEntity);
                    altarBlockEntity.getRecipesToAwardAndPopExperience(serverLevel, Vec3.atCenterOf(pos));
                }
                super.onRemove(state, level, pos, newState, isMoving);
                level.updateNeighbourForOutputSignal(pos, this);
            } else {
                super.onRemove(state, level, pos, newState, isMoving);
            }
        }
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
        builder.add(FACING, CHARGING, BLASTING);
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
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
