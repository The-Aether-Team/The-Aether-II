package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class HighlandsSurfaceBuilders {
    private static final SurfaceRules.RuleSource WATER = SurfaceRules.state(Blocks.WATER.defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource HOLYSTONE = SurfaceRules.state(AetherIIBlocks.HOLYSTONE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource UNDERSHALE = SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_SNOW_BLOCK = SurfaceRules.state(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_ICE = SurfaceRules.state(AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());

    public static SurfaceRules.RuleSource surfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), AETHER_GRASS_BLOCK), AETHER_DIRT);
        return SurfaceRules.sequence(

                lakeHolystone(0.08D, 113),
                lakeHolystone(0.175D, 114),
                lakeHolystone(0.25D, 115),
                lakeHolystone(0.3D, 116),
                lakeHolystone(0.35D, 117),
                lakeHolystone(0.39D, 118),
                lakeHolystone(0.43D, 119),
                lakeHolystone(0.47D, 120),
                lakeHolystone(0.5D, 121),
                lakeHolystone(0.6D, 122),
                lakeHolystone(0.75D, 123),

                lakeLayer(0.08D, 113),
                lakeLayer(0.175D, 114),
                lakeLayer(0.25D, 115),
                lakeLayer(0.3D, 116),
                lakeLayer(0.35D, 117),
                lakeLayer(0.39D, 118),
                lakeLayer(0.43D, 119),
                lakeLayer(0.47D, 120),
                lakeLayer(0.5D, 121),
                lakeLayer(0.6D, 122),
                lakeLayer(0.75D, 123),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN, AetherIIBiomes.FROZEN_LAKES),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_bottom", VerticalAnchor.absolute(112), VerticalAnchor.absolute(112)), AETHER_DIRT)),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_top", VerticalAnchor.absolute(124), VerticalAnchor.absolute(124)), WATER)),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FROZEN_LAKES),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_top", VerticalAnchor.absolute(124), VerticalAnchor.absolute(124)), ARCTIC_ICE)),


                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.5D, 0.35D),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ARCTIC_SNOW_BLOCK))),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.5D, 0.35D),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, ARCTIC_SNOW_BLOCK))),


                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_grass_block", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)),

                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_dirt", VerticalAnchor.belowTop(272), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, AETHER_DIRT)),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(64), VerticalAnchor.absolute(72)), UNDERSHALE)
        );
    }

    public static SurfaceRules.RuleSource lakeHolystone(double noise, int y) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN, AetherIIBiomes.FROZEN_LAKES),
                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.noiseCondition(AetherIINoises.LAKE_FLOOR, -noise, noise)),
                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.noiseCondition(AetherIINoises.LAKE_HOLYSTONE, -0.2D, 0.2D)),
                                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_holystone", VerticalAnchor.absolute(y), VerticalAnchor.absolute(y)), HOLYSTONE)))
        );
    }

    public static SurfaceRules.RuleSource lakeLayer(double noise, int y) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN, AetherIIBiomes.FROZEN_LAKES),
                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.noiseCondition(AetherIINoises.LAKE_FLOOR, -noise, noise)),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_layer", VerticalAnchor.absolute(y), VerticalAnchor.absolute(y)), AETHER_DIRT))
        );
    }
}