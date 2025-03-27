package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AetherIIRewardWrappers {
    public static final ResourceKey<Registry<RewardWrapper>> REWARD_WRAPPER_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "reward_wrapper"));

    private static ResourceKey<RewardWrapper> createKey(String name) {
        return ResourceKey.create(AetherIIRewardWrappers.REWARD_WRAPPER_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<RewardWrapper> context) {
        bestiaryWrappers(context);
        effectsWrappers(context);
    }

    public static void bestiaryWrappers(BootstrapContext<RewardWrapper> context) {
        String path = "bestiary/";
        for (Map.Entry<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> entry : AetherIIBestiaryEntries.ENTITIES.entrySet()) {
            EntityType<?> entityType = entry.getValue().value();
            ResourceLocation observeId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix(path);
            RewardWrapper observeWrapper = new RewardWrapper(observeId, entry.getKey().location(), List.of(
                    BestiaryEntry.ICON.id(),
                    BestiaryEntry.NAME.id(),
                    BestiaryEntry.SLOT_NAME.id(),
                    BestiaryEntry.SLOT_SUBTITLE.id(),
                    BestiaryEntry.DESCRIPTION_KEY.id(),
                    BestiaryEntry.ENTITY_TYPE.id(),
                    BestiaryEntry.HEALTH.id(),
                    BestiaryEntry.SLASH_DEFENSE.id(),
                    BestiaryEntry.IMPACT_DEFENSE.id(),
                    BestiaryEntry.PIERCE_DEFENSE.id(),
                    BestiaryEntry.EFFECT_RESISTANCE.id() + "_0",
                    BestiaryEntry.EFFECT_RESISTANCE.id() + "_1",
                    BestiaryEntry.EFFECT_RESISTANCE.id() + "_2",
                    BestiaryEntry.EFFECT_RESISTANCE.id() + "_3",
                    BestiaryEntry.SCALE_MULTIPLIER.id(),
                    BestiaryEntry.LOOT.id() + "_0",
                    BestiaryEntry.LOOT.id() + "_1",
                    BestiaryEntry.LOOT.id() + "_2",
                    BestiaryEntry.FOOD.id()));
            context.register(ResourceKey.create(REWARD_WRAPPER_REGISTRY_KEY, observeId), observeWrapper);
        }
    }

    public static void effectsWrappers(BootstrapContext<RewardWrapper> context) {
        String path = "effects/";
        for (Map.Entry<ResourceKey<EffectsEntry>, Holder<MobEffect>> entry : AetherIIEffectsEntries.EFFECTS.entrySet()) {
            Holder<MobEffect> effect = entry.getValue();
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "obtain_" + effect.getKey().location().getPath()).withPrefix(path);

            RewardWrapper observeWrapper = new RewardWrapper(id, entry.getKey().location(), List.of(
                    EffectsEntry.ICON.id(),
                    EffectsEntry.NAME.id(),
                    EffectsEntry.SLOT_NAME.id(),
                    EffectsEntry.SLOT_SUBTITLE.id(),
                    EffectsEntry.DESCRIPTION_KEY.id(),
                    EffectsEntry.EFFECT.id(),
                    EffectsEntry.ITEM.id() + "_0",
                    EffectsEntry.ITEM.id() + "_1",
                    EffectsEntry.ITEM.id() + "_2",
                    EffectsEntry.ITEM.id() + "_3",
                    EffectsEntry.ITEM.id() + "_4",
                    EffectsEntry.ITEM.id() + "_5"));
            context.register(ResourceKey.create(REWARD_WRAPPER_REGISTRY_KEY, id), observeWrapper);
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
