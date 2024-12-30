package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class AetherIIFluidTagData extends FluidTagsProvider {
    public AetherIIFluidTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, AetherII.MODID);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Fluids.ACID).add(
                AetherIIFluids.ACID.get(),
                AetherIIFluids.FLOWING_ACID.get());
        this.tag(AetherIITags.Fluids.ALLOWED_BUCKET_PICKUP).add(
                Fluids.WATER,
                Fluids.FLOWING_WATER);
    }
}