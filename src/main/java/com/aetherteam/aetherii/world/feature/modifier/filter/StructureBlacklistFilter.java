package com.aetherteam.aetherii.world.feature.modifier.filter;

import com.aetherteam.aetherii.AetherIITags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * A {@link PlacementFilter} to prevent the feature from generating inside a structure.
 */
public class StructureBlacklistFilter extends PlacementFilter {

    public static final MapCodec<StructureBlacklistFilter> CODEC = RecordCodecBuilder.mapCodec((codec) -> codec.group(
            TagKey.codec(Registries.STRUCTURE).fieldOf("tag").forGetter((filter) -> filter.tag)
    ).apply(codec, StructureBlacklistFilter::new));

    private final TagKey<Structure> tag;

    public StructureBlacklistFilter(TagKey<Structure> tag) {
        this.tag = tag;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        if (!(context.getLevel() instanceof WorldGenRegion)) {
            return false;
        }
        StructureManager structureManager = context.getLevel().getLevel().structureManager();
        Registry<Structure> configuredStructureFeatureRegistry = context.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE);
        for (Holder<Structure> structure : configuredStructureFeatureRegistry.getTagOrEmpty(tag)) {
            if (structureManager.getStructureAt(pos, structure.value()).isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherIIPlacementModifierTypes.STRUCTURE_BLACKLIST_FILTER.get();
    }
}
