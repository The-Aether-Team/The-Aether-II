package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.RewardWrapper;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AetherIIAdvancementData extends AdvancementProvider {
    public static List<RewardWrapper> REWARD_WRAPPERS = new ArrayList<>();

    public AetherIIAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new BestiaryAdvancements()));
    }

    public static class BestiaryAdvancements implements AdvancementSubProvider {
        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            String path = "bestiary/";
            HolderGetter<EntityType<?>> entityGetter = provider.lookupOrThrow(Registries.ENTITY_TYPE);
            HolderGetter<Item> itemGetter = provider.lookupOrThrow(Registries.ITEM);
            for (Map.Entry<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> entry : AetherIIBestiaryEntries.ENTITIES.entrySet()) {
                EntityType<?> entityType = entry.getValue().value();
                ResourceLocation observeId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix(path);
                observe(itemGetter, entityGetter, Advancement.Builder.advancement(), entityType).requirements(AdvancementRequirements.Strategy.OR).save(consumer, observeId);
                RewardWrapper observeWrapper = new RewardWrapper(observeId, entry.getKey().location(), List.of("test"));
                if (!REWARD_WRAPPERS.contains(observeWrapper)) {
                    REWARD_WRAPPERS.add(observeWrapper);
                }

                ResourceLocation understandId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "understand_" + entityType.toShortString()).withPrefix(path);
                understand(itemGetter, entityGetter, Advancement.Builder.advancement(), entityType).requirements(AdvancementRequirements.Strategy.OR).save(consumer, understandId);
                RewardWrapper understandWrapper = new RewardWrapper(understandId, entry.getKey().location(), List.of("test"));
                if (!REWARD_WRAPPERS.contains(understandWrapper)) {
                    REWARD_WRAPPERS.add(understandWrapper);
                }
            }
        }

        private static Advancement.Builder observe(HolderGetter<Item> itemGetter, HolderGetter<EntityType<?>> entityGetter, Advancement.Builder builder, EntityType<?> entity) {
            return understand(itemGetter, entityGetter, builder.addCriterion("observe", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(entityGetter, entity)).build()))), entity);
        }

        private static Advancement.Builder understand(HolderGetter<Item> itemGetter, HolderGetter<EntityType<?>> entityGetter, Advancement.Builder builder, EntityType<?> entity) {
            builder.addCriterion("kill_" + entity.toShortString(), KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityGetter, entity)));
            Map<EntityType<?>, TagKey<Item>> fedEntities = AetherIIBestiaryEntries.getFedEntityTypes();
            if (fedEntities.containsKey(entity)) {
                TagKey<Item> food = fedEntities.get(entity);
                builder.addCriterion("feed_" + entity.toShortString(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(itemGetter, food), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityGetter, entity)))));
            }
            return builder;
        }
    }
}
