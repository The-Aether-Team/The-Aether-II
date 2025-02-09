package com.aetherteam.aetherii.api.guidebook;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record RewardWrapper<T extends GuidebookEntry>(ResourceLocation advancement, ResourceKey<Registry<T>> entryRegistry, ResourceLocation entryId, String... entryValues) {
//    public void execute(RegistryAccess registryAccess, AdvancementHolder advancementHolder) { //todo client version; pass in mutable entry?
//
//    }
}
