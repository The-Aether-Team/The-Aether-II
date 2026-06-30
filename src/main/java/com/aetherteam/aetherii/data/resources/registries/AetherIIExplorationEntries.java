package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AetherIIExplorationEntries {
    private static ResourceKey<ExplorationEntry> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.EXPLORATION_ENTRY, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<ExplorationEntry> context) {

    }

    public static Registry<ExplorationEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIRegistries.EXPLORATION_ENTRY);
    }
}
