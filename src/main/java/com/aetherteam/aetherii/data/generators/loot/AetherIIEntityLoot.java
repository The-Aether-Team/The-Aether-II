package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class AetherIIEntityLoot extends EntityLootSubProvider {
    public AetherIIEntityLoot(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        this.add(AetherIIEntityTypes.FLYING_COW.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_RIB_CUT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
        );
        this.add(AetherIIEntityTypes.PHYG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.RAW_TAEGORE_MEAT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
        );
        this.add(AetherIIEntityTypes.AERBUNNY.get(), LootTable.lootTable());

        this.add(AetherIIEntityTypes.SHEEPUFF.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.KIRRID_LOIN)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
        );
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_BLACK, createSheepuffTable(Blocks.BLACK_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_BLUE, createSheepuffTable(Blocks.BLUE_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_BROWN, createSheepuffTable(Blocks.BROWN_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_CYAN, createSheepuffTable(Blocks.CYAN_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_GRAY, createSheepuffTable(Blocks.GRAY_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_GREEN, createSheepuffTable(Blocks.GREEN_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_LIGHT_BLUE, createSheepuffTable(Blocks.LIGHT_BLUE_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_LIGHT_GRAY, createSheepuffTable(Blocks.LIGHT_GRAY_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_LIME, createSheepuffTable(Blocks.LIME_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_MAGENTA, createSheepuffTable(Blocks.MAGENTA_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_ORANGE, createSheepuffTable(Blocks.ORANGE_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_PINK, createSheepuffTable(Blocks.PINK_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_PURPLE, createSheepuffTable(Blocks.PURPLE_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_RED, createSheepuffTable(Blocks.RED_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_WHITE, createSheepuffTable(Blocks.WHITE_WOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_YELLOW, createSheepuffTable(Blocks.YELLOW_WOOL));

        this.add(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), createBurrukaiTable());
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), createKirridTable());
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), createKirridTable());
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), createKirridTable());
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.HIGHFIELDS_KIRRID_FUR, createKirridWithWoolTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.MAGNETIC_KIRRID_FUR, createKirridWithWoolTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ARCTIC_KIRRID_FUR, createKirridWithWoolTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.CLOUDWOOL));

        this.add(AetherIIEntityTypes.MOA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
        );

        this.add(AetherIIEntityTypes.AECHOR_PLANT.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.AECHOR_PETAL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        )
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIBlocks.AECHOR_CUTTING.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
        );

        this.add(AetherIIEntityTypes.ZEPHYR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIBlocks.COLD_AERCLOUD.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        )
                )
        );

        this.add(AetherIIEntityTypes.TEMPEST.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIBlocks.STORM_AERCLOUD.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        )
                )
        );
    }

    protected LootTable.Builder createBurrukaiTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_RIB_CUT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.BURRUKAI_PELT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                );
    }

    protected LootTable.Builder createKirridTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.KIRRID_LOIN)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                );
    }

    protected LootTable.Builder createKirridWithWoolTable(EntityType<?> entityType, ItemLike wool) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(wool)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(NestedLootTable.lootTableReference(entityType.getDefaultLootTable())));
    }

    private LootTable.Builder createSheepuffTable(ItemLike wool) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(wool)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(NestedLootTable.lootTableReference(AetherIIEntityTypes.SHEEPUFF.get().getDefaultLootTable())));
    }


    @Override
    public Stream<EntityType<?>> getKnownEntityTypes() {
        return AetherIIEntityTypes.ENTITY_TYPES.getEntries().stream().flatMap(entityType -> Stream.of(entityType.get()));
    }
}
