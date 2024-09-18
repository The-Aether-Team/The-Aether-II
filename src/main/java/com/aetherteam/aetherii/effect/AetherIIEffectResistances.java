package com.aetherteam.aetherii.effect;

import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AetherIIEffectResistances {
    public static final BiMap<Holder<MobEffect>, Holder<Attribute>> RESISTANCES = new ImmutableBiMap.Builder<Holder<MobEffect>, Holder<Attribute>>()
            .put(AetherIIEffects.WOUND, AetherIIAttributes.WOUND_EFFECT_RESISTANCE)
            .put(AetherIIEffects.STUN, AetherIIAttributes.STUN_EFFECT_RESISTANCE)
            .put(AetherIIEffects.FRACTURE, AetherIIAttributes.FRACTURE_EFFECT_RESISTANCE)
            .put(AetherIIEffects.AMBROSIUM_POISONING, AetherIIAttributes.AMBROSIUM_POISONING_EFFECT_RESISTANCE)
            .put(AetherIIEffects.TOXIN, AetherIIAttributes.TOXIN_EFFECT_RESISTANCE)
            .put(AetherIIEffects.VENOM, AetherIIAttributes.VENOM_EFFECT_RESISTANCE)
            .put(AetherIIEffects.CHARGED, AetherIIAttributes.CHARGED_EFFECT_RESISTANCE)
            .put(AetherIIEffects.WEBBED, AetherIIAttributes.WEBBED_EFFECT_RESISTANCE)
            .put(AetherIIEffects.IMMOLATION, AetherIIAttributes.IMMOLATION_EFFECT_RESISTANCE)
            .put(AetherIIEffects.FROSTBITE, AetherIIAttributes.FROSTBITE_EFFECT_RESISTANCE)
            .put(AetherIIEffects.FUNGAL_ROT, AetherIIAttributes.FUNGAL_ROT_EFFECT_RESISTANCE)
            .put(AetherIIEffects.CRYSTALLIZED, AetherIIAttributes.CRYSTALLIZED_EFFECT_RESISTANCE)
            .build();
}
