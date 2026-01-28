package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AetherIIAdvancementData extends AdvancementProvider {
    public AetherIIAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new HighlandsAdvancements(), new BestiaryAdvancements(), new EffectsAdvancements()));
    }

    public static class HighlandsAdvancements implements AdvancementSubProvider {
        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            HolderGetter<EntityType<?>> entityTypes = provider.lookupOrThrow(Registries.ENTITY_TYPE);

            AdvancementHolder theAether = Advancement.Builder.advancement()
                    .display(AetherIIItems.AETHER_PORTAL_FRAME.get(),
                            Component.translatable("advancement.aether_ii.the_highlands"),
                            Component.translatable("advancement.aether_ii.the_highlands.desc"),
                            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/block/holystone.png"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("the_highlands", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "the_highlands"));

            AdvancementHolder enterAether = Advancement.Builder.advancement()
                    .parent(theAether)
                    .display(Blocks.GLOWSTONE,
                            Component.translatable("advancement.aether_ii.enter_highlands"),
                            Component.translatable("advancement.aether_ii.enter_highlands.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("enter_highlands", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "enter_highlands"));

            AdvancementHolder zanite = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.ZANITE_GEMSTONE.get(),
                            Component.translatable("advancement.aether_ii.zanite"),
                            Component.translatable("advancement.aether_ii.zanite.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("zanite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ZANITE_GEMSTONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite"));

            AdvancementHolder craftAltar = Advancement.Builder.advancement()
                    .parent(zanite)
                    .display(AetherIIBlocks.ALTAR.get(),
                            Component.translatable("advancement.aether_ii.craft_altar"),
                            Component.translatable("advancement.aether_ii.craft_altar.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_altar", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ALTAR.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "craft_altar"));

            AdvancementHolder icestone = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherIIBlocks.ICESTONE.get(),
                            Component.translatable("advancement.aether_ii.icestone"),
                            Component.translatable("advancement.aether_ii.icestone.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("icestone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ICESTONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "icestone"));

            AdvancementHolder blueAercloud = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIBlocks.BLUE_AERCLOUD.get(),
                            Component.translatable("advancement.aether_ii.blue_aercloud"),
                            Component.translatable("advancement.aether_ii.blue_aercloud.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("blue_aercloud", EnterBlockTrigger.TriggerInstance.entersBlock(AetherIIBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "blue_aercloud"));

            AdvancementHolder obtainEgg = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherIIBlocks.MOA_EGG.get(),
                            Component.translatable("advancement.aether_ii.obtain_egg"),
                            Component.translatable("advancement.aether_ii.obtain_egg.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.MOA_EGG.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "obtain_egg"));

            AdvancementHolder obtainPetal = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.AECHOR_PETAL.get(),
                            Component.translatable("advancement.aether_ii.obtain_petal"),
                            Component.translatable("advancement.aether_ii.obtain_petal.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("aechor_petal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.AECHOR_PETAL.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "obtain_petal"));

            AdvancementHolder enchantedGravitite = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherIIItems.GRAVITITE_PLATE.get(),
                            Component.translatable("advancement.aether_ii.gravitite_plate"),
                            Component.translatable("advancement.aether_ii.gravitite_plate.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("gravitite_plate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_PLATE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "enchanted_gravitite"));

            AdvancementHolder gravititeArmor = Advancement.Builder.advancement()
                    .parent(enchantedGravitite)
                    .display(AetherIIItems.GRAVITITE_CHESTPLATE.get(),
                            Component.translatable("advancement.aether_ii.gravitite_armor"),
                            Component.translatable("advancement.aether_ii.gravitite_armor.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("gravitite_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_HELMET.get()))
                    .addCriterion("gravitite_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_CHESTPLATE.get()))
                    .addCriterion("gravitite_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_LEGGINGS.get()))
                    .addCriterion("gravitite_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_BOOTS.get()))
                    .addCriterion("gravitite_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_GLOVES.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gravitite_armor"));

            AdvancementHolder slider = Advancement.Builder.advancement()
                    .parent(enchantedGravitite)
                    .display(AetherIIBlocks.SENTRY_BRICKS.get(),
                            Component.translatable("advancement.aether_ii.slider"),
                            Component.translatable("advancement.aether_ii.slider.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("kill_slider",KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SLIDER.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "slider"));

            AdvancementHolder hammerLoot = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.HAMMER_OF_DEMOLITION.get(),
                            Component.translatable("advancement.aether_ii.hammer_loot"),
                            Component.translatable("advancement.aether_ii.hammer_loot.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("hammer_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.HAMMER_OF_DEMOLITION))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hammer_loot"));
        }
    }

    private static ItemUsedOnLocationTrigger.TriggerInstance itemUsedOnLocationCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
        ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.create(LocationCheck.checkLocation(location).build(), LocationCheck.checkLocation(above, BlockPos.ZERO.above()).build(), MatchTool.toolMatches(item).build());
        return new ItemUsedOnLocationTrigger.TriggerInstance(Optional.empty(), Optional.of(contextawarepredicate));
    }

    public static Criterion<ItemUsedOnLocationTrigger.TriggerInstance> itemUsedOnBlockCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
        return CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(itemUsedOnLocationCheckAbove(location, above, item));
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

//                ResourceLocation understandId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "understand_" + entityType.toShortString()).withPrefix(path);
//                understand(itemGetter, entityGetter, Advancement.Builder.advancement(), entityType).requirements(AdvancementRequirements.Strategy.OR).save(consumer, understandId);
//                RewardWrapper understandWrapper = new RewardWrapper(understandId, entry.getKey().location(), List.of("test"));
//                if (!REWARD_WRAPPERS.contains(understandWrapper)) {
//                    REWARD_WRAPPERS.add(understandWrapper);
//                }
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

    public static class EffectsAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            String path = "effects/";
            for (Map.Entry<ResourceKey<EffectsEntry>, Holder<MobEffect>> entry : AetherIIEffectsEntries.EFFECTS.entrySet()) {
                Holder<MobEffect> effect = entry.getValue();
                ResourceLocation id = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "obtain_" + effect.getKey().location().getPath()).withPrefix(path);
                Advancement.Builder.advancement().addCriterion("obtain_" + effect.getKey().location().getPath(), EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(effect))).save(consumer, id);
            }
        }
    }
}
