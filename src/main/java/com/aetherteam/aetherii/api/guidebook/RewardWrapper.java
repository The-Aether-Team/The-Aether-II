package com.aetherteam.aetherii.api.guidebook;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record RewardWrapper(ResourceLocation advancement, ResourceKey<Registry<? extends GuidebookEntry>> entryRegistry, ResourceLocation entryId, List<String> entryValues) {
    public void execute(RegistryAccess registryAccess, AdvancementHolder advancementHolder) {
        if (advancementHolder.id().equals(this.advancement())) {
            GuidebookEntry entry = registryAccess.lookupOrThrow(this.entryRegistry()).get(this.entryId).orElseThrow().value();
            this.entryValues().forEach(name -> {
                if (entry.getValues().containsKey(name)) {
                    entry.getValues().get(name).reveal();
                }
            });
        }
    }
}
