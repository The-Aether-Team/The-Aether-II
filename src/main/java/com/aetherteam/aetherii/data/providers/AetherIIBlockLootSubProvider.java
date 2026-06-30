package com.aetherteam.aetherii.data.providers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.BlockLootAccessor;
import com.aetherteam.aetherii.loot.conditions.TierCompare;
import com.aetherteam.aetherii.loot.functions.SpawnSkyrootLizard;
import com.aetherteam.nitrogen.data.providers.NitrogenBlockLootSubProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.stream.IntStream;

public abstract class AetherIIBlockLootSubProvider extends NitrogenBlockLootSubProvider {
    public AetherIIBlockLootSubProvider(Set<Item> items, FeatureFlagSet flags) {
        super(items, flags);
    }

    public static final BooleanProperty GROWN = AetherIIBlockStateProperties.BRETTL_GROWN;

    protected LootTable.Builder droppingIrradiatedDustLoot(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(BlockLootAccessor.aether_ii$hasSilkTouch()).add(LootItem.lootTableItem(block)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(BlockLootAccessor.aether_ii$hasSilkTouch().invert())
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_WEAPON.get()))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_TOOL.get()))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_ARMOR.get()))
                        .add(LootItem.lootTableItem(AetherIIItems.IRRADIATED_CHUNK.get()))
                );
    }

    protected LootTable.Builder createSkyRootsDrops(Block block) {
        return this.createSilkTouchOrShearsDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch().invert())
                                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(AetherIIItems.ARCTIC_SNOWBALL.get())
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AetherHangingRootsBlock.SNOWY, true)))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                )));
    }

    public LootTable.Builder droppingSnowLayer(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                        .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
                        .add(AlternativesEntry.alternatives(
                                AlternativesEntry.alternatives(
                                        SnowLayerBlock.LAYERS.getPossibleValues(),
                                        layer -> ((LootPoolSingletonContainer.Builder<?>) LootItem.lootTableItem(AetherIIItems.ARCTIC_SNOWBALL.get())
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnowLayerBlock.LAYERS, layer))))
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) layer)))).when(BlockLootAccessor.aether_ii$hasSilkTouch().invert()),
                                AlternativesEntry.alternatives(
                                        SnowLayerBlock.LAYERS.getPossibleValues(),
                                        layer -> layer == 8 ? LootItem.lootTableItem(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get()) : LootItem.lootTableItem(AetherIIBlocks.ARCTIC_SNOW.get())
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) layer)))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnowLayerBlock.LAYERS, layer))))
                                )
                        )
        );
    }

    protected LootTable.Builder createQuartzOreDrops(Block block) {
        return this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block,
                        LootItem.lootTableItem(Items.QUARTZ)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                                .apply(SetNameFunction.setName(Component.translatable("item.aether_ii.aether_quartz")))
                )
        );
    }

    public LootTable.Builder droppingAmberoot(Block original, Block block, Item item) {
        return LootTable.lootTable()
                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(original)
                        .when(BlockLootAccessor.aether_ii$hasSilkTouch()))))
                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block)
                        .when(BlockLootAccessor.aether_ii$hasSilkTouch().invert()))))
                .withPool(this.applyExplosionDecay(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item)
                        .when(() -> new TierCompare("#" + AetherIITags.Items.GOLDEN_AMBER_HARVESTERS.location()))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.AXES)))
                        .when(BlockLootAccessor.aether_ii$hasSilkTouch().invert())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

    public LootTable.Builder droppingLeafPile(Block block, Block leaves) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .when(HAS_SHEARS)
                .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
                .add(AlternativesEntry.alternatives(
                        AetherLeafPileBlock.PILES.getPossibleValues(),
                        piles -> piles == 16 ? LootItem.lootTableItem(leaves) : LootItem.lootTableItem(block)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) piles)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AetherLeafPileBlock.PILES, piles))))
                )
        );
    }

    public LootTable.Builder droppingWithChancesAndSkyrootSticksWithLizard(Block block, Block sapling, float... chances) {
        return createForgeSilkTouchOrShearsDispatchTable(block, this.applyExplosionCondition( block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionDecay(block,
                                        LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).apply(SpawnSkyrootLizard.builder(block.builtInRegistryHolder()))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionCondition(block, LootItem.lootTableItem(AetherIIItems.SKYROOT_PINECONE.get()))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.01F, 0.011111112F, 0.0125F, 0.0111111125F, 0.05F))));
    }

    public LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
        return createForgeSilkTouchOrShearsDispatchTable(block, this.applyExplosionCondition( block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionDecay(block,
                                        LootItem.lootTableItem(AetherIIItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch().invert())
                                .add(this.applyExplosionCondition(block, LootItem.lootTableItem(AetherIIItems.SKYROOT_PINECONE.get()))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.01F, 0.011111112F, 0.0125F, 0.0111111125F, 0.05F))));
    }

    protected LootTable.Builder createSilkTouchOrShearsTable(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch()).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item)));
    }

    public LootTable.Builder droppingArilumBulbs(Block block, Item drop) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)).invert()))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS))))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)).when(BlockLootAccessor.aether_ii$hasShearsOrSilkTouch()));
    }

    public LootTable.Builder droppingSativalShoot(Block block, Item drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)))
        ).withPool(LootPool.lootPool().add(LootItem.lootTableItem(block)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)).invert()));
    }

    public LootTable.Builder droppingBerryBush(Block block, Item drop) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .when(BlockLootAccessor.aether_ii$hasSilkTouch().invert())
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block))
                        .when(BlockLootAccessor.aether_ii$hasSilkTouch())
                );
    }

    public LootTable.Builder droppingOrangeTree(Block block, Item drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OrangeTreeBlock.AGE, 4)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)))
        ).withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(block))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OrangeTreeBlock.AGE, 4)).invert())
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)))
        );
    }

    public LootTable.Builder droppingValkyrieSprout(Block block, Item drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ValkyrieSproutBlock.AGE, 2)))
        ).withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(block))
        );
    }

    protected LootTable.Builder droppingBrettlPlant(Block block, ItemLike drop, ItemLike dropGrown) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GROWN, false)))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(dropGrown)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GROWN, true)))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)))
                );
    }

    protected LootTable.Builder droppingBrettlPlantTip(Block block, ItemLike drop, ItemLike dropGrown) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GROWN, false)))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(dropGrown).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GROWN, true)))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherIITags.Items.TOOLS_TROWELS)))
                );
    }

    protected LootTable.Builder createSegmentedBlockDrops(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(block)
                        .apply(MossFlowersBlock.AMOUNT.getPossibleValues(), amount -> SetItemCountFunction.setCount(ConstantValue.exactly((float) amount))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MossFlowersBlock.AMOUNT, amount)))))));
    }

    protected LootTable.Builder dropTwigs(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(block)
                                                .apply(IntStream.rangeClosed(1, 4).boxed().toList(), count -> SetItemCountFunction.setCount(ConstantValue.exactly((float) count))
                                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TwigBlock.AMOUNT, count))))
                                )
                        )
        );
    }

    protected LootTable.Builder dropRocks(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(block)
                                .apply(IntStream.rangeClosed(1, 4).boxed().toList(), count -> SetItemCountFunction.setCount(ConstantValue.exactly((float) count))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RockBlock.AMOUNT, count))))
                        )
                )
        );
    }

    protected LootTable.Builder droppingMoaEgg(Block block) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("aether_ii_components", "aether_ii_components"))))
        );
    }
}
