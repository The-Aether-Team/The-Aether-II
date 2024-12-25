package com.aetherteam.aetherii.item.consumeeffect;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public record PreventStatusEffectConsumeEffect(Map<Holder<MobEffect>, Integer> effects) implements ConsumeEffect {
    public static final MapCodec<PreventStatusEffectConsumeEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.unboundedMap(BuiltInRegistries.MOB_EFFECT.holderByNameCodec(), Codec.INT).fieldOf("effects").forGetter(PreventStatusEffectConsumeEffect::effects)
    ).apply(instance, PreventStatusEffectConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PreventStatusEffectConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT), ByteBufCodecs.INT),
            PreventStatusEffectConsumeEffect::effects,
            PreventStatusEffectConsumeEffect::new);

    @Override
    public Type<PreventStatusEffectConsumeEffect> getType() {
        return AetherIIConsumeEffectTypes.REDUCE_EFFECT_BUILDUP.get();
    }

    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        boolean flag = false;
        for (Map.Entry<Holder<MobEffect>, Integer> entry : this.effects().entrySet()) {
            livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).reduceBuildup(entry.getKey(), entry.getValue());
            flag = true;
        }
        return flag;
    }
}
