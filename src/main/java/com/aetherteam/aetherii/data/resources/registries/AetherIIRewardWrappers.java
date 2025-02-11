package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import com.aetherteam.aetherii.data.generators.AetherIIAdvancementData;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class AetherIIRewardWrappers {
    public static final ResourceKey<Registry<RewardWrapper>> REWARD_WRAPPER_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reward_wrapper"));

    private static ResourceKey<RewardWrapper> createKey(String name) {
        return ResourceKey.create(AetherIIRewardWrappers.REWARD_WRAPPER_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<RewardWrapper> context) {
        for (RewardWrapper entry : AetherIIAdvancementData.REWARD_WRAPPERS) {
            context.register(ResourceKey.create(REWARD_WRAPPER_REGISTRY_KEY, entry.advancement()), entry);
        }
    }

    public static Registry<RewardWrapper> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIRewardWrappers.REWARD_WRAPPER_REGISTRY_KEY);
    }

    public static Optional<RewardWrapper> getWrapperForAdvancement(RegistryAccess registryAccess, AdvancementHolder advancementHolder) {
        for (RewardWrapper wrapper : getRegistry(registryAccess)) {
            if (wrapper.advancement().equals(advancementHolder.id())) {
                return Optional.of(wrapper);
            }
        }
        return Optional.empty();
    }
}
