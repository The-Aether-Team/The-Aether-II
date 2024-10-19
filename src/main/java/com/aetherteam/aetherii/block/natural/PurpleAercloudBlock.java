package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PurpleAercloudBlock extends AercloudBlock {
    public static final Direction[] DIRECTIONS =  new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    public static final DirectionProperty FACING = DirectionProperty.create("facing", DIRECTIONS);
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public PurpleAercloudBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.SOUTH));
    }

    /**
     * Launches the entity inside the block in the same direction as the block is facing and resets their fall distance when not holding the shift key.
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
            switch (state.getValue(FACING)) {
                case EAST -> entity.setDeltaMovement(2.0, entity.getDeltaMovement().y(), entity.getDeltaMovement().z());
                case WEST -> entity.setDeltaMovement(-2.0, entity.getDeltaMovement().y(), entity.getDeltaMovement().z());
                case NORTH -> entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y(), -2.0);
                case SOUTH -> entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y(), 2.0);
            }
            level.playSound((entity instanceof Player player ? player : null), pos, AetherIISoundEvents.BLOCK_PURPLE_AERCLOUD_BOUNCE.get(), SoundSource.BLOCKS, 0.8F, 0.5F + (((float) (Math.pow(level.getRandom().nextDouble(), 2.5))) * 0.5F));
        } else {
            super.entityInside(state, level, pos, entity);
        }
    }

    /**
     * Shoots white smoke particles with a velocity in the same direction as the block is facing.
     * @param state The {@link BlockState} of the block.
     * @param level The {@link Level} the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @param random The {@link RandomSource} of the level.
     */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double xOffset = (double) pos.getX() + 0.5F;
        double yOffset = (double) pos.getY() + 0.5F;
        double zOffset = (double) pos.getZ() + 0.5F;
        double xSpeed = 0.0;
        double ySpeed = 0.0;
        double zSpeed = 0.0;

        switch (state.getValue(FACING)) {
            case EAST -> {
                xOffset += 0.5F;
                xSpeed = 1.0;
            }
            case WEST -> {
                xOffset -= 0.5F;
                xSpeed = -1.0;
            }
            case NORTH -> {
                zOffset -= 0.5F;
                zSpeed = -1.0;
            }
            case SOUTH -> {
                zOffset += 0.5F;
                zSpeed = 1.0;
            }
        }
        level.addParticle(ParticleTypes.POOF, xOffset, yOffset, zOffset, xSpeed, ySpeed, zSpeed);
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }
}