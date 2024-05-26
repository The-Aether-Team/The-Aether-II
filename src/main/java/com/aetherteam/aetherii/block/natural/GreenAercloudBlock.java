package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GreenAercloudBlock extends AercloudBlock {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public GreenAercloudBlock(Properties properties) {
        super(properties);
    }

    /**
     * Launches the entity inside the block in a random horizontal direction and resets their fall distance when not holding the shift key.
     * If they are holding the shift key, behavior defaults to that of {@link AercloudBlock#entityInside(BlockState, Level, BlockPos, Entity)}.
     * @param state The {@link BlockState} of the block.
     * @param level The {@link Level} the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @param entity The {@link Entity} in the block.
     */
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isShiftKeyDown()) {
            entity.resetFallDistance();
            int direction = level.getRandom().nextInt(4);
            switch (direction) {
                case 0 -> entity.setDeltaMovement(2.0, entity.getDeltaMovement().y(), 0);
                case 1 -> entity.setDeltaMovement(-2.0, entity.getDeltaMovement().y(), 0);
                case 2 -> entity.setDeltaMovement(0, entity.getDeltaMovement().y(), 2.0);
                case 3 -> entity.setDeltaMovement(0, entity.getDeltaMovement().y(), -2.0);
            }
        } else {
            super.entityInside(state, level, pos, entity);
        }
    }

    @Override
    public VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }
}