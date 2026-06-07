package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AercloudBlock extends HalfTransparentBlock implements LiquidBlockContainer {
    protected static final VoxelShape COLLISION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 0.01, 16.0);
    protected static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.9, 1.0);
    protected static final VoxelShape ITEM_COLLISION_SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.65, 1.0);

    public AercloudBlock(Properties properties) {
        super(properties);
    }

    /**
     * Slows an entity's movement and resets their fall damage when inside an Aercloud block.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to override.
     *
     * @param state  The {@link BlockState} of the block.
     * @param level  The {@link Level} the block is in.
     * @param pos    The {@link BlockPos} of the block.
     * @param entity The {@link Entity} in the block.
     */
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean p_451772_) {
        entity.resetFallDistance();
        if (!(entity instanceof ItemEntity itemEntity)) {
            if (entity.getDeltaMovement().y < -0.0784000015258789) {
                if (!(entity instanceof Projectile) && !(entity instanceof CloudSkiff)) {
                    entity.setDeltaMovement(entity.getDeltaMovement().x(), -0.08, entity.getDeltaMovement().z());
                }
                entity.setOnGround(entity instanceof LivingEntity livingEntity && (!(livingEntity instanceof Player player) || !player.getAbilities().flying));
            }
        } else {
            itemEntity.setDeltaMovement(entity.getDeltaMovement().scale(0.99F));
        }
    }

    public void runAercloudEffect(BlockState state, Level level, BlockPos pos, Entity entity) {

    }

    /**
     * This block does not cause fall damage, so this method is overridden from {@link Block#fallOn(Level, BlockState, BlockPos, Entity, double)} to be empty.
     *
     * @param level        The {@link Level} the block is in.
     * @param state        The {@link BlockState} of the block.
     * @param pos          The {@link BlockPos} of the block.
     * @param entity       The {@link Entity} that fell on the block.
     * @param fallDistance The fall distance of the entity as a {@link Float}.
     */
    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.AbstractGlassBlock#propagatesSkylightDown(BlockState, BlockGetter, BlockPos)}.
     */
    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return false;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.AbstractGlassBlock#getShadeBrightness(BlockState, BlockGetter, BlockPos)}.<br><br>
     */
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 0.25F;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.PowderSnowBlock#getCollisionShape(BlockState, BlockGetter, BlockPos, CollisionContext)}.<br><br>
     * Resolves a quirk with fall behavior where an entity will still receive fall damage if falling fast enough into a block with a shape like {@link AercloudBlock#COLLISION_SHAPE},
     * even if the fall damage should be negated.<br><br>
     *
     * @param state   The {@link BlockState} of the block.
     * @param level   The {@link Level} the block is in.
     * @param pos     The {@link BlockPos} of the block.
     * @param context The {@link CollisionContext} of the entity with the block.
     * @return The collision {@link VoxelShape} of the block.
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!this.getDefaultCollisionShape(state, level, pos, context).isEmpty() && level.getBlockState(pos.above()).getBlock() instanceof AercloudBlock) { // Aerclouds with other Aerclouds above them are solid.
            return Shapes.block();
        }
        if (context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity != null) {
                if (entity instanceof CloudSkiff) {
                    if (context.isAbove(Shapes.block(), pos, true)) {
                        return Shapes.block();
                    } else {
                        return Shapes.empty();
                    }
                } else if (entity instanceof ItemEntity) {
                    return ITEM_COLLISION_SHAPE;
                } else if (entity.fallDistance > 2.5F && (!(entity instanceof LivingEntity livingEntity) || !livingEntity.isFallFlying())) {
                    return FALLING_COLLISION_SHAPE; // Alternate shape when falling fast enough.
                }
            }
        }
        return this.getDefaultCollisionShape(state, level, pos, context); // Default shape.
    }

    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (!(entity instanceof ItemEntity)) {
            return entity instanceof CloudSkiff ? 0.92F : super.getFriction(state, level, pos, entity);
        } else {
            return 0.85F;
        }
    }

    protected VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.level.block.AbstractGlassBlock#getVisualShape(BlockState, BlockGetter, BlockPos, CollisionContext)}.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to override.
     */
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity livingEntity, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return blockGetter.getBlockState(blockPos.above()).is(fluid.defaultFluidState().createLegacyBlock().getBlock());
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @Override
    public boolean isPathfindable(BlockState p_279414_, PathComputationType p_279299_) {
        return false;
    }
}