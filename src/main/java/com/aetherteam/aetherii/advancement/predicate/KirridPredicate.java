package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record KirridPredicate(Optional<Boolean> sheared, Optional<Kirrid.KirridColor> color) implements EntitySubPredicate {
    public static final MapCodec<KirridPredicate> CODEC = RecordCodecBuilder.mapCodec((p_368666_) -> p_368666_.group(
            Codec.BOOL.optionalFieldOf("sheared").forGetter(KirridPredicate::sheared),
            Kirrid.KirridColor.CODEC.optionalFieldOf("color").forGetter(KirridPredicate::color)
    ).apply(p_368666_, KirridPredicate::new));

    @Override
    public MapCodec<KirridPredicate> codec() {
        return AetherIIEntitySubPredicates.KIRRID.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel serverLevel, @Nullable Vec3 vec3) {
        if (entity instanceof Kirrid kirrid) {
            if (this.sheared.isPresent() && kirrid.isSheared() != this.sheared.get()) {
                return false;
            } else if (this.color.isPresent() && kirrid.getColor().isPresent()) {
                return this.color.get() == kirrid.getColor().get();
            } else {
                return this.color.isEmpty() && kirrid.getColor().isEmpty();
            }
        }
        return false;
    }

    public static KirridPredicate hasWool(Kirrid.KirridColor color) {
        return new KirridPredicate(Optional.of(false), Optional.of(color));
    }
}
