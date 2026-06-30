package com.aetherteam.aetherii.data.generators.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class AetherIIFeatureTagData implements DataProvider {
    public AetherIIFeatureTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "Aether II Feature Tags";
    }
}
