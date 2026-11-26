package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record AetherVegetationPatchConfiguration(TagKey<Block> replaceable, BlockStateProvider groundState, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance) implements FeatureConfiguration {
    public static final Codec<AetherVegetationPatchConfiguration> CODEC = RecordCodecBuilder.create((i) -> i.group(
            TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter(AetherVegetationPatchConfiguration::replaceable),
            BlockStateProvider.CODEC.fieldOf("ground_state").forGetter(AetherVegetationPatchConfiguration::groundState),
            PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(AetherVegetationPatchConfiguration::vegetationFeature),
            CaveSurface.CODEC.fieldOf("surface").forGetter(AetherVegetationPatchConfiguration::surface),
            IntProviders.codec(1, 128).fieldOf("depth").forGetter(AetherVegetationPatchConfiguration::depth),
            Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(AetherVegetationPatchConfiguration::extraBottomBlockChance),
            Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(AetherVegetationPatchConfiguration::verticalRange),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(AetherVegetationPatchConfiguration::vegetationChance),
            IntProviders.CODEC.fieldOf("xz_radius").forGetter(AetherVegetationPatchConfiguration::xzRadius),
            Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(AetherVegetationPatchConfiguration::extraEdgeColumnChance)
    ).apply(i, AetherVegetationPatchConfiguration::new));
}
