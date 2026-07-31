package com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.aetherteam.aetherii.world.surfacerule.DensityFunctionPlacementRule;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.function.Function;

public class HolyIslesSurfaceBuilders {
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ENCHANTED_AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource UNDERSHALE = SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_SNOW_BLOCK = SurfaceRules.state(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource QUICKSOIL = SurfaceRules.state(AetherIIBlocks.QUICKSOIL.get().defaultBlockState());

    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> AGIOSITE = (function) -> new DensityFunctionPlacementRule(AetherIIBlocks.AGIOSITE.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_AGIOSITE), 0.6);
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> MOSSY_HOLYSTONE = (function) -> new DensityFunctionPlacementRule(AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_MOSSY_HOLYSTONE), 0.7);
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> ARCTIC_PACKED_ICE = (function) -> new DensityFunctionPlacementRule(AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_ARCTIC_PACKED_ICE), 0.5);
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> IRRADIATED_HOLYSTONE = (function) -> new DensityFunctionPlacementRule(AetherIIBlocks.IRRADIATED_HOLYSTONE.get().defaultBlockState(), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_IRRADIATED_HOLYSTONE), 0.25);


//        SurfaceRules.RuleSource FERROSITE = new NoisePalette3DPlacementRule(AetherIIBlocks.FERROSITE.get().defaultBlockState(), 9, 20, AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS), 0.05);
    ////    private static final SurfaceRules.RuleSource RUSTED_FERROSITE = new NoisePalette3DPlacementRule(AetherIIBlocks.RUSTED_FERROSITE.get().defaultBlockState(), 1, 9, 0.03);
//        SurfaceRules.RuleSource ICHORITE = new NoisePalette3DPlacementRule(AetherIIBlocks.ICHORITE.get().defaultBlockState(), 16, 12, AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS), 0.075);


    public static SurfaceRules.RuleSource surfaceRules(HolderGetter<DensityFunction> function) {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.BATTLEGROUND_WASTES), ENCHANTED_AETHER_GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.CONTAMINATED_JUNGLE), ENCHANTED_AETHER_GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), AETHER_GRASS_BLOCK),
                AETHER_DIRT);
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.5D, 0.35D),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ARCTIC_SNOW_BLOCK))),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FRIGID_SIERRA),
                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(AetherIINoises.ARCTIC_SNOW, -0.5D, 0.35D),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, ARCTIC_SNOW_BLOCK))),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.BATTLEGROUND_WASTES, HolyIslesBiomes.CONTAMINATED_JUNGLE),
                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("aether_grass_block", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))),
                                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.noiseCondition(AetherIINoises.QUICKSOIL_IRRADIATED, -0.5D, 0.5D)),
                                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.steep()),
                                                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, 2, CaveSurface.FLOOR), QUICKSOIL))))),

                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("aether_grass_block", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)),

                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("aether_dirt", VerticalAnchor.belowTop(272), VerticalAnchor.belowTop(272))),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, AETHER_DIRT)),

//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.HESTVEIL_CAVERNS), ICHORITE),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(89), VerticalAnchor.absolute(101)), UNDERSHALE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FLOURISHING_FIELD), MOSSY_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.VERDANT_WOODS), MOSSY_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.SHROUDED_FOREST), MOSSY_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.SHIMMERING_BASIN), MOSSY_HOLYSTONE.apply(function)),
//
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FRIGID_SIERRA), ARCTIC_PACKED_ICE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.ENDURING_WOODLAND), ARCTIC_PACKED_ICE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FROZEN_LAKES), ARCTIC_PACKED_ICE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.SHEER_TUNDRA), ARCTIC_PACKED_ICE.apply(function)),
//
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.MAGNETIC_SCAR), FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.TURQUOISE_FOREST), FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.GLISTENING_SWAMP), FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.VIOLET_HIGHWOODS), FERROSITE),

//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.MAGNETIC_SCAR), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.TURQUOISE_FOREST), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.GLISTENING_SWAMP), RUSTED_FERROSITE),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(HighlandsBiomes.VIOLET_HIGHWOODS), RUSTED_FERROSITE) //todo

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.CONTAMINATED_JUNGLE), IRRADIATED_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.BATTLEGROUND_WASTES), IRRADIATED_HOLYSTONE.apply(function))
        );
    }
}