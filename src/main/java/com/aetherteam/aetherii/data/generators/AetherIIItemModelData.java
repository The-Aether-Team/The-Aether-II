package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
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

        this.generateReinforcedItem(itemModels, AetherIIItems.ZANITE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(itemModels, AetherIIItems.ZANITE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(itemModels, AetherIIItems.ZANITE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(itemModels, AetherIIItems.ZANITE_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_SHEARS.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        // Combat
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(itemModels, AetherIIItems.SKYROOT_CROSSBOW.get());
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_SHIELD.get(), ModelTemplates.FLAT_HANDHELD_ITEM); //TODO

        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(itemModels, AetherIIItems.HOLYSTONE_CROSSBOW.get());
        itemModels.generateFlatItem(AetherIIItems.HOLYSTONE_SHIELD.get(), ModelTemplates.FLAT_HANDHELD_ITEM); //TODO

        this.generateReinforcedItem(itemModels, AetherIIItems.ZANITE_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(itemModels, AetherIIItems.ZANITE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(itemModels, AetherIIItems.ZANITE_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(itemModels, AetherIIItems.ZANITE_CROSSBOW.get());
        itemModels.generateFlatItem(AetherIIItems.ZANITE_SHIELD.get(), ModelTemplates.FLAT_HANDHELD_ITEM); //TODO

        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(itemModels, AetherIIItems.ARKENIUM_CROSSBOW.get());
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_SHIELD.get(), ModelTemplates.FLAT_HANDHELD_ITEM); //TODO

        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(itemModels, AetherIIItems.GRAVITITE_CROSSBOW.get());
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_SHIELD.get(), ModelTemplates.FLAT_HANDHELD_ITEM); //TODO

        itemModels.generateFlatItem(AetherIIItems.SCATTERGLASS_BOLT.get(), ModelTemplates.FLAT_ITEM);

        // Armor
        this.generateDyedArmorItem(itemModels, AetherIIItems.TAEGORE_HIDE_HELMET.get(), -3150087);
        this.generateDyedArmorItem(itemModels, AetherIIItems.TAEGORE_HIDE_CHESTPLATE.get(), -3150087);
        this.generateDyedArmorItem(itemModels, AetherIIItems.TAEGORE_HIDE_LEGGINGS.get(), -3150087);
        this.generateDyedArmorItem(itemModels, AetherIIItems.TAEGORE_HIDE_BOOTS.get(), -3150087);
        this.generateDyedArmorItem(itemModels, AetherIIItems.TAEGORE_HIDE_GLOVES.get(), -3150087);

        this.generateDyedArmorItem(itemModels, AetherIIItems.BURRUKAI_PELT_HELMET.get(), -10380096);
        this.generateDyedArmorItem(itemModels, AetherIIItems.BURRUKAI_PELT_CHESTPLATE.get(), -10380096);
        this.generateDyedArmorItem(itemModels, AetherIIItems.BURRUKAI_PELT_LEGGINGS.get(), -10380096);
        this.generateDyedArmorItem(itemModels, AetherIIItems.BURRUKAI_PELT_BOOTS.get(), -10380096);
        this.generateDyedArmorItem(itemModels, AetherIIItems.BURRUKAI_PELT_GLOVES.get(), -10380096);

        itemModels.generateFlatItem(AetherIIItems.ZANITE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ZANITE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ZANITE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ZANITE_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ZANITE_GLOVES.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_GLOVES.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_GLOVES.get(), ModelTemplates.FLAT_ITEM);

        // Materials
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_STICK.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SCATTERGLASS_SHARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.AMBROSIUM_SHARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ZANITE_GEMSTONE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.INERT_ARKENIUM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_PLATES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.INERT_GRAVITITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GRAVITITE_PLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.CORROBONITE_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GLINT_GEMSTONE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GOLDEN_AMBER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.CLOUDTWINE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.TAEGORE_HIDE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BURRUKAI_PELT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_PINECONE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.VALKYRIE_WINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BRETTL_CANE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BRETTL_GRASS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BRETTL_ROPE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BRETTL_FLOWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARILUM_BULBS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.AECHOR_PETAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARCTIC_SNOWBALL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GREEN_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BLUE_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.PURPLE_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GOLDEN_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.WHITE_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SWET_SUGAR.get(), ModelTemplates.FLAT_ITEM);
        this.generateMoaFeatherItem(itemModels, AetherIIItems.MOA_FEATHER.get());
        itemModels.generateFlatItem(AetherIIItems.COCKATRICE_FEATHER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SCATTERGLASS_VIAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.CHARGE_CORE.get(), ModelTemplates.FLAT_ITEM);

        // Irradiated Items
        itemModels.generateFlatItem(AetherIIItems.IRRADIATED_ARMOR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.IRRADIATED_WEAPON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.IRRADIATED_TOOL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.IRRADIATED_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.IRRADIATED_DUST.get(), ModelTemplates.FLAT_ITEM);

        // Food
        itemModels.generateFlatItem(AetherIIItems.BLUEBERRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ENCHANTED_BLUEBERRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ORANGE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SATIVAL_BULB.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.WYNDBERRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ENCHANTED_WYNDBERRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GREEN_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BLUE_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.PURPLE_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GOLDEN_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.WHITE_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BURRUKAI_RIBS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BURRUKAI_RIB_CUT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.KIRRID_CUTLET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.KIRRID_LOIN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.RAW_TAEGORE_MEAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.TAEGORE_STEAK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK.get(), ModelTemplates.FLAT_ITEM);

        // Consumables
        itemModels.generateFlatItem(AetherIIItems.WATER_VIAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BANDAGE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SPLINT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ANTITOXIN_VIAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ANTIVENOM_VIAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.VALKYRIE_TEA.get(), ModelTemplates.FLAT_ITEM);
        this.generateHealingStoneItem(itemModels, AetherIIItems.HEALING_STONE.get());

        // Gliders
        itemModels.generateFlatItem(AetherIIItems.COLD_AERCLOUD_GLIDER.get(), ModelTemplates.FLAT_ITEM); //TODO
        itemModels.generateFlatItem(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BLUE_AERCLOUD_GLIDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get(), ModelTemplates.FLAT_ITEM);
//        this.gliderItem(AetherIIItems.COLD_AERCLOUD_GLIDER.get(), false);
//        this.gliderItem(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get(), false);
//        this.gliderItem(AetherIIItems.BLUE_AERCLOUD_GLIDER.get(), true);
//        this.gliderItem(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get(), true);
//
        // Skyroot Buckets
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_WATER_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_MILK_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_COD_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_SALMON_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_AXOLOTL_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.SKYROOT_TADPOLE_BUCKET.get(), ModelTemplates.FLAT_ITEM);

        // Arkenium Canisters
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_CANISTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_ACID_CANISTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ARKENIUM_GAS_CANISTER.get(), ModelTemplates.FLAT_ITEM);

        // Music Discs
        itemModels.generateFlatItem(AetherIIItems.MUSIC_DISC_AETHER_TUNE.get(), ModelTemplates.MUSIC_DISC);
        itemModels.generateFlatItem(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN.get(), ModelTemplates.MUSIC_DISC);
        itemModels.generateFlatItem(AetherIIItems.MUSIC_DISC_AERWHALE.get(), ModelTemplates.MUSIC_DISC);
        itemModels.generateFlatItem(AetherIIItems.MUSIC_DISC_APPROACHES.get(), ModelTemplates.MUSIC_DISC);
        itemModels.generateFlatItem(AetherIIItems.MUSIC_DISC_DEMISE.get(), ModelTemplates.MUSIC_DISC);
        itemModels.generateFlatItem(AetherIIItems.RECORDING_892.get(), ModelTemplates.MUSIC_DISC);

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

        // Misc
        this.generateMoaEggItem(itemModels, AetherIIItems.MOA_EGG.get());
        itemModels.generateFlatItem(AetherIIItems.MOA_FEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.BLUEBERRY_MOA_FEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.ENCHANTED_MOA_FEED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.MOA_SADDLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.GLINT_COIN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(AetherIIItems.AETHER_PORTAL_FRAME.get(), ModelTemplates.FLAT_ITEM);
//        itemModels.generateLayeredItem(AetherIIItems.AETHER_PORTAL_FRAME.get(), //todo
//                TextureMapping.getItemTexture(AetherIIItems.AETHER_PORTAL_FRAME.get()),
//                TextureMapping.getItemTexture(AetherIIItems.AETHER_PORTAL_FRAME.get()).withSuffix("_inside"));

        this.createFenceItem(blockModels, AetherIIBlocks.SKYROOT_FENCE.asItem(), AetherIIBlocks.SKYROOT_PLANKS.get());
    }
}
