package com.aetherteam.aetherii.block.dungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LargeShelfRotshroomBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0, 5.0, 0.0, 16.0, 11.0, 16.0);

    public LargeShelfRotshroomBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}