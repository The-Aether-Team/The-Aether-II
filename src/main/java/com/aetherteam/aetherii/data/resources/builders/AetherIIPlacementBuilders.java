package com.aetherteam.aetherii.data.resources.builders;

import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class AetherIIPlacementBuilders {

    public static List<PlacementModifier> aercloudPlacement(int above, int range, int chance) {
        return List.of(
                HeightRangePlacement.uniform(VerticalAnchor.absolute(above), VerticalAnchor.absolute(above + range)),
                RarityFilter.onAverageOnceEvery(chance),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
    }
}