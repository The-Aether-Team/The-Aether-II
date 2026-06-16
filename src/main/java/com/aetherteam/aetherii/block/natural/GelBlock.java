package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class GelBlock extends HalfTransparentBlock {
    public static final MapCodec<GelBlock> CODEC = simpleCodec(GelBlock::new);
    private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 15.0);

    @Override
    public MapCodec<GelBlock> codec() {
        return CODEC;
    }

    public GelBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        if (other.getBlock() == Blocks.SLIME_BLOCK || other.getBlock() == Blocks.HONEY_BLOCK) {
            return false;
        }
        return super.canStickTo(state, other);
    }

    @Override
    public PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.STICKY_HONEY;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        entity.playSound(AetherIISoundEvents.BLOCK_GEL_SLIDE.get(), 1.0F, 1.0F);
        if (level instanceof ServerLevel serverLevel) {
            this.showParticles(serverLevel, state, entity, 10);
        }
        if (entity.causeFallDamage(fallDistance, 0.2F, level.damageSources().fall())) {
            entity.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (this.isSlidingDown(pos, entity)) {
            this.maybeDoSlideAchievement(entity, pos);
            this.doSlideMovement(entity);
            this.maybeDoSlideEffects(level, state, entity);
        }
        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }

    private boolean isSlidingDown(BlockPos pos, Entity entity) {
        if (entity.onGround()) {
            return false;
        } else if (entity.getY() > pos.getY() + 0.9375F - 1.0E-7) {
            return false;
        } else if (getOldDeltaY(entity.getDeltaMovement().y()) >= -0.08) {
            return false;
        } else {
            double dx = Math.abs(pos.getX() + 0.5F - entity.getX());
            double dz = Math.abs(pos.getZ() + 0.5F - entity.getZ());
            double overlapDistance = 0.4375F + (entity.getBbWidth() / 2.0F);
            return dx + 1.0E-7 > overlapDistance || dz + 1.0E-7 > overlapDistance;
        }
    }

    private static double getOldDeltaY(double deltaY) {
        return deltaY / 0.98F + 0.08;
    }

    private static double getNewDeltaY(double deltaY) {
        return (deltaY - 0.08) * 0.98F;
    }

    private void maybeDoSlideAchievement(Entity entity, BlockPos pos) {
        if (entity instanceof ServerPlayer serverPlayer && entity.level().getGameTime() % 20L == 0L) {
            CriteriaTriggers.HONEY_BLOCK_SLIDE.trigger(serverPlayer, entity.level().getBlockState(pos));
        }
    }

    private void doSlideMovement(Entity entity) {
        Vec3 deltaMovement = entity.getDeltaMovement();
        if (getOldDeltaY(entity.getDeltaMovement().y()) < -0.13) {
            double horizontalReductionFactor = -0.05 / getOldDeltaY(entity.getDeltaMovement().y());
            entity.setDeltaMovement(new Vec3(deltaMovement.x() * horizontalReductionFactor, getNewDeltaY(-0.05), deltaMovement.z() * horizontalReductionFactor));
        } else {
            entity.setDeltaMovement(new Vec3(deltaMovement.x(), getNewDeltaY(-0.05), deltaMovement.z()));
        }
        entity.resetFallDistance();
    }

    private void maybeDoSlideEffects(Level level, BlockState state, Entity entity) {
        if (doesEntityDoSlideEffects(entity)) {
            RandomSource random = level.getRandom();
            if (random.nextInt(5) == 0) {
                entity.playSound(AetherIISoundEvents.BLOCK_GEL_SLIDE.get(), 1.0F, 1.0F);
            }
            if (level instanceof ServerLevel serverLevel && random.nextInt(5) == 0) {
                this.showParticles(serverLevel, state, entity, 5);
            }
        }
    }

    private void showParticles(ServerLevel serverLevel, BlockState state, Entity entity, int count) {
        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), entity.getX(), entity.getY(), entity.getZ(), count, 0, 0, 0, 0);
    }

    private static boolean doesEntityDoSlideEffects(Entity entity) {
        return entity instanceof LivingEntity || entity instanceof AbstractMinecart || entity instanceof PrimedTnt || entity instanceof AbstractBoat;
    }
}
