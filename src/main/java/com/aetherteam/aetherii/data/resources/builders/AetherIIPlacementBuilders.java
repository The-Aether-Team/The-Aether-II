package com.aetherteam.aetherii.data.resources.builders;

import com.aetherteam.aetherii.world.placementmodifier.StructureBlacklistFilter;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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

    public static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier placement) {
        return ImmutableList.<PlacementModifier>builder()
                .add(placement)
                .add(InSquarePlacement.spread())
                .add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR)
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(BiomeFilter.biome());
    }

    public static List<PlacementModifier> treePlacement(PlacementModifier placement) {
        return treePlacementBase(placement)
                .add(new StructureBlacklistFilter())
                .build();
    }

    public static List<PlacementModifier> treePlacement(PlacementModifier placement, Block saplingBlock) {
        return treePlacementBase(placement)
                .add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(saplingBlock.defaultBlockState(), BlockPos.ZERO)))
                .build();
    }
}