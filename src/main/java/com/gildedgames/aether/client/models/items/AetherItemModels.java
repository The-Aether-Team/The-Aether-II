package com.gildedgames.aether.client.models.items;

import com.gildedgames.aether.client.util.ItemModelBuilder;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import com.gildedgames.aether.common.blocks.dungeon.BlockDivine;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.entities.living.enemies.EntitySwet;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.consumables.ItemGummySwet;
import com.gildedgames.aether.common.items.consumables.ItemSwetJelly;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.tile_entities.TileEntityAltar;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthTotem;
import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Map;

public class AetherItemModels
{
	public static void preInit()
	{
		registerModels();

		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.skyroot_chest), 0, TileEntitySkyrootChest.class);
		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.labyrinth_totem), 0, TileEntityLabyrinthTotem.class);
		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.altar), 0, TileEntityAltar.class);
	}

	private static void registerModels()
	{
		registerItemModels(BlocksAether.aether_dirt, "aether_dirt");

		registerItemModels(BlocksAether.aether_grass, new ItemModelBuilder("aether_grass/")
				.add(BlockAetherGrass.AETHER_GRASS.getMeta(), "aether_grass")
				.add(BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta(), "enchanted_aether_grass"));

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

		registerItemModels(BlocksAether.skyroot_log, "aether_log/skyroot_log");

		registerItemModels(BlocksAether.golden_oak_log, "aether_log/golden_oak_log");

		registerItemModels(BlocksAether.blue_skyroot_leaves, "aether_leaves/blue_skyroot_leaves");
		registerItemModels(BlocksAether.green_skyroot_leaves, "aether_leaves/green_skyroot_leaves");
		registerItemModels(BlocksAether.dark_blue_skyroot_leaves, "aether_leaves/dark_blue_skyroot_leaves");
		registerItemModels(BlocksAether.golden_oak_leaves, "aether_leaves/golden_oak_leaves");
		registerItemModels(BlocksAether.purple_crystal_leaves, "aether_leaves/purple_crystal_leaves");
		registerItemModels(BlocksAether.purple_fruit_leaves, "aether_leaves/purple_fruit_leaves");

		registerItemModels(BlocksAether.blueberry_bush, new ItemModelBuilder("blueberry_bush/")
				.add(BlockBlueberryBush.BERRY_BUSH_STEM, "blueberry_bush_stem")
				.add(BlockBlueberryBush.BERRY_BUSH_RIPE, "blueberry_bush_ripe"));

		registerItemModels(BlocksAether.aether_flower, new ItemModelBuilder("aether_flower/")
				.add(BlockAetherFlower.WHITE_ROSE.getMeta(), "white_rose")
				.add(BlockAetherFlower.PURPLE_FLOWER.getMeta(), "purple_flower"));

		registerItemModels(BlocksAether.aether_sapling, new ItemModelBuilder("aether_sapling/")
				.add(BlockAetherSapling.BLUE_SKYROOT_SAPLING.getMeta(), "blue_skyroot_sapling")
				.add(BlockAetherSapling.GREEN_SKYROOT_SAPLING.getMeta(), "green_skyroot_sapling")
				.add(BlockAetherSapling.DARK_BLUE_SKYROOT_SAPLING.getMeta(), "dark_blue_skyroot_sapling")
				.add(BlockAetherSapling.GOLDEN_OAK_SAPLING.getMeta(), "golden_oak_sapling")
				.add(BlockAetherSapling.PURPLE_CRYSTAL_SAPLING.getMeta(), "purple_crystal_sapling"));

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
		registerItemModels(BlocksAether.continuum_ore, "ores/continuum_ore");
		registerItemModels(BlocksAether.arkenium_ore, "ores/arkenium_ore");

		registerItemModels(BlocksAether.quicksoil, "quicksoil");
		registerItemModels(BlocksAether.icestone_ore, "icestone_ore");
		registerItemModels(BlocksAether.icestone_bricks, "icestone_bricks");
		registerItemModels(BlocksAether.aerogel, "aerogel");

		registerItemModels(BlocksAether.skyroot_crafting_table, "skyroot_crafting_table");
		registerItemModels(ItemsAether.skyroot_bed, "skyroot_bed");

		registerItemModels(BlocksAether.tall_aether_grass, "tall_aether_grass");
		registerItemModels(BlocksAether.orange_tree, "orange_tree");

		registerItemModels(BlocksAether.zanite_block, "zanite_block");
		registerItemModels(BlocksAether.enchanted_gravitite, "enchanted_gravitite");

		registerItemModels(BlocksAether.skyroot_planks, "skyroot_planks");
		registerItemModels(BlocksAether.holystone_brick, "holystone_brick");

		registerItemModels(BlocksAether.quicksoil_glass, "quicksoil_glass");

		registerItemModels(BlocksAether.aether_portal, "aether_portal");

		registerItemModels(BlocksAether.standing_skyroot_sign, "tesr");
		registerItemModels(BlocksAether.wall_skyroot_sign, "tesr");

		registerItemModels(ItemsAether.skyroot_stick, "skyroot_stick");
		registerItemModels(ItemsAether.golden_amber, "golden_amber");

		registerItemModels(ItemsAether.ambrosium_shard, "ambrosium_shard");
		registerItemModels(ItemsAether.ambrosium_chunk, "ambrosium_chunk");
		registerItemModels(ItemsAether.continuum_orb, "continuum_orb");
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

		registerItemModels(ItemsAether.obsidian_helmet, "armor/obsidian_helmet");
		registerItemModels(ItemsAether.obsidian_chestplate, "armor/obsidian_chestplate");
		registerItemModels(ItemsAether.obsidian_leggings, "armor/obsidian_leggings");
		registerItemModels(ItemsAether.obsidian_boots, "armor/obsidian_boots");

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

		registerItemModels(ItemsAether.sentry_boots, "armor/sentry_boots");

		registerItemModels(ItemsAether.aechor_petal, "aechor_petal");
		registerItemModels(ItemsAether.blueberries, "blueberries");
		registerItemModels(ItemsAether.enchanted_blueberry, "enchanted_blueberry");
		registerItemModels(ItemsAether.orange, "orange");
		registerItemModels(ItemsAether.healing_stone, "healing_stone");
		registerItemModels(ItemsAether.healing_stone_depleted, "healing_stone_depleted");
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
				.add(EntitySwet.Type.CREAM.ordinal(), "cream_swet_jelly"));

		registerItemModels(ItemsAether.gummy_swet, new ItemModelBuilder("gummy_swet/")
				.add(EntitySwet.Type.BLUE.ordinal(), "blue_gummy_swet")
				.add(EntitySwet.Type.GOLDEN.ordinal(), "golden_gummy_swet")
				.add(EntitySwet.Type.DARK.ordinal(), "dark_gummy_swet")
				.add(EntitySwet.Type.CREAM.ordinal(), "cream_gummy_swet"));

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

		registerItemModels(ItemsAether.skyroot_crossbow, new ItemModelBuilder("crossbow/")
				.add(0, "skyroot_crossbow_fired")
				.add(1, "skyroot_crossbow_loading1")
				.add(2, "skyroot_crossbow_loading2")
				.add(3, "skyroot_crossbow_loaded")
				.add(4, "skyroot_crossbow_fired"));

		registerItemModels(ItemsAether.holystone_crossbow, new ItemModelBuilder("crossbow/")
				.add(0, "holystone_crossbow_fired")
				.add(1, "holystone_crossbow_loading1")
				.add(2, "holystone_crossbow_loading2")
				.add(3, "holystone_crossbow_loaded")
				.add(4, "holystone_crossbow_fired"));

		registerItemModels(ItemsAether.zanite_crossbow, new ItemModelBuilder("crossbow/")
				.add(0, "zanite_crossbow_fired")
				.add(1, "zanite_crossbow_loading1")
				.add(2, "zanite_crossbow_loading2")
				.add(3, "zanite_crossbow_loaded")
				.add(4, "zanite_crossbow_fired"));

		registerItemModels(ItemsAether.arkenium_crossbow, new ItemModelBuilder("crossbow/")
				.add(0, "arkenium_crossbow_fired")
				.add(1, "arkenium_crossbow_loading1")
				.add(2, "arkenium_crossbow_loading2")
				.add(3, "arkenium_crossbow_loaded")
				.add(4, "arkenium_crossbow_fired"));

		registerItemModels(ItemsAether.gravitite_crossbow, new ItemModelBuilder("crossbow/")
				.add(0, "gravitite_crossbow_fired")
				.add(1, "gravitite_crossbow_loading1")
				.add(2, "gravitite_crossbow_loading2")
				.add(3, "gravitite_crossbow_loaded")
				.add(4, "gravitite_crossbow_fired"));

		registerItemModels(ItemsAether.vampire_crossbow, new ItemModelBuilder("crossbow/")
				.add(0, "vampire_crossbow_fired")
				.add(1, "vampire_crossbow_loading1")
				.add(2, "vampire_crossbow_loading2")
				.add(3, "vampire_crossbow_loaded")
				.add(4, "vampire_crossbow_fired"));

		registerItemModels(ItemsAether.bolt, new ItemModelBuilder("bolts/")
				.add(ItemBoltType.SKYROOT.ordinal(), "skyroot_bolt")
				.add(ItemBoltType.STONE.ordinal(), "holystone_bolt")
				.add(ItemBoltType.ZANITE.ordinal(), "zanite_bolt")
				.add(ItemBoltType.GRAVITITE.ordinal(), "gravitite_bolt")
				.add(ItemBoltType.ARKENIUM.ordinal(), "arkenium_bolt"));

		registerItemModels(ItemsAether.flaming_sword, "weapons/flaming_sword");
		registerItemModels(ItemsAether.holy_sword, "weapons/holy_sword");
		registerItemModels(ItemsAether.lightning_sword, "weapons/lightning_sword");

		registerItemModels(ItemsAether.pig_slayer, "weapons/pig_slayer");
		registerItemModels(ItemsAether.vampire_blade, "weapons/vampire_blade");
		registerItemModels(ItemsAether.candy_cane_sword, "weapons/candy_cane_sword");

		registerItemModels(ItemsAether.skyroot_door, "skyroot_door");
		registerItemModels(ItemsAether.arkenium_door, "arkenium_door");
		registerItemModels(BlocksAether.skyroot_trapdoor, "skyroot_trapdoor");
		registerItemModels(BlocksAether.skyroot_ladder, "skyroot_ladder");
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

		registerItemModels(ItemsAether.daggerfrost_locket, "accessories/daggerfrost_locket");

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

		registerItemModels(ItemsAether.zanite_gloves, "accessories/zanite_gloves");
		registerItemModels(ItemsAether.gravitite_gloves, "accessories/gravitite_gloves");
		registerItemModels(ItemsAether.neptune_gloves, "accessories/neptune_gloves");
		registerItemModels(ItemsAether.phoenix_gloves, "accessories/phoenix_gloves");
		registerItemModels(ItemsAether.valkyrie_gloves, "accessories/valkyrie_gloves");
		registerItemModels(ItemsAether.obsidian_gloves, "accessories/obsidian_gloves");
		registerItemModels(ItemsAether.leather_gloves, "accessories/leather_gloves");
		registerItemModels(ItemsAether.iron_gloves, "accessories/iron_gloves");
		registerItemModels(ItemsAether.gold_gloves, "accessories/gold_gloves");
		registerItemModels(ItemsAether.chain_gloves, "accessories/chain_gloves");
		registerItemModels(ItemsAether.diamond_gloves, "accessories/diamond_gloves");

		registerItemModels(BlocksAether.carved_capstone, "carved_capstone");
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

		registerItemModels(ItemsAether.iron_barbed_wire, "accessories/iron_barbed_wire");
		registerItemModels(ItemsAether.wisdom_bauble, "accessories/wisdom_bauble");
		registerItemModels(ItemsAether.bone_shard, "accessories/bone_shard");
		registerItemModels(ItemsAether.moa_feather, "accessories/moa_feather");
		registerItemModels(ItemsAether.blight_ward, "accessories/blight_ward");
		registerItemModels(ItemsAether.skyroot_twig, "accessories/skyroot_twig");
		registerItemModels(ItemsAether.gold_barbed_wire, "accessories/gold_barbed_wire");
		registerItemModels(ItemsAether.ambrosium_talisman, "accessories/ambrosium_talisman");
		registerItemModels(ItemsAether.carrion_petal, "accessories/carrion_petal");
		registerItemModels(ItemsAether.moonlit_petal, "accessories/moonlit_petal");
		registerItemModels(ItemsAether.cockatrice_heart, "accessories/cockatrice_heart");
		registerItemModels(ItemsAether.damaged_moa_feather, "accessories/damaged_moa_feather");
		registerItemModels(ItemsAether.osseous_bane, "accessories/osseous_bane");
		registerItemModels(ItemsAether.rot_bane, "accessories/rot_bane");
		registerItemModels(ItemsAether.continuum_talisman, "accessories/continuum_talisman");
		registerItemModels(ItemsAether.labyrinth_plans, "accessories/labyrinth_plans");

		registerItemModels(BlocksAether.skyroot_stairs, "aether_stairs/skyroot_stairs");
		registerItemModels(BlocksAether.holystone_stairs, "aether_stairs/holystone_stairs");
		registerItemModels(BlocksAether.mossy_holystone_stairs, "aether_stairs/mossy_holystone_stairs");
		registerItemModels(BlocksAether.holystone_brick_stairs, "aether_stairs/holystone_brick_stairs");
		registerItemModels(BlocksAether.sentry_stone_stairs, "aether_stairs/sentry_stone_stairs");
		registerItemModels(BlocksAether.divine_sentry_stone_stairs, "aether_stairs/divine_sentry_stone_stairs");
		registerItemModels(BlocksAether.carved_stone_stairs, "aether_stairs/carved_stone_stairs");
		registerItemModels(BlocksAether.divine_carved_stone_stairs, "aether_stairs/divine_carved_stone_stairs");

		registerItemModels(BlocksAether.woven_skyroot_sticks, "woven_skyroot_sticks");

		registerItemModels(ItemsAether.moa_egg, "moa_egg/moa_egg");
		registerItemModels(ItemsAether.rainbow_moa_egg, "rainbow_moa_egg");

		registerItemModels(BlocksAether.moa_egg, "moa_egg");
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
