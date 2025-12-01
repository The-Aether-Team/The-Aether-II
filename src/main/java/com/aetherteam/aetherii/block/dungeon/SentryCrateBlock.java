package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.blockentity.AetherIIBlockEntityTypes;
import com.aetherteam.aetherii.blockentity.SentryCrateBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

public class SentryCrateBlock extends BaseEntityBlock {
    public static final MapCodec<SentryCrateBlock> CODEC = simpleCodec(SentryCrateBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Block.column(14.0F, 0.0F, 14.0F);
    private static final Map<Direction, VoxelShape> HALF_SHAPES = Shapes.rotateHorizontal(Block.boxZ(14.0F, 0.0F, 14.0F, 0.0F, 15.0F));
    private static final DoubleBlockCombiner.Combiner<SentryCrateBlockEntity, Optional<Container>> CHEST_COMBINER = new DoubleBlockCombiner.Combiner<>() {
        @Override
        public Optional<Container> acceptDouble(SentryCrateBlockEntity one, SentryCrateBlockEntity two) {
            return Optional.of(new CompoundContainer(one, two));
        }

        @Override
        public Optional<Container> acceptSingle(SentryCrateBlockEntity single) {
            return Optional.of(single);
        }

        @Override
        public Optional<Container> acceptNone() {
            return Optional.empty();
        }
    };
    private static final DoubleBlockCombiner.Combiner<SentryCrateBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<>() {
        @Override
        public Optional<MenuProvider> acceptDouble(final SentryCrateBlockEntity one, final SentryCrateBlockEntity two) {
            final Container container = new CompoundContainer(one, two);
            return Optional.of(new MenuProvider() {
                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    if (one.canOpen(player) && two.canOpen(player)) {
                        one.unpackLootTable(inventory.player);
                        two.unpackLootTable(inventory.player);
                        return ChestMenu.sixRows(id, inventory, container);
                    } else {
                        return null;
                    }
                }

                @Override
                public Component getDisplayName() {
                    if (one.hasCustomName()) {
                        return one.getDisplayName();
                    } else {
                        return (two.hasCustomName() ? two.getDisplayName() : Component.translatable("aether_ii.container.sentry_crate"));
                    }
                }
            });
        }

        @Override
        public Optional<MenuProvider> acceptSingle(SentryCrateBlockEntity blockEntity) {
            return Optional.of(blockEntity);
        }

        @Override
        public Optional<MenuProvider> acceptNone() {
            return Optional.empty();
        }
    };

    @Override
    public MapCodec<SentryCrateBlock> codec() {
        return CODEC;
    }

    public SentryCrateBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SentryCrateBlockEntity(pos, state);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (neighborState.is(this) && direction.getAxis().isHorizontal()) {
            ChestType type = neighborState.getValue(TYPE);
            if (state.getValue(TYPE) == ChestType.SINGLE && type != ChestType.SINGLE && state.getValue(FACING) == neighborState.getValue(FACING) && ChestBlock.getConnectedDirection(neighborState) == direction.getOpposite()) {
                return state.setValue(TYPE, type.getOpposite());
            }
        } else if (ChestBlock.getConnectedDirection(state) == direction) {
            return state.setValue(TYPE, ChestType.SINGLE);
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(TYPE)) {
            case SINGLE -> SHAPE;
            case LEFT, RIGHT -> HALF_SHAPES.get(ChestBlock.getConnectedDirection(state));
        };
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SentryCrateBlockEntity sentryCrateBlockEntity) {
            sentryCrateBlockEntity.recheckOpen();
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverlevel) {
            MenuProvider menu = this.getMenuProvider(state, level, pos);
            if (menu != null) {
                player.openMenu(menu);
                player.awardStat(Stats.OPEN_BARREL);
                PiglinAi.angerNearbyPiglins(serverlevel, player, true);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ChestType type = ChestType.SINGLE;
        Direction oppDirection = context.getHorizontalDirection().getOpposite();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = context.isSecondaryUseActive();
        Direction clickedFace = context.getClickedFace();
        if (clickedFace.getAxis().isHorizontal() && flag) {
            Direction facingCandidate = this.candidatePartnerFacing(context, clickedFace.getOpposite());
            if (facingCandidate != null && facingCandidate.getAxis() != clickedFace.getAxis()) {
                oppDirection = facingCandidate;
                type = facingCandidate.getCounterClockWise() == clickedFace.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
            }
        }
        if (type == ChestType.SINGLE && !flag) {
            if (oppDirection == this.candidatePartnerFacing(context, oppDirection.getClockWise())) {
                type = ChestType.LEFT;
            } else if (oppDirection == this.candidatePartnerFacing(context, oppDirection.getCounterClockWise())) {
                type = ChestType.RIGHT;
            }
        }
        return this.defaultBlockState().setValue(FACING, oppDirection).setValue(TYPE, type).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Nullable
    private Direction candidatePartnerFacing(BlockPlaceContext context, Direction direction) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos().relative(direction));
        return state.is(this) && state.getValue(TYPE) == ChestType.SINGLE ? state.getValue(FACING) : null;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean isMoving) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        BlockState rotated = state.rotate(mirror.getRotation(state.getValue(FACING)));
        return mirror == Mirror.NONE ? rotated : rotated.setValue(TYPE, rotated.getValue(TYPE).getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, TYPE, WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return this.combine(state, level, pos, false).apply(MENU_PROVIDER_COMBINER).orElse(null);
    }

    public DoubleBlockCombiner.NeighborCombineResult<SentryCrateBlockEntity> combine(BlockState state, Level level, BlockPos pos, boolean override) {
        BiPredicate<LevelAccessor, BlockPos> predicate;
        if (override) {
            predicate = (levelAccessor, predicatePos) -> false;
        } else {
            predicate = ChestBlock::isChestBlockedAt;
        }
        return DoubleBlockCombiner.combineWithNeigbour(AetherIIBlockEntityTypes.SENTRY_CRATE.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, state, level, pos, predicate);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathType) {
        return false;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(OPEN) ? super.getLightEmission(state, level, pos) : 0;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return state.getValue(OPEN) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return side == Direction.UP ? state.getSignal(blockAccess, pos, side) : 0;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer(getContainer(this, blockState, level, pos, false));
    }

    @Nullable
    public static Container getContainer(SentryCrateBlock block, BlockState state, Level level, BlockPos pos, boolean override) {
        return block.combine(state, level, pos, override).apply(CHEST_COMBINER).orElse(null);
    }
}
