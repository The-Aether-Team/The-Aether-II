package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;

import java.util.concurrent.CompletableFuture;

public class AetherIIStructureTagData extends StructureTagsProvider {
    public AetherIIStructureTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AetherII.MODID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Structures.CAMPS).add(
                AetherIIStructures.CAMP_HIGHFIELDS,
                AetherIIStructures.CAMP_MAGNETIC,
                AetherIIStructures.CAMP_ARCTIC
        );
        this.tag(AetherIITags.Structures.DUNGEONS).add(
                AetherIIStructures.SENTRY_RUINS,
                AetherIIStructures.INFECTED_GUARDIAN_TREE
        );

        this.tag(AetherIITags.Structures.TREE_BLACKLIST_FILTER).add(
                AetherIIStructures.CAMP_HIGHFIELDS,
                AetherIIStructures.CAMP_MAGNETIC,
                AetherIIStructures.CAMP_ARCTIC
        );
        this.tag(AetherIITags.Structures.ALKAHEST_POOL_BLACKLIST_FILTER).add(
                AetherIIStructures.SENTRY_RUINS,
                AetherIIStructures.INFECTED_GUARDIAN_TREE
        );
        this.tag(AetherIITags.Structures.COAST_BLACKLIST_FILTER).add(
                AetherIIStructures.SENTRY_RUINS
        );
        this.tag(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER).add(
                AetherIIStructures.SENTRY_RUINS,
                AetherIIStructures.INFECTED_GUARDIAN_TREE
        );
    }
}