package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.advancement.predicate.KirridPredicate;
import com.aetherteam.aetherii.advancement.predicate.SheepuffPredicate;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Iterator;
import java.util.Map;
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
                ).withPool(createSheepuffDispatchPool(AetherIILoot.ENTITIES_SHEEPUFF_WOOL_BY_DYE))
        );

        Sheepuff.SheepuffColor.CLOUDWOOL_BY_SHEEPUFF_COLOR.forEach((color, lootTable) -> this.add(AetherIIEntityTypes.SHEEPUFF.get(), AetherIILoot.ENTITIES_SHEEPUFF_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(lootTable)))));

        this.add(AetherIIEntityTypes.HIGHFIELDS_TAEGORE.get(), createTaegoreTable());
        this.add(AetherIIEntityTypes.MAGNETIC_TAEGORE.get(), createTaegoreTable());
        this.add(AetherIIEntityTypes.ARCTIC_TAEGORE.get(), createTaegoreTable());

        this.add(AetherIIEntityTypes.HIGHFIELDS_BURRUKAI.get(), createBurrukaiTable());
        this.add(AetherIIEntityTypes.MAGNETIC_BURRUKAI.get(), createBurrukaiTable());
        this.add(AetherIIEntityTypes.ARCTIC_BURRUKAI.get(), createBurrukaiTable());

        this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), createKirridTable(AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_WOOL_BY_DYE));
        this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), createKirridTable(AetherIILoot.ENTITIES_MAGNETIC_KIRRID_WOOL_BY_DYE));
        this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), createKirridTable(AetherIILoot.ENTITIES_ARCTIC_KIRRID_WOOL_BY_DYE));

        Kirrid.KirridColor.CLOUDWOOL_BY_KIRRID_COLOR.forEach((color, lootTable) -> {
            this.add(AetherIIEntityTypes.HIGHFIELDS_KIRRID.get(), AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(lootTable))));
            this.add(AetherIIEntityTypes.MAGNETIC_KIRRID.get(), AetherIILoot.ENTITIES_MAGNETIC_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(lootTable))));
            this.add(AetherIIEntityTypes.ARCTIC_KIRRID.get(), AetherIILoot.ENTITIES_ARCTIC_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(lootTable))));
        });

        this.add(AetherIIEntityTypes.MOA.get(), LootTable.lootTable());

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
                        .add(LootItem.lootTableItem(AetherIIItems.COCKATRICE_FEATHER)
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
                        .add(LootItem.lootTableItem(AetherIIItems.SWET_SUGAR)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))
                                )
                        ))
        );

        this.add(AetherIIEntityTypes.SKEPHID.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.CLOUDTWINE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))
                                )
                        )
                ));

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

    protected LootTable.Builder createKirridTable(Map<Kirrid.KirridColor, ResourceKey<LootTable>> wool) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherIIItems.KIRRID_LOIN)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true)))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                ).withPool(createKirridDispatchPool(wool));
    }

    public static LootPool.Builder createKirridDispatchPool(Map<Kirrid.KirridColor, ResourceKey<LootTable>> map) {
        AlternativesEntry.Builder builder = AlternativesEntry.alternatives();
        Map.Entry<Kirrid.KirridColor, ResourceKey<LootTable>> entry;
        for (Iterator<Map.Entry<Kirrid.KirridColor, ResourceKey<LootTable>>> var2 = map.entrySet().iterator(); var2.hasNext(); builder = builder.otherwise(NestedLootTable.lootTableReference(entry.getValue()).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(KirridPredicate.hasWool(entry.getKey())))))) {
            entry = var2.next();
        }
        return LootPool.lootPool().add(builder);
    }

    public static LootPool.Builder createSheepuffDispatchPool(Map<Sheepuff.SheepuffColor, ResourceKey<LootTable>> map) {
        AlternativesEntry.Builder builder = AlternativesEntry.alternatives();
        Map.Entry<Sheepuff.SheepuffColor, ResourceKey<LootTable>> entry;
        for (Iterator<Map.Entry<Sheepuff.SheepuffColor, ResourceKey<LootTable>>> var2 = map.entrySet().iterator(); var2.hasNext(); builder = builder.otherwise(NestedLootTable.lootTableReference(entry.getValue()).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SheepuffPredicate.hasWool(entry.getKey())))))) {
            entry = var2.next();
        }
        return LootPool.lootPool().add(builder);
    }


    @Override
    public Stream<EntityType<?>> getKnownEntityTypes() {
        return AetherIIEntityTypes.ENTITY_TYPES.getEntries().stream().flatMap(entityType -> Stream.of(entityType.get()));
    }
}