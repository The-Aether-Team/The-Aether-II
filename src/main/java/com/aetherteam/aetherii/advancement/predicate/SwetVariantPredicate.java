package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.api.SwetVariant;
import com.aetherteam.aetherii.entity.monster.Swet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record SwetVariantPredicate(Holder<SwetVariant> swetVariant) implements EntitySubPredicate {
    public static final MapCodec<SwetVariantPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            SwetVariant.CODEC.fieldOf("variant").forGetter(SwetVariantPredicate::swetVariant)
    ).apply(instance, SwetVariantPredicate::new));

    @Override
    public MapCodec<SwetVariantPredicate> codec() {
        return AetherIIEntitySubPredicates.SWET.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel serverLevel, @Nullable Vec3 vec3) {
        if (entity instanceof Swet swet) {
            return swet.getVariant().is(this.swetVariant());
        }
        return false;
    }
}
