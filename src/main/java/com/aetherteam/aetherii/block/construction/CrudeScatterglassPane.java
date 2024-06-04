package com.aetherteam.aetherii.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrudeScatterglassPane extends IronBarsBlock {
    public CrudeScatterglassPane(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getVisualShape(BlockState arRW, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.getCollisionShape(arRW, level, pos, context);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isCollisionShapeFullBlock(level, pos) ? 0.2F : 1.0F;
    }
}
