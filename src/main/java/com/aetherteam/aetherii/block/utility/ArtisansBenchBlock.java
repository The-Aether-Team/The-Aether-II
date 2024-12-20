package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.inventory.menu.ArtisansBenchMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ArtisansBenchBlock extends Block {
    public static final MapCodec<ArtisansBenchBlock> CODEC = simpleCodec(ArtisansBenchBlock::new);
    private static final Component CONTAINER_TITLE = Component.translatable("menu.aether_ii.artisans_bench");
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape SHAPE_BASE = Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
    protected static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(0.0, 13.0, 14.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 13.0, 0.0, 2.0, 16.0, 14.0),
            Block.box(14.0, 13.0, 0.0, 16.0, 16.0, 14.0),
            SHAPE_BASE);
    protected static final VoxelShape SHAPE_SOUTH = Shapes.or(
            Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 2.0),
            Block.box(0.0, 13.0, 2.0, 2.0, 16.0, 16.0),
            Block.box(14.0, 13.0, 2.0, 16.0, 16.0, 16.0),
            SHAPE_BASE);
    protected static final VoxelShape SHAPE_EAST = Shapes.or(
            Block.box(0.0, 13.0, 0.0, 2.0, 16.0, 16.0),
            Block.box(2.0, 13.0, 0.0, 16.0, 16.0, 2.0),
            Block.box(2.0, 13.0, 14.0, 16.0, 16.0, 16.0),
            SHAPE_BASE);
    protected static final VoxelShape SHAPE_WEST = Shapes.or(
            Block.box(14.0, 13.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 13.0, 0.0, 14.0, 16.0, 2.0),
            Block.box(0.0, 13.0, 14.0, 14.0, 16.0, 16.0),
            SHAPE_BASE);

    @Override
    public MapCodec<ArtisansBenchBlock> codec() {
        return CODEC;
    }

    public ArtisansBenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) -> new ArtisansBenchMenu(id, inventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
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
        builder.add(FACING);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
