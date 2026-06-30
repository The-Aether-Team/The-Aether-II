package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AetherIIRewardWrappers {
    private static ResourceKey<RewardWrapper> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.REWARD_WRAPPER, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<RewardWrapper> context) {
        bestiaryWrappers(context);
        effectsWrappers(context);
    }

    public static void bestiaryWrappers(BootstapContext<RewardWrapper> context) {
        String path = "bestiary/";
        for (Map.Entry<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> entry : AetherIIBestiaryEntries.ENTITIES.entrySet()) {
            EntityType<?> entityType = entry.getValue().value();
            ResourceLocation observeId = new ResourceLocation(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix(path);
            RewardWrapper observeWrapper = new RewardWrapper(observeId, entry.getKey().location(), List.of(
                    BestiaryEntry.ICON.getId(),
                    BestiaryEntry.NAME.getId(),
                    BestiaryEntry.SLOT_NAME.getId(),
                    BestiaryEntry.SLOT_SUBTITLE.getId(),
                    BestiaryEntry.DESCRIPTION_KEY.getId(),
                    BestiaryEntry.ENTITY_TYPE.getId(),
                    BestiaryEntry.HEALTH.getId(),
                    BestiaryEntry.SLASH_DEFENSE.getId(),
                    BestiaryEntry.IMPACT_DEFENSE.getId(),
                    BestiaryEntry.PIERCE_DEFENSE.getId(),
                    BestiaryEntry.EFFECT_RESISTANCE.getId() + "_0",
                    BestiaryEntry.EFFECT_RESISTANCE.getId() + "_1",
                    BestiaryEntry.EFFECT_RESISTANCE.getId() + "_2",
                    BestiaryEntry.EFFECT_RESISTANCE.getId() + "_3",
                    BestiaryEntry.SCALE_MULTIPLIER.getId(),
                    BestiaryEntry.LOOT.getId() + "_0",
                    BestiaryEntry.LOOT.getId() + "_1",
                    BestiaryEntry.LOOT.getId() + "_2",
                    BestiaryEntry.FOOD.getId()));
            context.register(ResourceKey.create(AetherIIRegistries.REWARD_WRAPPER, observeId), observeWrapper);
        }
    }

    public static void effectsWrappers(BootstapContext<RewardWrapper> context) {
        String path = "effects/";
        for (Map.Entry<ResourceKey<EffectsEntry>, Holder<MobEffect>> entry : AetherIIEffectsEntries.EFFECTS.entrySet()) {
            Holder<MobEffect> effect = entry.getValue();
            ResourceLocation effectId = BuiltInRegistries.MOB_EFFECT.getKey(effect.value());
            ResourceLocation id = new ResourceLocation(AetherII.MODID, "obtain_" + effectId.getPath()).withPrefix(path);

            RewardWrapper observeWrapper = new RewardWrapper(id, entry.getKey().location(), List.of(
                    EffectsEntry.ICON.getId(),
                    EffectsEntry.NAME.getId(),
                    EffectsEntry.SLOT_NAME.getId(),
                    EffectsEntry.SLOT_SUBTITLE.getId(),
                    EffectsEntry.DESCRIPTION_KEY.getId(),
                    EffectsEntry.EFFECT.getId(),
                    EffectsEntry.ITEM.getId() + "_0",
                    EffectsEntry.ITEM.getId() + "_1",
                    EffectsEntry.ITEM.getId() + "_2",
                    EffectsEntry.ITEM.getId() + "_3",
                    EffectsEntry.ITEM.getId() + "_4",
                    EffectsEntry.ITEM.getId() + "_5"));
            context.register(ResourceKey.create(AetherIIRegistries.REWARD_WRAPPER, id), observeWrapper);
        }
    }

    public static Registry<RewardWrapper> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIRegistries.REWARD_WRAPPER);
    }

    public static Optional<RewardWrapper> getWrapperForAdvancement(RegistryAccess registryAccess, ResourceLocation advancement) {
        for (RewardWrapper wrapper : getRegistry(registryAccess)) {
            if (wrapper.advancement().equals(advancement)) {
                return Optional.of(wrapper);
            }
        }
        return Optional.empty();
    }
}
