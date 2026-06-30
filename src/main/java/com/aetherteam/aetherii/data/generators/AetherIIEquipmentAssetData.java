package com.aetherteam.aetherii.data.generators;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class AetherIIEquipmentAssetData implements DataProvider {
    public AetherIIEquipmentAssetData(PackOutput packOutput) {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "Aether II Equipment Assets";
    }
}
