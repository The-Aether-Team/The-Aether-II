package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.AetherIIStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIStructureTagData extends StructureTagsProvider {
    public AetherIIStructureTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, AetherII.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Structures.STRUCTURE_BLACKLIST_FILTER).add(AetherIIStructures.CAMP_HIGHFIELDS);
    }
}