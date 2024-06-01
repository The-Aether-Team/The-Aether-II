package com.aetherteam.aetherii.data.resources.builders.highlands;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBiomes;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class HighlandsSurfaceBuilders {
    private static final SurfaceRules.RuleSource AETHER_GRASS_BLOCK = SurfaceRules.state(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource AETHER_DIRT = SurfaceRules.state(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
    private static final SurfaceRules.RuleSource IRRADIATED_HOLYSTONE = SurfaceRules.state(AetherIIBlocks.IRRADIATED_HOLYSTONE.get().defaultBlockState());

    public static SurfaceRules.RuleSource surfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), AETHER_GRASS_BLOCK), AETHER_DIRT);
        return SurfaceRules.sequence(

                SurfaceRules.ifTrue(SurfaceRules.isBiome(AetherIIBiomes.CONTAMINATED_JUNGLE, AetherIIBiomes.BATTLEGROUND_WASTES),
                        SurfaceRules.ifTrue(SurfaceRules.steep(),
                                SurfaceRules.ifTrue(SurfaceRules.not(
                                        SurfaceRules.verticalGradient("irradiated_holystone", VerticalAnchor.belowTop(296), VerticalAnchor.belowTop(288))), IRRADIATED_HOLYSTONE))),

                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_grass_block", VerticalAnchor.belowTop(276), VerticalAnchor.belowTop(272))), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)),

                SurfaceRules.ifTrue(SurfaceRules.not(
                        SurfaceRules.verticalGradient("aether_dirt", VerticalAnchor.belowTop(272), VerticalAnchor.belowTop(272))), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, AETHER_DIRT)),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("undershale", VerticalAnchor.absolute(64), VerticalAnchor.absolute(72)), SurfaceRules.state(AetherIIBlocks.UNDERSHALE.get().defaultBlockState())));
    }
}