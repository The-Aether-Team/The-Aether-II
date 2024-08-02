package com.aetherteam.aetherii.world.placementmodifier;

import com.aetherteam.aetherii.AetherIITags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * A {@link PlacementFilter} to prevent the feature from generating inside a structure.
 */
public class StructureBlacklistFilter extends PlacementFilter {
    public static final MapCodec<StructureBlacklistFilter> CODEC = MapCodec.unit(StructureBlacklistFilter::new);

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        if (!(context.getLevel() instanceof WorldGenRegion)) {
            return false;
        }
        StructureManager structureManager = context.getLevel().getLevel().structureManager();
        Registry<Structure> configuredStructureFeatureRegistry = context.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE);
        for (Holder<Structure> structure : configuredStructureFeatureRegistry.getOrCreateTag(AetherIITags.Structures.STRUCTURE_BLACKLIST_FILTER)) {
            if (structureManager.getStructureAt(pos, structure.value()).isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifiers.STRUCTURE_BLACKLIST_FILTER.get();
    }
}