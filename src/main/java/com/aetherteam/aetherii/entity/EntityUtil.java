package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.mixin.mixins.common.accessor.EntityAccessor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.Optional;

public final class EntityUtil {
    /**
     * Copies rotation values from one entity to another.
     *
     * @param entity The {@link Entity} to copy rotations to.
     * @param source The {@link Entity} to copy rotations from.
     */
    public static void copyRotations(Entity entity, Entity source) {
        entity.setYRot((float) Mth.rotLerp(1.0 / 3.0, source.getYRot(), source.yRotO));
        entity.setXRot((float) Mth.rotLerp(1.0 / 3.0, source.getXRot(), source.xRotO));
        entity.setYBodyRot((float) Mth.rotLerp(1.0 / 3.0, source.getYRot(), source.yRotO));
        entity.setYHeadRot((float) Mth.rotLerp(1.0 / 3.0, source.getYRot(), source.yRotO));
    }

    /**
     * Spawns explosion particles used for various entity movement effects.
     *
     * @param entity The {@link Entity} to spawn the particles for.
     */
    public static void spawnMovementExplosionParticles(Entity entity) {
        RandomSource random = ((EntityAccessor) entity).aether_ii$getRandom();
        double d0 = random.nextGaussian() * 0.02;
        double d1 = random.nextGaussian() * 0.02;
        double d2 = random.nextGaussian() * 0.02;
        double d3 = 10.0;
        double x = entity.getX() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d0 * d3;
        double y = entity.getY() + ((double) random.nextFloat() * entity.getBbHeight()) - d1 * d3;
        double z = entity.getZ() + ((double) random.nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d2 * d3;
        entity.level().addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T clone(final T entity) {
        CompoundTag compoundTag = new CompoundTag();
        entity.save(compoundTag);
        final Optional<T> newEnt = (Optional<T>) EntityType.create(compoundTag, entity.level());

        return newEnt.orElse(null);
    }
}
