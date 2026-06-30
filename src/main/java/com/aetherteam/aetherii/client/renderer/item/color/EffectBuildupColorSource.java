package com.aetherteam.aetherii.client.renderer.item.color;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.aetherteam.aetherii.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

public record EffectBuildupColorSource(int defaultColor) {
    public static final MapCodec<EffectBuildupColorSource> MAP_CODEC = RecordCodecBuilder.mapCodec((color) -> color.group(
            ExtraCodecs.intRange(0, 0xFFFFFF).fieldOf("default").forGetter(EffectBuildupColorSource::defaultColor)
    ).apply(color, EffectBuildupColorSource::new));

    public EffectBuildupColorSource() {
        this(BuildupContents.DEFAULT_COLOR);
    }

    public int calculate(ItemStack stack) {
        BuildupContents buildupContents = AetherIIDataComponents.get(stack, AetherIIDataComponents.BUILDUP_CONTENTS);
        return buildupContents != null ? ARGB.opaque(buildupContents.getColor()) : ARGB.opaque(this.defaultColor);
    }
}
