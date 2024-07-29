package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class AetherIIEffectsEntries {
    public static final ResourceKey<Registry<EffectsEntry>> EFFECTS_ENTRY_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "effects_entry"));

    private static ResourceKey<EffectsEntry> createKey(String name) {
        return ResourceKey.create(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<EffectsEntry> context) {

    }

    public static Registry<EffectsEntry> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIEffectsEntries.EFFECTS_ENTRY_REGISTRY_KEY);
    }
}
