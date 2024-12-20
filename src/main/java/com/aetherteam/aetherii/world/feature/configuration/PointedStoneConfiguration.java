package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PointedStoneConfiguration(BlockStateProvider stoneBlock, BlockStateProvider pointedStoneBlock, float chanceOfTallerDripstone, float chanceOfDirectionalSpread, float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) implements FeatureConfiguration {
    public static final Codec<PointedStoneConfiguration> CODEC = RecordCodecBuilder.create((p_191286_) -> p_191286_.group(
            BlockStateProvider.CODEC.fieldOf("stone_block").forGetter(PointedStoneConfiguration::stoneBlock),
            BlockStateProvider.CODEC.fieldOf("pointed_stone_block").forGetter(PointedStoneConfiguration::pointedStoneBlock),
            Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter(PointedStoneConfiguration::chanceOfTallerDripstone),
            Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter(PointedStoneConfiguration::chanceOfDirectionalSpread),
            Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter(PointedStoneConfiguration::chanceOfSpreadRadius2),
            Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter(PointedStoneConfiguration::chanceOfSpreadRadius3)
    ).apply(p_191286_, PointedStoneConfiguration::new));
}
