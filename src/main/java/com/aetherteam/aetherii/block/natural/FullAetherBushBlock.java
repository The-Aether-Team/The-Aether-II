package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FullAetherBushBlock extends AetherBushBlock {
    public FullAetherBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context.isAbove(Shapes.block(), pos, true) && !context.isDescending()) {
            return Shapes.block();
        } else {
            return Shapes.empty();
        }
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75F, 0.8F));
            if (entity.getX() != entity.xOld && entity.getZ() != entity.zOld) {
                if (level.getRandom().nextInt(10) == 0) {
                    level.playSound(null, pos, SoundEvents.CAVE_VINES_STEP, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
                }
                int count = entity.isCrouching() ? 5 : 15;
                this.spawnParticles(level, pos, count);
            }
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!entity.isCrouching() && entity.getX() != entity.xOld && entity.getZ() != entity.zOld) {
            this.spawnParticles(level, pos, 5);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, entity.damageSources().fall());
        this.spawnParticles(level, pos, 50);
    }

    private void spawnParticles(Level level, BlockPos pos, int count) {
        Vec3 vec3 = Vec3.atCenterOf(pos);
        for (int j = 0; j < count; ++j) {
            double d0 = vec3.x + Mth.nextDouble(level.getRandom(), -0.6, 0.6);
            double d1 = vec3.y + Mth.nextDouble(level.getRandom(), -0.6, 0.6);
            double d2 = vec3.z + Mth.nextDouble(level.getRandom(), -0.6, 0.6);
            double d3 = Mth.nextDouble(level.getRandom(), -0.6, 0.6);
            double d4 = Mth.nextDouble(level.getRandom(), -0.6, 0.6);
            double d5 = Mth.nextDouble(level.getRandom(), -0.6, 0.6);
            level.addParticle(AetherIIParticleTypes.SKYROOT_LEAVES.get(), d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}
