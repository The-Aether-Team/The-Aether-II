package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record BuildupContents(EffectBuildupPresets.Preset preset, int amount) implements TooltipProvider {
    public static final Codec<BuildupContents> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EffectBuildupPresets.Preset.CODEC.fieldOf("preset").forGetter(BuildupContents::preset),
            Codec.INT.fieldOf("amount").forGetter(BuildupContents::amount)
    ).apply(instance, BuildupContents::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BuildupContents> STREAM_CODEC = StreamCodec.composite(
            EffectBuildupPresets.Preset.STREAM_CODEC, BuildupContents::preset,
            ByteBufCodecs.INT, BuildupContents::amount,
            BuildupContents::new);
    public static final int DEFAULT_COLOR = -13083194;

    public BuildupContents(EffectBuildupPresets.Preset preset) {
        this(preset, 20);
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        MobEffectInstance instance = this.preset().createMobEffectInstance();
        Holder<MobEffect> holder = instance.getEffect();
        int i = instance.getAmplifier();
        MutableComponent buildup = Component.translatable("aether_ii.tooltip.item.effect_buildup", PotionContents.getPotionDescription(holder, i));
        Component full = Component.translatable("aether_ii.tooltip.item.effect_buildup.inflicts", buildup.withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GOLD);
        consumer.accept(full);
    }

    public Component getName(String name) {
        ResourceLocation location = BuiltInRegistries.MOB_EFFECT.getKey(this.preset().type().value());
        if (location != null) {
            return Component.translatable(name + location.getPath());
        }
        return Component.translatable(name + ".invalid");
    }

    public int getColor() {
        int i1 = this.preset.type().value().getColor();
        int j1 = this.preset.amplifier() + 1;
        int i = j1 * ARGB.red(i1);
        int j = j1 * ARGB.green(i1);
        int k = j1 * ARGB.blue(i1);
        return ARGB.color(i / j1, j / j1, k / j1);
    }
}
