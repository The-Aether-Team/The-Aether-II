package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class AetherIIChestLoot implements LootTableSubProvider {
    protected final HolderLookup.Provider registries;

    public AetherIIChestLoot(HolderLookup.Provider registries) {
        this.registries = registries;
    }
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {

        builder.accept(AetherIILoot.CHESTS_CAMP_HIGHFIELDS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_CAMP_HIGHFIELDS_HUNTER))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_CAMP_HIGHFIELDS_FARMER))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_CAMP_HIGHFIELDS_FLETCHER))
                )
        );

        builder.accept(AetherIILoot.CHESTS_CAMP_HIGHFIELDS_HUNTER, LootTable.lootTable()
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
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_SHIELD).setWeight(7))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PLATE_SHIELD).setWeight(4))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_SHIELD))
                )
        );

        builder.accept(AetherIILoot.CHESTS_CAMP_HIGHFIELDS_FARMER, LootTable.lootTable()
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
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_TROWEL).setWeight(5))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_TROWEL).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_TROWEL))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_BUCKET))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_WATER_BUCKET))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_MILK_BUCKET))
                )
        );

        builder.accept(AetherIILoot.CHESTS_CAMP_HIGHFIELDS_FLETCHER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_BOLT).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_CROSSBOW).setWeight(9))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_CROSSBOW).setWeight(6))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_CROSSBOW))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_IRRADIATED_ITEMS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_ARMOR))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_WEAPON))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_TOOL))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_CHUNK))
                )
        );
        builder.accept(AetherIILoot.CHESTS_DUNGEONS_MUSIC_DISCS, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.MUSIC_DISC_AETHER_TUNE))
                        .add(LootItem.lootTableItem(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN))
                        .add(LootItem.lootTableItem(AetherIIItems.MUSIC_DISC_AERWHALE))
                        .add(LootItem.lootTableItem(AetherIIItems.MUSIC_DISC_APPROACHES))
                        .add(LootItem.lootTableItem(AetherIIItems.MUSIC_DISC_DEMISE))
                        .add(LootItem.lootTableItem(AetherIIItems.RECORDING_892))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_WORKSHOP_COMMON, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_SENTRY_WORKSHOP_RARE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.UNDERSHALE_BRICKS).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.SENTRY_BRICKS).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_TORCH).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.COCKATRICE_FEATHER).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_BLUEBERRY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_ORANGE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )

                        .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GEMSTONE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.CORROBONITE_CRYSTAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_PLATES).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.GLINT_GEMSTONE))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_WORKSHOP_RARE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.COLD_AERCLOUD_GLIDER).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUE_AERCLOUD_GLIDER))
                        .add(LootItem.lootTableItem(AetherIIItems.PURPLE_AERCLOUD_GLIDER))
                        .add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AERCLOUD_GLIDER))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_PICKAXE).setWeight(4))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_HAMMER).setWeight(3))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_PICKAXE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_HAMMER))
                        .add(LootItem.lootTableItem(AetherIIItems.MOA_SADDLE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_BUCKET).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_CANISTER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.HEALING_STONE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUD_SKIFF).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.HIDE_BUNDLE).setWeight(2))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_IRRADIATED_ITEMS).setWeight(2))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_MUSIC_DISCS).setWeight(2))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_WORKSHOP_MATERIAL_DEPOSIT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_SENTRY_WORKSHOP_RARE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.HOLYSTONE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 9.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.UNDERSHALE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 9.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AMBROSIUM_TORCH).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.ARKENIUM_LANTERN).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.COCKATRICE_FEATHER).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_BLUEBERRY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_ORANGE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GEMSTONE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.CORROBONITE_CRYSTAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_PLATES).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.GLINT_GEMSTONE))
                        .add(LootItem.lootTableItem(AetherIIItems.GRAVITITE_PLATE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.ZANITE_BLOCK))
                )
        );

        builder.accept(AetherIILoot.CHESTS_DUNGEONS_SENTRY_WORKSHOP_COLD_STORAGE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(NestedLootTable.lootTableReference(AetherIILoot.CHESTS_DUNGEONS_SENTRY_WORKSHOP_RARE))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherIIBlocks.ICESTONE).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.AECHOR_PETAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.COCKATRICE_FEATHER).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_GEL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ORANGE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_BLUEBERRY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_ORANGE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.WYNDBERRY).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ENCHANTED_WYNDBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_JELLY).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_SUGAR).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ANTITOXIN_VIAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.ANTIVENOM_VIAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.VALKYRIE_WINGS).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                .add(LootItem.lootTableItem(AetherIIItems.AMBROSIUM_SHARD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                .add(LootItem.lootTableItem(AetherIIItems.SCATTERGLASS_SHARD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))))
                .add(LootItem.lootTableItem(AetherIIItems.ZANITE_GEMSTONE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                .add(LootItem.lootTableItem(AetherIIItems.CORROBONITE_CRYSTAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_PLATES).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                .add(LootItem.lootTableItem(AetherIIItems.GLINT_GEMSTONE))
                .add(LootItem.lootTableItem(AetherIIItems.GRAVITITE_PLATE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )
        );
    }
}