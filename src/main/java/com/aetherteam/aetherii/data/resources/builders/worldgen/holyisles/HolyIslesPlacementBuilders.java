package com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.modifier.StructureBlacklistFilter;
import com.aetherteam.aetherii.world.feature.modifier.UndercloudBiomeFilter;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

import java.util.List;

public class HolyIslesPlacementBuilders {
    public static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier placement, ResourceKey<Biome> biomeException) {
        return ImmutableList.<PlacementModifier>builder()
                .add(placement)
                .add(InSquarePlacement.spread())
                .add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR)
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(new UndercloudBiomeFilter(biomeException));
    }

    public static List<PlacementModifier> treePlacement(PlacementModifier placement, ResourceKey<Biome> biomeException) {
        return treePlacementBase(placement, biomeException)
                .add(new StructureBlacklistFilter(AetherIITags.Structures.TREE_BLACKLIST_FILTER))
                .build();
    }
}