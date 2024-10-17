package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherIIFluidTagData extends FluidTagsProvider {
    public AetherIIFluidTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, AetherII.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Fluids.ALLOWED_BUCKET_PICKUP).add(
                Fluids.WATER,
                Fluids.FLOWING_WATER);
    }
}