package com.aetherteam.aetherii.client.renderer.item.color;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record EffectBuildupColorSource(int defaultColor) implements ItemTintSource {
    public static final MapCodec<EffectBuildupColorSource> MAP_CODEC = RecordCodecBuilder.mapCodec((color) -> color.group(
            ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(EffectBuildupColorSource::defaultColor)
    ).apply(color, EffectBuildupColorSource::new));

    public EffectBuildupColorSource() {
        this(BuildupContents.DEFAULT_COLOR);
    }

    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity livingEntity) {
        BuildupContents buildupContents = stack.get(AetherIIDataComponents.BUILDUP_CONTENTS.get());
        return buildupContents != null ? ARGB.opaque(buildupContents.getColor()) : ARGB.opaque(this.defaultColor);
    }

    public MapCodec<EffectBuildupColorSource> type() {
        return MAP_CODEC;
    }
}
