package com.aetherteam.aetherii.data.generators.tags;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AetherIIFluidTagData extends FluidTagsProvider {
    public AetherIIFluidTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, AetherII.MODID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherIITags.Fluids.ALKAHEST).add(
                AetherIIFluids.ALKAHEST.get(),
                AetherIIFluids.FLOWING_ALKAHEST.get()
        );
        this.tag(AetherIITags.Fluids.ALLOWED_SKYROOT_BUCKET_PICKUP).add(
                Fluids.WATER,
                Fluids.FLOWING_WATER
        );
    }
}
