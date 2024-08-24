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
        );
        this.add(AetherIIEntityTypes.PHYG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.RAW_TAEGORE_MEAT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
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
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_PLAIN, createSheepuffTable(AetherIIBlocks.CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_BLACK, createSheepuffTable(AetherIIBlocks.BLACK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_BLUE, createSheepuffTable(AetherIIBlocks.BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_BROWN, createSheepuffTable(AetherIIBlocks.BROWN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_CYAN, createSheepuffTable(AetherIIBlocks.CYAN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_GRAY, createSheepuffTable(AetherIIBlocks.GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_GREEN, createSheepuffTable(AetherIIBlocks.GREEN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_LIGHT_BLUE, createSheepuffTable(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_LIGHT_GRAY, createSheepuffTable(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_LIME, createSheepuffTable(AetherIIBlocks.LIME_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_MAGENTA, createSheepuffTable(AetherIIBlocks.MAGENTA_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_ORANGE, createSheepuffTable(AetherIIBlocks.ORANGE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_PINK, createSheepuffTable(AetherIIBlocks.PINK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_PURPLE, createSheepuffTable(AetherIIBlocks.PURPLE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_RED, createSheepuffTable(AetherIIBlocks.RED_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_WHITE, createSheepuffTable(AetherIIBlocks.WHITE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_YELLOW, createSheepuffTable(AetherIIBlocks.YELLOW_CLOUDWOOL));

        this.add(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), createTaegoreTable());
        this.add(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), createTaegoreTable());
        this.add(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), createTaegoreTable());

        this.add(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), createBurrukaiTable());
        this.add(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), createBurrukaiTable());
        this.add(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), createBurrukaiTable());

        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), createKirridTable());
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_PLAIN, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_BLACK, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.BLACK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_BLUE, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_BROWN, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.BROWN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_CYAN, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.CYAN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_GRAY, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_GREEN, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.GREEN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_LIGHT_BLUE, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_LIGHT_GRAY, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_LIME, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.LIME_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_MAGENTA, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.MAGENTA_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_ORANGE, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.ORANGE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_PINK, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.PINK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_PURPLE, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.PURPLE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_RED, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.RED_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_WHITE, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.WHITE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_YELLOW, createKirridTable(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIIBlocks.YELLOW_CLOUDWOOL));

        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), createKirridTable());
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_PLAIN, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_BLACK, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.BLACK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_BLUE, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_BROWN, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.BROWN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_CYAN, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.CYAN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_GRAY, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_GREEN, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.GREEN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_LIGHT_BLUE, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_LIGHT_GRAY, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_LIME, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.LIME_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_MAGENTA, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.MAGENTA_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_ORANGE, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.ORANGE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_PINK, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.PINK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_PURPLE, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.PURPLE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_RED, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.RED_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_WHITE, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.WHITE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_YELLOW, createKirridTable(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIIBlocks.YELLOW_CLOUDWOOL));

        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), createKirridTable());
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_PLAIN, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_BLACK, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.BLACK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_BLUE, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_BROWN, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.BROWN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_CYAN, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.CYAN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_GRAY, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_GREEN, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.GREEN_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_LIGHT_BLUE, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_LIGHT_GRAY, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_LIME, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.LIME_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_MAGENTA, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.MAGENTA_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_ORANGE, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.ORANGE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_PINK, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.PINK_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_PURPLE, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.PURPLE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_RED, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.RED_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_WHITE, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.WHITE_CLOUDWOOL));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_YELLOW, createKirridTable(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIIBlocks.YELLOW_CLOUDWOOL));

        this.add(AetherIIEntityTypes.MOA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )
        );

        this.add(AetherIIEntityTypes.SKYROOT_LIZARD.get(), LootTable.lootTable());

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
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                         .add(LootItem.lootTableItem(AetherIIItems.CHARGE_CORE.get())
                                 .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                         )
                )
        );

        this.add(AetherIIEntityTypes.COCKATRICE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        )
                )
        );
        this.add(AetherIIEntityTypes.SWET.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.BLUE_SWET_GEL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.SUGAR)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))
                                )
                        ))
        );
    }

    protected LootTable.Builder createTaegoreTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.RAW_TAEGORE_MEAT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.TAEGORE_HIDE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
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

    protected LootTable.Builder createKirridTable(EntityType<?> entityType, ItemLike wool) {
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