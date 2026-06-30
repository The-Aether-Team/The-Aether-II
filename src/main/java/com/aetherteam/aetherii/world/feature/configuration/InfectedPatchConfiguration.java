package com.aetherteam.aetherii.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record InfectedPatchConfiguration(TagKey<Block> replaceable, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance) implements FeatureConfiguration {
    public static final Codec<InfectedPatchConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter(InfectedPatchConfiguration::replaceable),
            PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(InfectedPatchConfiguration::vegetationFeature),
            CaveSurface.CODEC.fieldOf("surface").forGetter(InfectedPatchConfiguration::surface),
            IntProvider.codec(1, 128).fieldOf("depth").forGetter(InfectedPatchConfiguration::depth),
            Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(InfectedPatchConfiguration::extraBottomBlockChance),
            Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(InfectedPatchConfiguration::verticalRange),
            Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(InfectedPatchConfiguration::vegetationChance),
            IntProvider.CODEC.fieldOf("xz_radius").forGetter(InfectedPatchConfiguration::xzRadius),
            Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(InfectedPatchConfiguration::extraEdgeColumnChance)
    ).apply(instance, InfectedPatchConfiguration::new));
}