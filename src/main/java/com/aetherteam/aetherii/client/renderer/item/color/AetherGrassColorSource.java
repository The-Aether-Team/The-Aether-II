package com.aetherteam.aetherii.client.renderer.item.color;

import com.aetherteam.aetherii.client.AetherIIColorResolvers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record AetherGrassColorSource(int tintIndex, int defaultColor, float darkSaturationOffset, float lightSaturationOffset) implements ItemTintSource {
    public static final MapCodec<AetherGrassColorSource> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.INT.fieldOf("tintIndex").forGetter(AetherGrassColorSource::tintIndex),
            Codec.INT.fieldOf("defaultColor").forGetter(AetherGrassColorSource::defaultColor),
            Codec.FLOAT.fieldOf("darkSaturationOffset").forGetter(AetherGrassColorSource::darkSaturationOffset),
            Codec.FLOAT.fieldOf("lightSaturationOffset").forGetter(AetherGrassColorSource::lightSaturationOffset)
    ).apply(instance, AetherGrassColorSource::new));

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity livingEntity) {
        return AetherIIColorResolvers.createTriTintGrassColor(this.tintIndex(), this.defaultColor(), this.darkSaturationOffset(), this.lightSaturationOffset());
    }

    @Override
    public MapCodec<AetherGrassColorSource> type() {
        return MAP_CODEC;
    }
}
