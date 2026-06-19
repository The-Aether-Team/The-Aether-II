package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.predicate.*;
import com.aetherteam.aetherii.advancement.trigger.*;
import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.api.guidebook.EffectsEntry;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEffectsEntries;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesBiomes;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
        super(output, registries, List.of(new HolyIslesAdvancements(), new BestiaryAdvancements(), new EffectsAdvancements()));
    }

    public static class HolyIslesAdvancements implements AdvancementSubProvider {
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
                            Component.translatable("advancement.aether_ii.the_holy_isles"),
                            Component.translatable("advancement.aether_ii.the_holy_isles.desc").withStyle(ChatFormatting.AQUA),
                            Identifier.fromNamespaceAndPath(AetherII.MODID, "block/holystone"),
                            AdvancementType.TASK, false, false, false)
                    .addCriterion("the_holy_isles", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "the_holy_isles"));

            AdvancementHolder enterAether = Advancement.Builder.advancement()
                    .parent(theAether)
                    .display(Blocks.GLOWSTONE,
                            Component.translatable("advancement.aether_ii.enter_holy_isles"),
                            Component.translatable("advancement.aether_ii.enter_holy_isles.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("enter_holy_isles", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "enter_holy_isles"));


            AdvancementHolder trowel = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.SKYROOT_TROWEL.get(),
                            Component.translatable("advancement.aether_ii.trowel"),
                            Component.translatable("advancement.aether_ii.trowel.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("break_satival_shoot", ItemBreakBlockTrigger.Instance.itemBrokeBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIIBlocks.SATIVAL_SHOOT.get())),
                            ItemPredicate.Builder.item().of(items, AetherIITags.Items.TOOLS_TROWELS)))
                    .addCriterion("break_berry_bush", ItemBreakBlockTrigger.Instance.itemBrokeBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIIBlocks.BLUEBERRY_BUSH.get())),
                            ItemPredicate.Builder.item().of(items, AetherIITags.Items.TOOLS_TROWELS)))
                    .addCriterion("break_orange_tree", ItemBreakBlockTrigger.Instance.itemBrokeBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIIBlocks.ORANGE_TREE.get())),
                            ItemPredicate.Builder.item().of(items, AetherIITags.Items.TOOLS_TROWELS)))
                    .addCriterion("break_brettl_plant", ItemBreakBlockTrigger.Instance.itemBrokeBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIIBlocks.BRETTL_PLANT.get())),
                            ItemPredicate.Builder.item().of(items, AetherIITags.Items.TOOLS_TROWELS)))
                    .addCriterion("break_valkyrie_sprout", ItemBreakBlockTrigger.Instance.itemBrokeBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIIBlocks.VALKYRIE_SPROUT.get())),
                            ItemPredicate.Builder.item().of(items, AetherIITags.Items.TOOLS_TROWELS)))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "trowel"));

            AdvancementHolder enchantedAetherGrass = Advancement.Builder.advancement()
                    .parent(trowel)
                    .display(AetherIIItems.ENCHANTED_BLUEBERRY.get(),
                            Component.translatable("advancement.aether_ii.enchanted_aether_grass"),
                            Component.translatable("advancement.aether_ii.enchanted_aether_grass.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("enchanted_aether_grass", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())),
                            ItemPredicate.Builder.item().of(items, AetherIIItems.AMBROSIUM_SHARD.get())))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "enchanted_aether_grass"));

            AdvancementHolder plantCutting = Advancement.Builder.advancement()
                    .parent(trowel)
                    .display(AetherIIBlocks.CARRION_CUTTING.get(),
                            Component.translatable("advancement.aether_ii.plant_cutting"),
                            Component.translatable("advancement.aether_ii.plant_cutting.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("aechor_cutting", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.AECHOR_CUTTING.get()))
                    .addCriterion("carrion_cutting", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.CARRION_CUTTING.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "plant_cutting"));

            AdvancementHolder goldenWyndberry = Advancement.Builder.advancement()
                    .parent(plantCutting)
                    .display(AetherIIItems.GOLDEN_WYNDBERRY.get(),
                            Component.translatable("advancement.aether_ii.golden_wyndberry"),
                            Component.translatable("advancement.aether_ii.golden_wyndberry.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("golden_wyndberry", itemUsedOnSpecificEntity(ItemPredicate.Builder.item().of(items, AetherIIItems.GOLDEN_AMBER.get()), EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.CARRION_SPROUT.get())))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "golden_wyndberry"));


            AdvancementHolder ambrosium = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.AMBROSIUM_SHARD.get(),
                            Component.translatable("advancement.aether_ii.ambrosium"),
                            Component.translatable("advancement.aether_ii.ambrosium.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("ambrosium", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.AMBROSIUM_SHARD.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "ambrosium"));

            AdvancementHolder goldenAmber = Advancement.Builder.advancement()
                    .parent(ambrosium)
                    .display(AetherIIItems.GOLDEN_AMBER.get(),
                            Component.translatable("advancement.aether_ii.golden_amber"),
                            Component.translatable("advancement.aether_ii.golden_amber.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("golden_amber", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GOLDEN_AMBER.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "golden_amber"));

            AdvancementHolder amberHourglass = Advancement.Builder.advancement()
                    .parent(goldenAmber)
                    .display(AetherIIBlocks.AMBER_HOURGLASS.get(),
                            Component.translatable("advancement.aether_ii.amber_hourglass"),
                            Component.translatable("advancement.aether_ii.amber_hourglass.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("amber_hourglass", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.AMBER_HOURGLASS.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "amber_hourglass"));

            AdvancementHolder zanite = Advancement.Builder.advancement()
                    .parent(amberHourglass)
                    .display(AetherIIItems.ZANITE_GEMSTONE.get(),
                            Component.translatable("advancement.aether_ii.zanite"),
                            Component.translatable("advancement.aether_ii.zanite.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("zanite", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ZANITE_GEMSTONE.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "zanite"));

            AdvancementHolder craftAltar = Advancement.Builder.advancement()
                    .parent(zanite)
                    .display(AetherIIBlocks.ALTAR.get(),
                            Component.translatable("advancement.aether_ii.craft_altar"),
                            Component.translatable("advancement.aether_ii.craft_altar.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_altar", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ALTAR.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "craft_altar"));

            AdvancementHolder icestone = Advancement.Builder.advancement()
                    .parent(ambrosium)
                    .display(AetherIIBlocks.ICESTONE.get(),
                            Component.translatable("advancement.aether_ii.icestone"),
                            Component.translatable("advancement.aether_ii.icestone.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("icestone", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ICESTONE.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "icestone"));

            AdvancementHolder antitoxin = Advancement.Builder.advancement()
                    .parent(icestone)
                    .display(AetherIIItems.ANTITOXIN_VIAL,
                            Component.translatable("advancement.aether_ii.antitoxin"),
                            Component.translatable("advancement.aether_ii.antitoxin.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("antitoxin_vial", buildupReductionItemConsumed(ItemPredicate.Builder.item().of(items, AetherIIItems.ANTITOXIN_VIAL), AetherIIMobEffects.TOXIN))
                    .addCriterion("antivenom_vial", buildupReductionItemConsumed(ItemPredicate.Builder.item().of(items, AetherIIItems.ANTIVENOM_VIAL), AetherIIMobEffects.VENOM))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "antitoxin"));

            AdvancementHolder engravedDiscs = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.MUSIC_PLAYER.get(),
                            Component.translatable("advancement.aether_ii.engraved_discs"),
                            Component.translatable("advancement.aether_ii.engraved_discs.desc").withStyle(ChatFormatting.GOLD),
                            null,
                            AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("ascending_dawn", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_ASCENDING_DAWN.get()))
                    .addCriterion("aerwhale", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_AERWHALE.get()))
                    .addCriterion("approaches", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_APPROACHES.get()))
                    .addCriterion("demise", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_DEMISE.get()))
                    .addCriterion("chinchilla", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_CHINCHILLA.get()))
                    .addCriterion("high", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_HIGH.get()))
                    .addCriterion("revolutions", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ENGRAVED_DISC_REVOLUTIONS.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "engraved_discs"));


            AdvancementHolder outpostCampfire = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIBlocks.OUTPOST_CAMPFIRE.get(),
                            Component.translatable("advancement.aether_ii.outpost_campfire"),
                            Component.translatable("advancement.aether_ii.outpost_campfire.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("outpost_campfire", OutpostCampfireTrigger.Instance.setSpawn())
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "outpost_campfire"));

            AdvancementHolder glint = Advancement.Builder.advancement()
                    .parent(outpostCampfire)
                    .display(AetherIIItems.GLINT_COIN.get(),
                            Component.translatable("advancement.aether_ii.glint"),
                            Component.translatable("advancement.aether_ii.glint.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("glint", CurrencyTrigger.Instance.forValue(1000))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "glint"));

            AdvancementHolder bestiary = createBestiaryAdvancement(outpostCampfire, consumer);



            AdvancementHolder aerbunny = Advancement.Builder.advancement()
                    .parent(enterAether)
                    .display(AetherIIItems.ORANGE.get(),
                            Component.translatable("advancement.aether_ii.aerbunny"),
                            Component.translatable("advancement.aether_ii.aerbunny.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("aerbunny", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().passenger(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.AERBUNNY.get()))))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "aerbunny"));

            AdvancementHolder aerbunnyBell = Advancement.Builder.advancement()
                    .parent(aerbunny)
                    .display(AetherIIItems.AERBUNNY_BELL.get(),
                            Component.translatable("advancement.aether_ii.aerbunny_bell"),
                            Component.translatable("advancement.aether_ii.aerbunny_bell.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("aerbunny_bell", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, AetherIIItems.AERBUNNY_BELL.get()).withComponents(DataComponentMatchers.Builder.components().any(AetherIIDataComponents.COMPANION_NBT.get()).build())))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "aerbunny_bell"));

            AdvancementHolder bedroll = Advancement.Builder.advancement()
                    .parent(aerbunny)
                    .display(AetherIIBlocks.CLOUDWOOL_BEDROLL.get(),
                            Component.translatable("advancement.aether_ii.bedroll"),
                            Component.translatable("advancement.aether_ii.bedroll.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("slept_in_bedroll", SleptInBedrollTrigger.Instance.sleptInBedroll())
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "bedroll"));

            AdvancementHolder blueAercloud = Advancement.Builder.advancement()
                    .parent(aerbunny)
                    .display(AetherIIBlocks.BLUE_AERCLOUD.get(),
                            Component.translatable("advancement.aether_ii.blue_aercloud"),
                            Component.translatable("advancement.aether_ii.blue_aercloud.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("blue_aercloud", EnterBlockTrigger.TriggerInstance.entersBlock(AetherIIBlocks.BLUE_AERCLOUD.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "blue_aercloud"));

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
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "cloud_skiff"));

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
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "aercloud_glider"));

            AdvancementHolder shiftingGlass = Advancement.Builder.advancement()
                    .parent(blueAercloud)
                    .display(AetherIIItems.SHIFTING_GLASS.get(),
                            Component.translatable("advancement.aether_ii.shifting_glass"),
                            Component.translatable("advancement.aether_ii.shifting_glass.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("shifting_glass", itemUsed(ItemPredicate.Builder.item().of(items, AetherIIItems.SHIFTING_GLASS.get())))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "shifting_glass"));

            AdvancementHolder obtainEgg = Advancement.Builder.advancement()
                    .parent(aerbunny)
                    .display(AetherIIBlocks.MOA_EGG.get(),
                            Component.translatable("advancement.aether_ii.obtain_egg"),
                            Component.translatable("advancement.aether_ii.obtain_egg.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("moa_egg", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.MOA_EGG.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "obtain_egg"));

            AdvancementHolder obtainPetal = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.AECHOR_PETAL.get(),
                            Component.translatable("advancement.aether_ii.obtain_petal"),
                            Component.translatable("advancement.aether_ii.obtain_petal.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("aechor_petal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.AECHOR_PETAL.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "obtain_petal"));

            AdvancementHolder moaFeed = Advancement.Builder.advancement()
                    .parent(obtainPetal)
                    .display(AetherIIItems.BLUEBERRY_MOA_FEED.get(),
                            Component.translatable("advancement.aether_ii.moa_feed"),
                            Component.translatable("advancement.aether_ii.moa_feed.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("feed_moa", FeedMoaTrigger.Instance.itemUsedOnEntity(ItemPredicate.Builder.item().of(items, AetherIITags.Items.MOA_FOOD)))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "moa_feed"));

            AdvancementHolder skyrootLizard = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get(),
                            Component.translatable("advancement.aether_ii.skyroot_lizard"),
                            Component.translatable("advancement.aether_ii.skyroot_lizard.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("skyroot_lizard", itemUsedOnSpecificEntity(ItemPredicate.Builder.item().of(items, AetherIIItems.SKYROOT_STICK.get()), EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SKYROOT_LIZARD.get())))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot_lizard"));

            AdvancementHolder incubateMoa = Advancement.Builder.advancement()
                    .parent(obtainEgg)
                    .display(AetherIIItems.MOA_FEATHER.get(),
                            Component.translatable("advancement.aether_ii.incubate_moa"),
                            Component.translatable("advancement.aether_ii.incubate_moa.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("incubate_moa", IncubationTrigger.Instance.incubate())
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "incubate_moa"));

            Advancement.Builder.advancement()
                    .parent(incubateMoa)
                    .display(AetherIIItems.GRAVITITE_BOOTS.get(),
                            Component.translatable("advancement.aether_ii.explore_aether"),
                            Component.translatable("advancement.aether_ii.explore_aether.desc").withStyle(ChatFormatting.GOLD),
                            null,
                            AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("flourishing_field", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.FLOURISHING_FIELD))))
                    .addCriterion("verdant_woods", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.VERDANT_WOODS))))
                    .addCriterion("shrouded_forest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.SHROUDED_FOREST))))
                    .addCriterion("shimmering_basin", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.SHIMMERING_BASIN))))
                    .addCriterion("magnetic_scar", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_SCAR))))
                    .addCriterion("turquoise_forest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.TURQUOISE_FOREST))))
                    .addCriterion("glistening_swamp", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.GLISTENING_SWAMP))))
                    .addCriterion("violet_highwoods", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.VIOLET_HIGHWOODS))))
                    .addCriterion("frigid_sierra", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.FRIGID_SIERRA))))
                    .addCriterion("enduring_woodland", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.ENDURING_WOODLAND))))
                    .addCriterion("frozen_lakes", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.FROZEN_LAKES))))
                    .addCriterion("sheer_tundra", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.SHEER_TUNDRA))))
                    .addCriterion("contaminated_jungle", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.CONTAMINATED_JUNGLE))))
                    .addCriterion("battleground_wastes", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.BATTLEGROUND_WASTES))))
                    .addCriterion("aercloud_sea", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.AERCLOUD_SEA))))
                    .addCriterion("highfields_expanse", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.HIGHFIELDS_EXPANSE))))
                    .addCriterion("magnetic_expanse", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_EXPANSE))))
                    .addCriterion("arctic_expanse", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.ARCTIC_EXPANSE))))
                    .addCriterion("irradiated_expanse", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.IRRADIATED_EXPANSE))))
                    .addCriterion("highfields_undercloud", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.HIGHFIELDS_UNDERCLOUD))))
                    .addCriterion("magnetic_undercloud", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.MAGNETIC_UNDERCLOUD))))
                    .addCriterion("arctic_undercloud", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.ARCTIC_UNDERCLOUD))))
                    .addCriterion("irradiated_undercloud", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.IRRADIATED_UNDERCLOUD))))
                    .addCriterion("hestveil_caverns", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(HolyIslesBiomes.HESTVEIL_CAVERNS))))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "explore_aether"));

            AdvancementHolder gravititePlate = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherIIItems.GRAVITITE_PLATE.get(),
                            Component.translatable("advancement.aether_ii.gravitite_plate"),
                            Component.translatable("advancement.aether_ii.gravitite_plate.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("gravitite_plate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.GRAVITITE_PLATE.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "gravitite_plate"));

            AdvancementHolder gravititeArmor = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIItems.GRAVITITE_CHESTPLATE.get(),
                            Component.translatable("advancement.aether_ii.gravitite_armor"),
                            Component.translatable("advancement.aether_ii.gravitite_armor.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("gravitite_armor", armorSet(AetherIITags.Items.GRAVITITE_ARMOR))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "gravitite_armor"));

            AdvancementHolder arkeniumPlate = Advancement.Builder.advancement()
                    .parent(craftAltar)
                    .display(AetherIIItems.ARKENIUM_PLATE.get(),
                            Component.translatable("advancement.aether_ii.arkenium_plate"),
                            Component.translatable("advancement.aether_ii.arkenium_plate.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("arkenium_plate", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ARKENIUM_PLATE.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "arkenium_plate"));

            AdvancementHolder alkahestCanister = Advancement.Builder.advancement()
                    .parent(arkeniumPlate)
                    .display(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get(),
                            Component.translatable("advancement.aether_ii.alkahest_canister"),
                            Component.translatable("advancement.aether_ii.alkahest_canister.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("alkahest_canister", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "alkahest_canister"));

            AdvancementHolder craftAlkahestPurifier = Advancement.Builder.advancement()
                    .parent(alkahestCanister)
                    .display(AetherIIBlocks.ALKAHEST_PURIFIER.get(),
                            Component.translatable("advancement.aether_ii.craft_alkahest_purifier"),
                            Component.translatable("advancement.aether_ii.craft_alkahest_purifier.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_alkahest_purifier", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ALKAHEST_PURIFIER.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "craft_alkahest_purifier"));

            AdvancementHolder irradiatedItem = Advancement.Builder.advancement()
                    .parent(craftAlkahestPurifier)
                    .display(AetherIIItems.IRRADIATED_WEAPON.get(),
                            Component.translatable("advancement.aether_ii.irradiated_item"),
                            Component.translatable("advancement.aether_ii.irradiated_item.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("irradiated_weapon", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_weapon"))))
                    .addCriterion("irradiated_tool", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_tool"))))
                    .addCriterion("irradiated_armor", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_armor"))))
                    .addCriterion("irradiated_chunk", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(AetherII.MODID, "purify_irradiated_chunk"))))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "irradiated_item"));

            AdvancementHolder dartShooter = Advancement.Builder.advancement()
                    .parent(goldenAmber)
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
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "dart_shooter"));

            AdvancementHolder corroboniteCrystal = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIItems.CORROBONITE_CRYSTAL.get(),
                            Component.translatable("advancement.aether_ii.corrobonite_crystal"),
                            Component.translatable("advancement.aether_ii.corrobonite_crystal.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("corrobonite_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.CORROBONITE_CRYSTAL.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "corrobonite_crystal"));

            AdvancementHolder craftArkeniumForge = Advancement.Builder.advancement()
                    .parent(corroboniteCrystal)
                    .display(AetherIIBlocks.ARKENIUM_FORGE.get(),
                            Component.translatable("advancement.aether_ii.craft_arkenium_forge"),
                            Component.translatable("advancement.aether_ii.craft_arkenium_forge.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("craft_arkenium_forge", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIBlocks.ARKENIUM_FORGE.get()))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "craft_arkenium_forge"));

            AdvancementHolder charm = Advancement.Builder.advancement()
                    .parent(craftArkeniumForge)
                    .display(AetherIIItems.CHARM_OF_RESISTANCE_I.get(),
                            Component.translatable("advancement.aether_ii.charm"),
                            Component.translatable("advancement.aether_ii.charm.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("charm", ForgingCharmTrigger.Instance.charm())
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "charm"));

            AdvancementHolder slider = Advancement.Builder.advancement()
                    .parent(gravititePlate)
                    .display(AetherIIBlocks.SENTRY_BRICKS.get(),
                            Component.translatable("advancement.aether_ii.slider"),
                            Component.translatable("advancement.aether_ii.slider.desc").withStyle(ChatFormatting.GOLD),
                            null,
                            AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("kill_slider", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, AetherIIEntityTypes.SLIDER.get())))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "slider"));

            AdvancementHolder demolitionHammerLoot = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.HAMMER_OF_DEMOLITION.get(),
                            Component.translatable("advancement.aether_ii.demolition_hammer_loot"),
                            Component.translatable("advancement.aether_ii.demolition_hammer_loot.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("demolition_hammer_loot", InventoryChangeTrigger.TriggerInstance.hasItems(AetherIIItems.HAMMER_OF_DEMOLITION))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "demolition_hammer_loot"));

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
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "kill_golem_with_demolition_hammer"));

            AdvancementHolder neptuneArmor = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.NEPTUNE_CHESTPLATE.get(),
                            Component.translatable("advancement.aether_ii.neptune_armor_loot"),
                            Component.translatable("advancement.aether_ii.neptune_armor_loot.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.GOAL, true, true, false)
                    .addCriterion("neptune_armor", armorSet(AetherIITags.Items.NEPTUNE_ARMOR))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "neptune_armor_loot"));

            AdvancementHolder sentryBootsFall = Advancement.Builder.advancement()
                    .parent(slider)
                    .display(AetherIIItems.SENTRY_BOOTS.get(),
                            Component.translatable("advancement.aether_ii.sentry_boots_fall"),
                            Component.translatable("advancement.aether_ii.sentry_boots_fall.desc").withStyle(ChatFormatting.AQUA),
                            null,
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("sentry_boots_fall", FallOnGroundTrigger.Instance.forValue(
                            EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().feet(ItemPredicate.Builder.item().of(items, AetherIIItems.SENTRY_BOOTS.get()))),
                            MinMaxBounds.Doubles.atLeast(22),
                            MinMaxBounds.Doubles.between(14.0, 20.0)
                    ))
                    .save(consumer, Identifier.fromNamespaceAndPath(AetherII.MODID, "sentry_boots_fall"));
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
            Identifier observeId = Identifier.fromNamespaceAndPath(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix("bestiary/");

            EntityPredicate.Builder builder = EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().checkAdvancementDone(observeId, true).build());
            LootItemCondition condition = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, builder).build();
            bestiary = bestiary.addCriterion(entityType.toShortString(), CriteriaTriggers.TICK.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(ContextAwarePredicate.create(condition)))));
        }

        return bestiary.save(output, Identifier.fromNamespaceAndPath(AetherII.MODID, "bestiary"));
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

    public static class BestiaryAdvancements implements AdvancementSubProvider {
        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            String path = "bestiary/";
            HolderGetter<EntityType<?>> entityGetter = provider.lookupOrThrow(Registries.ENTITY_TYPE);
            HolderGetter<Item> itemGetter = provider.lookupOrThrow(Registries.ITEM);
            for (Map.Entry<ResourceKey<BestiaryEntry>, Holder<EntityType<?>>> entry : AetherIIBestiaryEntries.ENTITIES.entrySet()) {
                EntityType<?> entityType = entry.getValue().value();
                Identifier observeId = Identifier.fromNamespaceAndPath(AetherII.MODID, "observe_" + entityType.toShortString()).withPrefix(path);
                observe(itemGetter, entityGetter, Advancement.Builder.advancement(), entityType).requirements(AdvancementRequirements.Strategy.OR).save(consumer, observeId);

//                Identifier understandId = Identifier.fromNamespaceAndPath(AetherII.MODID, "understand_" + entityType.toShortString()).withPrefix(path);
//                understand(itemGetter, entityGetter, Advancement.Builder.advancement(), entityType).requirements(AdvancementRequirements.Strategy.OR).save(consumer, understandId);
//                RewardWrapper understandWrapper = new RewardWrapper(understandId, entry.getKey().identifier(), List.of("test"));
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
                Identifier id = Identifier.fromNamespaceAndPath(AetherII.MODID, "obtain_" + effect.getKey().identifier().getPath()).withPrefix(path);
                Advancement.Builder.advancement()
                        .requirements(AdvancementRequirements.Strategy.OR)
                        .addCriterion("obtain_" + effect.getKey().identifier().getPath(), EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(effect)))
                        .addCriterion("buildup_" + effect.getKey().identifier().getPath(), EffectBuildupTrigger.Instance.effect(Optional.empty(), Optional.empty(), HolderSet.direct(effect), false))
                        .save(consumer, id);
            }
        }
    }
}
