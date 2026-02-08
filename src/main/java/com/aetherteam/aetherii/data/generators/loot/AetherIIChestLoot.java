package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BuildupContents;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetCustomDataFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class AetherIIChestLoot implements LootTableSubProvider {
    protected final HolderLookup.Provider registries;

    public AetherIIChestLoot(HolderLookup.Provider registries) {
        this.registries = registries;
    }
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {

        builder.accept(AetherIILoot.CHESTS_CAMP_SELECTOR, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_CAMP_HUNTER))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_CAMP_FARMER))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_CAMP_FLETCHER))
                )
        );

        builder.accept(AetherIILoot.CHESTS_CAMP_HUNTER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.KIRRID_LOIN).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.RAW_TAEGORE_MEAT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_RIBS).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.BEAST_PELT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.CLOUDWOOL).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_SPEAR).setWeight(5))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_SPEAR).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_SPEAR))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_SHIELD).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_SHIELD).setWeight(2))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.CLOUDWOOL_BEDROLL))
                        .add(LootItem.lootTableItem(AetherIIItems.BEAST_PELT_BUNDLE))
                )
        );

        builder.accept(AetherIILoot.CHESTS_CAMP_FARMER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.BRETTL_GRASS).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.VALKYRIE_WINGS).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.AECHOR_PETAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_PINECONE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_TROWEL).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_TROWEL).setWeight(1))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_BUCKET))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_WATER_BUCKET))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_MILK_BUCKET))
                )
        );

        builder.accept(AetherIILoot.CHESTS_CAMP_FLETCHER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_BOLT).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.MOA_FEATHER).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_CROSSBOW).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_CROSSBOW).setWeight(2))
                )
        );

        builder.accept(AetherIILoot.CHESTS_WATCHTOWER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_WATCHTOWER_CROSSBOW).setWeight(3))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_WATCHTOWER_DART_SHOOTER))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_TORCH).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SATIVAL_BULB).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIBlocks.CLOUDWOOL_BEDROLL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_CAMPFIRE))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_BUCKET))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.BRETTL_ROPE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )
        );
        builder.accept(AetherIILoot.CHESTS_WATCHTOWER_CROSSBOW, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_CROSSBOW).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_CROSSBOW).setWeight(2))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_BOLT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                )
        );
        builder.accept(AetherIILoot.CHESTS_WATCHTOWER_DART_SHOOTER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.DART_SHOOTER))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AMBER).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBER_DARTS).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBER_DARTS).apply(SetComponentsFunction.setComponent(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(EffectBuildupPresets.TOXIN))))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBER_DARTS).apply(SetComponentsFunction.setComponent(AetherIIDataComponents.BUILDUP_CONTENTS.get(), new BuildupContents(EffectBuildupPresets.VENOM))))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_IRRADIATED_ITEMS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_ARMOR))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_WEAPON))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_TOOL))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_CHUNK))
                )
        );
        builder.accept(AetherIILoot.CHESTS_DUNGEONS_MUSIC_DISCS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_AETHER_TUNE))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_ASCENDING_DAWN))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_AERWHALE))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_APPROACHES))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_DEMISE))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_CHINCHILLA))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_HIGH))
                        .add(LootItem.lootTableItem(AetherIIItems.ENGRAVED_DISC_REVOLUTIONS))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_RARE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_PICKAXE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_CROSSBOW).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_PICKAXE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_HAMMER))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_HELMET))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_LEGGINGS))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_BOOTS))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_GLOVES))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_SHIELD).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GLOVES))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_PENDANT).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.BEAST_PELT_BUNDLE).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.COLD_AERCLOUD_GLIDER).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AERCLOUD_GLIDER))
                        .add(LootItem.lootTableItem(AetherIIItems.MOA_SADDLE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.GUIDEBOOK_PAGE))
                        .add(LootItem.lootTableItem(AetherIIItems.HEALING_STONE).apply(SetComponentsFunction.setComponent(AetherIIDataComponents.HEALING_STONE_CHARGES.get(), 1)))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_MUSIC_DISCS))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_COMMON, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_RARE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_TORCH).setWeight(14).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_BOLT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_PICKAXE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_HAMMER))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_CROSSBOW))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AMBER).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GEMSTONE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SATIVAL_BULB).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_GEL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_SUGAR).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BEAST_PELT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.COCKATRICE_FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_MATERIAL_DEPOSIT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_RARE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_TORCH).setWeight(14).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_BOLT).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_PICKAXE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_SHOVEL).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_HAMMER))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_CROSSBOW))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.HOLYSTONE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.UNDERSHALE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AGIOSITE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.CRUDE_SCATTERGLASS).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).setWeight(12).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(9).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE).setWeight(9).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AMBER).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GEMSTONE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_BLOCK).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.GLINT_GEMSTONE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SATIVAL_BULB).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_GEL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_SUGAR).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.COCKATRICE_FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_COLD_STORAGE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_RARE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_TORCH).setWeight(12).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_BOLT).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_PICKAXE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_HAMMER).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_CROSSBOW))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.ICESTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AMBER).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GEMSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_GEL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_SUGAR).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BEAST_PELT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.COCKATRICE_FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_JELLY).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.WYNDBERRY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SATIVAL_BULB).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK))
                )

        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_RUINS_BOSS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_MUSIC_DISCS).setWeight(2))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_IRRADIATED_ITEMS))
                )

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_HELMET).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_CHESTPLATE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_LEGGINGS).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_BOOTS).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_GLOVES).setWeight(2))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.HAMMER_OF_DEMOLITION).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.SENTRY_BOOTS).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.KINETIC_THRUSTERS))
                )

                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_HELMET).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_CHESTPLATE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_LEGGINGS).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_BOOTS).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GLOVES))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_HELMET))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_CHESTPLATE))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_LEGGINGS))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_BOOTS))
                        .add(LootItem.lootTableItem(AetherIIItems.NEPTUNE_GLOVES))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_PICKAXE).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_HAMMER).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_PENDANT).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_SHIELD))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_HELMET))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_CHESTPLATE))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_LEGGINGS))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_BOOTS))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GLOVES))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_EFFICIENCY_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_DAMAGE_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_DEXTERITY_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_KNOCKBACK_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_HEALTH_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_DEFENSE_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_TOUGHNESS_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_RESISTANCE_I))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARM_OF_AGILITY_I))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_BLUEBERRY).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_ORANGE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.GLINT_COIN).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.GLINT_GEMSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GEMSTONE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.INERT_ARKENIUM))
                )
        );
    }
}