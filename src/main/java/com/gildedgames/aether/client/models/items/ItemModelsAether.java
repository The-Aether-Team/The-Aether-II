package com.gildedgames.aether.client.models.items;

import com.gildedgames.aether.client.util.ItemModelBuilder;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.construction.BlockAetherPlanks;
import com.gildedgames.aether.common.blocks.containers.BlockAetherCraftingTable;
import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import com.gildedgames.aether.common.blocks.containers.BlockIncubator;
import com.gildedgames.aether.common.blocks.dungeon.BlockDivine;
import com.gildedgames.aether.common.blocks.dungeon.BlockLabyrinthContainer;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherDirt;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.plants.BlockWovenSticks;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.entities.living.enemies.EntitySwet;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthTotem;
import com.gildedgames.aether.common.tile_entities.TileEntityPresent;
import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Map;

public class ItemModelsAether
{
	public static void preInit()
	{
		registerModels();

		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.skyroot_chest), 0, TileEntitySkyrootChest.class);
		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.labyrinth_totem), 0, TileEntityLabyrinthTotem.class);
		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.altar), 0, TileEntityAltar.class);
		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.present), 0, TileEntityPresent.class);
	}

	private static void registerModels()
	{
		registerItemModels(BlocksAether.aether_dirt, new ItemModelBuilder("aether_dirt/")
				.add(BlockAetherDirt.DIRT.getMeta(), "dirt")
				.add(BlockAetherDirt.PERMAFROST.getMeta(), "permafrost"));

		registerItemModels(BlocksAether.aether_grass, new ItemModelBuilder("grass/")
				.add(BlockAetherGrass.AETHER_GRASS.getMeta(), "aether_grass")
				.add(BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta(), "enchanted_aether_grass")
				.add(BlockAetherGrass.FROSTROOT.getMeta(), "frostroot")
				.add(BlockAetherGrass.BLIGHTED.getMeta(), "blighted_aether_grass"));

		registerItemModels(BlocksAether.holystone, new ItemModelBuilder("holystone/")
				.add(BlockHolystone.NORMAL_HOLYSTONE.getMeta(), "holystone")
				.add(BlockHolystone.MOSSY_HOLYSTONE.getMeta(), "mossy_holystone"));

		registerItemModels(BlocksAether.aercloud, new ItemModelBuilder("aercloud/")
				.add(BlockAercloud.COLD_AERCLOUD.getMeta(), "cold_aercloud")
				.add(BlockAercloud.BLUE_AERCLOUD.getMeta(), "blue_aercloud")
				.add(BlockAercloud.GOLDEN_AERCLOUD.getMeta(), "golden_aercloud")
				.add(BlockAercloud.GREEN_AERCLOUD.getMeta(), "green_aercloud")
				.add(BlockAercloud.STORM_AERCLOUD.getMeta(), "storm_aercloud")
				.add(BlockAercloud.PURPLE_AERCLOUD.getMeta(), "purple_aercloud"));

		registerItemModels(BlocksAether.skyroot_log, "logs/skyroot_log");
		registerItemModels(BlocksAether.golden_oak_log, "logs/golden_oak_log");
		/*registerItemModels(BlocksAether.frostpine_log, "logs/frostpine_log");
		registerItemModels(BlocksAether.blightwillow_log, "logs/blightwillow_log");
		registerItemModels(BlocksAether.earthshifter_log, "logs/earthshifter_log");*/

		registerItemModels(BlocksAether.blue_skyroot_leaves, "leaves/blue_skyroot_leaves");
		registerItemModels(BlocksAether.green_skyroot_leaves, "leaves/green_skyroot_leaves");
		registerItemModels(BlocksAether.dark_blue_skyroot_leaves, "leaves/dark_blue_skyroot_leaves");
		registerItemModels(BlocksAether.golden_oak_leaves, "leaves/golden_oak_leaves");
		/*registerItemModels(BlocksAether.blighted_leaves, "leaves/blighted_leaves");
		registerItemModels(BlocksAether.frostpine_leaves, "leaves/frostpine_leaves");
		registerItemModels(BlocksAether.blightwillow_leaves, "leaves/blightwillow_leaves");
		registerItemModels(BlocksAether.earthshifter_leaves, "leaves/earthshifter_leaves");
		registerItemModels(BlocksAether.vined_earthshifter_leaves, "leaves/vined_earthshifter_leaves");*/

		registerItemModels(BlocksAether.blueberry_bush, new ItemModelBuilder("bushes/")
				.add(BlockBlueberryBush.BERRY_BUSH_STEM, "blueberry_bush_stem")
				.add(BlockBlueberryBush.BERRY_BUSH_RIPE, "blueberry_bush_ripe"));

		/*registerItemModels(BlocksAether.enchanted_blueberry_bush, new ItemModelBuilder("bushes/")
				.add(BlockBlueberryBush.BERRY_BUSH_STEM, "enchanted_blueberry_bush_stem")
				.add(BlockBlueberryBush.BERRY_BUSH_RIPE, "enchanted_blueberry_bush_ripe"));*/

		registerItemModels(BlocksAether.aether_flower, new ItemModelBuilder("flowers/")
				.add(BlockAetherFlower.WHITE_ROSE.getMeta(), "white_rose")
				.add(BlockAetherFlower.PURPLE_FLOWER.getMeta(), "purple_flower")
				.add(BlockAetherFlower.BURSTBLOSSOM.getMeta(), "burstblossom")
				.add(BlockAetherFlower.MOONLIT_BLOOM.getMeta(), "moonlit_bloom"));

		registerItemModels(BlocksAether.aether_sapling, new ItemModelBuilder("saplings/")
				.add(BlockAetherSapling.BLUE_SKYROOT.getMeta(), "blue_skyroot_sapling")
				.add(BlockAetherSapling.GREEN_SKYROOT.getMeta(), "green_skyroot_sapling")
				.add(BlockAetherSapling.DARK_BLUE_SKYROOT.getMeta(), "dark_blue_skyroot_sapling")
				.add(BlockAetherSapling.GOLDEN_OAK.getMeta(), "golden_oak_sapling")
				.add(BlockAetherSapling.BLIGHTED.getMeta(), "blighted_sapling")
				.add(BlockAetherSapling.BLIGHTWILLOW.getMeta(), "blightwillow_sapling")
				.add(BlockAetherSapling.EARTHSHIFTER.getMeta(), "earthshifter_sapling")
				.add(BlockAetherSapling.FROSTPINE.getMeta(), "frostpine_sapling"));

		registerItemModels(BlocksAether.carved_stone, new ItemModelBuilder("carved_stone/")
				.add(BlockDivine.NORMAL, "carved_stone")
				.add(BlockDivine.DIVINE, "divine_carved_stone"));

		registerItemModels(BlocksAether.sentry_stone, new ItemModelBuilder("sentry_stone/")
				.add(BlockDivine.NORMAL, "sentry_stone")
				.add(BlockDivine.DIVINE, "divine_sentry_stone"));

		registerItemModels(BlocksAether.labyrinth_lightstone, "labyrinth_lightstone");

		registerItemModels(BlocksAether.holystone_furnace, new ItemModelBuilder("holystone_furnace/")
				.add(BlockHolystoneFurnace.LIT_META, "holystone_furnace_lit")
				.add(BlockHolystoneFurnace.UNLIT_META, "holystone_furnace_unlit"));

		registerItemModels(BlocksAether.skyroot_fence, "skyroot_fence");
		registerItemModels(BlocksAether.skyroot_fence_gate, "skyroot_fence_gate");

		registerItemModels(BlocksAether.ambrosium_ore, "ores/ambrosium_ore");
		registerItemModels(BlocksAether.zanite_ore, "ores/zanite_ore");
		registerItemModels(BlocksAether.gravitite_ore, "ores/gravitite_ore");
		registerItemModels(BlocksAether.arkenium_ore, "ores/arkenium_ore");

		registerItemModels(BlocksAether.quicksoil, "quicksoil");
		registerItemModels(BlocksAether.icestone_ore, "icestone_ore");
		registerItemModels(BlocksAether.icestone_bricks, "icestone_bricks");
		registerItemModels(BlocksAether.aerogel, "aerogel");

		registerItemModels(BlocksAether.aether_crafting_table, new ItemModelBuilder("crafting_tables/")
				.add(BlockAetherCraftingTable.SKYROOT.getMeta(), "skyroot_crafting_table")
				.add(BlockAetherCraftingTable.BLIGHTWILLOW.getMeta(), "blightwillow_crafting_table")
				.add(BlockAetherCraftingTable.EARTHSHIFTER.getMeta(), "earthshifter_crafting_table")
				.add(BlockAetherCraftingTable.FROSTPINE.getMeta(), "frostpine_crafting_table"));

		registerItemModels(ItemsAether.skyroot_bed, "skyroot_bed");

		registerItemModels(BlocksAether.tall_aether_grass, "tall_aether_grass");

		registerItemModels(BlocksAether.orange_tree, "orange_tree");

		registerItemModels(BlocksAether.zanite_block, "zanite_block");
		registerItemModels(BlocksAether.enchanted_gravitite, "enchanted_gravitite");

		registerItemModels(BlocksAether.aether_planks, new ItemModelBuilder("planks/")
				.add(BlockAetherPlanks.SKYROOT.getMeta(), "skyroot_planks")
				.add(BlockAetherPlanks.BLIGHTWILLOW.getMeta(), "blightwillow_planks")
				.add(BlockAetherPlanks.EARTHSHIFTER.getMeta(), "earthshifter_planks")
				.add(BlockAetherPlanks.FROSTPINE.getMeta(), "frostpine_planks"));

		registerItemModels(BlocksAether.holystone_brick, "holystone_brick");

		registerItemModels(BlocksAether.quicksoil_glass, "quicksoil_glass");

		registerItemModels(BlocksAether.aether_portal, "aether_portal");

		registerItemModels(BlocksAether.standing_skyroot_sign, "tesr");
		registerItemModels(BlocksAether.wall_skyroot_sign, "tesr");

		registerItemModels(ItemsAether.skyroot_stick, "skyroot_stick");
		registerItemModels(ItemsAether.golden_amber, "golden_amber");

		registerItemModels(ItemsAether.ambrosium_shard, "ambrosium_shard");
		registerItemModels(ItemsAether.ambrosium_chunk, "ambrosium_chunk");
		registerItemModels(ItemsAether.zanite_gemstone, "zanite_gemstone");
		registerItemModels(ItemsAether.arkenium, "arkenium");
		registerItemModels(ItemsAether.arkenium_strip, "arkenium_strip");

		registerItemModels(ItemsAether.skyroot_pickaxe, "tools/skyroot_pickaxe");
		registerItemModels(ItemsAether.skyroot_axe, "tools/skyroot_axe");
		registerItemModels(ItemsAether.skyroot_shovel, "tools/skyroot_shovel");
		registerItemModels(ItemsAether.skyroot_sword, "weapons/skyroot_sword");

		registerItemModels(ItemsAether.holystone_pickaxe, "tools/holystone_pickaxe");
		registerItemModels(ItemsAether.holystone_axe, "tools/holystone_axe");
		registerItemModels(ItemsAether.holystone_shovel, "tools/holystone_shovel");
		registerItemModels(ItemsAether.holystone_sword, "weapons/holystone_sword");

		registerItemModels(ItemsAether.zanite_pickaxe, "tools/zanite_pickaxe");
		registerItemModels(ItemsAether.zanite_axe, "tools/zanite_axe");
		registerItemModels(ItemsAether.zanite_shovel, "tools/zanite_shovel");
		registerItemModels(ItemsAether.zanite_sword, "weapons/zanite_sword");

		registerItemModels(ItemsAether.gravitite_pickaxe, "tools/gravitite_pickaxe");
		registerItemModels(ItemsAether.gravitite_axe, "tools/gravitite_axe");
		registerItemModels(ItemsAether.gravitite_shovel, "tools/gravitite_shovel");
		registerItemModels(ItemsAether.gravitite_sword, "weapons/gravitite_sword");

		registerItemModels(ItemsAether.arkenium_pickaxe, "tools/arkenium_pickaxe");
		registerItemModels(ItemsAether.arkenium_axe, "tools/arkenium_axe");
		registerItemModels(ItemsAether.arkenium_shovel, "tools/arkenium_shovel");
		registerItemModels(ItemsAether.arkenium_sword, "weapons/arkenium_sword");
		registerItemModels(ItemsAether.arkenium_shears, "tools/arkenium_shears");

		registerItemModels(ItemsAether.valkyrie_pickaxe, "tools/valkyrie_pickaxe");
		registerItemModels(ItemsAether.valkyrie_axe, "tools/valkyrie_axe");
		registerItemModels(ItemsAether.valkyrie_shovel, "tools/valkyrie_shovel");
		registerItemModels(ItemsAether.valkyrie_lance, "weapons/valkyrie_lance");

		registerItemModels(ItemsAether.zanite_helmet, "armor/zanite_helmet");
		registerItemModels(ItemsAether.zanite_chestplate, "armor/zanite_chestplate");
		registerItemModels(ItemsAether.zanite_leggings, "armor/zanite_leggings");
		registerItemModels(ItemsAether.zanite_boots, "armor/zanite_boots");

		registerItemModels(ItemsAether.gravitite_helmet, "armor/gravitite_helmet");
		registerItemModels(ItemsAether.gravitite_chestplate, "armor/gravitite_chestplate");
		registerItemModels(ItemsAether.gravitite_leggings, "armor/gravitite_leggings");
		registerItemModels(ItemsAether.gravitite_boots, "armor/gravitite_boots");

		registerItemModels(ItemsAether.neptune_helmet, "armor/neptune_helmet");
		registerItemModels(ItemsAether.neptune_chestplate, "armor/neptune_chestplate");
		registerItemModels(ItemsAether.neptune_leggings, "armor/neptune_leggings");
		registerItemModels(ItemsAether.neptune_boots, "armor/neptune_boots");

		registerItemModels(ItemsAether.phoenix_helmet, "armor/phoenix_helmet");
		registerItemModels(ItemsAether.phoenix_chestplate, "armor/phoenix_chestplate");
		registerItemModels(ItemsAether.phoenix_leggings, "armor/phoenix_leggings");
		registerItemModels(ItemsAether.phoenix_boots, "armor/phoenix_boots");

		registerItemModels(ItemsAether.valkyrie_helmet, "armor/valkyrie_helmet");
		registerItemModels(ItemsAether.valkyrie_chestplate, "armor/valkyrie_chestplate");
		registerItemModels(ItemsAether.valkyrie_leggings, "armor/valkyrie_leggings");
		registerItemModels(ItemsAether.valkyrie_boots, "armor/valkyrie_boots");

		registerItemModels(ItemsAether.aechor_petal, "aechor_petal");
		registerItemModels(ItemsAether.blueberries, "blueberries");
		registerItemModels(ItemsAether.enchanted_blueberry, "enchanted_blueberry");
		registerItemModels(ItemsAether.orange, "orange");
		registerItemModels(ItemsAether.healing_stone, "healing_stone/healing_stone");
		registerItemModels(ItemsAether.healing_stone_depleted, "healing_stone/healing_stone_depleted");
		registerItemModels(ItemsAether.wyndberry, "wyndberry");
		registerItemModels(ItemsAether.enchanted_wyndberry, "enchanted_wyndberry");
		registerItemModels(ItemsAether.candy_corn, "candy_corn");
		registerItemModels(ItemsAether.cocoatrice, "cocoatrice");
		registerItemModels(ItemsAether.wrapped_chocolates, "wrapped_chocolates");
		registerItemModels(ItemsAether.jelly_pumpkin, "jelly_pumpkin");
		registerItemModels(ItemsAether.stomper_pop, "stomper_pop");
		registerItemModels(ItemsAether.blueberry_lollipop, "blueberry_lollipop");
		registerItemModels(ItemsAether.orange_lollipop, "orange_lollipop");
		registerItemModels(ItemsAether.icestone_poprocks, "icestone_poprocks");
		registerItemModels(ItemsAether.ginger_bread_man, "ginger_bread_man");
		registerItemModels(ItemsAether.candy_cane, "candy_cane");

		registerItemModels(ItemsAether.swet_jelly, new ItemModelBuilder("swet_jelly/")
				.add(EntitySwet.Type.BLUE.ordinal(), "blue_swet_jelly")
				.add(EntitySwet.Type.GOLDEN.ordinal(), "golden_swet_jelly")
				.add(EntitySwet.Type.DARK.ordinal(), "dark_swet_jelly")
				.add(EntitySwet.Type.LIGHT.ordinal(), "cream_swet_jelly"));

		registerItemModels(ItemsAether.gummy_swet, new ItemModelBuilder("gummy_swet/")
				.add(EntitySwet.Type.BLUE.ordinal(), "blue_gummy_swet")
				.add(EntitySwet.Type.GOLDEN.ordinal(), "golden_gummy_swet")
				.add(EntitySwet.Type.DARK.ordinal(), "dark_gummy_swet")
				.add(EntitySwet.Type.LIGHT.ordinal(), "cream_gummy_swet"));

		registerItemModels(ItemsAether.skyroot_bucket, "skyroot_bucket/skyroot_bucket");
		registerItemModels(ItemsAether.skyroot_water_bucket, "skyroot_bucket/skyroot_water_bucket");
		registerItemModels(ItemsAether.skyroot_milk_bucket, "skyroot_bucket/skyroot_milk_bucket");
		registerItemModels(ItemsAether.skyroot_poison_bucket, "skyroot_bucket/skyroot_poison_bucket");

		registerItemModels(ItemsAether.aerwhale_music_disc, "records/aerwhale_music_disc");
		registerItemModels(ItemsAether.labyrinth_music_disc, "records/labyrinth_music_disc");
		registerItemModels(ItemsAether.moa_music_disc, "records/moa_music_disc");
		registerItemModels(ItemsAether.valkyrie_music_disc, "records/valkyrie_music_disc");
		registerItemModels(ItemsAether.recording_892, "records/recording_892");

		registerItemModels(ItemsAether.dart_shooter, new ItemModelBuilder("dart_shooter/")
				.add(ItemDartType.GOLDEN.ordinal(), "golden_dart_shooter")
				.add(ItemDartType.ENCHANTED.ordinal(), "enchanted_dart_shooter")
				.add(ItemDartType.POISON.ordinal(), "poison_dart_shooter")
				.add(ItemDartType.PHOENIX.ordinal(), "phoenix_dart_shooter"));

		registerItemModels(ItemsAether.dart, new ItemModelBuilder("dart/")
				.add(ItemDartType.GOLDEN.ordinal(), "golden_dart")
				.add(ItemDartType.ENCHANTED.ordinal(), "enchanted_dart")
				.add(ItemDartType.POISON.ordinal(), "poison_dart"));

		registerItemModels(ItemsAether.skyroot_crossbow, "crossbow/skyroot_crossbow");
		registerItemModels(ItemsAether.holystone_crossbow, "crossbow/holystone_crossbow");
		registerItemModels(ItemsAether.zanite_crossbow, "crossbow/zanite_crossbow");
		registerItemModels(ItemsAether.arkenium_crossbow, "crossbow/arkenium_crossbow");
		registerItemModels(ItemsAether.gravitite_crossbow, "crossbow/gravitite_crossbow");
		registerItemModels(ItemsAether.vampire_crossbow, "crossbow/vampire_crossbow");

		registerItemModels(ItemsAether.bolt, new ItemModelBuilder("bolts/")
				.add(ItemBoltType.SKYROOT.ordinal(), "skyroot_bolt")
				.add(ItemBoltType.STONE.ordinal(), "holystone_bolt")
				.add(ItemBoltType.ZANITE.ordinal(), "zanite_bolt")
				.add(ItemBoltType.GRAVITITE.ordinal(), "gravitite_bolt")
				.add(ItemBoltType.ARKENIUM.ordinal(), "arkenium_bolt"));

		registerItemModels(ItemsAether.flaming_sword, "weapons/flaming_sword");
		registerItemModels(ItemsAether.holy_sword, "weapons/holy_sword");
		registerItemModels(ItemsAether.lightning_sword, "weapons/lightning_sword");

		registerItemModels(ItemsAether.vampire_blade, "weapons/vampire_blade");
		registerItemModels(ItemsAether.candy_cane_sword, "weapons/candy_cane_sword");

		registerItemModels(ItemsAether.skyroot_door, "skyroot_door");
		registerItemModels(ItemsAether.arkenium_door, "arkenium_door");
		/*registerItemModels(ItemsAether.blightwillow_door, "blightwillow_door");
		registerItemModels(ItemsAether.earthshifter_door, "earthshifter_door");
		registerItemModels(ItemsAether.frostpine_door, "frostpine_door");*/

		registerItemModels(BlocksAether.skyroot_trapdoor, "skyroot_trapdoor");

		registerItemModels(BlocksAether.skyroot_ladder, "ladders/skyroot_ladder");
		/*registerItemModels(BlocksAether.blightwillow_ladder, "ladders/blightwillow_ladder");
		registerItemModels(BlocksAether.earthshifter_ladder, "ladders/earthshifter_ladder");
		registerItemModels(BlocksAether.frostpine_ladder, "ladders/frostpine_ladder");*/

		registerItemModels(BlocksAether.skyroot_pressure_plate, "skyroot_pressure_plate");
		registerItemModels(BlocksAether.skyroot_button, "skyroot_button");

		registerItemModels(BlocksAether.holystone_pressure_plate, "holystone_pressure_plate");
		registerItemModels(BlocksAether.holystone_button, "holystone_button");

		registerItemModels(ItemsAether.gold_ring, "accessories/gold_ring");
		registerItemModels(ItemsAether.iron_ring, "accessories/iron_ring");
		registerItemModels(ItemsAether.iron_pendant, "accessories/iron_pendant");
		registerItemModels(ItemsAether.gold_pendant, "accessories/gold_pendant");

		registerItemModels(ItemsAether.zanite_ring, "accessories/zanite_ring");
		registerItemModels(ItemsAether.zanite_pendant, "accessories/zanite_pendant");

		registerItemModels(ItemsAether.iron_bubble, "accessories/iron_bubble");
		registerItemModels(ItemsAether.regeneration_stone, "accessories/regeneration_stone");

		registerItemModels(BlocksAether.skyroot_chest, "skyroot_chest");
		registerItemModels(BlocksAether.ambrosium_torch, "ambrosium_torch");

		registerItemModels(ItemsAether.ice_ring, "accessories/ice_ring");
		registerItemModels(ItemsAether.ice_pendant, "accessories/ice_pendant");

		registerItemModels(ItemsAether.daggerfrost_rune, "accessories/daggerfrost_rune");

		registerItemModels(ItemsAether.candy_ring, "accessories/candy_ring");
		registerItemModels(ItemsAether.bone_ring, "accessories/bone_ring");
		registerItemModels(ItemsAether.skyroot_ring, "accessories/skyroot_ring");

		registerItemModels(ItemsAether.icestone, "icestone");

		registerItemModels(ItemsAether.skyroot_sign, "skyroot_sign");

		registerItemModels(BlocksAether.holystone_wall, "aether_wall/holystone_wall");
		registerItemModels(BlocksAether.mossy_holystone_wall, "aether_wall/mossy_holystone_wall");
		registerItemModels(BlocksAether.holystone_brick_wall, "aether_wall/holystone_brick_wall");
		registerItemModels(BlocksAether.carved_stone_wall, "aether_wall/carved_stone_wall");
		registerItemModels(BlocksAether.skyroot_log_wall, "aether_wall/skyroot_log_wall");
		registerItemModels(BlocksAether.icestone_wall, "aether_wall/icestone_wall");
		registerItemModels(BlocksAether.aerogel_wall, "aether_wall/aerogel_wall");
		registerItemModels(BlocksAether.divine_stone_wall, "aether_wall/divine_stone_wall");
		registerItemModels(BlocksAether.sentry_stone_wall, "aether_wall/sentry_stone_wall");
		registerItemModels(BlocksAether.divine_sentry_wall, "aether_wall/divine_sentry_wall");

		registerItemModels(ItemsAether.aether_portal_frame, "aether_portal_frame");

		registerItemModels(BlocksAether.altar, "tesr");
		registerItemModels(BlocksAether.labyrinth_totem, "tesr");
		registerItemModels(BlocksAether.multiblock_dummy, "tesr");
		registerItemModels(BlocksAether.present, "tesr");

		registerItemModels(ItemsAether.zanite_gloves, "accessories/zanite_gloves");
		registerItemModels(ItemsAether.gravitite_gloves, "accessories/gravitite_gloves");
		registerItemModels(ItemsAether.neptune_gloves, "accessories/neptune_gloves");
		registerItemModels(ItemsAether.phoenix_gloves, "accessories/phoenix_gloves");
		registerItemModels(ItemsAether.valkyrie_gloves, "accessories/valkyrie_gloves");
		registerItemModels(ItemsAether.leather_gloves, "accessories/leather_gloves");
		registerItemModels(ItemsAether.iron_gloves, "accessories/iron_gloves");
		registerItemModels(ItemsAether.gold_gloves, "accessories/gold_gloves");
		registerItemModels(ItemsAether.chain_gloves, "accessories/chain_gloves");
		registerItemModels(ItemsAether.diamond_gloves, "accessories/diamond_gloves");

		registerItemModels(BlocksAether.labyrinth_capstone, "labyrinth_capstone");
		registerItemModels(BlocksAether.unstable_labyrinth_capstone, "labyrinth_capstone");
		registerItemModels(BlocksAether.labyrinth_glowing_pillar, "labyrinth_glowing_pillar");
		registerItemModels(BlocksAether.labyrinth_pillar, "labyrinth_pillar");
		registerItemModels(BlocksAether.labyrinth_wall, "labyrinth_wall");
		registerItemModels(BlocksAether.labyrinth_lightstone, "labyrinth_lightstone");
		registerItemModels(BlocksAether.labyrinth_base, "labyrinth_base");
		registerItemModels(BlocksAether.labyrinth_headstone, "labyrinth_headstone");
		registerItemModels(BlocksAether.labyrinth_chest, "labyrinth_chest");

		registerItemModels(ItemsAether.pink_baby_swet, "companions/pink_baby_swet");

		registerItemModels(ItemsAether.shard_of_life, "miscellaneous/shard_of_life");

		registerItemModels(ItemsAether.skyroot_shield, "skyroot_shield");
		registerItemModels(ItemsAether.holystone_shield, "holystone_shield");
		registerItemModels(ItemsAether.zanite_shield, "zanite_shield");
		registerItemModels(ItemsAether.arkenium_shield, "arkenium_shield");
		registerItemModels(ItemsAether.gravitite_shield, "gravitite_shield");

		registerItemModels(ItemsAether.ethereal_stone, "companions/ethereal_stone");
		registerItemModels(ItemsAether.fleeting_stone, "companions/fleeting_stone");
		registerItemModels(ItemsAether.soaring_stone, "companions/soaring_stone");
		registerItemModels(ItemsAether.frostpine_totem, "companions/frostpine_totem");
		registerItemModels(ItemsAether.kraisith_capsule, "companions/kraisith_capsule");
		registerItemModels(ItemsAether.orb_of_arkenzus, "companions/orb_of_arkenzus");
		registerItemModels(ItemsAether.fangrin_capsule, "companions/fangrin_capsule");
		registerItemModels(ItemsAether.death_seal, new ItemModelBuilder("companions/").add(0, "death_seal").add(1, "death_seal_broken"));

		registerItemModels(BlocksAether.skyroot_slab, "aether_slab/skyroot_slab");
		registerItemModels(BlocksAether.holystone_slab, "aether_slab/holystone_slab");
		registerItemModels(BlocksAether.holystone_brick_slab, "aether_slab/holystone_brick_slab");
		registerItemModels(BlocksAether.carved_stone_slab, "aether_slab/carved_stone_slab");
		registerItemModels(BlocksAether.divine_carved_stone_slab, "aether_slab/divine_carved_stone_slab");
		registerItemModels(BlocksAether.sentry_stone_slab, "aether_slab/sentry_stone_slab");
		registerItemModels(BlocksAether.divine_sentry_stone_slab, "aether_slab/divine_sentry_stone_slab");
		registerItemModels(BlocksAether.icestone_slab, "aether_slab/icestone_slab");
		registerItemModels(BlocksAether.labyrinth_capstone_slab, "aether_slab/labyrinth_capstone_slab");
		registerItemModels(BlocksAether.labyrinth_wall_slab, "aether_slab/labyrinth_wall_slab");

		registerItemModels(BlocksAether.labyrinth_strongblock, "labyrinth_strongblock");

		registerItemModels(ItemsAether.barbed_iron_ring, "accessories/barbed_iron_ring");
		registerItemModels(ItemsAether.barbed_gold_ring, "accessories/barbed_gold_ring");

		registerItemModels(ItemsAether.solar_band, "accessories/solar_band");
		registerItemModels(ItemsAether.lunar_band, "accessories/lunar_band");

		registerItemModels(ItemsAether.ring_of_growth, "accessories/ring_of_growth");
		registerItemModels(ItemsAether.plague_coil, "accessories/plague_coil");

		registerItemModels(ItemsAether.fleeting_ring, "accessories/fleeting_ring");
		registerItemModels(ItemsAether.lesser_ring_of_growth, "accessories/lesser_ring_of_growth");
		registerItemModels(ItemsAether.winged_ring, "accessories/winged_ring");
		registerItemModels(ItemsAether.life_coil, "accessories/life_coil");

		registerItemModels(ItemsAether.glamoured_iron_screw, "iron_screw");
		registerItemModels(ItemsAether.wisdom_bauble, "accessories/wisdom_bauble");
		registerItemModels(ItemsAether.glamoured_bone_shard, "bone_shard");
		registerItemModels(ItemsAether.moa_feather, "accessories/moa_feather");
		registerItemModels(ItemsAether.blight_ward, "accessories/blight_ward");
		registerItemModels(ItemsAether.glamoured_skyroot_twig, "skyroot_twig");
		registerItemModels(ItemsAether.glamoured_gold_screw, "gold_screw");
		registerItemModels(ItemsAether.ambrosium_talisman, "accessories/ambrosium_talisman");
		registerItemModels(ItemsAether.sunlit_scroll, "accessories/sunlit_scroll");
		registerItemModels(ItemsAether.moonlit_scroll, "accessories/moonlit_scroll");
		registerItemModels(ItemsAether.glamoured_cockatrice_heart, "cockatrice_heart");
		registerItemModels(ItemsAether.damaged_moa_feather, "accessories/damaged_moa_feather");
		registerItemModels(ItemsAether.osseous_bane, "accessories/osseous_bane");
		registerItemModels(ItemsAether.rot_bane, "accessories/rot_bane");

		registerItemModels(BlocksAether.skyroot_stairs, "aether_stairs/skyroot_stairs");
		registerItemModels(BlocksAether.holystone_stairs, "aether_stairs/holystone_stairs");
		registerItemModels(BlocksAether.mossy_holystone_stairs, "aether_stairs/mossy_holystone_stairs");
		registerItemModels(BlocksAether.holystone_brick_stairs, "aether_stairs/holystone_brick_stairs");
		registerItemModels(BlocksAether.sentry_stone_stairs, "aether_stairs/sentry_stone_stairs");
		registerItemModels(BlocksAether.divine_sentry_stone_stairs, "aether_stairs/divine_sentry_stone_stairs");
		registerItemModels(BlocksAether.carved_stone_stairs, "aether_stairs/carved_stone_stairs");
		registerItemModels(BlocksAether.divine_carved_stone_stairs, "aether_stairs/divine_carved_stone_stairs");
		registerItemModels(BlocksAether.icestone_brick_stairs, "aether_stairs/icestone_brick_stairs");
		registerItemModels(BlocksAether.aerogel_stairs, "aether_stairs/aerogel_stairs");

		registerItemModels(BlocksAether.woven_sticks, new ItemModelBuilder("woven_sticks/")
				.add(BlockWovenSticks.SKYROOT.getMeta(), "woven_skyroot_sticks")
				.add(BlockWovenSticks.BLIGHTWILLOW.getMeta(), "woven_blightwillow_sticks")
				.add(BlockWovenSticks.EARTHSHIFTER.getMeta(), "woven_earthshifter_sticks")
				.add(BlockWovenSticks.FROSTPINE.getMeta(), "woven_frostpine_sticks"));

		registerItemModels(ItemsAether.moa_egg, "moa_egg/moa_egg");
		registerItemModels(ItemsAether.rainbow_moa_egg, "rainbow_moa_egg");

		registerItemModels(BlocksAether.moa_egg, "moa_egg/moa_egg");

		registerItemModels(ItemsAether.aether_developer_wand, "aether_developer_wand");

		registerItemModels(ItemsAether.cloud_parachute, new ItemModelBuilder("cloud_parachute/")
				.add(EntityParachute.Type.COLD.ordinal(), "cold_cloud_parachute")
				.add(EntityParachute.Type.GOLDEN.ordinal(), "golden_cloud_parachute")
				.add(EntityParachute.Type.PURPLE.ordinal(), "purple_cloud_parachute")
				.add(EntityParachute.Type.GREEN.ordinal(), "green_cloud_parachute")
				.add(EntityParachute.Type.BLUE.ordinal(), "blue_cloud_parachute"));

		registerItemModels(ItemsAether.zanite_studded_choker, "accessories/zanite_studded_choker");
		registerItemModels(ItemsAether.lesser_amulet_of_growth, "accessories/lesser_amulet_of_growth");
		registerItemModels(ItemsAether.hide_gorget, "accessories/hide_gorget");
		registerItemModels(ItemsAether.amulet_of_growth, "accessories/amulet_of_growth");
		registerItemModels(ItemsAether.arkenium_studded_choker, "accessories/arkenium_studded_choker");
		registerItemModels(ItemsAether.raegorite_gorget, "accessories/raegorite_gorget");
		registerItemModels(ItemsAether.gruegar_scarf, "accessories/gruegar_scarf");
		registerItemModels(ItemsAether.moon_sect_warden_gorget, "accessories/moon_sect_warden_gorget");
		registerItemModels(ItemsAether.thiefs_gorget, "accessories/thiefs_gorget");
		registerItemModels(ItemsAether.frostward_scarf, "accessories/frostward_scarf");

		registerItemModels(ItemsAether.glamoured_holystone_chip, "holystone_chip");
		registerItemModels(ItemsAether.glamoured_zephyr_husk, "zephyr_husk");
		registerItemModels(ItemsAether.glamoured_ice_shard, "ice_shard");
		registerItemModels(ItemsAether.glamoured_blue_swet_jelly, "swet_jelly/blue_swet_jelly");
		registerItemModels(ItemsAether.glamoured_cockatrice_talons, "cockatrice_talons");
		registerItemModels(ItemsAether.glamoured_coal_ember, "coal_ember");

		registerItemModels(ItemsAether.granite_ring, "accessories/granite_ring");
		registerItemModels(ItemsAether.gust_ring, "accessories/gust_ring");
		registerItemModels(ItemsAether.typhoon_ring, "accessories/typhoon_ring");
		registerItemModels(ItemsAether.sporing_ring, "accessories/sporing_ring");
		registerItemModels(ItemsAether.ember_ring, "accessories/ember_ring");

		registerItemModels(ItemsAether.white_moa_feather, "accessories/white_moa_feather");
		registerItemModels(ItemsAether.sakura_moa_feather, "accessories/sakura_moa_feather");

		registerItemModels(ItemsAether.gravitite_core, "accessories/gravitite_core");
		registerItemModels(ItemsAether.sunlit_tome, "accessories/sunlit_tome");
		registerItemModels(ItemsAether.moonlit_tome, "accessories/moonlit_tome");
		registerItemModels(ItemsAether.primal_totem_of_survival, "accessories/primal_totem_of_survival");
		registerItemModels(ItemsAether.primal_totem_of_rage, "accessories/primal_totem_of_rage");
		registerItemModels(ItemsAether.valkyrie_wings, "accessories/valkyrie_wings");
		registerItemModels(ItemsAether.divine_beacon, "accessories/divine_beacon");
		registerItemModels(ItemsAether.phoenix_rune, "accessories/phoenix_rune");
		registerItemModels(ItemsAether.glamoured_taegore_tusk, "taegore_tusk");

		registerItemModels(ItemsAether.dust_ring, "accessories/dust_ring");
		registerItemModels(ItemsAether.mud_ring, "accessories/mud_ring");
		registerItemModels(ItemsAether.storm_ring, "accessories/storm_ring");
		registerItemModels(ItemsAether.steam_ring, "accessories/steam_ring");

		registerItemModels(ItemsAether.butchers_knife, "accessories/butchers_knife");

		registerItemModels(ItemsAether.gruegar_ring, "accessories/gruegar_ring");
		registerItemModels(ItemsAether.ring_of_strength, "accessories/ring_of_strength");
		registerItemModels(ItemsAether.arkenium_ring, "accessories/arkenium_ring");
		registerItemModels(ItemsAether.swift_ribbon, "accessories/swift_ribbon");
		registerItemModels(ItemsAether.wynd_cluster_ring, "accessories/wynd_cluster_ring");
		registerItemModels(ItemsAether.lesser_ring_of_wisdom, "accessories/lesser_ring_of_wisdom");
		registerItemModels(ItemsAether.ring_of_wisdom, "accessories/ring_of_wisdom");

		registerItemModels(ItemsAether.hide_pouch, "accessories/hide_pouch");
		registerItemModels(ItemsAether.gruegar_pouch, "accessories/gruegar_pouch");
		registerItemModels(ItemsAether.soul_shard, "accessories/soul_shard");
		registerItemModels(ItemsAether.angel_bandage, "accessories/angel_bandage");
		registerItemModels(ItemsAether.swift_rune, "accessories/swift_rune");
		registerItemModels(ItemsAether.wynd_cluster, "accessories/wynd_cluster");
		registerItemModels(ItemsAether.wisdom_rune, "accessories/wisdom_rune");
		registerItemModels(ItemsAether.glamoured_aerogel_chip, "aerogel_chip");

		registerItemModels(ItemsAether.fleeting_scarf, "accessories/fleeting_scarf");
		registerItemModels(ItemsAether.winged_necklace, "accessories/winged_necklace");
		registerItemModels(ItemsAether.gust_amulet, "accessories/gust_amulet");
		registerItemModels(ItemsAether.typhoon_amulet, "accessories/typhoon_amulet");
		registerItemModels(ItemsAether.chain_of_sporing_bones, "accessories/chain_of_sporing_bones");
		registerItemModels(ItemsAether.molten_amulet, "accessories/molten_amulet");
		registerItemModels(ItemsAether.granite_studded_choker, "accessories/granite_studded_choker");
		registerItemModels(ItemsAether.muggers_cloak, "accessories/muggers_cloak");
		registerItemModels(ItemsAether.bandit_shawl, "accessories/bandit_shawl");

		/*registerItemModels(BlocksAether.skyroot_vines, "vines/skyroot_vines");
		registerItemModels(BlocksAether.enchanted_skyroot_vines, "vines/enchanted_skyroot_vines");*/

		registerItemModels(ItemsAether.iron_screw, "iron_screw");
		registerItemModels(ItemsAether.gold_screw, "gold_screw");
		registerItemModels(ItemsAether.bone_shard, "bone_shard");
		registerItemModels(ItemsAether.skyroot_twig, "skyroot_twig");
		registerItemModels(ItemsAether.blue_skyroot_twig, "blue_skyroot_twig");
		registerItemModels(ItemsAether.dark_blue_skyroot_twig, "dark_blue_skyroot_twig");
		/*registerItemModels(ItemsAether.blighted_twig, "blighted_twig");*/
		registerItemModels(ItemsAether.enchanted_skyroot_twig, "enchanted_skyroot_twig");
		registerItemModels(ItemsAether.cockatrice_heart, "cockatrice_heart");
		registerItemModels(ItemsAether.holystone_chip, "holystone_chip");
		registerItemModels(ItemsAether.zephyr_husk, "zephyr_husk");
		registerItemModels(ItemsAether.ice_shard, "ice_shard");
		registerItemModels(ItemsAether.coal_ember, "coal_ember");
		registerItemModels(ItemsAether.aerogel_chip, "aerogel_chip");

		registerItemModels(ItemsAether.cockatrice_keratin, "cockatrice_keratin");
		registerItemModels(ItemsAether.cockatrice_talons, "cockatrice_talons");

		registerItemModels(ItemsAether.taegore_tusk, "taegore_tusk");

		registerItemModels(ItemsAether.glamoured_cockatrice_keratin, "cockatrice_keratin");

		registerItemModels(ItemsAether.irradiated_chunk, "irradiated_chunk");
		registerItemModels(ItemsAether.irradiated_sword, "irradiated_sword");
		registerItemModels(ItemsAether.irradiated_armor, "irradiated_armor");
		registerItemModels(ItemsAether.irradiated_tool, "irradiated_tool");
		registerItemModels(ItemsAether.irradiated_ring, "irradiated_ring");
		registerItemModels(ItemsAether.irradiated_neckwear, "irradiated_neckwear");
		registerItemModels(ItemsAether.irradiated_charm, "irradiated_charm");

		registerItemModels(ItemsAether.irradiated_dust, "irradiated_dust");

		registerItemModels(BlocksAether.frostpine_cooler, "holystone_furnace/holystone_furnace_unlit");

		registerItemModels(BlocksAether.incubator, new ItemModelBuilder("incubator/")
				.add(BlockIncubator.LIT_META, "lit")
				.add(BlockIncubator.UNLIT_META, "unlit"));

		registerItemModels(ItemsAether.sentry_vaultbox, "sentry_vaultbox");
		registerItemModels(ItemsAether.wrapping_paper, "wrapping_paper");
		registerItemModels(ItemsAether.fried_moa_egg, "fried_moa_egg");

		registerItemModels(BlocksAether.labyrinth_container, new ItemModelBuilder("labyrinth_container/")
				.add(BlockLabyrinthContainer.VARIANT_SMALL.getMeta(), "labyrinth_container_small")
				.add(BlockLabyrinthContainer.VARIANT_LARGE.getMeta(), "labyrinth_container_large"));

		ModelLoader.setCustomModelResourceLocation(getItem(BlocksAether.structure_extended), 0, new ModelResourceLocation("structure_block", "inventory"));
	}

	private static void registerItemModels(Block block, String path)
	{
		registerItemModels(getItem(block), path);
	}

	private static void registerItemModels(Item item, String path)
	{
		ModelResourceLocation resource = new ModelResourceLocation(AetherCore.getResourcePath(path), "inventory");

		ModelLoader.setCustomModelResourceLocation(item, 0, resource);
	}

	private static void registerItemModels(Block block, ItemModelBuilder builder)
	{
		registerItemModels(getItem(block), builder);
	}

	private static void registerItemModels(Item item, ItemModelBuilder builder)
	{
		for (Map.Entry<Integer, ModelResourceLocation> entry : builder.getRegistrations().entrySet())
		{
			ModelLoader.setCustomModelResourceLocation(item, entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Shorthand utility method for Item.getItemFromBlock(block).
	 */
	private static Item getItem(Block block)
	{
		return Item.getItemFromBlock(block);
	}
}
