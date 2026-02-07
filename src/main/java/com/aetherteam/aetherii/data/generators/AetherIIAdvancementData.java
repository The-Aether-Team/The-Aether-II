package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.predicate.AlivePredicate;
import com.aetherteam.aetherii.advancement.predicate.ArmorSetPredicate;
import com.aetherteam.aetherii.advancement.predicate.EffectBuildupPredicate;
import com.aetherteam.aetherii.advancement.trigger.*;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsBiomes;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.*;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

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
            HolderGetter<Block> blocks = provider.lookupOrThrow(Registries.BLOCK);
            HolderGetter<Item> items = provider.lookupOrThrow(Registries.ITEM);
            HolderGetter<EntityType<?>> entityTypes = provider.lookupOrThrow(Registries.ENTITY_TYPE);
            HolderGetter<MobEffect> mobEffects = provider.lookupOrThrow(Registries.MOB_EFFECT);
            HolderGetter<Biome> biomes = provider.lookupOrThrow(Registries.BIOME);

            AdvancementHolder theAether = Advancement.Builder.advancement()
                    .display(AetherIIItems.AETHER_PORTAL_FRAME.get(),
                            Component.translatable("advancement.aether_ii.the_highlands"),
                            Component.translatable("advancement.aether_ii.the_highlands.desc").withStyle(ChatFormatting.AQUA),
                            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "block/holystone"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("the_highlands", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "the_highlands"));

            AdvancementHolder enterAether = Advancement.Builder.advancement()
                    .parent(theAether)
                    .display(Blocks.GLOWSTONE,
                            Component.translatable("advancement.aether_ii.enter_highlands"),
                            Component.translatable("advancement.aether_ii.enter_highlands.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("enter_highlands", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "enter_highlands"));

            AdvancementHolder ambrosium = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.AMBROSIUM_SHARD.get(),
                            Component.translatable("advancement.aether_ii.ambrosium"),
                            Component.translatable("advancement.aether_ii.ambrosium.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("ambrosium", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.AMBROSIUM_SHARD.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "ambrosium"));

            AdvancementHolder enchantedAetherGrass = Advancement.Builder.advancement()
                    .parent(ambrosium)
                    .display(AetherIIItems.ENCHANTED_BLUEBERRY.get(),
                            Component.translatable("advancement.aether_ii.enchanted_aether_grass"),
                            Component.translatable("advancement.aether_ii.enchanted_aether_grass.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("enchanted_aether_grass", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())),
                            ItemPredicate.Builder.item().of(items, AetherIIItems.AMBROSIUM_SHARD.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "enchanted_aether_grass"));

            AdvancementHolder zanite = Advancement.Builder.advancement()
                    .parent(ambrosium)
                    .display(AetherIIItems.ZANITE_GEMSTONE.get(),
                            Component.translatable("advancement.aether_ii.zanite"),
                            Component.translatable("advancement.aether_ii.zanite.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("zanite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ZANITE_GEMSTONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "zanite"));

            AdvancementHolder craftAltar = Advancement.Builder.advancement()
                    .parent(zanite)
                    .display(AetherIIBlocks.ALTAR.get(),
                            Component.translatable("advancement.aether_ii.craft_altar"),
                            Component.translatable("advancement.aether_ii.craft_altar.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_altar", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ALTAR.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "craft_altar"));

            AdvancementHolder icestone = Advancement.Builder.advancement()
                    .parent(ambrosium)
                    .display(AetherIIBlocks.ICESTONE.get(),
                            Component.translatable("advancement.aether_ii.icestone"),
                            Component.translatable("advancement.aether_ii.icestone.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("icestone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ICESTONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "icestone"));

            AdvancementHolder antitoxin = Advancement.Builder.advancement()
                    .parent(icestone)
                    .display(AetherIIItems.ANTITOXIN_VIAL,
                            Component.translatable("advancement.aether_ii.antitoxin"),
                            Component.translatable("advancement.aether_ii.antitoxin.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("antitoxin_vial", buildupReductionItemConsumed(ItemPredicate.Builder.item().of(items, AetherIIItems.ANTITOXIN_VIAL), AetherIIEffects.TOXIN))
                    .addCriterion("antivenom_vial", buildupReductionItemConsumed(ItemPredicate.Builder.item().of(items, AetherIIItems.ANTIVENOM_VIAL), AetherIIEffects.VENOM))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "antitoxin"));

            AdvancementHolder engravedDiscs = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.MUSIC_PLAYER.get(),
                            Component.translatable("advancement.aether_ii.engraved_discs"),
                            Component.translatable("advancement.aether_ii.engraved_discs.desc").withStyle(ChatFormatting.GOLD),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("aether_tune", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_AETHER_TUNE.get()))
                    .addCriterion("ascending_dawn", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_ASCENDING_DAWN.get()))
                    .addCriterion("aerwhale", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_AERWHALE.get()))
                    .addCriterion("approaches", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_APPROACHES.get()))
                    .addCriterion("demise", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_DEMISE.get()))
                    .addCriterion("chinchilla", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_CHINCHILLA.get()))
                    .addCriterion("high", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_HIGH.get()))
                    .addCriterion("revolutions", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_REVOLUTIONS.get()))
                    .addCriterion("chase", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_CHASE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "engraved_discs"));

            AdvancementHolder blueAercloud = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIBlocks.BLUE_AERCLOUD.get(),
                            Component.translatable("advancement.aether_ii.blue_aercloud"),
                            Component.translatable("advancement.aether_ii.blue_aercloud.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("blue_aercloud", EnterBlockTrigger.TriggerInstance.entersBlock(AetherIIBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "blue_aercloud"));

            AdvancementHolder outpostCampfire = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIBlocks.OUTPOST_CAMPFIRE.get(),
                            Component.translatable("advancement.aether_ii.outpost_campfire"),
                            Component.translatable("advancement.aether_ii.outpost_campfire.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("outpost_campfire", OutpostCampfireTrigger.Instance.setSpawn())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "outpost_campfire"));

            AdvancementHolder glint = Advancement.Builder.advancement()
                    .parent(outpostCampfire)
                    .display(AetherIIItems.GLINT_COIN.get(),
                            Component.translatable("advancement.aether_ii.glint"),
                            Component.translatable("advancement.aether_ii.glint.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("glint", CurrencyTrigger.Instance.forValue(100))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "glint"));

            AdvancementHolder bestiary = createBestiaryAdvancement(outpostCampfire, consumer);


            AdvancementHolder cloudSkiff = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherIIItems.CLOUD_SKIFF.get(),
                            Component.translatable("advancement.aether_ii.cloud_skiff"),
                            Component.translatable("advancement.aether_ii.cloud_skiff.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("cloud_skiff", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                            .vehicle(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.CLOUD_SKIFF.get()))
                            .movementAffectedBy(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIITags.Blocks.AERCLOUDS)))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "cloud_skiff"));

            AdvancementHolder aercloudGlider = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get(),
                            Component.translatable("advancement.aether_ii.aercloud_glider"),
                            Component.translatable("advancement.aether_ii.aercloud_glider.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("cold_aercloud_glider", itemUsed(ItemPredicate.Builder.item().of(items, AetherIIItems.COLD_AERCLOUD_GLIDER.get())))
                    .addCriterion("golden_aercloud_glider", itemUsed(ItemPredicate.Builder.item().of(items, AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get())))
                    .addCriterion("blue_aercloud_glider", itemUsed(ItemPredicate.Builder.item().of(items, AetherIIItems.BLUE_AERCLOUD_GLIDER.get())))
                    .addCriterion("purple_aercloud_glider", itemUsed(ItemPredicate.Builder.item().of(items, AetherIIItems.PURPLE_AERCLOUD_GLIDER.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aercloud_glider"));

            AdvancementHolder obtainEgg = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherIIBlocks.MOA_EGG.get(),
                            Component.translatable("advancement.aether_ii.obtain_egg"),
                            Component.translatable("advancement.aether_ii.obtain_egg.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.MOA_EGG.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "obtain_egg"));

            AdvancementHolder obtainPetal = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.AECHOR_PETAL.get(),
                            Component.translatable("advancement.aether_ii.obtain_petal"),
                            Component.translatable("advancement.aether_ii.obtain_petal.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("aechor_petal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.AECHOR_PETAL.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "obtain_petal"));

            AdvancementHolder skyrootLizard = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get(),
                            Component.translatable("advancement.aether_ii.skyroot_lizard"),
                            Component.translatable("advancement.aether_ii.skyroot_lizard.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("skyroot_lizard", itemUsedOnSpecificEntity(ItemPredicate.Builder.item().of(items, AetherIIItems.SKYROOT_STICK.get()), EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SKYROOT_LIZARD.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_lizard"));

            AdvancementHolder incubateMoa = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.MOA_FEATHER.get(),
                            Component.translatable("advancement.aether_ii.incubate_moa"),
                            Component.translatable("advancement.aether_ii.incubate_moa.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("incubate_moa", IncubationTrigger.Instance.incubate())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "incubate_moa"));

            Advancement.Builder.advancement()
                    .parent(incubateMoa)
                    .display(AetherIIItems.GRAVITITE_BOOTS.get(),
                            Component.translatable("advancement.aether_ii.explore_aether"),
                            Component.translatable("advancement.aether_ii.explore_aether.desc").withStyle(ChatFormatting.GOLD),
                            null,
                            AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("flourishing_field", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.FLOURISHING_FIELD))))
                    .addCriterion("verdant_woods", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.VERDANT_WOODS))))
                    .addCriterion("shrouded_forest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.SHROUDED_FOREST))))
                    .addCriterion("shimmering_basin", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.SHIMMERING_BASIN))))
                    .addCriterion("magnetic_scar", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.MAGNETIC_SCAR))))
                    .addCriterion("turquoise_forest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.TURQUOISE_FOREST))))
                    .addCriterion("glistening_swamp", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.GLISTENING_SWAMP))))
                    .addCriterion("violet_highwoods", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.VIOLET_HIGHWOODS))))
                    .addCriterion("frigid_sierra", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.FRIGID_SIERRA))))
                    .addCriterion("enduring_woodland", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.ENDURING_WOODLAND))))
                    .addCriterion("frozen_lakes", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.FROZEN_LAKES))))
                    .addCriterion("sheer_tundra", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.SHEER_TUNDRA))))
                    .addCriterion("contaminated_jungle", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.CONTAMINATED_JUNGLE))))
                    .addCriterion("battleground_wastes", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.BATTLEGROUND_WASTES))))
                    .addCriterion("hestveil_caverns", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.HESTVEIL_CAVERNS))))
                    //.addCriterion("expanse", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HighlandsBiomes.EXPANSE))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "explore_aether"));

            AdvancementHolder gravititePlate = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherIIItems.GRAVITITE_PLATE.get(),
                            Component.translatable("advancement.aether_ii.gravitite_plate"),
                            Component.translatable("advancement.aether_ii.gravitite_plate.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("gravitite_plate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_PLATE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gravitite_plate"));

            AdvancementHolder gravititeArmor = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIItems.GRAVITITE_CHESTPLATE.get(),
                            Component.translatable("advancement.aether_ii.gravitite_armor"),
                            Component.translatable("advancement.aether_ii.gravitite_armor.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("gravitite_armor", armorSet(AetherIITags.Items.GRAVITITE_ARMOR))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gravitite_armor"));

            AdvancementHolder arkeniumPlate = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherIIItems.ARKENIUM_PLATE.get(),
                            Component.translatable("advancement.aether_ii.arkenium_plate"),
                            Component.translatable("advancement.aether_ii.arkenium_plate.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("arkenium_plate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ARKENIUM_PLATE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "arkenium_plate"));

            AdvancementHolder alkahestCanister = Advancement.Builder.advancement()
                    .parent(arkeniumPlate)
                    .display(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get(),
                            Component.translatable("advancement.aether_ii.alkahest_canister"),
                            Component.translatable("advancement.aether_ii.alkahest_canister.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("alkahest_canister", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "alkahest_canister"));

            AdvancementHolder craftAlkahestPurifier = Advancement.Builder.advancement()
                    .parent(alkahestCanister)
                    .display(AetherIIBlocks.ALKAHEST_PURIFIER.get(),
                            Component.translatable("advancement.aether_ii.craft_alkahest_purifier"),
                            Component.translatable("advancement.aether_ii.craft_alkahest_purifier.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_alkahest_purifier", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ALKAHEST_PURIFIER.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "craft_alkahest_purifier"));

            AdvancementHolder irradiatedItem = Advancement.Builder.advancement()
                    .parent(alkahestCanister)
                    .display(AetherIIItems.IRRADIATED_WEAPON.get(),
                            Component.translatable("advancement.aether_ii.irradiated_item"),
                            Component.translatable("advancement.aether_ii.irradiated_item.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("irradiated_weapon", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_weapon"))))
                    .addCriterion("irradiated_tool", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_tool"))))
                    .addCriterion("irradiated_armor", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_armor"))))
                    .addCriterion("irradiated_chunk", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_chunk"))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "irradiated_item"));

            AdvancementHolder dartShooter = Advancement.Builder.advancement()
                    .parent(zanite)
                    .display(AetherIIItems.DART_SHOOTER.get(),
                            Component.translatable("advancement.aether_ii.dart_shooter"),
                            Component.translatable("advancement.aether_ii.dart_shooter.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("dart_shooter", EffectBuildupTrigger.Instance.effect(
                            Optional.of(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.AMBER_DART.get()).build()),
                            Optional.empty(),
                            mobEffects.getOrThrow(AetherIITags.MobEffects.DART_EFFECTS),
                            true))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dart_shooter"));

            AdvancementHolder corroboniteCrystal = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIItems.CORROBONITE_CRYSTAL.get(),
                            Component.translatable("advancement.aether_ii.corrobonite_crystal"),
                            Component.translatable("advancement.aether_ii.corrobonite_crystal.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("corrobonite_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.CORROBONITE_CRYSTAL.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "corrobonite_crystal"));

            AdvancementHolder craftArkeniumForge = Advancement.Builder.advancement()
                    .parent(corroboniteCrystal)
                    .display(AetherIIBlocks.ARKENIUM_FORGE.get(),
                            Component.translatable("advancement.aether_ii.craft_arkenium_forge"),
                            Component.translatable("advancement.aether_ii.craft_arkenium_forge.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_arkenium_forge", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ARKENIUM_FORGE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "craft_arkenium_forge"));

            AdvancementHolder charm = Advancement.Builder.advancement()
                    .parent(craftArkeniumForge)
                    .display(AetherIIItems.CHARM_OF_RESISTANCE_I.get(),
                            Component.translatable("advancement.aether_ii.charm"),
                            Component.translatable("advancement.aether_ii.charm.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("charm", ForgingCharmTrigger.Instance.charm())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charm"));

            AdvancementHolder slider = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIBlocks.SENTRY_BRICKS.get(),
                            Component.translatable("advancement.aether_ii.slider"),
                            Component.translatable("advancement.aether_ii.slider.desc").withStyle(ChatFormatting.GOLD),
                            null,
                            AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("kill_slider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SLIDER.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "slider"));

            AdvancementHolder demolitionHammerLoot = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.HAMMER_OF_DEMOLITION.get(),
                            Component.translatable("advancement.aether_ii.demolition_hammer_loot"),
                            Component.translatable("advancement.aether_ii.demolition_hammer_loot.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("demolition_hammer_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.HAMMER_OF_DEMOLITION))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "demolition_hammer_loot"));

            AdvancementHolder killGolemWithDemolitionHammer = Advancement.Builder.advancement()
                    .parent(demolitionHammerLoot)
                    .display(AetherIIItems.HAMMER_OF_DEMOLITION.get(),
                            Component.translatable("advancement.aether_ii.kill_golem_with_demolition_hammer"),
                            Component.translatable("advancement.aether_ii.kill_golem_with_demolition_hammer.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("killed_sentry_golem", KilledTrigger.TriggerInstance.playerKilledEntity(
                                    EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SENTRY_GOLEM.get()),
                                    DamageSourcePredicate.Builder.damageType().direct(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.DEMOLITION_PROJECTILE.get()))
                            )
                    )
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "kill_golem_with_demolition_hammer"));

            AdvancementHolder neptuneArmor = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.NEPTUNE_CHESTPLATE.get(),
                            Component.translatable("advancement.aether_ii.neptune_armor_loot"),
                            Component.translatable("advancement.aether_ii.neptune_armor_loot.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("neptune_armor", armorSet(AetherIITags.Items.NEPTUNE_ARMOR))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "neptune_armor_loot"));

            AdvancementHolder sentryBootsFall = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.SENTRY_BOOTS.get(),
                            Component.translatable("advancement.aether_ii.sentry_boots_fall"),
                            Component.translatable("advancement.aether_ii.sentry_boots_fall.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("sentry_boots_fall", fallDistance(
                            EntityPredicate.Builder.entity()
                                    .equipment(EntityEquipmentPredicate.Builder.equipment().feet(ItemPredicate.Builder.item().of(items, AetherIIItems.SENTRY_BOOTS.get())))
                                    .subPredicate(new AlivePredicate()),
                            DistancePredicate.vertical(MinMaxBounds.Doubles.atLeast(22.0))))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sentry_boots_fall"));
        }
    }

    public static AdvancementHolder createBestiaryAdvancement(AdvancementHolder parent, Consumer<AdvancementHolder> output) {
        Advancement.Builder bestiary = Advancement.Builder.advancement()
                .parent(parent)
                .display(AetherIIItems.GUIDEBOOK_PAGE.get(),
                        Component.translatable("advancement.aether_ii.bestiary"),
                        Component.translatable("advancement.aether_ii.bestiary.desc").withStyle(ChatFormatting.GOLD),
                        null,
                        AdvancementType.CHALLENGE, true, true, false);

        for (Holder<EntityType<?>> entry : AetherIIBestiaryEntries.ENTRY_ORDER) {
            EntityType<?> entityType = entry.value();
            ResourceLocation observeId = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix("bestiary/");

            EntityPredicate.Builder builder = EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().checkAdvancementDone(observeId, true).build());
            LootItemCondition condition = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, builder).build();
            bestiary = bestiary.addCriterion(entityType.toShortString(), CriteriaTriggers.TICK.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(ContextAwarePredicate.create(condition)))));
        }

        return bestiary.save(output, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "bestiary"));
    }

    public static Criterion<ConsumeItemTrigger.TriggerInstance> buildupReductionItemConsumed(ItemPredicate.Builder item, Holder<MobEffect> effect) {
        EntityPredicate.Builder builder = EntityPredicate.Builder.entity().subPredicate(new EffectBuildupPredicate(effect, Optional.empty()));
        LootItemCondition condition = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, builder).build();
        return CriteriaTriggers.CONSUME_ITEM.createCriterion(new ConsumeItemTrigger.TriggerInstance(Optional.of(ContextAwarePredicate.create(condition)), Optional.of(item.build())));
    }

    public static Criterion<UsingItemTrigger.TriggerInstance> itemUsed(ItemPredicate.Builder itemPredicate) {
        return CriteriaTriggers.USING_ITEM.createCriterion(new UsingItemTrigger.TriggerInstance(Optional.empty(), Optional.of(itemPredicate.build())));
    }

    public static Criterion<PlayerInteractTrigger.TriggerInstance> itemUsedOnSpecificEntity(ItemPredicate.Builder item, EntityPredicate.Builder entity) {
        return PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(), item, Optional.of(EntityPredicate.wrap(entity)));
    }

    public static Criterion<PlayerTrigger.TriggerInstance> armorSet(TagKey<Item> armor) {
        EntityPredicate.Builder builder = EntityPredicate.Builder.entity().subPredicate(new ArmorSetPredicate(armor));
        LootItemCondition condition = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, builder).build();
        return CriteriaTriggers.TICK.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(ContextAwarePredicate.create(condition))));
    }

    public static Criterion<DistanceTrigger.TriggerInstance> fallDistance(EntityPredicate.Builder player, DistancePredicate distance) {
        return CriteriaTriggers.FALL_FROM_HEIGHT.createCriterion(new DistanceTrigger.TriggerInstance(Optional.of(EntityPredicate.wrap(player)), Optional.empty(), Optional.of(distance)));
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
            return understand(itemGetter, entityGetter, builder.addCriterion("observe_" + entity.toShortString(), PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(entityGetter, entity)).build()))), entity);
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
                Advancement.Builder.advancement()
                        .requirements(AdvancementRequirements.Strategy.OR)
                        .addCriterion("obtain_" + effect.getKey().location().getPath(), EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(effect)))
                        .addCriterion("buildup_" + effect.getKey().location().getPath(), EffectBuildupTrigger.Instance.effect(Optional.empty(), Optional.empty(), HolderSet.direct(effect), false))
                        .save(consumer, id);
            }
        }
    }
}
