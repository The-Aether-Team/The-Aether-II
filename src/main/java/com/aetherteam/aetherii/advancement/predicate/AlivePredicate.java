package com.aetherteam.aetherii.advancement.predicate;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record AlivePredicate() implements EntitySubPredicate {
    public static final MapCodec<AlivePredicate> CODEC = MapCodec.unit(new AlivePredicate());

    @Override
    public MapCodec<AlivePredicate> codec() {
        return AetherIIEntitySubPredicates.ALIVE.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return entity.isAlive();
    }
}
