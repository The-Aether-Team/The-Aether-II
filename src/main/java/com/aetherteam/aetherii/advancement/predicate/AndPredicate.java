package com.aetherteam.aetherii.advancement.predicate;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record AndPredicate(EntitySubPredicate first, EntitySubPredicate second) implements EntitySubPredicate {
    public static final MapCodec<AndPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            EntitySubPredicate.CODEC.fieldOf("first").forGetter(AndPredicate::first),
            EntitySubPredicate.CODEC.fieldOf("second").forGetter(AndPredicate::second)
    ).apply(instance, AndPredicate::new));

    @Override
    public MapCodec<AndPredicate> codec() {
        return AetherIIEntitySubPredicates.AND.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        return this.first().matches(entity, level, position) && this.second().matches(entity, level, position);
    }
}
