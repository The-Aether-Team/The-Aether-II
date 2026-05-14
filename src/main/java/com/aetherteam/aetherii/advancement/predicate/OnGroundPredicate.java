package com.aetherteam.aetherii.advancement.predicate;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record OnGroundPredicate() implements EntitySubPredicate {
    public static final MapCodec<OnGroundPredicate> CODEC = MapCodec.unit(new OnGroundPredicate());

    @Override
    public MapCodec<OnGroundPredicate> codec() {
        return AetherIIEntitySubPredicates.ON_GROUND.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return entity.onGround();
    }
}
