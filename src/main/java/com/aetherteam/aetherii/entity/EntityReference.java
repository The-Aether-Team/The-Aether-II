package com.aetherteam.aetherii.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record EntityReference<T extends Entity>(UUID uuid) {
    public static <T extends Entity> EntityReference<T> of(UUID uuid) {
        return new EntityReference<>(uuid);
    }

    public static <T extends Entity> EntityReference<T> of(T entity) {
        return new EntityReference<>(entity.getUUID());
    }

    public static <T extends Entity> EntityReference<T> of(EntityReference<T> reference) {
        return new EntityReference<>(reference.uuid());
    }

    public UUID getUUID() {
        return this.uuid();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public T getEntity(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.uuid);
            return entity == null ? null : (T) entity;
        }
        return null;
    }

    @Nullable
    public <E extends Entity> E getEntity(Level level, Class<E> entityClass) {
        T entity = this.getEntity(level);
        return entityClass.isInstance(entity) ? entityClass.cast(entity) : null;
    }
}
