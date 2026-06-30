package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIIStructureTagData extends StructureTagsProvider {
    public AetherIIStructureTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, AetherII.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Structures.OUTPOSTS).add(
                AetherIIStructures.OUTPOST
        );
        this.tag(AetherIITags.Structures.CAMPS).add(
                AetherIIStructures.CAMP_HIGHFIELDS,
                AetherIIStructures.CAMP_MAGNETIC,
                AetherIIStructures.CAMP_ARCTIC
        );
        this.tag(AetherIITags.Structures.DUNGEONS).add(
                AetherIIStructures.SENTRY_RUINS,
                AetherIIStructures.INFECTED_GUARDIAN_TREE
        );
        this.tag(AetherIITags.Structures.WATCHTOWERS).add(
                AetherIIStructures.WATCHTOWER
        );
        this.tag(AetherIITags.Structures.SURFACE_RUINS).add(
                AetherIIStructures.VERADEXIAN_RUINS_TEMPERATE,
                AetherIIStructures.VERADEXIAN_RUINS_ARCTIC,
                AetherIIStructures.VERADEXIAN_LIBRARY_TEMPERATE
        );

        this.tag(AetherIITags.Structures.TREE_BLACKLIST_FILTER).addTags(
                AetherIITags.Structures.CAMPS
        );
        this.tag(AetherIITags.Structures.ALKAHEST_POOL_BLACKLIST_FILTER).addTags(
                AetherIITags.Structures.DUNGEONS
        );
        this.tag(AetherIITags.Structures.COAST_BLACKLIST_FILTER).add(
                AetherIIStructures.SENTRY_RUINS
        );
        this.tag(AetherIITags.Structures.FERROSITE_SPIKE_BLACKLIST_FILTER).addTags(
                AetherIITags.Structures.OUTPOSTS,
                AetherIITags.Structures.CAMPS,
                AetherIITags.Structures.WATCHTOWERS
        );
        this.tag(AetherIITags.Structures.ARCTIC_ICE_SPIKE_BLACKLIST_FILTER).addTags(
                AetherIITags.Structures.OUTPOSTS,
                AetherIITags.Structures.CAMPS,
                AetherIITags.Structures.WATCHTOWERS
        );
        this.tag(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER).addTags(
                AetherIITags.Structures.DUNGEONS
        );
    }
}
