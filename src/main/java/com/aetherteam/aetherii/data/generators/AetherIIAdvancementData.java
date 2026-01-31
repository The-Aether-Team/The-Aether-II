package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.advancement.trigger.IncubationTrigger;
import com.aetherteam.aetherii.advancement.trigger.OutpostCampfireTrigger;
import com.aetherteam.aetherii.advancement.trigger.SentryBootsFallTrigger;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsBiomes;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
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
            HolderGetter<Block> blocks = provider.lookupOrThrow(Registries.BLOCK);
            HolderGetter<Item> items = provider.lookupOrThrow(Registries.ITEM);
            HolderGetter<EntityType<?>> entityTypes = provider.lookupOrThrow(Registries.ENTITY_TYPE);
            HolderGetter<Biome> biomes = provider.lookupOrThrow(Registries.BIOME);

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

            AdvancementHolder ambrosium = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.AMBROSIUM_SHARD.get(),
                            Component.translatable("advancement.aether_ii.ambrosium"),
                            Component.translatable("advancement.aether_ii.ambrosium.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("ambrosium", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.AMBROSIUM_SHARD.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "ambrosium"));

            AdvancementHolder enchantedAetherGrass = Advancement.Builder.advancement()
                    .parent(ambrosium)
                    .display(AetherIIItems.ENCHANTED_BLUEBERRY.get(),
                            Component.translatable("advancement.aether_ii.enchanted_aether_grass"),
                            Component.translatable("advancement.aether_ii.enchanted_aether_grass.desc"),
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
                    .parent(ambrosium)
                    .display(AetherIIBlocks.ICESTONE.get(),
                            Component.translatable("advancement.aether_ii.icestone"),
                            Component.translatable("advancement.aether_ii.icestone.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("icestone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ICESTONE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "icestone"));

            AdvancementHolder antitoxin = Advancement.Builder.advancement()
                    .parent(icestone)
                    .display(AetherIIItems.ANTITOXIN_VIAL,
                            Component.translatable("advancement.aether_ii.antitoxin"),
                            Component.translatable("advancement.aether_ii.antitoxin.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("antitoxin", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ANTITOXIN_VIAL.get(), AetherIIItems.ANTIVENOM_VIAL.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "antitoxin"));

            AdvancementHolder engravedDiscs = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.MUSIC_PLAYER.get(),
                            Component.translatable("advancement.aether_ii.engraved_discs"),
                            Component.translatable("advancement.aether_ii.engraved_discs.desc"),
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
                            Component.translatable("advancement.aether_ii.blue_aercloud.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("blue_aercloud", EnterBlockTrigger.TriggerInstance.entersBlock(AetherIIBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "blue_aercloud"));

            AdvancementHolder outpostCampfire = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIBlocks.OUTPOST_CAMPFIRE.get(),
                            Component.translatable("advancement.aether_ii.outpost_campfire"),
                            Component.translatable("advancement.aether_ii.outpost_campfire.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("outpost_campfire", OutpostCampfireTrigger.Instance.forItem(ItemPredicate.Builder.item().of(items, Blocks.AIR).build()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "outpost_campfire"));

            AdvancementHolder glint = Advancement.Builder.advancement()
                    .parent(outpostCampfire)
                    .display(AetherIIItems.GLINT_COIN.get(),
                            Component.translatable("advancement.aether_ii.glint"),
                            Component.translatable("advancement.aether_ii.glint.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("glint", hasNumberofItem(64, AetherIIItems.GLINT_COIN.get())) //todo: make count 100 and make glint slot work
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "glint"));


            AdvancementHolder bestiary = Advancement.Builder.advancement()
                    .parent(outpostCampfire)
                    .display(AetherIIItems.GUIDEBOOK_PAGE.get(),
                            Component.translatable("advancement.aether_ii.bestiary"),
                            Component.translatable("advancement.aether_ii.bestiary.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("glint", hasNumberofItem(64, AetherIIItems.GLINT_COIN.get())) //.addCriterion("bestiary", PlayerPredicate .Builder.player().checkAdvancementDone(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "observe"), true))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "bestiary")); //todo



            AdvancementHolder cloudSkiff = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherIIItems.CLOUD_SKIFF.get(),
                            Component.translatable("advancement.aether_ii.cloud_skiff"),
                            Component.translatable("advancement.aether_ii.cloud_skiff.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("cloud_skiff", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.CLOUD_SKIFF.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "cloud_skiff"));

            AdvancementHolder aercloudGlider = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get(),
                            Component.translatable("advancement.aether_ii.aercloud_glider"),
                            Component.translatable("advancement.aether_ii.aercloud_glider.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("aercloud_glider", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.COLD_AERCLOUD_GLIDER.get(), AetherIIItems.BLUE_AERCLOUD_GLIDER.get(), AetherIIItems.PURPLE_AERCLOUD_GLIDER.get(), AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aercloud_glider"));

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

            AdvancementHolder skyrootLizard = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get(),
                            Component.translatable("advancement.aether_ii.skyroot_lizard"),
                            Component.translatable("advancement.aether_ii.skyroot_lizard.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("skyroot_lizard", itemUsedOnSpecificEntity(ItemPredicate.Builder.item().of(items, AetherIIItems.SKYROOT_STICK.get()), EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SKYROOT_LIZARD.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "skyroot_lizard"));

            AdvancementHolder incubateMoa = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.MOA_FEATHER.get(),
                            Component.translatable("advancement.aether_ii.incubate_moa"),
                            Component.translatable("advancement.aether_ii.incubate_moa.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("incubate_moa", IncubationTrigger.Instance.forItem(ItemPredicate.Builder.item().of(items, Blocks.AIR).build()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "incubate_moa"));

            Advancement.Builder.advancement()
                    .parent(incubateMoa)
                    .display(AetherIIItems.GRAVITITE_BOOTS.get(),
                            Component.translatable("advancement.aether_ii.explore_aether"),
                            Component.translatable("advancement.aether_ii.explore_aether.desc"),
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
                            Component.translatable("advancement.aether_ii.gravitite_plate.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("gravitite_plate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_PLATE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gravitite_plate"));

            AdvancementHolder gravititeArmor = Advancement.Builder.advancement()
                    .parent(gravititePlate)
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

            AdvancementHolder arkeniumPlates = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherIIItems.ARKENIUM_PLATES.get(),
                            Component.translatable("advancement.aether_ii.arkenium_plates"),
                            Component.translatable("advancement.aether_ii.arkenium_plates.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("arkenium_plates", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ARKENIUM_PLATES.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "arkenium_plates"));

            AdvancementHolder alkahestCanister = Advancement.Builder.advancement()
                    .parent(arkeniumPlates)
                    .display(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get(),
                            Component.translatable("advancement.aether_ii.alkahest_canister"),
                            Component.translatable("advancement.aether_ii.alkahest_canister.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("alkahest_canister", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "alkahest_canister"));

            AdvancementHolder craftAlkahestPurifier = Advancement.Builder.advancement()
                    .parent(alkahestCanister)
                    .display(AetherIIBlocks.ALKAHEST_PURIFIER.get(),
                            Component.translatable("advancement.aether_ii.craft_alkahest_purifier"),
                            Component.translatable("advancement.aether_ii.craft_alkahest_purifier.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_alkahest_purifier", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ALKAHEST_PURIFIER.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "craft_alkahest_purifier"));

            AdvancementHolder irradiatedItem = Advancement.Builder.advancement()
                    .parent(alkahestCanister)
                    .display(AetherIIItems.IRRADIATED_WEAPON.get(),
                            Component.translatable("advancement.aether_ii.irradiated_item"),
                            Component.translatable("advancement.aether_ii.irradiated_item.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("irradiated_item", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.IRRADIATED_WEAPON.get(), AetherIIItems.IRRADIATED_TOOL.get(), AetherIIItems.IRRADIATED_ARMOR.get(), AetherIIItems.IRRADIATED_CHUNK.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "irradiated_item"));

            AdvancementHolder dartShooter = Advancement.Builder.advancement()
                    .parent(zanite)
                    .display(AetherIIItems.DART_SHOOTER.get(),
                            Component.translatable("advancement.aether_ii.dart_shooter"),
                            Component.translatable("advancement.aether_ii.dart_shooter.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("dart_shooter", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.DART_SHOOTER.get()))
                    .addCriterion("amber_darts", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.AMBER_DARTS.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "dart_shooter"));

            AdvancementHolder corroboniteCrystal = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIItems.CORROBONITE_CRYSTAL.get(),
                            Component.translatable("advancement.aether_ii.corrobonite_crystal"),
                            Component.translatable("advancement.aether_ii.corrobonite_crystal.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("corrobonite_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.CORROBONITE_CRYSTAL.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "corrobonite_crystal"));

            AdvancementHolder craftArkeniumForge = Advancement.Builder.advancement()
                    .parent(corroboniteCrystal)
                    .display(AetherIIBlocks.ARKENIUM_FORGE.get(),
                            Component.translatable("advancement.aether_ii.craft_arkenium_forge"),
                            Component.translatable("advancement.aether_ii.craft_arkenium_forge.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_arkenium_forge", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ARKENIUM_FORGE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "craft_arkenium_forge"));

            AdvancementHolder charm = Advancement.Builder.advancement()
                    .parent(craftArkeniumForge)
                    .display(AetherIIItems.CHARM_OF_AGILITY_I.get(),
                            Component.translatable("advancement.aether_ii.charm"),
                            Component.translatable("advancement.aether_ii.charm.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("charm", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.CHARM_OF_RESISTANCE_I.get())) //todo
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "charm"));

            AdvancementHolder slider = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIBlocks.SENTRY_BRICKS.get(),
                            Component.translatable("advancement.aether_ii.slider"),
                            Component.translatable("advancement.aether_ii.slider.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("kill_slider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SLIDER.get())))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "slider"));

            AdvancementHolder demolitionHammerLoot = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.HAMMER_OF_DEMOLITION.get(),
                            Component.translatable("advancement.aether_ii.demolition_hammer_loot"),
                            Component.translatable("advancement.aether_ii.demolition_hammer_loot.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("demolition_hammer_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.HAMMER_OF_DEMOLITION))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "demolition_hammer_loot"));

            AdvancementHolder killGolemWithDemolitionHammer = Advancement.Builder.advancement()
                    .parent(demolitionHammerLoot)
                    .display(AetherIIItems.HAMMER_OF_DEMOLITION.get(),
                            Component.translatable("advancement.aether_ii.kill_golem_with_demolition_hammer"),
                            Component.translatable("advancement.aether_ii.kill_golem_with_demolition_hammer.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
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
                            Component.translatable("advancement.aether_ii.neptune_armor_loot.desc"),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("neptune_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.NEPTUNE_HELMET.get()))
                    .addCriterion("neptune_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.NEPTUNE_CHESTPLATE.get()))
                    .addCriterion("neptune_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.NEPTUNE_LEGGINGS.get()))
                    .addCriterion("neptune_boots", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.NEPTUNE_BOOTS.get()))
                    .addCriterion("neptune_gloves", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.NEPTUNE_GLOVES.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "neptune_armor_loot"));

            AdvancementHolder sentryBootsFall = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.SENTRY_BOOTS.get(),
                            Component.translatable("advancement.aether_ii.sentry_boots_fall"),
                            Component.translatable("advancement.aether_ii.sentry_boots_fall.desc"),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("sentry_boots_fall", SentryBootsFallTrigger.Instance.forItem(ItemPredicate.Builder.item().of(items, AetherIIItems.SENTRY_BOOTS).build()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sentry_boots_fall"));
        }
    }

    private static ItemUsedOnLocationTrigger.TriggerInstance itemUsedOnLocationCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
        ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.create(LocationCheck.checkLocation(location).build(), LocationCheck.checkLocation(above, BlockPos.ZERO.above()).build(), MatchTool.toolMatches(item).build());
        return new ItemUsedOnLocationTrigger.TriggerInstance(Optional.empty(), Optional.of(contextawarepredicate));
    }

    public static Criterion<ItemUsedOnLocationTrigger.TriggerInstance> itemUsedOnBlockCheckAbove(LocationPredicate.Builder location, LocationPredicate.Builder above, ItemPredicate.Builder item) {
        return CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(itemUsedOnLocationCheckAbove(location, above, item));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> hasNumberofItem(int count, ItemLike... items) { //todo
        ItemPredicate[] aitempredicate = new ItemPredicate[items.length];

        for (int i = 0; i < items.length; i++) {
            aitempredicate[i] = new ItemPredicate(
                    Optional.of(HolderSet.direct(items[i].asItem().builtInRegistryHolder())), MinMaxBounds.Ints.atLeast(count), DataComponentMatchers.ANY);
        }

        return InventoryChangeTrigger.TriggerInstance.hasItems(aitempredicate);
    }

    public static Criterion<PlayerInteractTrigger.TriggerInstance> itemUsedOnSpecificEntity(ItemPredicate.Builder item, EntityPredicate.Builder entity) {
        return PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(), item, Optional.of(EntityPredicate.wrap(entity)));
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
