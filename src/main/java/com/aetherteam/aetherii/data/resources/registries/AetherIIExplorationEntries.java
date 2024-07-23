package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AetherIIExplorationEntries {
    public static final ResourceKey<Registry<ExplorationEntry>> EXPLORATION_ENTRY_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "exploration_entry"));

    private static ResourceKey<ExplorationEntry> createKey(String name) {
        return ResourceKey.create(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ExplorationEntry> context) {

    }

    public static Registry<ExplorationEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIExplorationEntries.EXPLORATION_ENTRY_REGISTRY_KEY);
    }
}
