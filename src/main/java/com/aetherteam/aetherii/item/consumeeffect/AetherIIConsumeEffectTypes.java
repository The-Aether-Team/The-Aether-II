package com.aetherteam.aetherii.item.consumeeffect;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIConsumeEffectTypes {
    public static final DeferredRegister<ConsumeEffect.Type<?>> CONSUME_EFFECT_TYPE = DeferredRegister.create(BuiltInRegistries.CONSUME_EFFECT_TYPE, AetherII.MODID);

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<PreventStatusEffectConsumeEffect>> REDUCE_EFFECT_BUILDUP = CONSUME_EFFECT_TYPE.register("reduce_effect_buildup", () -> new ConsumeEffect.Type<>(PreventStatusEffectConsumeEffect.CODEC, PreventStatusEffectConsumeEffect.STREAM_CODEC));
}
