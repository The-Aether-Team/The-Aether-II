package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class AetherIIGiftLoot implements LootTableSubProvider {
    public AetherIIGiftLoot() {
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        BiConsumer<ResourceKey<LootTable>, LootTable.Builder> keyedBuilder = (key, table) -> builder.accept(key.location(), table);
        keyedBuilder.accept(AetherIILoot.TAEGORE_DIGGING, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK.get()))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_PINECONE.get()))
                        .add(LootItem.lootTableItem(AetherIIItems.VALKYRIE_WINGS.get()))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY.get()))
                        .add(LootItem.lootTableItem(AetherIIItems.SATIVAL_BULB.get()))
                        .add(LootItem.lootTableItem(AetherIIBlocks.SKYROOT_TWIG.get().asItem()))
                        .add(LootItem.lootTableItem(AetherIIBlocks.HOLYSTONE_ROCK.get().asItem()))
        ));

        keyedBuilder.accept(AetherIILoot.PRISMALLARD_LAY, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.PRISMALLARD_EGG.get()))
                )
        );
    }
}