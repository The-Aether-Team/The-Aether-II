package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record EffectBuildupPredicate(Holder<MobEffect> effect, Optional<Integer> buildupLimit) implements EntitySubPredicate {
    public static final MapCodec<EffectBuildupPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect").forGetter(EffectBuildupPredicate::effect),
            Codec.INT.optionalFieldOf("buildup_limit").forGetter(EffectBuildupPredicate::buildupLimit)
    ).apply(instance, EffectBuildupPredicate::new));

    @Override
    public MapCodec<EffectBuildupPredicate> codec() {
        return AetherIIEntitySubPredicates.EFFECT_BUILDUP.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        EffectBuildupInstance instance = entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).getActiveBuildups().get(this.effect());
        if (instance != null) {
            if (this.buildupLimit().isPresent()) {
                return instance.getBuildup() >= this.buildupLimit().get();
            }
            return true;
        }
        return false;
    }
}
