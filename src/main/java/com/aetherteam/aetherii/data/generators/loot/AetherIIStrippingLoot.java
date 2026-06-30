package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class AetherIIStrippingLoot implements LootTableSubProvider {
    public AetherIIStrippingLoot() {
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        BiConsumer<ResourceKey<LootTable>, LootTable.Builder> keyedBuilder = (key, table) -> builder.accept(key.location(), table);
        keyedBuilder.accept(AetherIILoot.STRIP_MOSSY_WISPROOT_BASE, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherIIBlocks.BRYALINN_MOSS_VINES.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        keyedBuilder.accept(AetherIILoot.STRIP_MOSSY_WISPROOT, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherIIBlocks.BRYALINN_MOSS_VINES.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        keyedBuilder.accept(AetherIILoot.STRIP_AMBEROOT_DEPOSIT, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AMBER.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
}