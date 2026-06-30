package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.advancement.predicate.SheepuffPredicate;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class AetherIIShearingLoot implements LootTableSubProvider {
    public AetherIIShearingLoot() {
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        BiConsumer<ResourceKey<LootTable>, LootTable.Builder> keyedBuilder = (key, table) -> builder.accept(key.location(), table);
        Kirrid.KirridColor.CLOUDWOOL_BY_KIRRID_COLOR.forEach((color, wool) -> {
            keyedBuilder.accept(AetherIILoot.SHEARING_HIGHFIELDS_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool))));
            keyedBuilder.accept(AetherIILoot.SHEARING_MAGNETIC_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool))));
            keyedBuilder.accept(AetherIILoot.SHEARING_ARCTIC_KIRRID_WOOL_BY_DYE.get(color), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool))));
        });

        keyedBuilder.accept(AetherIILoot.SHEARING_HIGHFIELDS_KIRRID_WOOL_UNDYED, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherIIBlocks.CLOUDWOOL.get()))));
        keyedBuilder.accept(AetherIILoot.SHEARING_MAGNETIC_KIRRID_WOOL_UNDYED, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherIIBlocks.CLOUDWOOL.get()))));
        keyedBuilder.accept(AetherIILoot.SHEARING_ARCTIC_KIRRID_WOOL_UNDYED, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherIIBlocks.CLOUDWOOL.get()))));

        keyedBuilder.accept(AetherIILoot.SHEARING_HIGHFIELDS_KIRRID, LootTable.lootTable().withPool(AetherIIEntityLoot.createKirridDispatchPool(AetherIILoot.SHEARING_HIGHFIELDS_KIRRID_WOOL_BY_DYE, AetherIILoot.SHEARING_HIGHFIELDS_KIRRID_WOOL_UNDYED)));
        keyedBuilder.accept(AetherIILoot.SHEARING_MAGNETIC_KIRRID, LootTable.lootTable().withPool(AetherIIEntityLoot.createKirridDispatchPool(AetherIILoot.SHEARING_MAGNETIC_KIRRID_WOOL_BY_DYE, AetherIILoot.SHEARING_MAGNETIC_KIRRID_WOOL_UNDYED)));
        keyedBuilder.accept(AetherIILoot.SHEARING_ARCTIC_KIRRID, LootTable.lootTable().withPool(AetherIIEntityLoot.createKirridDispatchPool(AetherIILoot.SHEARING_ARCTIC_KIRRID_WOOL_BY_DYE, AetherIILoot.SHEARING_ARCTIC_KIRRID_WOOL_UNDYED)));

        Sheepuff.SheepuffColor.CLOUDWOOL_BY_SHEEPUFF_COLOR.forEach(
                (color, wool) -> keyedBuilder.accept(AetherIILoot.SHEARING_SHEEPUFF_WOOL_BY_DYE.get(color),
                        LootTable.lootTable()
                                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).add(LootItem.lootTableItem(wool)).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SheepuffPredicate.isPuffed(false)))))
                                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F)).add(LootItem.lootTableItem(wool)).when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(SheepuffPredicate.isPuffed(true)))))
                ));
        keyedBuilder.accept(AetherIILoot.SHEARING_SHEEPUFF, LootTable.lootTable().withPool(AetherIIEntityLoot.createSheepuffDispatchPool(AetherIILoot.SHEARING_SHEEPUFF_WOOL_BY_DYE)));
    }
}
