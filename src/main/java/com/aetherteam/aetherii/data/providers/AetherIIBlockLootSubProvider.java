package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.block.natural.OrangeTreeBlock;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.BlockLootAccessor;
import com.aetherteam.nitrogen.data.providers.NitrogenBlockLootSubProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public abstract class AetherIIBlockLootSubProvider extends NitrogenBlockLootSubProvider {
    public AetherIIBlockLootSubProvider(Set<Item> items, FeatureFlagSet flags) {
        super(items, flags);
    }

    public LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
        return createForgeSilkTouchOrShearsDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionDecay(block,
                                        LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    public LootTable.Builder droppingBerryBush(Block block, Block stem, Item drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(BlockLootAccessor.aether_ii$hasSilkTouch().invert())
        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block))
                .when(BlockLootAccessor.aether_ii$hasSilkTouch())
        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(stem)
                        .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS).invert()))
        );
    }

    public LootTable.Builder droppingOrangeTree(Block block, Item drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OrangeTreeBlock.AGE, 4)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
        ).withPool(LootPool.lootPool()
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OrangeTreeBlock.AGE, 4)).invert())
                .add(LootItem.lootTableItem(block))
        );
    }
}