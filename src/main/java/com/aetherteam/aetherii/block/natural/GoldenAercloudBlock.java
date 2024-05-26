package com.aetherteam.aetherii.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GoldenAercloudBlock extends AercloudBlock {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public GoldenAercloudBlock(Properties properties) {
        super(properties);
    }

    /**
     * If they are holding the shift key, behavior defaults to that of {@link AercloudBlock#entityInside(BlockState, Level, BlockPos, Entity)}.
     *
     * @param state  The {@link BlockState} of the block.
     * @param level  The {@link Level} the block is in.
     * @param pos    The {@link BlockPos} of the block.
     * @param entity The {@link Entity} in the block.
     */
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isShiftKeyDown() && (!entity.isVehicle() || !(entity.getControllingPassenger() instanceof Player))) {
            entity.resetFallDistance();
            entity.setDeltaMovement(entity.getDeltaMovement().x(), -2.0, entity.getDeltaMovement().z());
            if (!(entity instanceof Projectile)) {
                entity.setOnGround(false);
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