package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import com.aetherteam.aetherii.world.surfacerule.NoisePalette3DPlacementRule;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class HighlandsSurfaceBuilders {
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ENCHANTED_AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource UNDERSHALE = SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_SNOW_BLOCK = SurfaceRules.state(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource MOSSY_HOLYSTONE = new NoisePalette3DPlacementRule(AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 2, 5, 0.045);
    private static final SurfaceRules.RuleSource PACKED_ICE = new NoisePalette3DPlacementRule(AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 2, 5, 0.075);
    private static final SurfaceRules.RuleSource FERROSITE = new NoisePalette3DPlacementRule(AetherIIBlocks.FERROSITE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 3, 5, 0.05);
//    private static final SurfaceRules.RuleSource RUSTED_FERROSITE = new NoisePalette3DPlacementRule(AetherIIBlocks.RUSTED_FERROSITE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 1, 9, 0.03);

    public static SurfaceRules.RuleSource surfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.BATTLEGROUND_WASTES), ENCHANTED_AETHER_GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.CONTAMINATED_JUNGLE), ENCHANTED_AETHER_GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), AETHER_GRASS_BLOCK),
                AETHER_DIRT);
        return SurfaceRules.sequence(


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

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(80), VerticalAnchor.absolute(88)), UNDERSHALE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FLOURISHING_FIELD), MOSSY_HOLYSTONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.VERDANT_WOODS), MOSSY_HOLYSTONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHROUDED_FOREST), MOSSY_HOLYSTONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHIMMERING_BASIN), MOSSY_HOLYSTONE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FRIGID_SIERRA), PACKED_ICE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.ENDURING_WOODLAND), PACKED_ICE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.FROZEN_LAKES), PACKED_ICE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.SHEER_TUNDRA), PACKED_ICE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.MAGNETIC_SCAR), FERROSITE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.TURQUOISE_FOREST), FERROSITE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.GLISTENING_SWAMP), FERROSITE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.VIOLET_HIGHWOODS), FERROSITE)

//                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.MAGNETIC_SCAR), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.TURQUOISE_FOREST), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.GLISTENING_SWAMP), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.VIOLET_HIGHWOODS), RUSTED_FERROSITE) //todo
        );
    }
}