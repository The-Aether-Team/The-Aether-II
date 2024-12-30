package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.providers.AetherIIItemModelProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
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
//
//        // Blocks
//        // Dirt
//        this.itemBlock(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.AETHER_DIRT_PATH.get());
//        this.itemBlock(AetherIIBlocks.AETHER_DIRT.get());
//        this.itemBlock(AetherIIBlocks.COARSE_AETHER_DIRT.get());
//        this.itemBlock(AetherIIBlocks.AETHER_FARMLAND.get());
//        this.itemBlock(AetherIIBlocks.SHIMMERING_SILT.get());
//
//        // Underground
//        this.itemBlock(AetherIIBlocks.HOLYSTONE.get());
//        this.itemBlock(AetherIIBlocks.UNSTABLE_HOLYSTONE.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE.get());
//        this.itemBlock(AetherIIBlocks.UNSTABLE_UNDERSHALE.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE.get());
//        this.itemBlock(AetherIIBlocks.CRUDE_SCATTERGLASS.get());
//        this.itemBlockFlat(AetherIIBlocks.SKY_ROOTS.get(), "natural/");
//        this.itemBlock(AetherIIBlocks.ICHORITE.get());
//        this.pointedStone(AetherIIBlocks.POINTED_HOLYSTONE.get(), "natural/");
//        this.pointedStone(AetherIIBlocks.POINTED_ICHORITE.get(), "natural/");
//
//        // Highfields
//        this.itemBlock(AetherIIBlocks.QUICKSOIL.get());
//        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE.get());
//        this.itemBlock(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.BRYALINN_MOSS_CARPET.get());
//        this.itemBlockFlat(AetherIIBlocks.BRYALINN_MOSS_VINES.get(), "natural/");
//        this.itemBlockFlatItem(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get());
//        this.itemBlock(AetherIIBlocks.TANGLED_BRANCHES.get());
//
//        // Magnetic
//        this.itemBlock(AetherIIBlocks.FERROSITE_SAND.get());
//        this.itemBlock(AetherIIBlocks.FERROSITE_MUD.get());
//        this.itemBlock(AetherIIBlocks.FERROSITE.get());
//        this.itemBlock(AetherIIBlocks.RUSTED_FERROSITE.get());
//        this.itemBlockFlat(AetherIIBlocks.MAGNETIC_SHROOM.get(), "natural/");
//
//        // Arctic
//        this.itemBlock(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.ARCTIC_SNOW.get());
//        this.itemBlock(AetherIIBlocks.ARCTIC_ICE.get());
//        this.itemBlock(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE.get());
//        this.itemBlockFlat(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get(), "natural/");
//        this.itemBlock(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.SHAYELINN_MOSS_CARPET.get());
//        this.itemBlockFlat(AetherIIBlocks.SHAYELINN_MOSS_VINES.get(), "natural/");
//
//        // Irradiated
//        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_DUST_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.AMBRELINN_MOSS_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.AMBRELINN_MOSS_CARPET.get());
//        this.itemBlockFlat(AetherIIBlocks.AMBRELINN_MOSS_VINES.get(), "natural/");
//        this.itemBlockFlatItem(AetherIIBlocks.TARAHESP_FLOWERS.get());
//
//        // Ores
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get());
//        this.itemBlock(AetherIIBlocks.AMBROSIUM_ORE.get());
//        this.itemBlock(AetherIIBlocks.ZANITE_ORE.get());
//        this.itemBlock(AetherIIBlocks.GLINT_ORE.get());
//        this.itemBlock(AetherIIBlocks.ARKENIUM_ORE.get());
//        this.itemBlock(AetherIIBlocks.GRAVITITE_ORE.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_GLINT_ORE.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get());
//        this.itemBlock(AetherIIBlocks.CORROBONITE_ORE.get());
//        this.itemBlockFlat(AetherIIBlocks.CORROBONITE_CLUSTER.get(), "natural/");
//
//        // Aerclouds
//        this.aercloudItem(AetherIIBlocks.COLD_AERCLOUD.get());
//        this.aercloudItem(AetherIIBlocks.BLUE_AERCLOUD.get());
//        this.aercloudItem(AetherIIBlocks.GOLDEN_AERCLOUD.get());
//        this.aercloudItem(AetherIIBlocks.GREEN_AERCLOUD.get());
//        this.itemBlock(AetherIIBlocks.PURPLE_AERCLOUD.get());
//        this.aercloudItem(AetherIIBlocks.STORM_AERCLOUD.get());
//
//        // Moa Nest
//        this.itemBlock(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());
//
//        // Logs
//        this.itemBlock(AetherIIBlocks.SKYROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.AMBEROOT_LOG.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_WOOD.get());
//        this.itemBlock(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_WOOD.get());
//        this.itemBlock(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_WOOD.get());
//        this.itemBlock(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get());
//        this.itemBlock(AetherIIBlocks.AMBEROOT_WOOD.get());
//
//        // Leaf Pile
//        this.itemBlock(AetherIIBlocks.SKYROOT_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.SKYPLANE_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.SKYPINE_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.WISPTOP_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.GREATOAK_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.GREATBOA_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.AMBEROOT_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get());
//
//        // Leaves
//        this.itemBlock(AetherIIBlocks.SKYROOT_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.SKYPLANE_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.SKYBIRCH_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.SKYPINE_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.WISPTOP_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.GREATOAK_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.GREATBOA_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.AMBEROOT_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());
//
//        // Saplings
//        this.itemBlockFlat(AetherIIBlocks.SKYROOT_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.SKYPLANE_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.SKYBIRCH_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.SKYPINE_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.WISPROOT_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.GREATROOT_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.GREATOAK_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.GREATBOA_SAPLING.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.AMBEROOT_SAPLING.get(), "natural/");
//
//        // Grasses
//        this.itemBlockGrass(AetherIIBlocks.AETHER_SHORT_GRASS.get(), "natural/");
//        this.itemBlockGrass(AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), "natural/");
//        this.itemBlockGrass(AetherIIBlocks.AETHER_LONG_GRASS.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.HIGHLAND_FERN.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.SHIELD_FERN.get(), "natural/");
//
//        // Flowers
//        this.itemBlockFlat(AetherIIBlocks.HESPEROSE.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.TARABLOOM.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.POASPROUT.get(), "natural/");
//        this.itemBlockFlatItem(AetherIIBlocks.LILICHIME.get());
//        this.itemBlockFlatItem(AetherIIBlocks.PLURACIAN.get());
//        this.itemBlockFlat0(AetherIIBlocks.SATIVAL_SHOOT.get());
//        this.itemBlockFlatItem(AetherIIBlocks.HOLPUPEA.get());
//        this.itemBlockFlat0(AetherIIBlocks.BLADE_POA.get());
//        this.itemBlockFlat(AetherIIBlocks.AECHOR_CUTTING.get(), "natural/");
//
//        // Bushes
//        this.itemBlock(AetherIIBlocks.HIGHLANDS_BUSH.get());
//        this.itemBlock(AetherIIBlocks.BLUEBERRY_BUSH.get());
//        this.itemBlockFlat(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), "natural/");
//
//        // Orange Tree
//        this.orangeTree(AetherIIBlocks.ORANGE_TREE.get());
//
//        // Valkyrie Sprout
//        this.itemBlockFlat0(AetherIIBlocks.VALKYRIE_SPROUT.get());
//
//        // Lake
//        this.itemBlockFlat(AetherIIBlocks.ARILUM.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.ARILUM_PLANT.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.BLOOMING_ARILUM.get(), "natural/");
//        this.itemBlockFlat(AetherIIBlocks.BLOOMING_ARILUM_PLANT.get(), "natural/");
//
//        // Ground Decoration
//        itemModels.generateFlatItem(AetherIIBlocks.SKYROOT_TWIG.asItem(), "miscellaneous/");
//        itemModels.generateFlatItem(AetherIIBlocks.HOLYSTONE_ROCK.asItem(), "miscellaneous/");
//
//        // Skyroot Planks
//        this.itemBlock(AetherIIBlocks.SKYROOT_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_SLAB.get());
//        this.itemFence(AetherIIBlocks.SKYROOT_FENCE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
//        this.itemBlock(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
//        itemModels.generateFlatItem(AetherIIBlocks.SKYROOT_DOOR.get().asItem(), "miscellaneous/");
//        this.itemBlock(AetherIIBlocks.SKYROOT_TRAPDOOR.get(), "_bottom");
//        this.itemButton(AetherIIBlocks.SKYROOT_BUTTON.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
//        this.itemBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());
//
//        // Skyroot Decorative Blocks
//        this.itemBlock(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_SHINGLES.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_BASE_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_TOP_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_BASE_BEAM.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_TOP_BEAM.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_BEAM.get());
//        itemModels.generateFlatItem(AetherIIBlocks.SECRET_SKYROOT_DOOR.get().asItem(), "miscellaneous/");
//        this.itemBlock(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(), "_bottom");
//
//        // Greatroot Planks
//        this.itemBlock(AetherIIBlocks.GREATROOT_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_SLAB.get());
//        this.itemFence(AetherIIBlocks.GREATROOT_FENCE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
//        this.itemBlock(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
//        itemModels.generateFlatItem(AetherIIBlocks.GREATROOT_DOOR.get().asItem(), "miscellaneous/");
//        this.itemBlock(AetherIIBlocks.GREATROOT_TRAPDOOR.get(), "_bottom");
//        this.itemButton(AetherIIBlocks.GREATROOT_BUTTON.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
//        this.itemBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());
//
//        // Greatroot Decorative Blocks
//        this.itemBlock(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_SHINGLES.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_BASE_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_TOP_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_BASE_BEAM.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_TOP_BEAM.get());
//        this.itemBlock(AetherIIBlocks.GREATROOT_BEAM.get());
//        itemModels.generateFlatItem(AetherIIBlocks.SECRET_GREATROOT_DOOR.get().asItem(), "miscellaneous/");
//        this.itemBlock(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(), "_bottom");
//
//        // Wisproot Planks
//        this.itemBlock(AetherIIBlocks.WISPROOT_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_SLAB.get());
//        this.itemFence(AetherIIBlocks.WISPROOT_FENCE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
//        this.itemBlock(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
//        itemModels.generateFlatItem(AetherIIBlocks.WISPROOT_DOOR.get().asItem(), "miscellaneous/");
//        this.itemBlock(AetherIIBlocks.WISPROOT_TRAPDOOR.get(), "_bottom");
//        this.itemButton(AetherIIBlocks.WISPROOT_BUTTON.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
//        this.itemBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());
//
//        // Wisproot Decorative Blocks
//        this.itemBlock(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_SHINGLES.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_BASE_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_TOP_PLANKS.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_BASE_BEAM.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_TOP_BEAM.get());
//        this.itemBlock(AetherIIBlocks.WISPROOT_BEAM.get());
//        itemModels.generateFlatItem(AetherIIBlocks.SECRET_WISPROOT_DOOR.get().asItem(), "miscellaneous/");
//        this.itemBlock(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get(), "_bottom");
//
//        // Holystone
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
//        this.itemButton(AetherIIBlocks.HOLYSTONE_BUTTON.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());
//
//        // Mossy Holystone
//        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");
//
//        // Irradiated Holystone
//        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), "natural/");
//
//        // Holystone Bricks
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");
//
//        // Holystone Decorative Blocks
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_PILLAR.get());
//
//        // Faded Holystone Bricks
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), "construction/");
//
//        // Faded Holystone Decorative Blocks
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get());
//
//        // Undershale
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");
//
//        // Undershale Bricks
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_BRICK_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.UNDERSHALE_BRICK_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.UNDERSHALE_BRICK_WALL.get(), AetherIIBlocks.UNDERSHALE_BRICKS.get(), "construction/");
//
//        // Agiosite
//        this.itemBlock(AetherIIBlocks.AGIOSITE_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");
//
//        // Agiosite Bricks
//        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");
//
//        // Agiosite Decorative Blocks
//        this.itemBlock(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.AGIOSITE_PILLAR.get());
//
//        // Icestone Bricks
//        this.itemBlock(AetherIIBlocks.ICESTONE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_BRICK_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_BRICK_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.ICESTONE_BRICK_WALL.get(), AetherIIBlocks.ICESTONE_BRICKS.get(), "construction/");
//
//        // Icestone Decorative Blocks
//        this.itemBlock(AetherIIBlocks.ICESTONE_FLAGSTONES.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_KEYSTONE.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_BASE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_BASE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_PILLAR.get());
//
//        // Icestone
//        this.itemBlock(AetherIIBlocks.ICESTONE_STAIRS.get());
//        this.itemBlock(AetherIIBlocks.ICESTONE_SLAB.get());
//        this.itemWallBlock(AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get(), "natural/");
//
//        // Glass
//        this.itemBlock(AetherIIBlocks.QUICKSOIL_GLASS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS.get());
//        this.itemBlock(AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get());
//        this.itemBlock(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get());
//        this.itemBlock(AetherIIBlocks.SCATTERGLASS.get());
//        this.itemBlock(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get());
//        this.itemBlock(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get());
//
//        // Glass Panes
//        this.pane(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");
//        this.pane(AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS.get(), "decorative/");
//        this.pane(AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS.get(), "decorative/");
//        this.pane(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.CRUDE_SCATTERGLASS.get(), "natural/");
//        this.pane(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get(), "decorative/");
//        this.pane(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get(), "decorative/");
//        this.pane(AetherIIBlocks.SCATTERGLASS_PANE.get(), AetherIIBlocks.SCATTERGLASS.get(), "construction/");
//        this.pane(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(), "decorative/");
//        this.pane(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get(), "decorative/");
//
//        // Wool
//        this.itemBlock(AetherIIBlocks.CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.WHITE_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.ORANGE_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.MAGENTA_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.YELLOW_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.LIME_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.PINK_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.GRAY_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.CYAN_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.PURPLE_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.BLUE_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.BROWN_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.GREEN_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.RED_CLOUDWOOL.get());
//        this.itemBlock(AetherIIBlocks.BLACK_CLOUDWOOL.get());
//
//        // Carpet
//        this.itemBlock(AetherIIBlocks.CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.RED_CLOUDWOOL_CARPET.get());
//        this.itemBlock(AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get());
//
//        // Arkenium Blocks
//        itemModels.generateFlatItem(AetherIIBlocks.ARKENIUM_DOOR.get().asItem(), "miscellaneous/");
//        this.itemBlock(AetherIIBlocks.ARKENIUM_TRAPDOOR.get(), "_bottom");
//
//        // Mineral Blocks
//        this.itemBlock(AetherIIBlocks.AMBROSIUM_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.ZANITE_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.ARKENIUM_BLOCK.get());
//        this.itemBlock(AetherIIBlocks.GRAVITITE_BLOCK.get());
//
//        // Arilum Lanterns
//        this.itemBlock(AetherIIBlocks.GREEN_ARILUM_LANTERN.get());
//        this.itemBlock(AetherIIBlocks.BLUE_ARILUM_LANTERN.get());
//        this.itemBlock(AetherIIBlocks.PURPLE_ARILUM_LANTERN.get());
//        this.itemBlock(AetherIIBlocks.GOLDEN_ARILUM_LANTERN.get());
//        this.itemBlock(AetherIIBlocks.WHITE_ARILUM_LANTERN.get());
//
//        // Utility
//        this.itemBlockFlat(AetherIIBlocks.AMBROSIUM_TORCH.get(), "utility/");
//        this.itemBlock(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_FURNACE.get());
//        this.itemBlock(AetherIIBlocks.ARTISANS_BENCH.get());
//        this.itemBlock(AetherIIBlocks.ALTAR.get());
//        this.itemBlock(AetherIIBlocks.ARKENIUM_FORGE.get());
//        this.lookalikeBlock(AetherIIBlocks.SKYROOT_CHEST.get(), this.mcLoc("item/chest"));
//        this.itemBlockFlat(AetherIIBlocks.SKYROOT_LADDER.get(), "construction/");
//        this.lookalikeBlock(AetherIIBlocks.SKYROOT_BED.get(), this.mcLoc("item/template_bed"));
//
//        itemModels.generateFlatItem(AetherIIBlocks.SKYROOT_SIGN.get().asItem(), "miscellaneous/");
//        itemModels.generateFlatItem(AetherIIBlocks.SKYROOT_HANGING_SIGN.get().asItem(), "miscellaneous/");
////
//        itemModels.generateFlatItem(AetherIIBlocks.GREATROOT_SIGN.get().asItem(), "miscellaneous/");
//        itemModels.generateFlatItem(AetherIIBlocks.GREATROOT_HANGING_SIGN.get().asItem(), "miscellaneous/");
////
//        itemModels.generateFlatItem(AetherIIBlocks.WISPROOT_SIGN.get().asItem(), "miscellaneous/");
//        itemModels.generateFlatItem(AetherIIBlocks.WISPROOT_HANGING_SIGN.get().asItem(), "miscellaneous/");
//
//        // Bookshelves
//        this.itemBlock(AetherIIBlocks.SKYROOT_BOOKSHELF.get());
//        this.itemBlock(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get());
//
//        // Furniture
//        this.blockWithItem(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), "miscellaneous/");
    }
}
