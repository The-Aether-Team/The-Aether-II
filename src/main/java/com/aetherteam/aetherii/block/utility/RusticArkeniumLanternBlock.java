package com.aetherteam.aetherii.block.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RusticArkeniumLanternBlock extends LanternBlock {

    private static final VoxelShape SHAPE_STANDING = Shapes.or(Block.box(4, 0.0, 4, 12, 1.0, 12), Block.box(5, 1.0, 5, 11, 9.0, 11), Block.box(4, 7.0, 4, 12, 8.0, 12), Block.box(6, 9.0, 6, 10, 12.0, 10));
    private static final VoxelShape SHAPE_HANGING = SHAPE_STANDING.move(0.0, 0.0625, 0.0).optimize();

    public RusticArkeniumLanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? SHAPE_HANGING : SHAPE_STANDING;
    }
}