package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsBiomes;
import com.aetherteam.aetherii.world.surfacerule.NoisePalette3DPlacementRule;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class HighlandsSurfaceBuilders {
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ENCHANTED_AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource UNDERSHALE = SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_SNOW_BLOCK = SurfaceRules.state(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource MOSSY_HOLYSTONE = new NoisePalette3DPlacementRule(AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 3, 10, 0.045);
    private static final SurfaceRules.RuleSource PACKED_ICE = new NoisePalette3DPlacementRule(AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 3, 10, 0.075);
    private static final SurfaceRules.RuleSource FERROSITE = new NoisePalette3DPlacementRule(AetherIIBlocks.FERROSITE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 9, 20, 0.05);
//    private static final SurfaceRules.RuleSource RUSTED_FERROSITE = new NoisePalette3DPlacementRule(AetherIIBlocks.RUSTED_FERROSITE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 1, 9, 0.03);
private static final SurfaceRules.RuleSource IRRADIATED_HOLYSTONE = new NoisePalette3DPlacementRule(AetherIIBlocks.IRRADIATED_HOLYSTONE.get().defaultBlockState(), AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 3, 10, 0.045);

    public static SurfaceRules.RuleSource surfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.BATTLEGROUND_WASTES), ENCHANTED_AETHER_GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.CONTAMINATED_JUNGLE), ENCHANTED_AETHER_GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), AETHER_GRASS_BLOCK),
                AETHER_DIRT);
        return SurfaceRules.sequence(


                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.5D, 0.35D),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ARCTIC_SNOW_BLOCK))),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.5D, 0.35D),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, ARCTIC_SNOW_BLOCK))),

                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_grass_block", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)),

                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_dirt", VerticalAnchor.belowTop(272), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, AETHER_DIRT)),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(89), VerticalAnchor.absolute(101)), UNDERSHALE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.FLOURISHING_FIELD), MOSSY_HOLYSTONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.VERDANT_WOODS), MOSSY_HOLYSTONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.SHROUDED_FOREST), MOSSY_HOLYSTONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.SHIMMERING_BASIN), MOSSY_HOLYSTONE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.FRIGID_SIERRA), PACKED_ICE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.ENDURING_WOODLAND), PACKED_ICE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.FROZEN_LAKES), PACKED_ICE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.SHEER_TUNDRA), PACKED_ICE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.MAGNETIC_SCAR), FERROSITE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.TURQUOISE_FOREST), FERROSITE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.GLISTENING_SWAMP), FERROSITE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.VIOLET_HIGHWOODS), FERROSITE),

//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.MAGNETIC_SCAR), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.TURQUOISE_FOREST), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.GLISTENING_SWAMP), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.VIOLET_HIGHWOODS), RUSTED_FERROSITE) //todo

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.CONTAMINATED_JUNGLE), IRRADIATED_HOLYSTONE),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.BATTLEGROUND_WASTES), IRRADIATED_HOLYSTONE)
        );
    }
}