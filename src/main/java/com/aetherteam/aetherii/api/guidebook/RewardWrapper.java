package com.aetherteam.aetherii.api.guidebook;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record RewardWrapper<T extends GuidebookEntry>(ResourceLocation advancement, ResourceKey<Registry<T>> entryRegistry, ResourceLocation entryId, String... entryValues) {
    public void execute(RegistryAccess registryAccess, AdvancementHolder advancementHolder) { //todo client version
        if (advancementHolder.id().equals(this.advancement())) {
            GuidebookEntry entry = registryAccess.lookupOrThrow(this.entryRegistry()).get(this.entryId).orElseThrow().value();
            List.of(this.entryValues()).forEach(name -> {
                if (entry.getValues().containsKey(name)) {
                    entry.getValues().get(name).reveal();
                }
            });
        }
    }
}
