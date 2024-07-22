package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import com.aetherteam.aetherii.data.resources.registries.AetherIINoises;
import com.aetherteam.aetherii.world.surfacerule.NoisePalette3DPlacementRule;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import java.util.List;

public class HighlandsSurfaceBuilders {
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ENCHANTED_AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource UNDERSHALE = SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARCTIC_SNOW_BLOCK = SurfaceRules.state(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource MOSSY_HOLYSTONE = new NoisePalette3DPlacementRule(List.of(Blocks.REDSTONE_BLOCK.defaultBlockState(), Blocks.EMERALD_BLOCK.defaultBlockState(), Blocks.GOLD_BLOCK.defaultBlockState()), Noises.SURFACE, 0.5);

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

                MOSSY_HOLYSTONE
        );
    }
}