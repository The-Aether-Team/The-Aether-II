package com.aetherteam.aetherii.block.construction;

import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIShapes;
import com.aetherteam.aetherii.blockentity.ShelfBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class ShelfBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<AetherIIBlockStateProperties.SideChain> SIDE_CHAIN = AetherIIBlockStateProperties.SIDE_CHAIN;
    protected static final Map<Direction, VoxelShape> SHAPES = AetherIIShapes.rotateHorizontal(Shapes.or(
        Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0),
        Block.box(0.0, 0.0, 11.0, 16.0, 4.0, 13.0),
        Block.box(0.0, 12.0, 11.0, 16.0, 16.0, 13.0)
    ));

    public ShelfBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(SIDE_CHAIN, AetherIIBlockStateProperties.SideChain.UNCONNECTED)
            .setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState()
            .setValue(FACING, context.getHorizontalDirection().getOpposite())
            .setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()))
            .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
        return this.updateSideChain(state, context.getLevel(), context.getClickedPos());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            boolean powered = level.hasNeighborSignal(pos);
            if (state.getValue(POWERED) != powered) {
                BlockState updatedState = this.updateSideChain(state.setValue(POWERED, powered), level, pos);
                level.setBlock(pos, updatedState, 3);
                this.playSound(level, pos, powered ? SoundEvents.WOODEN_BUTTON_CLICK_ON : SoundEvents.WOODEN_BUTTON_CLICK_OFF);
                level.gameEvent(powered ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(updatedState));
                this.updateConnectedNeighbors(level, pos, updatedState);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return direction.getAxis().isHorizontal() ? this.updateSideChain(state, level, pos) : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private BlockState updateSideChain(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(POWERED)) {
            return state.setValue(SIDE_CHAIN, AetherIIBlockStateProperties.SideChain.UNCONNECTED);
        }
        Direction facing = state.getValue(FACING);
        List<BlockPos> chain = this.getConnectedChain(level, pos, state);
        int index = chain.indexOf(pos);
        boolean left = index > 0;
        boolean right = index >= 0 && index < chain.size() - 1;
        return state.setValue(SIDE_CHAIN, getSideChain(left, right));
    }

    private boolean connectsTo(BlockState state, BlockState neighborState) {
        return neighborState.getBlock() instanceof ShelfBlock
            && neighborState.hasProperty(FACING)
            && neighborState.hasProperty(POWERED)
            && neighborState.getValue(FACING) == state.getValue(FACING)
            && neighborState.getValue(POWERED);
    }

    private List<BlockPos> getConnectedChain(BlockGetter level, BlockPos pos, BlockState state) {
        Direction leftDirection = state.getValue(FACING).getCounterClockWise();
        Direction rightDirection = state.getValue(FACING).getClockWise();
        List<BlockPos> result = new ArrayList<>();
        List<BlockPos> left = new ArrayList<>();
        for (int offset = 1; offset <= 2; offset++) {
            BlockPos candidate = pos.relative(leftDirection, offset);
            if (this.connectsTo(state, level.getBlockState(candidate))) {
                left.add(candidate);
            } else {
                break;
            }
        }
        for (int i = left.size() - 1; i >= 0; i--) {
            result.add(left.get(i));
        }
        result.add(pos);
        for (int offset = 1; offset <= 2 && result.size() < 3; offset++) {
            BlockPos candidate = pos.relative(rightDirection, offset);
            if (this.connectsTo(state, level.getBlockState(candidate))) {
                result.add(candidate);
            } else {
                break;
            }
        }
        while (result.size() > 3) {
            result.remove(0);
        }
        return result;
    }

    private void updateConnectedNeighbors(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        for (int offset = -2; offset <= 2; offset++) {
            if (offset == 0) {
                continue;
            }
            BlockPos candidate = pos.relative(facing.getClockWise(), offset);
            BlockState candidateState = level.getBlockState(candidate);
            if (candidateState.getBlock() instanceof ShelfBlock && candidateState.hasProperty(FACING) && candidateState.getValue(FACING) == facing) {
                BlockState updated = this.updateSideChain(candidateState, level, candidate);
                if (updated != candidateState) {
                    level.setBlock(candidate, updated, 3);
                }
            }
        }
    }

    private static AetherIIBlockStateProperties.SideChain getSideChain(boolean left, boolean right) {
        if (left && right) {
            return AetherIIBlockStateProperties.SideChain.CENTER;
        } else if (left) {
            return AetherIIBlockStateProperties.SideChain.LEFT;
        } else if (right) {
            return AetherIIBlockStateProperties.SideChain.RIGHT;
        } else {
            return AetherIIBlockStateProperties.SideChain.UNCONNECTED;
        }
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
        builder.add(FACING, POWERED, SIDE_CHAIN, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return type == PathComputationType.WATER && state.getFluidState().is(Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof ShelfBlockEntity shelf)) {
            return InteractionResult.PASS;
        }
        Optional<Vec2> hitCoordinates = getRelativeHitCoordinatesForBlockFace(hit, state.getValue(FACING));
        if (hitCoordinates.isEmpty()) {
            return InteractionResult.PASS;
        }
        int slot = getHitSlot(hitCoordinates.get());

        if (level.isClientSide()) {
            return player.getItemInHand(hand).isEmpty() && shelf.getItem(slot).isEmpty() ? InteractionResult.PASS : InteractionResult.SUCCESS;
        }

        Inventory inventory = player.getInventory();
        if (!state.getValue(POWERED)) {
            ItemStack previousHeld = player.getItemInHand(hand);
            if (previousHeld.isEmpty() && shelf.getItem(slot).isEmpty()) {
                return InteractionResult.PASS;
            }
            ItemStack removed = shelf.swapItemNoUpdate(slot, previousHeld);
            ItemStack newHeld = player.getAbilities().instabuild && removed.isEmpty() ? previousHeld.copy() : removed;
            inventory.setItem(inventory.selected, newHeld);
            inventory.setChanged();
            shelf.setChanged(GameEvent.ITEM_INTERACT_FINISH);
            this.playSound(level, pos, removed.isEmpty() ? SoundEvents.CHISELED_BOOKSHELF_INSERT : (previousHeld.isEmpty() ? SoundEvents.CHISELED_BOOKSHELF_PICKUP : SoundEvents.ITEM_FRAME_ADD_ITEM));
            return InteractionResult.CONSUME;
        } else {
            boolean swapped = this.swapHotbar(level, pos, inventory);
            if (swapped) {
                this.playSound(level, pos, SoundEvents.ITEM_FRAME_ADD_ITEM);
            }
            return InteractionResult.CONSUME;
        }
    }

    private static Optional<Vec2> getRelativeHitCoordinatesForBlockFace(BlockHitResult hit, Direction face) {
        Direction direction = hit.getDirection();
        if (face != direction) {
            return Optional.empty();
        }
        BlockPos relativePos = hit.getBlockPos().relative(direction);
        Vec3 location = hit.getLocation().subtract(relativePos.getX(), relativePos.getY(), relativePos.getZ());
        return switch (direction) {
            case NORTH -> Optional.of(new Vec2((float) (1.0D - location.x()), (float) location.y()));
            case SOUTH -> Optional.of(new Vec2((float) location.x(), (float) location.y()));
            case WEST -> Optional.of(new Vec2((float) location.z(), (float) location.y()));
            case EAST -> Optional.of(new Vec2((float) (1.0D - location.z()), (float) location.y()));
            default -> Optional.empty();
        };
    }

    private static int getHitSlot(Vec2 hitPos) {
        if (hitPos.x < 0.375F) {
            return 0;
        }
        return hitPos.x < 0.6875F ? 1 : 2;
    }

    private boolean swapHotbar(Level level, BlockPos pos, Inventory inventory) {
        List<BlockPos> connectedBlocks = this.getAllBlocksConnectedTo(level, pos);
        if (connectedBlocks.isEmpty()) {
            return false;
        }
        boolean anySwapped = false;
        for (int shelfPartIndex = 0; shelfPartIndex < connectedBlocks.size(); shelfPartIndex++) {
            if (level.getBlockEntity(connectedBlocks.get(shelfPartIndex)) instanceof ShelfBlockEntity shelfPart) {
                for (int slot = 0; slot < shelfPart.getContainerSize(); slot++) {
                    int inventorySlot = 9 - (connectedBlocks.size() - shelfPartIndex) * shelfPart.getContainerSize() + slot;
                    if (inventorySlot >= 0 && inventorySlot < 9) {
                        ItemStack placedInventoryItem = inventory.removeItemNoUpdate(inventorySlot);
                        ItemStack removedShelfItem = shelfPart.swapItemNoUpdate(slot, placedInventoryItem);
                        if (!placedInventoryItem.isEmpty() || !removedShelfItem.isEmpty()) {
                            inventory.setItem(inventorySlot, removedShelfItem);
                            anySwapped = true;
                        }
                    }
                }
                inventory.setChanged();
                shelfPart.setChanged(GameEvent.ENTITY_INTERACT);
            }
        }
        return anySwapped;
    }

    private List<BlockPos> getAllBlocksConnectedTo(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof ShelfBlock) || !state.getValue(POWERED)) {
            return List.of();
        }
        return this.getConnectedChain(level, pos, state).stream()
            .filter((candidate) -> level.getBlockEntity(candidate) instanceof ShelfBlockEntity)
            .toList();
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ShelfBlockEntity shelf && !shelf.isEmpty()) {
                for (int i = 0; i < shelf.getContainerSize(); i++) {
                    ItemStack stack = shelf.getItem(i);
                    if (!stack.isEmpty()) {
                        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                    }
                }
                shelf.clearContent();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            if (!level.isClientSide()) {
                this.updateConnectedNeighbors(level, pos, state);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) {
            this.updateConnectedNeighbors(level, pos, state);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.isClientSide()) {
            return 0;
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ShelfBlockEntity shelf) {
            int item1Bit = shelf.getItem(0).isEmpty() ? 0 : 1;
            int item2Bit = shelf.getItem(1).isEmpty() ? 0 : 1;
            int item3Bit = shelf.getItem(2).isEmpty() ? 0 : 1;
            return item1Bit | item2Bit << 1 | item3Bit << 2;
        }
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    private void playSound(LevelAccessor level, BlockPos pos, SoundEvent sound) {
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
