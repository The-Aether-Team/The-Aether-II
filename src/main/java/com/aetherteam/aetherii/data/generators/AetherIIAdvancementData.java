package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AetherIIAdvancementData extends AdvancementProvider {

    public AetherIIAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new BestiaryAdvancements()));
    }

    public static class BestiaryAdvancements implements AdvancementGenerator {
        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            String path = "bestiary/";
            for (EntityType<?> entityType : AetherIIBestiaryEntries.ENTITIES.values().stream().map(Holder::value).toList()) {
                ResourceLocation observeId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix(path);
                observe(Advancement.Builder.advancement(), entityType).requirements(AdvancementRequirements.Strategy.OR).save(consumer, observeId, existingFileHelper);

                ResourceLocation understandId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "understand_" + entityType.toShortString()).withPrefix(path);
                understand(Advancement.Builder.advancement(), entityType).requirements(AdvancementRequirements.Strategy.OR).save(consumer, understandId, existingFileHelper);
            }
        }

        private static Advancement.Builder observe(Advancement.Builder builder, EntityType<?> entity) {
            return understand(builder.addCriterion("observe", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(entity)).build()))), entity);
        }

        private static Advancement.Builder understand(Advancement.Builder builder, EntityType<?> entity) {
            builder.addCriterion("kill_" + entity.toShortString(), KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entity)));
            Map<EntityType<?>, TagKey<Item>> fedEntities = AetherIIBestiaryEntries.getFedEntityTypes();
            if (fedEntities.containsKey(entity)) {
                TagKey<Item> food = fedEntities.get(entity);
                builder.addCriterion("feed_" + entity.toShortString(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(food), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entity)))));
            }
            return builder;
        }
    }
}
