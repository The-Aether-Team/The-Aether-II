package com.aetherteam.aetherii.data.generators.models;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIItemModelSubProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class AetherIIItemModels extends AetherIIItemModelSubProvider {
    public AetherIIItemModels(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        // Tools
        this.generateFlatItem(AetherIIItems.SKYROOT_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        this.generateFlatItem(AetherIIItems.HOLYSTONE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.HOLYSTONE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.HOLYSTONE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.HOLYSTONE_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        this.generateReinforcedItem(AetherIIItems.ZANITE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(AetherIIItems.ZANITE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(AetherIIItems.ZANITE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(AetherIIItems.ZANITE_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        this.generateFlatItem(AetherIIItems.ARKENIUM_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        this.generateFlatItem(AetherIIItems.GRAVITITE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_TROWEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        this.generateFlatItem(AetherIIItems.ZANITE_SHEARS.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        // Combat
        this.generateFlatItem(AetherIIItems.SKYROOT_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(AetherIIItems.SKYROOT_CROSSBOW.get());

        this.generateFlatItem(AetherIIItems.HOLYSTONE_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.HOLYSTONE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.HOLYSTONE_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(AetherIIItems.HOLYSTONE_CROSSBOW.get());

        this.generateReinforcedItem(AetherIIItems.ZANITE_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(AetherIIItems.ZANITE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateReinforcedItem(AetherIIItems.ZANITE_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(AetherIIItems.ZANITE_CROSSBOW.get());

        this.generateFlatItem(AetherIIItems.ARKENIUM_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(AetherIIItems.ARKENIUM_CROSSBOW.get());

        this.generateFlatItem(AetherIIItems.GRAVITITE_SHORTSWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_SPEAR.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateCrossbow(AetherIIItems.GRAVITITE_CROSSBOW.get());

        this.generateModeledShield(AetherIIItems.SKYROOT_SHIELD.get());
        this.generateModeledShield(AetherIIItems.BURRUKAI_PLATE_SHIELD.get());
        this.generateModeledShield(AetherIIItems.ZANITE_SHIELD.get());
        this.generateModeledShield(AetherIIItems.ARKENIUM_SHIELD.get());
        this.generateModeledShield(AetherIIItems.GRAVITITE_SHIELD.get());

        this.generateDartShooter(AetherIIItems.DART_SHOOTER.get());
        this.generateDarts(AetherIIItems.AMBER_DARTS.get());

        this.generateFlatItem(AetherIIItems.SCATTERGLASS_BOLT.get(), ModelTemplates.FLAT_ITEM);

        // Armor
        this.generateDyedArmorItem(AetherIIItems.BEAST_PELT_HELMET.get(), 0xFFCFEEF9);
        this.generateDyedArmorItem(AetherIIItems.BEAST_PELT_CHESTPLATE.get(), 0xFFCFEEF9);
        this.generateDyedArmorItem(AetherIIItems.BEAST_PELT_LEGGINGS.get(), 0xFFCFEEF9);
        this.generateDyedArmorItem(AetherIIItems.BEAST_PELT_BOOTS.get(), 0xFFCFEEF9);
        this.generateDyedArmorItem(AetherIIItems.BEAST_PELT_GLOVES.get(), 0xFFCFEEF9);

        this.generateDyedArmorItem(AetherIIItems.BURRUKAI_PLATE_HELMET.get(), 0xFF619CC0);
        this.generateDyedArmorItem(AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get(), 0xFF619CC0);
        this.generateDyedArmorItem(AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get(), 0xFF619CC0);
        this.generateDyedArmorItem(AetherIIItems.BURRUKAI_PLATE_BOOTS.get(), 0xFF619CC0);
        this.generateDyedArmorItem(AetherIIItems.BURRUKAI_PLATE_GLOVES.get(), 0xFF619CC0);

        this.generateFlatItem(AetherIIItems.ZANITE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ZANITE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ZANITE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ZANITE_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ZANITE_GLOVES.get(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(AetherIIItems.ARKENIUM_HELMET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_GLOVES.get(), ModelTemplates.FLAT_ITEM);

        this.generateFlatItem(AetherIIItems.GRAVITITE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_GLOVES.get(), ModelTemplates.FLAT_ITEM);

        // Materials
        this.generateFlatItem(AetherIIItems.SKYROOT_STICK.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(AetherIIItems.SCATTERGLASS_SHARD.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.AMBROSIUM_SHARD.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ZANITE_GEMSTONE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.INERT_ARKENIUM.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_PLATES.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.INERT_GRAVITITE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_PLATE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.CORROBONITE_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GLINT_GEMSTONE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GOLDEN_AMBER.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.CLOUDTWINE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BEAST_PELT.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BURRUKAI_PLATE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.KIRRID_PLATE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_PINECONE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.VALKYRIE_WINGS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BRETTL_CANE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BRETTL_GRASS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BRETTL_ROPE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARILUM_BULBS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.AECHOR_PETAL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARCTIC_SNOWBALL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GREEN_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BLUE_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.PURPLE_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GOLDEN_SWET_GEL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SWET_SUGAR.get(), ModelTemplates.FLAT_ITEM);
        this.generateMoaFeatherItem(AetherIIItems.MOA_FEATHER.get());
        this.generateFlatItem(AetherIIItems.COCKATRICE_FEATHER.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SCATTERGLASS_VIAL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ZEPHYR_HUSK.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.CHARGE_CATALYST.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_CORE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_CORE.get(), ModelTemplates.FLAT_ITEM);

        // Irradiated Items
        this.generateFlatItem(AetherIIItems.IRRADIATED_ARMOR.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.IRRADIATED_WEAPON.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.IRRADIATED_TOOL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.IRRADIATED_CHUNK.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.IRRADIATED_DUST.get(), ModelTemplates.FLAT_ITEM);

        // Food
        this.generateFlatItem(AetherIIItems.BLUEBERRY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ENCHANTED_BLUEBERRY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ORANGE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ENCHANTED_ORANGE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.WYNDBERRY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ENCHANTED_WYNDBERRY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SATIVAL_BULB.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GREEN_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BLUE_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.PURPLE_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GOLDEN_SWET_JELLY.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BURRUKAI_RIBS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BURRUKAI_RIB_CUT.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.KIRRID_CUTLET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.KIRRID_LOIN.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.RAW_TAEGORE_MEAT.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.TAEGORE_STEAK.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK.get(), ModelTemplates.FLAT_ITEM);

        // Consumables
        this.generateFlatItem(AetherIIItems.WATER_VIAL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BANDAGE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SPLINT.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ANTITOXIN_VIAL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ANTIVENOM_VIAL.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.VALKYRIE_TEA.get(), ModelTemplates.FLAT_ITEM);
        this.generateHealingStoneItem(AetherIIItems.HEALING_STONE.get());

        // Gliders
        this.generateGliderItem(AetherIIItems.COLD_AERCLOUD_GLIDER.get(), false);
        this.generateGliderItem(AetherIIItems.GOLDEN_AERCLOUD_GLIDER.get(), false);
        this.generateGliderItem(AetherIIItems.BLUE_AERCLOUD_GLIDER.get(), true);
        this.generateGliderItem(AetherIIItems.PURPLE_AERCLOUD_GLIDER.get(), true);

        // Skyroot Buckets
        this.generateFlatItem(AetherIIItems.SKYROOT_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_WATER_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_MILK_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_COD_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_SALMON_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_AXOLOTL_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_TADPOLE_BUCKET.get(), ModelTemplates.FLAT_ITEM);

        // Arkenium Canisters
        this.generateFlatItem(AetherIIItems.ARKENIUM_CANISTER.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_HESTVEIL_CANISTER.get(), ModelTemplates.FLAT_ITEM);

        // Music Discs
        this.generateFlatItem(AetherIIItems.MUSIC_DISC_AETHER_TUNE.get(), ModelTemplates.MUSIC_DISC);
        this.generateFlatItem(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN.get(), ModelTemplates.MUSIC_DISC);
        this.generateFlatItem(AetherIIItems.MUSIC_DISC_AERWHALE.get(), ModelTemplates.MUSIC_DISC);
        this.generateFlatItem(AetherIIItems.MUSIC_DISC_APPROACHES.get(), ModelTemplates.MUSIC_DISC);
        this.generateFlatItem(AetherIIItems.MUSIC_DISC_DEMISE.get(), ModelTemplates.MUSIC_DISC);
        this.generateFlatItem(AetherIIItems.RECORDING_892.get(), ModelTemplates.MUSIC_DISC);

        // Spawn Eggs
        this.generateFlatItem(AetherIIItems.FLYING_COW_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SHEEPUFF_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.PHYG_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.AERBUNNY_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.AERWHALE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.HIGHFIELDS_TAEGORE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.MAGNETIC_TAEGORE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARCTIC_TAEGORE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.HIGHFIELDS_BURRUKAI_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.MAGNETIC_BURRUKAI_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARCTIC_BURRUKAI_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.HIGHFIELDS_KIRRID_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.MAGNETIC_KIRRID_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARCTIC_KIRRID_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.MOA_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKYROOT_LIZARD_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.AECHOR_PLANT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.CARRION_SPROUT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ZEPHYR_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.TEMPEST_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.COCKATRICE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SWET_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.SKEPHID_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ARKENIUM_TALUTON_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GRAVITITE_TALUTON_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        // Misc
        this.generateBundleModels(AetherIIItems.HIDE_BUNDLE.get());
        this.generateFlatItem(AetherIIItems.BRETTL_LASSO.get(), ModelTemplates.FLAT_ITEM);
        this.generateMoaEggItem(AetherIIItems.MOA_EGG.get());
        this.generateFlatItem(AetherIIItems.MOA_FEED.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.BLUEBERRY_MOA_FEED.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.ENCHANTED_MOA_FEED.get(), ModelTemplates.FLAT_ITEM);
        this.generateDyedSaddleItem(AetherIIItems.MOA_SADDLE.get());
        this.generateFlatItem(AetherIIItems.MOA_SADDLEBAG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.LARGE_MOA_SADDLEBAG.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.CLOUD_SKIFF.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GLINT_COIN.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(AetherIIItems.GUIDEBOOK_PAGE.get(), ModelTemplates.FLAT_ITEM);
        this.generatePortalFrameItem(AetherIIItems.AETHER_PORTAL_FRAME.get());

        // Blocks
        this.generateFlatItem(AetherIIBlocks.ARKENIUM_CHAIN.get().asItem(), ModelTemplates.FLAT_ITEM);
    }
}
