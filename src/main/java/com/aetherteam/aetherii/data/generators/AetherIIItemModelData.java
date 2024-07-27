package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIIItemModelProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AetherIIItemModelData extends AetherIIItemModelProvider {
    public AetherIIItemModelData(PackOutput output, ExistingFileHelper helper) {
        super(output, AetherII.MODID, helper);
    }

    @Override
    protected void registerModels() {
        // Tools
        this.handheldItem(AetherIIItems.SKYROOT_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.SKYROOT_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.HOLYSTONE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.HOLYSTONE_TROWEL.get(), "tools/");

        this.reinforcedItem(AetherIIItems.ZANITE_PICKAXE.get(), "tools/");
        this.reinforcedItem(AetherIIItems.ZANITE_AXE.get(), "tools/");
        this.reinforcedItem(AetherIIItems.ZANITE_SHOVEL.get(), "tools/");
        this.reinforcedItem(AetherIIItems.ZANITE_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.ARKENIUM_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.ARKENIUM_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.GRAVITITE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_AXE.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherIIItems.GRAVITITE_TROWEL.get(), "tools/");

        this.handheldItem(AetherIIItems.ARKENIUM_SHEARS.get(), "tools/");

        // Combat
        this.handheldItem(AetherIIItems.SKYROOT_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.SKYROOT_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.SKYROOT_SPEAR.get(), "weapons/");
        this.crossbowItem(AetherIIItems.SKYROOT_CROSSBOW.get(), "weapons/");

        this.handheldItem(AetherIIItems.HOLYSTONE_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.HOLYSTONE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.HOLYSTONE_SPEAR.get(), "weapons/");
        this.crossbowItem(AetherIIItems.HOLYSTONE_CROSSBOW.get(), "weapons/");

        this.reinforcedItem(AetherIIItems.ZANITE_SHORTSWORD.get(), "weapons/");
        this.reinforcedItem(AetherIIItems.ZANITE_HAMMER.get(), "weapons/");
        this.reinforcedItem(AetherIIItems.ZANITE_SPEAR.get(), "weapons/");
        this.crossbowItem(AetherIIItems.ZANITE_CROSSBOW.get(), "weapons/");

        this.handheldItem(AetherIIItems.ARKENIUM_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.ARKENIUM_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.ARKENIUM_SPEAR.get(), "weapons/");
        this.crossbowItem(AetherIIItems.ARKENIUM_CROSSBOW.get(), "weapons/");

        this.handheldItem(AetherIIItems.GRAVITITE_SHORTSWORD.get(), "weapons/");
        this.handheldItem(AetherIIItems.GRAVITITE_HAMMER.get(), "weapons/");
        this.handheldItem(AetherIIItems.GRAVITITE_SPEAR.get(), "weapons/");
        this.crossbowItem(AetherIIItems.GRAVITITE_CROSSBOW.get(), "weapons/");

        this.item(AetherIIItems.SCATTERGLASS_BOLT.get(), "weapons/");

        // Armor
        this.dyedHelmetItem(AetherIIItems.TAEGORE_HIDE_HELMET.get(), "armor/");
        this.dyedChestplateItem(AetherIIItems.TAEGORE_HIDE_CHESTPLATE.get(), "armor/");
        this.dyedLeggingsItem(AetherIIItems.TAEGORE_HIDE_LEGGINGS.get(), "armor/");
        this.dyedBootsItem(AetherIIItems.TAEGORE_HIDE_BOOTS.get(), "armor/");
        this.dyedGlovesItem(AetherIIItems.TAEGORE_HIDE_GLOVES.get(), "armor/");

        this.dyedHelmetItem(AetherIIItems.BURRUKAI_PELT_HELMET.get(), "armor/");
        this.dyedChestplateItem(AetherIIItems.BURRUKAI_PELT_CHESTPLATE.get(), "armor/");
        this.dyedLeggingsItem(AetherIIItems.BURRUKAI_PELT_LEGGINGS.get(), "armor/");
        this.dyedBootsItem(AetherIIItems.BURRUKAI_PELT_BOOTS.get(), "armor/");
        this.dyedGlovesItem(AetherIIItems.BURRUKAI_PELT_GLOVES.get(), "armor/");

        this.helmetItem(AetherIIItems.ZANITE_HELMET.get(), "armor/");
        this.chestplateItem(AetherIIItems.ZANITE_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherIIItems.ZANITE_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherIIItems.ZANITE_BOOTS.get(), "armor/");
        this.item(AetherIIItems.ZANITE_GLOVES.get(), "armor/");

        this.helmetItem(AetherIIItems.ARKENIUM_HELMET.get(), "armor/");
        this.chestplateItem(AetherIIItems.ARKENIUM_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherIIItems.ARKENIUM_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherIIItems.ARKENIUM_BOOTS.get(), "armor/");
        this.item(AetherIIItems.ARKENIUM_GLOVES.get(), "armor/");

        this.helmetItem(AetherIIItems.GRAVITITE_HELMET.get(), "armor/");
        this.chestplateItem(AetherIIItems.GRAVITITE_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherIIItems.GRAVITITE_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherIIItems.GRAVITITE_BOOTS.get(), "armor/");
        this.item(AetherIIItems.GRAVITITE_GLOVES.get(), "armor/");

        // Materials
        this.handheldItem(AetherIIItems.SKYROOT_STICK.get(), "materials/");
        this.item(AetherIIItems.SCATTERGLASS_SHARD.get(), "materials/");
        this.item(AetherIIItems.AMBROSIUM_SHARD.get(), "materials/");
        this.item(AetherIIItems.ZANITE_GEMSTONE.get(), "materials/");
        this.item(AetherIIItems.INERT_ARKENIUM.get(), "materials/");
        this.item(AetherIIItems.ARKENIUM_PLATES.get(), "materials/");
        this.item(AetherIIItems.INERT_GRAVITITE.get(), "materials/");
        this.item(AetherIIItems.GRAVITITE_PLATE.get(), "materials/");
        this.item(AetherIIItems.CORROBONITE_CRYSTAL.get(), "materials/");
        this.item(AetherIIItems.GLINT_GEMSTONE.get(), "materials/");
        this.item(AetherIIItems.GOLDEN_AMBER.get(), "materials/");
        this.item(AetherIIItems.CLOUDTWINE.get(), "materials/");
        this.item(AetherIIItems.TAEGORE_HIDE.get(), "materials/");
        this.item(AetherIIItems.BURRUKAI_PELT.get(), "materials/");
        this.item(AetherIIItems.SKYROOT_PINECONE.get(), "materials/");
        this.item(AetherIIItems.VALKYRIE_WINGS.get(), "materials/");
        this.item(AetherIIItems.BRETTL_CANE.get(), "materials/");
        this.item(AetherIIItems.BRETTL_GRASS.get(), "materials/");
        this.item(AetherIIItems.BRETTL_ROPE.get(), "materials/");
        this.item(AetherIIItems.BRETTL_FLOWER.get(), "materials/");
        this.item(AetherIIItems.AECHOR_PETAL.get(), "materials/");
        this.item(AetherIIItems.ARCTIC_SNOWBALL.get(), "materials/");
        this.item(AetherIIItems.GREEN_SWET_GEL.get(), "materials/");
        this.item(AetherIIItems.BLUE_SWET_GEL.get(), "materials/");
        this.item(AetherIIItems.PURPLE_SWET_GEL.get(), "materials/");
        this.item(AetherIIItems.GOLDEN_SWET_GEL.get(), "materials/");
        this.item(AetherIIItems.WHITE_SWET_GEL.get(), "materials/");
        this.item(AetherIIBlocks.MOA_EGG.get().asItem(), "materials/");
        this.item(AetherIIItems.CHARGE_CORE.get(), "materials/");

        // Food
        this.item(AetherIIItems.BLUEBERRY.get(), "food/");
        this.item(AetherIIItems.ENCHANTED_BLUEBERRY.get(), "food/");
        this.item(AetherIIItems.ORANGE.get(), "food/");
        this.item(AetherIIItems.WYNDBERRY.get(), "food/");
        this.item(AetherIIItems.ENCHANTED_WYNDBERRY.get(), "food/");
        this.item(AetherIIItems.GREEN_SWET_JELLY.get(), "food/");
        this.item(AetherIIItems.BLUE_SWET_JELLY.get(), "food/");
        this.item(AetherIIItems.PURPLE_SWET_JELLY.get(), "food/");
        this.item(AetherIIItems.GOLDEN_SWET_JELLY.get(), "food/");
        this.item(AetherIIItems.WHITE_SWET_JELLY.get(), "food/");
        this.item(AetherIIItems.BURRUKAI_RIBS.get(), "food/");
        this.item(AetherIIItems.BURRUKAI_RIB_CUT.get(), "food/");
        this.item(AetherIIItems.KIRRID_CUTLET.get(), "food/");
        this.item(AetherIIItems.KIRRID_LOIN.get(), "food/");
        this.item(AetherIIItems.RAW_TAEGORE_MEAT.get(), "food/");
        this.item(AetherIIItems.TAEGORE_STEAK.get(), "food/");
        this.item(AetherIIItems.SKYROOT_LIZARD_ON_A_STICK.get(), "food/");
        this.item(AetherIIItems.ROASTED_SKYROOT_LIZARD_ON_A_STICK.get(), "food/");

        // Skyroot Buckets
        this.item(AetherIIItems.SKYROOT_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_WATER_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_MILK_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_POWDER_SNOW_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_COD_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_SALMON_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_PUFFERFISH_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_TROPICAL_FISH_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_AXOLOTL_BUCKET.get(), "miscellaneous/");
        this.item(AetherIIItems.SKYROOT_TADPOLE_BUCKET.get(), "miscellaneous/");

        // Music Discs
        this.item(AetherIIItems.MUSIC_DISC_AETHER_TUNE.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_ASCENDING_DAWN.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_AERWHALE.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_APPROACHES.get(), "miscellaneous/");
        this.item(AetherIIItems.MUSIC_DISC_DEMISE.get(), "miscellaneous/");
        this.item(AetherIIItems.RECORDING_892.get(), "miscellaneous/");

        // Spawn Eggs
        this.eggItem(AetherIIItems.FLYING_COW_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.SHEEPUFF_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.PHYG_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.AERBUNNY_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.HIGHFIELDS_TAEGORE_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.MAGNETIC_TAEGORE_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.ARCTIC_TAEGORE_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.HIGHFIELDS_BURRUKAI_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.MAGNETIC_BURRUKAI_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.ARCTIC_BURRUKAI_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.HIGHFIELDS_KIRRID_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.MAGNETIC_KIRRID_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.ARCTIC_KIRRID_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.MOA_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.SKYROOT_LIZARD_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.AECHOR_PLANT_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.ZEPHYR_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.TEMPEST_SPAWN_EGG.get());
        this.eggItem(AetherIIItems.COCKATRICE_SPAWN_EGG.get());

        // Misc
        this.item(AetherIIItems.MOA_FEED.get(), "miscellaneous/");
        this.item(AetherIIItems.BLUEBERRY_MOA_FEED.get(), "miscellaneous/");
        this.item(AetherIIItems.ENCHANTED_MOA_FEED.get(), "miscellaneous/");
        this.item(AetherIIItems.GLINT_COIN.get(), "miscellaneous/");
        this.portalItem(AetherIIItems.AETHER_PORTAL_FRAME.get(), "miscellaneous/");

        // Blocks
        // Dirt
        this.itemBlock(AetherIIBlocks.AETHER_GRASS_BLOCK.get());
        this.itemBlock(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
        this.itemBlock(AetherIIBlocks.AETHER_DIRT_PATH.get());
        this.itemBlock(AetherIIBlocks.AETHER_DIRT.get());
        this.itemBlock(AetherIIBlocks.COARSE_AETHER_DIRT.get());
        this.itemBlock(AetherIIBlocks.AETHER_FARMLAND.get());

        // Underground
        this.itemBlock(AetherIIBlocks.HOLYSTONE.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE.get());
        this.itemBlock(AetherIIBlocks.CRUDE_SCATTERGLASS.get());

        // Highfields
        this.itemBlock(AetherIIBlocks.QUICKSOIL.get());
        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE.get());

        // Magnetic
        this.itemBlock(AetherIIBlocks.FERROSITE_SAND.get());
        this.itemBlock(AetherIIBlocks.FERROSITE.get());
        this.itemBlock(AetherIIBlocks.RUSTED_FERROSITE.get());

        // Arctic
        this.itemBlock(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get());
        this.itemBlock(AetherIIBlocks.ARCTIC_SNOW.get());
        this.itemBlock(AetherIIBlocks.ARCTIC_ICE.get());
        this.itemBlock(AetherIIBlocks.ARCTIC_PACKED_ICE.get());
        this.itemBlock(AetherIIBlocks.ICESTONE.get());

        // Irradiated
        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE.get());

        // Ores
        this.itemBlock(AetherIIBlocks.AMBROSIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.ZANITE_ORE.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.GRAVITITE_ORE.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get());
        this.itemBlock(AetherIIBlocks.GLINT_ORE.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get());
        this.itemBlock(AetherIIBlocks.CORROBONITE_ORE.get());
        this.itemBlockFlat(AetherIIBlocks.CORROBONITE_CLUSTER.get(), "natural/");

        // Aerclouds
        this.aercloudItem(AetherIIBlocks.COLD_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.BLUE_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.GOLDEN_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.GREEN_AERCLOUD.get());
        this.itemBlock(AetherIIBlocks.PURPLE_AERCLOUD.get());
        this.aercloudItem(AetherIIBlocks.STORM_AERCLOUD.get());

        // Moa Nest
        this.itemBlock(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get());

        // Logs
        this.itemBlock(AetherIIBlocks.SKYROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_SKYROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_GREATROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.MOSSY_WISPROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_WISPROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LOG.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_GREATROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.STRIPPED_WISPROOT_WOOD.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_WOOD.get());

        // Leaf Pile
        this.itemBlock(AetherIIBlocks.SKYROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.SKYPLANE_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.SKYBIRCH_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.SKYPINE_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.WISPTOP_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.GREATOAK_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.GREATBOA_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPINE_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPTOP_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATROOT_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATOAK_LEAF_PILE.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATBOA_LEAF_PILE.get());

        // Leaves
        this.itemBlock(AetherIIBlocks.SKYROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYPLANE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYBIRCH_LEAVES.get());
        this.itemBlock(AetherIIBlocks.SKYPINE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.WISPTOP_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATOAK_LEAVES.get());
        this.itemBlock(AetherIIBlocks.GREATBOA_LEAVES.get());
        this.itemBlock(AetherIIBlocks.AMBEROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get());

        // Saplings
        this.itemBlockFlat(AetherIIBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.SKYPLANE_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.SKYBIRCH_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.SKYPINE_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.WISPROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.WISPTOP_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.GREATROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.GREATOAK_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.GREATBOA_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.AMBEROOT_SAPLING.get(), "natural/");

        // Grasses
        this.itemBlockGrass(AetherIIBlocks.AETHER_SHORT_GRASS.get(), "natural/");
        this.itemBlockGrass(AetherIIBlocks.AETHER_MEDIUM_GRASS.get(), "natural/");
        this.itemBlockGrass(AetherIIBlocks.AETHER_LONG_GRASS.get(), "natural/");

        // Flowers
        this.itemBlockFlat(AetherIIBlocks.HESPEROSE.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.TARABLOOM.get(), "natural/");
        this.itemBlockFlat(AetherIIBlocks.AECHOR_CUTTING.get(), "natural/");

        // Bushes
        this.itemBlock(AetherIIBlocks.HIGHLANDS_BUSH.get());
        this.itemBlock(AetherIIBlocks.BLUEBERRY_BUSH.get());
        this.itemBlockFlat(AetherIIBlocks.BLUEBERRY_BUSH_STEM.get(), "natural/");

        // Orange Tree
        this.orangeTree(AetherIIBlocks.ORANGE_TREE.get());

        // Valkyrie Sprout
        this.valkyrieSprout(AetherIIBlocks.VALKYRIE_SPROUT.get());

        // Ground Decoration
        this.item(AetherIIBlocks.SKYROOT_TWIG.asItem(), "miscellaneous/");
        this.item(AetherIIBlocks.HOLYSTONE_ROCK.asItem(), "miscellaneous/");

        // Skyroot Planks
        this.itemBlock(AetherIIBlocks.SKYROOT_PLANKS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_SLAB.get());
        this.itemFence(AetherIIBlocks.SKYROOT_FENCE.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.SKYROOT_FENCE_GATE.get());
        this.item(AetherIIBlocks.SKYROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.SKYROOT_TRAPDOOR.get(), "_bottom");
        this.itemButton(AetherIIBlocks.SKYROOT_BUTTON.get(), AetherIIBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE.get());

        // Skyroot Decorative Blocks
        this.itemBlock(AetherIIBlocks.SKYROOT_FLOORBOARDS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_HIGHLIGHT.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_SMALL_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_BASE_PLANKS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_TOP_PLANKS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_BASE_BEAM.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_TOP_BEAM.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_BEAM.get());
        this.item(AetherIIBlocks.SECRET_SKYROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.SECRET_SKYROOT_TRAPDOOR.get(), "_bottom");

        // Greatroot Planks
        this.itemBlock(AetherIIBlocks.GREATROOT_PLANKS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_SLAB.get());
        this.itemFence(AetherIIBlocks.GREATROOT_FENCE.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.GREATROOT_FENCE_GATE.get());
        this.item(AetherIIBlocks.GREATROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.GREATROOT_TRAPDOOR.get(), "_bottom");
        this.itemButton(AetherIIBlocks.GREATROOT_BUTTON.get(), AetherIIBlocks.GREATROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE.get());

        // Greatroot Decorative Blocks
        this.itemBlock(AetherIIBlocks.GREATROOT_FLOORBOARDS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_HIGHLIGHT.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_SMALL_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_BASE_PLANKS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_TOP_PLANKS.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_BASE_BEAM.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_TOP_BEAM.get());
        this.itemBlock(AetherIIBlocks.GREATROOT_BEAM.get());
        this.item(AetherIIBlocks.SECRET_GREATROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.SECRET_GREATROOT_TRAPDOOR.get(), "_bottom");

        // Wisproot Planks
        this.itemBlock(AetherIIBlocks.WISPROOT_PLANKS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_STAIRS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_SLAB.get());
        this.itemFence(AetherIIBlocks.WISPROOT_FENCE.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.WISPROOT_FENCE_GATE.get());
        this.item(AetherIIBlocks.WISPROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.WISPROOT_TRAPDOOR.get(), "_bottom");
        this.itemButton(AetherIIBlocks.WISPROOT_BUTTON.get(), AetherIIBlocks.WISPROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE.get());

        // Wisproot Decorative Blocks
        this.itemBlock(AetherIIBlocks.WISPROOT_FLOORBOARDS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_HIGHLIGHT.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_SMALL_SHINGLES.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_BASE_PLANKS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_TOP_PLANKS.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_BASE_BEAM.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_TOP_BEAM.get());
        this.itemBlock(AetherIIBlocks.WISPROOT_BEAM.get());
        this.item(AetherIIBlocks.SECRET_WISPROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.SECRET_WISPROOT_TRAPDOOR.get(), "_bottom");

        // Holystone
        this.itemBlock(AetherIIBlocks.HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_WALL.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.itemButton(AetherIIBlocks.HOLYSTONE_BUTTON.get(), AetherIIBlocks.HOLYSTONE.get(), "natural/");
        this.itemBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE.get());

        // Mossy Holystone
        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherIIBlocks.MOSSY_HOLYSTONE.get(), "natural/");

        // Irradiated Holystone
        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.IRRADIATED_HOLYSTONE_WALL.get(), AetherIIBlocks.IRRADIATED_HOLYSTONE.get(), "natural/");

        // Holystone Bricks
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BRICK_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.HOLYSTONE_BRICKS.get(), "construction/");

        // Holystone Decorative Blocks
        this.itemBlock(AetherIIBlocks.HOLYSTONE_FLAGSTONES.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_HEADSTONE.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_KEYSTONE.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BASE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BASE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_CAPSTONE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_PILLAR.get());

        // Faded Holystone Bricks
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.FADED_HOLYSTONE_BRICK_WALL.get(), AetherIIBlocks.FADED_HOLYSTONE_BRICKS.get(), "construction/");

        // Faded Holystone Decorative Blocks
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_FLAGSTONES.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_HEADSTONE.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_KEYSTONE.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_BASE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_CAPSTONE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.FADED_HOLYSTONE_PILLAR.get());

        // Undershale
        this.itemBlock(AetherIIBlocks.UNDERSHALE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.UNDERSHALE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.UNDERSHALE_WALL.get(), AetherIIBlocks.UNDERSHALE.get(), "natural/");

        // Agiosite
        this.itemBlock(AetherIIBlocks.AGIOSITE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.AGIOSITE_WALL.get(), AetherIIBlocks.AGIOSITE.get(), "natural/");

        // Agiosite Bricks
        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BRICK_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL.get(), AetherIIBlocks.AGIOSITE_BRICKS.get(), "construction/");

        // Agiosite Decorative Blocks
        this.itemBlock(AetherIIBlocks.AGIOSITE_FLAGSTONES.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_KEYSTONE.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BASE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_BASE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_CAPSTONE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.AGIOSITE_PILLAR.get());

        // Icestone Bricks
        this.itemBlock(AetherIIBlocks.ICESTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_BRICK_STAIRS.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_BRICK_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.ICESTONE_BRICK_WALL.get(), AetherIIBlocks.ICESTONE_BRICKS.get(), "construction/");

        // Icestone Decorative Blocks
        this.itemBlock(AetherIIBlocks.ICESTONE_FLAGSTONES.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_KEYSTONE.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_BASE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_CAPSTONE_BRICKS.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_BASE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_CAPSTONE_PILLAR.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_PILLAR.get());

        // Icestone
        this.itemBlock(AetherIIBlocks.ICESTONE_STAIRS.get());
        this.itemBlock(AetherIIBlocks.ICESTONE_SLAB.get());
        this.itemWallBlock(AetherIIBlocks.ICESTONE_WALL.get(), AetherIIBlocks.ICESTONE.get(), "natural/");

        // Glass
        this.itemBlock(AetherIIBlocks.QUICKSOIL_GLASS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get());
        this.itemBlock(AetherIIBlocks.SCATTERGLASS.get());
        this.itemBlock(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get());

        // Glass Panes
        this.pane(AetherIIBlocks.QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.pane(AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_QUICKSOIL_GLASS.get(), "decorative/");
        this.pane(AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_QUICKSOIL_GLASS.get(), "decorative/");
        this.pane(AetherIIBlocks.CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.CRUDE_SCATTERGLASS.get(), "natural/");
        this.pane(AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_CRUDE_SCATTERGLASS.get(), "decorative/");
        this.pane(AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_CRUDE_SCATTERGLASS.get(), "decorative/");
        this.pane(AetherIIBlocks.SCATTERGLASS_PANE.get(), AetherIIBlocks.SCATTERGLASS.get(), "construction/");
        this.pane(AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS_PANE.get(), AetherIIBlocks.SKYROOT_FRAMED_SCATTERGLASS.get(), "decorative/");
        this.pane(AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS_PANE.get(), AetherIIBlocks.ARKENIUM_FRAMED_SCATTERGLASS.get(), "decorative/");

        // Wool
        this.itemBlock(AetherIIBlocks.CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.WHITE_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.ORANGE_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.MAGENTA_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.YELLOW_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.LIME_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.PINK_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.GRAY_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.CYAN_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.PURPLE_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.BLUE_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.BROWN_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.GREEN_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.RED_CLOUDWOOL.get());
        this.itemBlock(AetherIIBlocks.BLACK_CLOUDWOOL.get());

        // Carpet
        this.itemBlock(AetherIIBlocks.CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.WHITE_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.ORANGE_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.MAGENTA_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.YELLOW_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.LIME_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.PINK_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.GRAY_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.CYAN_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.PURPLE_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.BLUE_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.BROWN_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.GREEN_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.RED_CLOUDWOOL_CARPET.get());
        this.itemBlock(AetherIIBlocks.BLACK_CLOUDWOOL_CARPET.get());

        // Arkenium Blocks
        this.item(AetherIIBlocks.ARKENIUM_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherIIBlocks.ARKENIUM_TRAPDOOR.get(), "_bottom");

        // Mineral Blocks
        this.itemBlock(AetherIIBlocks.AMBROSIUM_BLOCK.get());
        this.itemBlock(AetherIIBlocks.ZANITE_BLOCK.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_BLOCK.get());
        this.itemBlock(AetherIIBlocks.GRAVITITE_BLOCK.get());

        // Utility
        this.itemBlockFlat(AetherIIBlocks.AMBROSIUM_TORCH.get(), "utility/");
        this.itemBlock(AetherIIBlocks.SKYROOT_CRAFTING_TABLE.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_FURNACE.get());
        this.itemBlock(AetherIIBlocks.ARTISANS_BENCH.get());
        this.itemBlock(AetherIIBlocks.ALTAR.get());
        this.itemBlock(AetherIIBlocks.ARKENIUM_FORGE.get());
        this.lookalikeBlock(AetherIIBlocks.SKYROOT_CHEST.get(), this.mcLoc("item/chest"));
        this.itemBlockFlat(AetherIIBlocks.SKYROOT_LADDER.get(), "construction/");
        this.lookalikeBlock(AetherIIBlocks.SKYROOT_BED.get(), this.mcLoc("item/template_bed"));

        this.item(AetherIIBlocks.SKYROOT_SIGN.get().asItem(), "miscellaneous/");
        this.item(AetherIIBlocks.SKYROOT_HANGING_SIGN.get().asItem(), "miscellaneous/");

        this.item(AetherIIBlocks.GREATROOT_SIGN.get().asItem(), "miscellaneous/");
        this.item(AetherIIBlocks.GREATROOT_HANGING_SIGN.get().asItem(), "miscellaneous/");

        this.item(AetherIIBlocks.WISPROOT_SIGN.get().asItem(), "miscellaneous/");
        this.item(AetherIIBlocks.WISPROOT_HANGING_SIGN.get().asItem(), "miscellaneous/");

        // Bookshelves
        this.itemBlock(AetherIIBlocks.SKYROOT_BOOKSHELF.get());
        this.itemBlock(AetherIIBlocks.HOLYSTONE_BOOKSHELF.get());

        // Furniture
        this.blockWithItem(AetherIIBlocks.OUTPOST_CAMPFIRE.get(), "miscellaneous/");
    }
}