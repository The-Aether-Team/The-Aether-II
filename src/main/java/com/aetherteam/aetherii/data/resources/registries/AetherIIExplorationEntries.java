package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.ExplorationEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class AetherIIExplorationEntries {
    private static ResourceKey<ExplorationEntry> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.EXPLORATION_ENTRY, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<ExplorationEntry> context) {

    }

    public static Registry<ExplorationEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIRegistries.EXPLORATION_ENTRY);
    }
}
