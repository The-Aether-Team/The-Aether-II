package com.aetherteam.aetherii.block.utility;

import com.aetherteam.aetherii.blockentity.AnimalStashBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AnimalStashBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape SHAPE_BASE = Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
    protected static final VoxelShape SHAPE_MIDDLE = Block.box(1.0, 3.0, 1.0, 1.0, 8.0, 1.0);
    protected static final VoxelShape SHAPE_TOP = Block.box(2.0, 8.0, 2.0, 2.0, 9.0, 2.0);
    protected static final VoxelShape SHAPE = Shapes.or(SHAPE_BASE, SHAPE_MIDDLE, SHAPE_TOP);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
public AnimalStashBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false).setValue(WATERLOGGED, false));
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hitResult) {
        if (level instanceof ServerLevel) {
            if(!state.getValue(OPEN)) {
                level.setBlock(pos, state.setValue(OPEN, true), 2);
            }
            if (level.getBlockEntity(pos) instanceof AnimalStashBlockEntity blockEntity) {
                player.openMenu(blockEntity);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnimalStashBlockEntity(pos, state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
