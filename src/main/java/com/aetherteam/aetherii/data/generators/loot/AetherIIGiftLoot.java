package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.chicken.ChickenVariants;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class AetherIIGiftLoot implements LootTableSubProvider {
    protected final HolderLookup.Provider registries;

    public AetherIIGiftLoot(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
        builder.accept(AetherIILoot.TAEGORE_DIGGING, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK))
                        .add(LootItem.lootTableItem(AetherIIItems.SKYROOT_PINECONE))
                        .add(LootItem.lootTableItem(AetherIIItems.VALKYRIE_WINGS))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUEBERRY))
                        .add(LootItem.lootTableItem(AetherIIItems.SATIVAL_BULB))
                        .add(LootItem.lootTableItem(AetherIIBlocks.SKYROOT_TWIG.asItem()))
                        .add(LootItem.lootTableItem(AetherIIBlocks.HOLYSTONE_ROCK.asItem()))
        ));

        builder.accept(AetherIILoot.PRISMALLARD_LAY, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(AetherIIItems.PRISMALLARD_EGG))
                )
        );
    }
}