package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AetherIIRewardWrappers {
    private static ResourceKey<RewardWrapper> createKey(String name) {
        return ResourceKey.create(AetherIIRegistries.REWARD_WRAPPER, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    public static void bootstrap(BootstrapContext<RewardWrapper> context) {
        bestiaryWrappers(context);
        effectsWrappers(context);
    }

    public static void bestiaryWrappers(BootstrapContext<RewardWrapper> context) {
        String path = "bestiary/";
        for (Map.Entry<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> entry : AetherIIBestiaryEntries.ENTITIES.entrySet()) {
            EntityType<?> entityType = entry.getValue().value();
            Identifier observeId = Identifier.fromNamespaceAndPath(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix(path);
            RewardWrapper observeWrapper = new RewardWrapper(observeId, entry.getKey().identifier(), List.of(
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
            context.register(ResourceKey.create(AetherIIRegistries.REWARD_WRAPPER, observeId), observeWrapper);
        }
    }

    public static void effectsWrappers(BootstrapContext<RewardWrapper> context) {
        String path = "effects/";
        for (Map.Entry<ResourceKey<EffectsEntry>, Holder<MobEffect>> entry : AetherIIEffectsEntries.EFFECTS.entrySet()) {
            Holder<MobEffect> effect = entry.getValue();
            Identifier id = Identifier.fromNamespaceAndPath(AetherII.MODID, "obtain_" + effect.getKey().identifier().getPath()).withPrefix(path);

            RewardWrapper observeWrapper = new RewardWrapper(id, entry.getKey().identifier(), List.of(
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
            context.register(ResourceKey.create(AetherIIRegistries.REWARD_WRAPPER, id), observeWrapper);
        }
    }

    public static Registry<RewardWrapper> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIRegistries.REWARD_WRAPPER);
    }

    public static Optional<RewardWrapper> getWrapperForAdvancement(RegistryAccess registryAccess, Identifier advancement) {
        for (RewardWrapper wrapper : getRegistry(registryAccess)) {
            if (wrapper.advancement().equals(advancement)) {
                return Optional.of(wrapper);
            }
        }
        return Optional.empty();
    }
}
