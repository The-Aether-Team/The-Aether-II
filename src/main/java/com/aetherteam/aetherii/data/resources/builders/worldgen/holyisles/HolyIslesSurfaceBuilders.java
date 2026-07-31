package com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.aetherteam.aetherii.world.surfacerule.DensityFunctionRule;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;
import java.util.function.Function;

public class HolyIslesSurfaceBuilders {
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ENCHANTED_AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource UNDERSHALE = SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_SNOW_BLOCK = SurfaceRules.state(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource QUICKSOIL = SurfaceRules.state(AetherIIBlocks.QUICKSOIL.get().defaultBlockState());

    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> ICHORITE = (function) -> new DensityFunctionRule(List.of(new DensityFunctionRule.DensityTest(AetherIIBlocks.ICHORITE.get().defaultBlockState(), -1.0, 0.15)), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_ICHORITE)); //todo
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> AGIOSITE = (function) -> new DensityFunctionRule(List.of(new DensityFunctionRule.DensityTest(AetherIIBlocks.AGIOSITE.get().defaultBlockState(), 0.0, 0.6)), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_AGIOSITE));
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> MOSSY_HOLYSTONE = (function) -> new DensityFunctionRule(List.of(new DensityFunctionRule.DensityTest(AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState(), -1.0, 0.7)), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_MOSSY_HOLYSTONE)); //todo
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> FERROSITE = (function) -> new DensityFunctionRule(List.of(new DensityFunctionRule.DensityTest(AetherIIBlocks.FERROSITE.get().defaultBlockState(), -0.2, 0.0), new DensityFunctionRule.DensityTest(AetherIIBlocks.RUSTED_FERROSITE.get().defaultBlockState(), -1.0, -0.2)), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_FERROSITE));
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> ARCTIC_PACKED_ICE = (function) -> new DensityFunctionRule(List.of(new DensityFunctionRule.DensityTest(AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(), -1.0, 0.5)), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_ARCTIC_PACKED_ICE)); //todo
    private static final Function<HolderGetter<DensityFunction>, SurfaceRules.RuleSource> IRRADIATED_HOLYSTONE = (function) -> new DensityFunctionRule(List.of(new DensityFunctionRule.DensityTest(AetherIIBlocks.IRRADIATED_HOLYSTONE.get().defaultBlockState(), -1.0, 0.25)), AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.STRATA_IRRADIATED_HOLYSTONE)); //todo

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

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.HESTVEIL_CAVERNS), ICHORITE.apply(function)),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("agiosite", VerticalAnchor.absolute(79), VerticalAnchor.absolute(89)), AGIOSITE.apply(function)),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(89), VerticalAnchor.absolute(101)), UNDERSHALE),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FLOURISHING_FIELD), MOSSY_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.VERDANT_WOODS), MOSSY_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.SHROUDED_FOREST), MOSSY_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.SHIMMERING_BASIN), MOSSY_HOLYSTONE.apply(function)),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FRIGID_SIERRA), ARCTIC_PACKED_ICE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.ENDURING_WOODLAND), ARCTIC_PACKED_ICE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.FROZEN_LAKES), ARCTIC_PACKED_ICE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.SHEER_TUNDRA), ARCTIC_PACKED_ICE.apply(function)),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.MAGNETIC_SCAR), FERROSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.TURQUOISE_FOREST), FERROSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.GLISTENING_SWAMP), FERROSITE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.VIOLET_HIGHWOODS), FERROSITE.apply(function)),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.CONTAMINATED_JUNGLE), IRRADIATED_HOLYSTONE.apply(function)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(HolyIslesBiomes.BATTLEGROUND_WASTES), IRRADIATED_HOLYSTONE.apply(function))
        );
    }
}