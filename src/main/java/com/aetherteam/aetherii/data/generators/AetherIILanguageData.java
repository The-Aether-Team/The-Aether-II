package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.providers.AetherIILanguageProvider;
import com.aetherteam.aetherii.item.AetherIICreativeTabs;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.data.PackOutput;

public class AetherIILanguageData extends AetherIILanguageProvider {
    public AetherIILanguageData(PackOutput output) {
        super(output, AetherII.MODID);
    }

    @Override
    protected void addTranslations() {
        this.addBlock(AetherIIBlocks.AETHER_PORTAL, "Aether Portal");

        this.addBlock(AetherIIBlocks.AETHER_GRASS_BLOCK, "Aether Grass Block");
        this.addBlock(AetherIIBlocks.AETHER_DIRT, "Aether Dirt");
        this.addBlock(AetherIIBlocks.QUICKSOIL, "Quicksoil");
        this.addBlock(AetherIIBlocks.HOLYSTONE, "Holystone");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE, "Mossy Holystone");
        this.addBlock(AetherIIBlocks.UNDERSHALE, "Undershale");
        this.addBlock(AetherIIBlocks.AGIOSITE, "Agiosite");

        this.addBlock(AetherIIBlocks.AMBROSIUM_ORE, "Ambrosium Ore");
        this.addBlock(AetherIIBlocks.ZANITE_ORE, "Zanite Ore");
        this.addBlock(AetherIIBlocks.ARKENIUM_ORE, "Arkenium Ore");
        this.addBlock(AetherIIBlocks.GRAVITITE_ORE, "Gravitite Ore");

        this.addBlock(AetherIIBlocks.COLD_AERCLOUD, "Cold Aercloud");
        this.addBlock(AetherIIBlocks.BLUE_AERCLOUD, "Blue Aercloud");
        this.addBlock(AetherIIBlocks.GOLDEN_AERCLOUD, "Golden Aercloud");
        this.addBlock(AetherIIBlocks.GREEN_AERCLOUD, "Green Aercloud");
        this.addBlock(AetherIIBlocks.PURPLE_AERCLOUD, "Purple Aercloud");
        this.addBlock(AetherIIBlocks.STORM_AERCLOUD, "Storm Aercloud");

        this.addBlock(AetherIIBlocks.SKYROOT_LOG, "Skyroot Log");
        this.addBlock(AetherIIBlocks.GREATROOT_LOG, "Greatroot Log");
        this.addBlock(AetherIIBlocks.WISPROOT_LOG, "Wisproot Log");
        this.addBlock(AetherIIBlocks.AMBEROOT_LOG, "Amberoot Log");
        this.addBlock(AetherIIBlocks.SKYROOT_WOOD, "Skyroot Wood");
        this.addBlock(AetherIIBlocks.GREATROOT_WOOD, "Greatroot Wood");
        this.addBlock(AetherIIBlocks.WISPROOT_WOOD, "Wisproot Wood");
        this.addBlock(AetherIIBlocks.AMBEROOT_WOOD, "Amberoot Wood");

        this.addBlock(AetherIIBlocks.SKYROOT_LEAVES, "Skyroot Leaves");
        this.addBlock(AetherIIBlocks.SKYPLANE_LEAVES, "Skyplane Leaves");
        this.addBlock(AetherIIBlocks.SKYBIRCH_LEAVES, "Skybirch Leaves");
        this.addBlock(AetherIIBlocks.SKYPINE_LEAVES, "Skypine Leaves");
        this.addBlock(AetherIIBlocks.WISPROOT_LEAVES, "Wisproot Leaves");
        this.addBlock(AetherIIBlocks.WISPTOP_LEAVES, "Wisptop Leaves");
        this.addBlock(AetherIIBlocks.GREATROOT_LEAVES, "Greatroot Leaves");
        this.addBlock(AetherIIBlocks.GREATOAK_LEAVES, "Greatoak Leaves");
        this.addBlock(AetherIIBlocks.GREATBOA_LEAVES, "Greatboa Leaves");
        this.addBlock(AetherIIBlocks.AMBEROOT_LEAVES, "Amberoot Leaves");

        this.addBlock(AetherIIBlocks.SKYROOT_PLANKS, "Skyroot Planks");
        this.addBlock(AetherIIBlocks.SKYROOT_STAIRS, "Skyroot Stairs");
        this.addBlock(AetherIIBlocks.SKYROOT_SLAB, "Skyroot Slab");
        this.addBlock(AetherIIBlocks.SKYROOT_FENCE, "Skyroot Fence");
        this.addBlock(AetherIIBlocks.SKYROOT_FENCE_GATE, "Skyroot Fence Gate");
        this.addBlock(AetherIIBlocks.SKYROOT_DOOR, "Skyroot Door");
        this.addBlock(AetherIIBlocks.SKYROOT_TRAPDOOR, "Skyroot Trapdoor");
        this.addBlock(AetherIIBlocks.SKYROOT_BUTTON, "Skyroot Button");
        this.addBlock(AetherIIBlocks.SKYROOT_PRESSURE_PLATE, "Skyroot Pressure Plate");

        this.addBlock(AetherIIBlocks.GREATROOT_PLANKS, "Greatroot Planks");
        this.addBlock(AetherIIBlocks.GREATROOT_STAIRS, "Greatroot Stairs");
        this.addBlock(AetherIIBlocks.GREATROOT_SLAB, "Greatroot Slab");
        this.addBlock(AetherIIBlocks.GREATROOT_FENCE, "Greatroot Fence");
        this.addBlock(AetherIIBlocks.GREATROOT_FENCE_GATE, "Greatroot Fence Gate");
        this.addBlock(AetherIIBlocks.GREATROOT_BUTTON, "Greatroot Button");
        this.addBlock(AetherIIBlocks.GREATROOT_PRESSURE_PLATE, "Greatroot Pressure Plate");

        this.addBlock(AetherIIBlocks.WISPROOT_PLANKS, "Wisproot Planks");
        this.addBlock(AetherIIBlocks.WISPROOT_STAIRS, "Wisproot Stairs");
        this.addBlock(AetherIIBlocks.WISPROOT_SLAB, "Wisproot Slab");
        this.addBlock(AetherIIBlocks.WISPROOT_FENCE, "Wisproot Fence");
        this.addBlock(AetherIIBlocks.WISPROOT_FENCE_GATE, "Wisproot Fence Gate");
        this.addBlock(AetherIIBlocks.WISPROOT_BUTTON, "Wisproot Button");
        this.addBlock(AetherIIBlocks.WISPROOT_PRESSURE_PLATE, "Wisproot Pressure Plate");

        this.addBlock(AetherIIBlocks.HOLYSTONE_STAIRS, "Holystone Stairs");
        this.addBlock(AetherIIBlocks.HOLYSTONE_SLAB, "Holystone Slab");
        this.addBlock(AetherIIBlocks.HOLYSTONE_WALL, "Holystone Wall");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BUTTON, "Holystone Button");
        this.addBlock(AetherIIBlocks.HOLYSTONE_PRESSURE_PLATE, "Holystone Pressure Plate");

        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_STAIRS, "Mossy Holystone Stairs");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_SLAB, "Mossy Holystone Slab");
        this.addBlock(AetherIIBlocks.MOSSY_HOLYSTONE_WALL, "Mossy Holystone Wall");

        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICKS, "Holystone Bricks");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_STAIRS, "Holystone Brick Stairs");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_SLAB, "Holystone Brick Slab");
        this.addBlock(AetherIIBlocks.HOLYSTONE_BRICK_WALL, "Holystone Brick Wall");

        this.addBlock(AetherIIBlocks.UNDERSHALE_STAIRS, "Undershale Stairs");
        this.addBlock(AetherIIBlocks.UNDERSHALE_SLAB, "Undershale Slab");
        this.addBlock(AetherIIBlocks.UNDERSHALE_WALL, "Undershale Wall");

        this.addBlock(AetherIIBlocks.AGIOSITE_STAIRS, "Agiosite Stairs");
        this.addBlock(AetherIIBlocks.AGIOSITE_SLAB, "Agiosite Slab");
        this.addBlock(AetherIIBlocks.AGIOSITE_WALL, "Agiosite Wall");

        this.addBlock(AetherIIBlocks.AGIOSITE_BRICKS, "Agiosite Bricks");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_STAIRS, "Agiosite Brick Stairs");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_SLAB, "Agiosite Brick Slab");
        this.addBlock(AetherIIBlocks.AGIOSITE_BRICK_WALL, "Agiosite Brick Wall");

        this.addBlock(AetherIIBlocks.QUICKSOIL_GLASS, "Quicksoil Glass");
        this.addBlock(AetherIIBlocks.QUICKSOIL_GLASS_PANE, "Quicksoil Glass Pane");

        this.addBlock(AetherIIBlocks.CLOUDWOOL, "Cloudwool");
        this.addBlock(AetherIIBlocks.CLOUDWOOL_CARPET, "Cloudwool Carpet");

        this.addBlock(AetherIIBlocks.AMBROSIUM_BLOCK, "Block of Ambrosium");
        this.addBlock(AetherIIBlocks.ZANITE_BLOCK, "Block of Zanite");
        this.addBlock(AetherIIBlocks.GRAVITITE_BLOCK, "Block of Gravitite");

        this.addBlock(AetherIIBlocks.AMBROSIUM_TORCH, "Ambrosium Torch");
        this.addBlock(AetherIIBlocks.SKYROOT_CRAFTING_TABLE, "Skyroot Crafting Table");
        this.addBlock(AetherIIBlocks.SKYROOT_LADDER, "Skyroot Ladder");


        this.addItem(AetherIIItems.SKYROOT_SWORD, "Skyroot Sword");
        this.addItem(AetherIIItems.SKYROOT_HAMMER, "Skyroot Hammer");
        this.addItem(AetherIIItems.SKYROOT_SPEAR, "Skyroot Spear");
        this.addItem(AetherIIItems.SKYROOT_PICKAXE, "Skyroot Pickaxe");
        this.addItem(AetherIIItems.SKYROOT_AXE, "Skyroot Axe");
        this.addItem(AetherIIItems.SKYROOT_SHOVEL, "Skyroot Shovel");
        this.addItem(AetherIIItems.SKYROOT_TROWEL, "Skyroot Trowel");

        this.addItem(AetherIIItems.HOLYSTONE_SWORD, "Holystone Sword");
        this.addItem(AetherIIItems.HOLYSTONE_HAMMER, "Holystone Hammer");
        this.addItem(AetherIIItems.HOLYSTONE_SPEAR, "Holystone Spear");
        this.addItem(AetherIIItems.HOLYSTONE_PICKAXE, "Holystone Pickaxe");
        this.addItem(AetherIIItems.HOLYSTONE_AXE, "Holystone Axe");
        this.addItem(AetherIIItems.HOLYSTONE_SHOVEL, "Holystone Shovel");
        this.addItem(AetherIIItems.HOLYSTONE_TROWEL, "Holystone Trowel");

        this.addItem(AetherIIItems.ZANITE_SWORD, "Zanite Sword");
        this.addItem(AetherIIItems.ZANITE_HAMMER, "Zanite Hammer");
        this.addItem(AetherIIItems.ZANITE_SPEAR, "Zanite Spear");
        this.addItem(AetherIIItems.ZANITE_PICKAXE, "Zanite Pickaxe");
        this.addItem(AetherIIItems.ZANITE_AXE, "Zanite Axe");
        this.addItem(AetherIIItems.ZANITE_SHOVEL, "Zanite Shovel");
        this.addItem(AetherIIItems.ZANITE_TROWEL, "Zanite Trowel");

        this.addItem(AetherIIItems.ARKENIUM_SWORD, "Arkenium Sword");
        this.addItem(AetherIIItems.ARKENIUM_HAMMER, "Arkenium Hammer");
        this.addItem(AetherIIItems.ARKENIUM_SPEAR, "Arkenium Spear");
        this.addItem(AetherIIItems.ARKENIUM_PICKAXE, "Arkenium Pickaxe");
        this.addItem(AetherIIItems.ARKENIUM_AXE, "Arkenium Axe");
        this.addItem(AetherIIItems.ARKENIUM_SHOVEL, "Arkenium Shovel");
        this.addItem(AetherIIItems.ARKENIUM_TROWEL, "Arkenium Trowel");

        this.addItem(AetherIIItems.GRAVITITE_SWORD, "Gravitite Sword");
        this.addItem(AetherIIItems.GRAVITITE_HAMMER, "Gravitite Hammer");
        this.addItem(AetherIIItems.GRAVITITE_SPEAR, "Gravitite Spear");
        this.addItem(AetherIIItems.GRAVITITE_PICKAXE, "Gravitite Pickaxe");
        this.addItem(AetherIIItems.GRAVITITE_AXE, "Gravitite Axe");
        this.addItem(AetherIIItems.GRAVITITE_SHOVEL, "Gravitite Shovel");
        this.addItem(AetherIIItems.GRAVITITE_TROWEL, "Gravitite Trowel");

        this.addItem(AetherIIItems.SKYROOT_STICK, "Skyroot Stick");
        this.addItem(AetherIIItems.AMBROSIUM_SHARD, "Ambrosium Shard");
        this.addItem(AetherIIItems.ZANITE_GEMSTONE, "Zanite Gemstone");
        this.addItem(AetherIIItems.RAW_ARKENIUM, "Raw Arkenium");
        this.addItem(AetherIIItems.ARKENIUM_PLATE, "Arkenium Plate");
        this.addItem(AetherIIItems.RAW_GRAVITITE, "Raw Gravitite");
        this.addItem(AetherIIItems.GRAVITITE_PLATE, "Gravitite Plate");
        this.addItem(AetherIIItems.GOLDEN_AMBER, "Golden Amber");
        this.addItem(AetherIIItems.TAEGORE_HIDE, "Taegore Hide");
        this.addItem(AetherIIItems.BURRUKAI_PELT, "Burrukai Pelt");
        this.addItem(AetherIIItems.AECHOR_PETAL, "Aechor Petal");

        this.addItem(AetherIIItems.BLUEBERRY, "Blueberry");
        this.addItem(AetherIIItems.ENCHANTED_BERRY, "Enchanted Berry");
        this.addItem(AetherIIItems.ORANGE, "Orange");
        this.addItem(AetherIIItems.WYNDBERRY, "Wyndberry");
        this.addItem(AetherIIItems.BLUE_SWET_JELLY, "Blue Swet Jelly");
        this.addItem(AetherIIItems.GOLDEN_SWET_JELLY, "Golden Swet Jelly");

        this.addItem(AetherIIItems.AETHER_PORTAL_FRAME, "Aether Portal Frame");

        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_BUILDING_BLOCKS.get(), "Aether II Building Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_DUNGEON_BLOCKS.get(), "Aether II Dungeon Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_NATURAL_BLOCKS.get(), "Aether II Natural Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_FUNCTIONAL_BLOCKS.get(), "Aether II Functional Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_REDSTONE_BLOCKS.get(), "Aether II Redstone Blocks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_EQUIPMENT_AND_UTILITIES.get(), "Aether II Equipment & Utilities");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_ARMOR_AND_ACCESSORIES.get(), "Aether II Armor & Accessories");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_FOOD_AND_DRINKS.get(), "Aether II Food & Drinks");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_INGREDIENTS.get(), "Aether II Ingredients");
        this.addCreativeTab(AetherIICreativeTabs.AETHER_II_SPAWN_EGGS.get(), "Aether II Spawn Eggs");

        this.addSubtitle("block", "aether_portal.ambient", "Aether Portal whooshes");
        this.addSubtitle("block", "aether_portal.trigger", "Aether Portal noise intensifies");
        this.addSubtitle("block", "aercloud.blue_aercloud_bounce", "Blue Aercloud bounces");

        this.addPackDescription("mod", "Aether II Resources");
    }
}