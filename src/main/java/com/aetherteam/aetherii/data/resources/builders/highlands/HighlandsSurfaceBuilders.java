package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class HighlandsSurfaceBuilders {
    private static final SurfaceRules.RuleSource AIR = SurfaceRules.state(Blocks.AIR.defaultBlockState());
    private static final SurfaceRules.RuleSource WATER = SurfaceRules.state(Blocks.WATER.defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_SNOW_BLOCK = SurfaceRules.state(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_ICE = SurfaceRules.state(AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());

    public static SurfaceRules.RuleSource surfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), AETHER_GRASS_BLOCK), AETHER_DIRT);
        return SurfaceRules.sequence(

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.not(
                                        SurfaceRules.verticalGradient("arctic_snow", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.7D, 0.7D),
                                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ARCTIC_SNOW_BLOCK)))),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.not(
                                        SurfaceRules.verticalGradient("arctic_snow", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.55D, 0.55D),
                                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, ARCTIC_SNOW_BLOCK)))),

                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_grass_block", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)),

                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_dirt", VerticalAnchor.belowTop(272), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, AETHER_DIRT)),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(64), VerticalAnchor.absolute(72)), SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState())),

                lakeLayer(0.08D, 117),
                lakeLayer(0.175D, 118),
                lakeLayer(0.25D, 119),
                lakeLayer(0.3D, 120),
                lakeLayer(0.35D, 121),
                lakeLayer(0.39D, 122),
                lakeLayer(0.43D, 123),
                lakeLayer(0.47D, 124),
                lakeLayer(0.5D, 125),
                lakeLayer(0.6D, 126),
                lakeLayer(0.75D, 127),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN, AetherIIBiomes.FROZEN_LAKES),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_bottom", VerticalAnchor.absolute(116), VerticalAnchor.absolute(116)), AETHER_DIRT)
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN, AetherIIBiomes.FROZEN_LAKES),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.AETHER_LAKES, -1.0D, 1.0D),
                                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("water", VerticalAnchor.absolute(127), VerticalAnchor.absolute(127)), WATER))
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_top", VerticalAnchor.absolute(128), VerticalAnchor.absolute(128)), WATER)
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FROZEN_LAKES),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_top", VerticalAnchor.absolute(128), VerticalAnchor.absolute(128)), ARCTIC_ICE)
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN, AetherIIBiomes.FROZEN_LAKES),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.AETHER_LAKES, -1.0D, 1.0D),
                                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("above_lake", VerticalAnchor.absolute(256), VerticalAnchor.absolute(256)), AIR))
                )
        );
    }

    public static SurfaceRules.RuleSource lakeLayer(double noise, int y) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN, AetherIIBiomes.FROZEN_LAKES),
                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.noiseCondition(AetherIINoises.AETHER_LAKES, -noise, noise)),
                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lake_layer", VerticalAnchor.absolute(y), VerticalAnchor.absolute(y)), AETHER_DIRT))
        );
    }
}