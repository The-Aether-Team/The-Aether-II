package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record DungeonEntranceConfiguration(ResourceLocation path, int xOffset, int zOffset) implements FeatureConfiguration {
    public static final Codec<DungeonEntranceConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("path").forGetter(DungeonEntranceConfiguration::path),
            Codec.intRange(-64, 64).fieldOf("x_offset").forGetter(DungeonEntranceConfiguration::xOffset),
            Codec.intRange(-64, 64).fieldOf("z_offset").forGetter(DungeonEntranceConfiguration::zOffset)
    ).apply(instance, DungeonEntranceConfiguration::new));
}