package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.providers.AetherIIItemModelProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class AetherIIItemModelData extends AetherIIItemModelProvider {
    public AetherIIItemModelData(PackOutput packOutput) {
        super(packOutput, AetherII.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Tools
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        // Spawn Eggs
        itemModels.generateSpawnEgg(AetherIIItems.FLYING_COW_SPAWN_EGG.get(), 0xC2C2C2, 0xFFDD61);
        itemModels.generateSpawnEgg(AetherIIItems.SHEEPUFF_SPAWN_EGG.get(), 0xE8F0F8, 0xA6D4FF);
        itemModels.generateSpawnEgg(AetherIIItems.PHYG_SPAWN_EGG.get(), 0xF7A6B1, 0xFFDD61);
        itemModels.generateSpawnEgg(AetherIIItems.AERBUNNY_SPAWN_EGG.get(), 0xE8F7FF, 0xFFD6F9);
        itemModels.generateSpawnEgg(AetherIIItems.HIGHFIELDS_TAEGORE_SPAWN_EGG.get(), 0xB2CCF2, 0xFFDE96);
        itemModels.generateSpawnEgg(AetherIIItems.MAGNETIC_TAEGORE_SPAWN_EGG.get(), 0x9DC2BE, 0xDBAD88);
        itemModels.generateSpawnEgg(AetherIIItems.ARCTIC_TAEGORE_SPAWN_EGG.get(), 0x797D97, 0xDEDEDE);
        itemModels.generateSpawnEgg(AetherIIItems.HIGHFIELDS_BURRUKAI_SPAWN_EGG.get(), 0x4E7EA8, 0x6C7080);
        itemModels.generateSpawnEgg(AetherIIItems.MAGNETIC_BURRUKAI_SPAWN_EGG.get(), 0x858071, 0x4C5667);
        itemModels.generateSpawnEgg(AetherIIItems.ARCTIC_BURRUKAI_SPAWN_EGG.get(), 0x786491, 0xB5C1E8);
        itemModels.generateSpawnEgg(AetherIIItems.HIGHFIELDS_KIRRID_SPAWN_EGG.get(), 0xADA896, 0xFFD787);
        itemModels.generateSpawnEgg(AetherIIItems.MAGNETIC_KIRRID_SPAWN_EGG.get(), 0x8788AF, 0xB1E0DC);
        itemModels.generateSpawnEgg(AetherIIItems.ARCTIC_KIRRID_SPAWN_EGG.get(), 0xC3C1BE, 0xAD9078);
        itemModels.generateSpawnEgg(AetherIIItems.MOA_SPAWN_EGG.get(), 0x91B2DB, 0xE8FCFF);
        itemModels.generateSpawnEgg(AetherIIItems.SKYROOT_LIZARD_SPAWN_EGG.get(), 0x595844, 0xD1F79E);
        itemModels.generateSpawnEgg(AetherIIItems.AECHOR_PLANT_SPAWN_EGG.get(), 0xCF95E2, 0x7477AB);
        itemModels.generateSpawnEgg(AetherIIItems.ZEPHYR_SPAWN_EGG.get(), 0xDEE6E7, 0xC4EFFF);
        itemModels.generateSpawnEgg(AetherIIItems.TEMPEST_SPAWN_EGG.get(), 0x676A7A, 0xDEEDFF);
        itemModels.generateSpawnEgg(AetherIIItems.COCKATRICE_SPAWN_EGG.get(), 0x8363A6, 0xB8FFC3);
        itemModels.generateSpawnEgg(AetherIIItems.SWET_SPAWN_EGG.get(), 0xC3E3EF, 0xA2D0CC);
        itemModels.generateSpawnEgg(AetherIIItems.SKEPHID_SPAWN_EGG.get(), 0x7D96AB, 0xF7CC94);
    }
}
