package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.advancement.predicate.SheepuffPredicate;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class AetherIIShearingLoot implements LootTableSubProvider {
    protected final HolderLookup.Provider registries;

    public AetherIIShearingLoot(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
        Kirrid.KirridColor.CLOUDWOOL_BY_KIRRID_COLOR.forEach(
                (color, wool) -> builder.accept(AetherIILoot.SHEARING_HIGHFIELDS_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool)))));
        Kirrid.KirridColor.CLOUDWOOL_BY_KIRRID_COLOR.forEach(
                (color, wool) -> builder.accept(AetherIILoot.SHEARING_MAGNETIC_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool)))));
        Kirrid.KirridColor.CLOUDWOOL_BY_KIRRID_COLOR.forEach(
                (color, wool) -> builder.accept(AetherIILoot.SHEARING_ARCTIC_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool)))));

        builder.accept(AetherIILoot.SHEARING_HIGHFIELDS_KIRRID, LootTable.lootTable().withPool(AetherIIEntityLoot.createKirridDispatchPool(AetherIILoot.SHEARING_HIGHFIELDS_KIRRID_WOOL_BY_DYE)));
        builder.accept(AetherIILoot.SHEARING_MAGNETIC_KIRRID, LootTable.lootTable().withPool(AetherIIEntityLoot.createKirridDispatchPool(AetherIILoot.SHEARING_MAGNETIC_KIRRID_WOOL_BY_DYE)));
        builder.accept(AetherIILoot.SHEARING_ARCTIC_KIRRID, LootTable.lootTable().withPool(AetherIIEntityLoot.createKirridDispatchPool(AetherIILoot.SHEARING_ARCTIC_KIRRID_WOOL_BY_DYE)));

        Sheepuff.SheepuffColor.CLOUDWOOL_BY_SHEEPUFF_COLOR.forEach(
                (color, wool) -> builder.accept(AetherIILoot.SHEARING_SHEEPUFF_WOOL_BY_DYE.get(color),
                        LootTable.lootTable()
                                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool)).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SheepuffPredicate.isPuffed())).invert()))
                                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F)).add(LootItem.lootTableItem(wool)).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SheepuffPredicate.isPuffed()))))
                ));
        builder.accept(AetherIILoot.SHEARING_SHEEPUFF, LootTable.lootTable().withPool(AetherIIEntityLoot.createSheepuffDispatchPool(AetherIILoot.SHEARING_SHEEPUFF_WOOL_BY_DYE)));
    }
}
