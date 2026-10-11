package com.aetherteam.aetherii.block.dungeon;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.world.AuxiliaryLightManager;

import javax.annotation.Nullable;

public abstract class CopyBlock extends BaseEntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty EMPTY = AetherIIBlockStateProperties.EMPTY;

    public CopyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(EMPTY, true));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isCreative()) {
            if (stack.getItem() instanceof BlockItem blockItem) {
                BlockState copyState = blockItem.getBlock().getStateForPlacement(new BlockPlaceContext(player, hand, stack, hitResult));
                if (copyState != null && copyState.getBlock() != this && copyState.is(AetherIITags.Blocks.COPYABLE_DUNGEON_BLOCKS)) {
                    if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
                        BlockState newState = state.setValue(CopyBlock.EMPTY, false);
                        this.setCopyBlocksInfo(level, pos, state, copyState, newState, blockEntity);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    protected void setCopyBlocksInfo(Level level, BlockPos pos, BlockState state, BlockState copyState, BlockState newState, CopyBlockEntity blockEntity) {
        blockEntity.setCopyState(copyState);
        level.setBlockAndUpdate(pos, newState);
        level.blockEvent(pos, newState.getBlock(), 1, 0);
        state.initCache();
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource randomSource) {
        if (!state.getValue(EMPTY)) {
            if (levelReader.getBlockEntity(currentPos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                blockEntity.setChanged();
            }
        } else {
            if (state.getValue(WATERLOGGED)) {
                scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
            }
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, currentPos, facing, facingPos, facingState, randomSource);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(EMPTY) && state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        BlockState placementState = this.defaultBlockState();
        if (stack.get(AetherIIDataComponents.BLOCK_STATE) != null) {
            placementState = placementState.setValue(EMPTY, false);
        } else {
            placementState = placementState.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
        }
        return placementState;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
            return blockEntity.getItem();
        } else {
            return super.getCloneItemStack(level, pos, state, includeData);
        }
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                super.spawnDestroyParticles(level, player, pos, state);
            }
        }
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().hidesNeighborFace(level, pos, neighborState, dir);
            }
        }
        return super.hidesNeighborFace(level, pos, state, neighborState, dir);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getShape(level, pos);
            }
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return super.getOcclusionShape(state);
        }
        return Shapes.empty();
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return RenderShape.MODEL;
        }
        return RenderShape.INVISIBLE;
    }

    @Override
    public boolean hasDynamicLightEmission(BlockState state) {
        return true;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            AuxiliaryLightManager lightManager = level.getAuxLightManager(pos);
            if (lightManager != null) {
                return lightManager.getLightAt(pos);
            }
        }
        return 0;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getShadeBrightness(level, pos);
            }
        }
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        if (!state.getValue(EMPTY)) {
            return super.propagatesSkylightDown(state);
        }
        return true;
    }

    @Override
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getMapColor(level, pos);
            }
        }
        return defaultColor;
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (!state.getValue(EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity && blockEntity.getCopyState() != null) {
                return blockEntity.getCopyState().getSoundType(level, pos, entity);
            }
        }
        return super.getSoundType(state, level, pos, entity);
    }
}
