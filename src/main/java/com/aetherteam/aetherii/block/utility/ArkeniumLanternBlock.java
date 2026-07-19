package com.aetherteam.aetherii.block.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArkeniumLanternBlock extends LitLanternBlock {
    private static final VoxelShape SHAPE_STANDING = Shapes.or(Block.column(4.0, 0.0, 11.0), Block.column(6.0, 1.0, 8.0), Block.column(8.0, 8.0, 9.0));
    private static final VoxelShape SHAPE_HANGING = SHAPE_STANDING.move(0.0, 0.0625, 0.0).optimize();

    public ArkeniumLanternBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HANGING, false).setValue(LIT, true).setValue(WATERLOGGED, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? SHAPE_HANGING : SHAPE_STANDING;
    }
}