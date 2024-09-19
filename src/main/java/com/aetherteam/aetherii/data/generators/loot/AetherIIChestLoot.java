package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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
                        .add(LootItem.lootTableItem(AetherIIItems.TAEGORE_HIDE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PELT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherIIBlocks.CLOUDWOOL).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_SPEAR).setWeight(5))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_SPEAR).setWeight(2))
                        .add(LootItem.lootTableItem(AetherIIItems.ZANITE_SPEAR))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_SHIELD).setWeight(7))
                        .add(LootItem.lootTableItem(AetherIIItems.HOLYSTONE_SHIELD).setWeight(4))
                        .add(LootItem.lootTableItem(AetherIIItems.ARKENIUM_SHIELD))
                )

                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(Blocks.AIR).setWeight(4))
                        .add(LootItem.lootTableItem(AetherIIItems.CHARGE_CORE))
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
    }
}