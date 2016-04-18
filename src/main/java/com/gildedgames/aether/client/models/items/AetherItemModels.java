package com.gildedgames.aether.client.models.items;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;

import com.gildedgames.aether.client.util.ItemModelList;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import com.gildedgames.aether.common.blocks.dungeon.BlockDungeon;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.consumables.ItemGummySwet;
import com.gildedgames.aether.common.items.consumables.ItemSwetJelly;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.tile_entities.TileEntitySkyrootChest;

public class AetherItemModels
{
	private static ArrayList<ItemModelList> models = new ArrayList<>();

	public static void preInit()
	{
		defineModels();
		registerModels();
	}

	private static void defineModels()
	{
		registerItemModels(new ItemModelList(BlocksAether.aether_dirt)
				.add(0, "aether_dirt"));

		registerItemModels(new ItemModelList(BlocksAether.aether_grass).root("aether_grass/")
				.add(BlockAetherGrass.AETHER_GRASS.getMeta(), "aether_grass")
				.add(BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta(), "enchanted_aether_grass"));

		registerItemModels(new ItemModelList(BlocksAether.holystone).root("holystone/")
				.add(BlockHolystone.NORMAL_HOLYSTONE.getMeta(), "holystone")
				.add(BlockHolystone.MOSSY_HOLYSTONE.getMeta(), "mossy_holystone"));

		registerItemModels(new ItemModelList(BlocksAether.aercloud).root("aercloud/")
				.add(BlockAercloud.COLD_AERCLOUD.getMeta(), "cold_aercloud")
				.add(BlockAercloud.BLUE_AERCLOUD.getMeta(), "blue_aercloud")
				.add(BlockAercloud.GOLDEN_AERCLOUD.getMeta(), "golden_aercloud")
				.add(BlockAercloud.GREEN_AERCLOUD.getMeta(), "green_aercloud")
				.add(BlockAercloud.STORM_AERCLOUD.getMeta(), "storm_aercloud")
				.add(BlockAercloud.PURPLE_AERCLOUD.getMeta(), "purple_aercloud"));

		registerItemModels(new ItemModelList(BlocksAether.skyroot_log).add(0, "aether_log/skyroot_log"));

		registerItemModels(new ItemModelList(BlocksAether.golden_oak_log).add(0, "aether_log/golden_oak_log"));

		registerItemModels(new ItemModelList(BlocksAether.blue_skyroot_leaves).add(0, "aether_leaves/blue_skyroot_leaves"));
		registerItemModels(new ItemModelList(BlocksAether.green_skyroot_leaves).add(0, "aether_leaves/green_skyroot_leaves"));
		registerItemModels(new ItemModelList(BlocksAether.dark_blue_skyroot_leaves).add(0, "aether_leaves/dark_blue_skyroot_leaves"));
		registerItemModels(new ItemModelList(BlocksAether.golden_oak_leaves).add(0, "aether_leaves/golden_oak_leaves"));
		registerItemModels(new ItemModelList(BlocksAether.purple_crystal_leaves).add(0, "aether_leaves/purple_crystal_leaves"));
		registerItemModels(new ItemModelList(BlocksAether.purple_fruit_leaves).add(0, "aether_leaves/purple_fruit_leaves"));

		registerItemModels(new ItemModelList(BlocksAether.blueberry_bush).root("blueberry_bush/")
				.add(BlockBlueberryBush.BERRY_BUSH_STEM, "blueberry_bush_stem")
				.add(BlockBlueberryBush.BERRY_BUSH_RIPE, "blueberry_bush_ripe"));

		registerItemModels(new ItemModelList(BlocksAether.aether_flower).root("aether_flower/")
				.add(BlockAetherFlower.WHITE_ROSE.getMeta(), "white_rose")
				.add(BlockAetherFlower.PURPLE_FLOWER.getMeta(), "purple_flower"));

		registerItemModels(new ItemModelList(BlocksAether.aether_sapling).root("aether_sapling/")
				.add(BlockAetherSapling.BLUE_SKYROOT_SAPLING.getMeta(), "blue_skyroot_sapling")
				.add(BlockAetherSapling.GREEN_SKYROOT_SAPLING.getMeta(), "green_skyroot_sapling")
				.add(BlockAetherSapling.DARK_BLUE_SKYROOT_SAPLING.getMeta(), "dark_blue_skyroot_sapling")
				.add(BlockAetherSapling.GOLDEN_OAK_SAPLING.getMeta(), "golden_oak_sapling")
				.add(BlockAetherSapling.PURPLE_CRYSTAL_SAPLING.getMeta(), "purple_crystal_sapling"));

		registerItemModels(new ItemModelList(BlocksAether.carved_stone).root("carved_stone/")
				.add(BlockDungeon.NORMAL, "carved_stone")
				.add(BlockDungeon.DIVINE, "divine_carved_stone"));

		registerItemModels(new ItemModelList(BlocksAether.sentry_stone).root("sentry_stone/")
				.add(BlockDungeon.NORMAL, "sentry_stone")
				.add(BlockDungeon.DIVINE, "divine_sentry_stone"));

		registerItemModels(new ItemModelList(BlocksAether.holystone_furnace).root("holystone_furnace/")
				.add(BlockHolystoneFurnace.LIT_META, "holystone_furnace_lit")
				.add(BlockHolystoneFurnace.UNLIT_META, "holystone_furnace_unlit"));

		registerItemModels(new ItemModelList(BlocksAether.skyroot_fence).add(0, "skyroot_fence"));
		registerItemModels(new ItemModelList(BlocksAether.skyroot_fence_gate).add(0, "skyroot_fence_gate"));

		registerItemModels(new ItemModelList(BlocksAether.ambrosium_ore).add(0, "ores/ambrosium_ore"));
		registerItemModels(new ItemModelList(BlocksAether.zanite_ore).add(0, "ores/zanite_ore"));
		registerItemModels(new ItemModelList(BlocksAether.gravitite_ore).add(0, "ores/gravitite_ore"));
		registerItemModels(new ItemModelList(BlocksAether.continuum_ore).add(0, "ores/continuum_ore"));

		registerItemModels(new ItemModelList(BlocksAether.quicksoil).add(0, "quicksoil"));
		registerItemModels(new ItemModelList(BlocksAether.icestone_ore).add(0, "icestone_ore"));
		registerItemModels(new ItemModelList(BlocksAether.icestone_bricks).add(0, "icestone_bricks"));
		registerItemModels(new ItemModelList(BlocksAether.aerogel).add(0, "aerogel"));

		registerItemModels(new ItemModelList(BlocksAether.altar).add(0, "altar"));
		registerItemModels(new ItemModelList(BlocksAether.skyroot_crafting_table).add(0, "skyroot_crafting_table"));

		registerItemModels(new ItemModelList(BlocksAether.tall_aether_grass).add(0, "tall_aether_grass"));
		registerItemModels(new ItemModelList(BlocksAether.orange_tree).add(0, "orange_tree"));

		registerItemModels(new ItemModelList(BlocksAether.zanite_block).add(0, "zanite_block"));
		registerItemModels(new ItemModelList(BlocksAether.enchanted_gravitite).add(0, "enchanted_gravitite"));

		registerItemModels(new ItemModelList(BlocksAether.skyroot_planks).add(0, "skyroot_planks"));
		registerItemModels(new ItemModelList(BlocksAether.holystone_brick).add(0, "holystone_brick"));

		registerItemModels(new ItemModelList(BlocksAether.quicksoil_glass).add(0, "quicksoil_glass"));

		registerItemModels(new ItemModelList(BlocksAether.aether_portal).add(0, "aether_portal"));

		registerItemModels(new ItemModelList(BlocksAether.standing_skyroot_sign).add(0, "tesr"));
		registerItemModels(new ItemModelList(BlocksAether.wall_skyroot_sign).add(0, "tesr"));

		registerItemModels(new ItemModelList(ItemsAether.skyroot_stick).add(0, "skyroot_stick"));
		registerItemModels(new ItemModelList(ItemsAether.golden_amber).add(0, "golden_amber"));

		registerItemModels(new ItemModelList(ItemsAether.ambrosium_shard).add(0, "ambrosium_shard"));
		registerItemModels(new ItemModelList(ItemsAether.continuum_orb).add(0, "continuum_orb"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_gemstone).add(0, "zanite_gemstone"));

		registerItemModels(new ItemModelList(ItemsAether.skyroot_pickaxe).add(0, "tools/skyroot_pickaxe"));
		registerItemModels(new ItemModelList(ItemsAether.skyroot_axe).add(0, "tools/skyroot_axe"));
		registerItemModels(new ItemModelList(ItemsAether.skyroot_shovel).add(0, "tools/skyroot_shovel"));
		registerItemModels(new ItemModelList(ItemsAether.skyroot_sword).add(0, "weapons/skyroot_sword"));

		registerItemModels(new ItemModelList(ItemsAether.holystone_pickaxe).add(0, "tools/holystone_pickaxe"));
		registerItemModels(new ItemModelList(ItemsAether.holystone_axe).add(0, "tools/holystone_axe"));
		registerItemModels(new ItemModelList(ItemsAether.holystone_shovel).add(0, "tools/holystone_shovel"));
		registerItemModels(new ItemModelList(ItemsAether.holystone_sword).add(0, "weapons/holystone_sword"));

		registerItemModels(new ItemModelList(ItemsAether.zanite_pickaxe).add(0, "tools/zanite_pickaxe"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_axe).add(0, "tools/zanite_axe"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_shovel).add(0, "tools/zanite_shovel"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_sword).add(0, "weapons/zanite_sword"));

		registerItemModels(new ItemModelList(ItemsAether.gravitite_pickaxe).add(0, "tools/gravitite_pickaxe"));
		registerItemModels(new ItemModelList(ItemsAether.gravitite_axe).add(0, "tools/gravitite_axe"));
		registerItemModels(new ItemModelList(ItemsAether.gravitite_shovel).add(0, "tools/gravitite_shovel"));
		registerItemModels(new ItemModelList(ItemsAether.gravitite_sword).add(0, "weapons/gravitite_sword"));

		registerItemModels(new ItemModelList(ItemsAether.valkyrie_pickaxe).add(0, "tools/valkyrie_pickaxe"));
		registerItemModels(new ItemModelList(ItemsAether.valkyrie_axe).add(0, "tools/valkyrie_axe"));
		registerItemModels(new ItemModelList(ItemsAether.valkyrie_shovel).add(0, "tools/valkyrie_shovel"));
		registerItemModels(new ItemModelList(ItemsAether.valkyrie_lance).add(0, "weapons/valkyrie_lance"));

		registerItemModels(new ItemModelList(ItemsAether.zanite_helmet).add(0, "armor/zanite_helmet"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_chestplate).add(0, "armor/zanite_chestplate"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_leggings).add(0, "armor/zanite_leggings"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_boots).add(0, "armor/zanite_boots"));

		registerItemModels(new ItemModelList(ItemsAether.gravitite_helmet).add(0, "armor/gravitite_helmet"));
		registerItemModels(new ItemModelList(ItemsAether.gravitite_chestplate).add(0, "armor/gravitite_chestplate"));
		registerItemModels(new ItemModelList(ItemsAether.gravitite_leggings).add(0, "armor/gravitite_leggings"));
		registerItemModels(new ItemModelList(ItemsAether.gravitite_boots).add(0, "armor/gravitite_boots"));

		registerItemModels(new ItemModelList(ItemsAether.obsidian_helmet).add(0, "armor/obsidian_helmet"));
		registerItemModels(new ItemModelList(ItemsAether.obsidian_chestplate).add(0, "armor/obsidian_chestplate"));
		registerItemModels(new ItemModelList(ItemsAether.obsidian_leggings).add(0, "armor/obsidian_leggings"));
		registerItemModels(new ItemModelList(ItemsAether.obsidian_boots).add(0, "armor/obsidian_boots"));

		registerItemModels(new ItemModelList(ItemsAether.neptune_helmet).add(0, "armor/neptune_helmet"));
		registerItemModels(new ItemModelList(ItemsAether.neptune_chestplate).add(0, "armor/neptune_chestplate"));
		registerItemModels(new ItemModelList(ItemsAether.neptune_leggings).add(0, "armor/neptune_leggings"));
		registerItemModels(new ItemModelList(ItemsAether.neptune_boots).add(0, "armor/neptune_boots"));

		registerItemModels(new ItemModelList(ItemsAether.phoenix_helmet).add(0, "armor/phoenix_helmet"));
		registerItemModels(new ItemModelList(ItemsAether.phoenix_chestplate).add(0, "armor/phoenix_chestplate"));
		registerItemModels(new ItemModelList(ItemsAether.phoenix_leggings).add(0, "armor/phoenix_leggings"));
		registerItemModels(new ItemModelList(ItemsAether.phoenix_boots).add(0, "armor/phoenix_boots"));

		registerItemModels(new ItemModelList(ItemsAether.valkyrie_helmet).add(0, "armor/valkyrie_helmet"));
		registerItemModels(new ItemModelList(ItemsAether.valkyrie_chestplate).add(0, "armor/valkyrie_chestplate"));
		registerItemModels(new ItemModelList(ItemsAether.valkyrie_leggings).add(0, "armor/valkyrie_leggings"));
		registerItemModels(new ItemModelList(ItemsAether.valkyrie_boots).add(0, "armor/valkyrie_boots"));

		registerItemModels(new ItemModelList(ItemsAether.sentry_boots).add(0, "armor/sentry_boots"));

		registerItemModels(new ItemModelList(ItemsAether.blueberry).add(0, "blueberry"));
		registerItemModels(new ItemModelList(ItemsAether.orange).add(0, "orange"));
		registerItemModels(new ItemModelList(ItemsAether.healing_stone).add(0, "healing_stone"));
		registerItemModels(new ItemModelList(ItemsAether.wyndberry).add(0, "wyndberry"));
		registerItemModels(new ItemModelList(ItemsAether.rainbow_strawberry).add(0, "rainbow_strawberry"));
		registerItemModels(new ItemModelList(ItemsAether.candy_corn).add(0, "candy_corn"));
		registerItemModels(new ItemModelList(ItemsAether.cocoatrice).add(0, "cocoatrice"));
		registerItemModels(new ItemModelList(ItemsAether.wrapped_chocolates).add(0, "wrapped_chocolates"));
		registerItemModels(new ItemModelList(ItemsAether.jelly_pumpkin).add(0, "jelly_pumpkin"));
		registerItemModels(new ItemModelList(ItemsAether.stomper_pop).add(0, "stomper_pop"));
		registerItemModels(new ItemModelList(ItemsAether.blueberry_lollipop).add(0, "blueberry_lollipop"));
		registerItemModels(new ItemModelList(ItemsAether.orange_lollipop).add(0, "orange_lollipop"));
		registerItemModels(new ItemModelList(ItemsAether.icestone_poprocks).add(0, "icestone_poprocks"));
		registerItemModels(new ItemModelList(ItemsAether.ginger_bread_man).add(0, "ginger_bread_man"));
		registerItemModels(new ItemModelList(ItemsAether.candy_cane).add(0, "candy_cane"));

		registerItemModels(new ItemModelList(ItemsAether.swet_jelly).root("swet_jelly/")
				.add(ItemSwetJelly.JellyType.BLUE.ordinal(), "blue_swet_jelly")
				.add(ItemSwetJelly.JellyType.GOLDEN.ordinal(), "golden_swet_jelly")
				.add(ItemSwetJelly.JellyType.DARK.ordinal(), "dark_swet_jelly"));

		registerItemModels(new ItemModelList(ItemsAether.gummy_swet).root("gummy_swet/")
				.add(ItemGummySwet.GummyType.BLUE.ordinal(), "blue_gummy_swet")
				.add(ItemGummySwet.GummyType.GOLDEN.ordinal(), "golden_gummy_swet")
				.add(ItemGummySwet.GummyType.DARK.ordinal(), "dark_gummy_swet"));

		registerItemModels(new ItemModelList(ItemsAether.skyroot_bucket).add(0, "skyroot_bucket/skyroot_bucket"));
		registerItemModels(new ItemModelList(ItemsAether.skyroot_water_bucket).add(0, "skyroot_bucket/skyroot_water_bucket"));
		registerItemModels(new ItemModelList(ItemsAether.skyroot_milk_bucket).add(0, "skyroot_bucket/skyroot_milk_bucket"));
		registerItemModels(new ItemModelList(ItemsAether.skyroot_poison_bucket).add(0, "skyroot_bucket/skyroot_poison_bucket"));

		registerItemModels(new ItemModelList(ItemsAether.aerwhale_music_disc).add(0, "records/aerwhale_music_disc"));
		registerItemModels(new ItemModelList(ItemsAether.labyrinth_music_disc).add(0, "records/labyrinth_music_disc"));
		registerItemModels(new ItemModelList(ItemsAether.moa_music_disc).add(0, "records/moa_music_disc"));
		registerItemModels(new ItemModelList(ItemsAether.valkyrie_music_disc).add(0, "records/valkyrie_music_disc"));
		registerItemModels(new ItemModelList(ItemsAether.recording_892).add(0, "records/recording_892"));

		registerItemModels(new ItemModelList(ItemsAether.dart_shooter).root("dart_shooter/")
				.add(ItemDartType.GOLDEN.ordinal(), "golden_dart_shooter")
				.add(ItemDartType.ENCHANTED.ordinal(), "enchanted_dart_shooter")
				.add(ItemDartType.POISON.ordinal(), "poison_dart_shooter")
				.add(ItemDartType.PHOENIX.ordinal(), "phoenix_dart_shooter"));

		registerItemModels(new ItemModelList(ItemsAether.dart).root("dart/")
				.add(ItemDartType.GOLDEN.ordinal(), "golden_dart")
				.add(ItemDartType.ENCHANTED.ordinal(), "enchanted_dart")
				.add(ItemDartType.POISON.ordinal(), "poison_dart"));

		registerItemModels(new ItemModelList(ItemsAether.crossbow).root("crossbow/").add(0, "crossbow"));
		registerItemModels(new ItemModelList(ItemsAether.bolt).root("bolts/")
				.add(ItemBoltType.STONE.ordinal(), "stone_bolt")
				.add(ItemBoltType.ZANITE.ordinal(), "zanite_bolt"));

		registerItemModels(new ItemModelList(ItemsAether.flaming_sword).add(0, "weapons/flaming_sword"));
		registerItemModels(new ItemModelList(ItemsAether.holy_sword).add(0, "weapons/holy_sword"));
		registerItemModels(new ItemModelList(ItemsAether.lightning_sword).add(0, "weapons/lightning_sword"));

		registerItemModels(new ItemModelList(ItemsAether.pig_slayer).add(0, "weapons/pig_slayer"));
		registerItemModels(new ItemModelList(ItemsAether.vampire_blade).add(0, "weapons/vampire_blade"));
		registerItemModels(new ItemModelList(ItemsAether.candy_cane_sword).add(0, "weapons/candy_cane_sword"));

		registerItemModels(new ItemModelList(ItemsAether.skyroot_door).add(0, "skyroot_door"));

		registerItemModels(new ItemModelList(ItemsAether.gold_ring).add(0, "accessories/gold_ring"));
		registerItemModels(new ItemModelList(ItemsAether.iron_ring).add(0, "accessories/iron_ring"));
		registerItemModels(new ItemModelList(ItemsAether.iron_pendant).add(0, "accessories/iron_pendant"));
		registerItemModels(new ItemModelList(ItemsAether.gold_pendant).add(0, "accessories/gold_pendant"));

		registerItemModels(new ItemModelList(ItemsAether.zanite_ring).add(0, "accessories/zanite_ring"));
		registerItemModels(new ItemModelList(ItemsAether.zanite_pendant).add(0, "accessories/zanite_pendant"));

		registerItemModels(new ItemModelList(ItemsAether.iron_bubble).add(0, "accessories/iron_bubble"));
		registerItemModels(new ItemModelList(ItemsAether.regeneration_stone).add(0, "accessories/regeneration_stone"));

		registerItemModels(new ItemModelList(BlocksAether.skyroot_chest).add(0, "skyroot_chest"));
		registerItemModels(new ItemModelList(BlocksAether.ambrosium_torch).add(0, "ambrosium_torch"));

		registerItemModels(new ItemModelList(ItemsAether.ice_ring).add(0, "accessories/ice_ring"));
		registerItemModels(new ItemModelList(ItemsAether.ice_pendant).add(0, "accessories/ice_pendant"));

		registerItemModels(new ItemModelList(ItemsAether.daggerfrost_locket).add(0, "accessories/daggerfrost_locket"));

		registerItemModels(new ItemModelList(ItemsAether.candy_ring).add(0, "accessories/candy_ring"));
		registerItemModels(new ItemModelList(ItemsAether.bone_ring).add(0, "accessories/bone_ring"));
		registerItemModels(new ItemModelList(ItemsAether.skyroot_ring).add(0, "accessories/skyroot_ring"));

		registerItemModels(new ItemModelList(ItemsAether.icestone).add(0, "icestone"));

		registerItemModels(new ItemModelList(ItemsAether.skyroot_sign).add(0, "skyroot_sign"));

		registerItemModels(new ItemModelList(BlocksAether.holystone_wall).add(0, "aether_wall/holystone_wall"));
		registerItemModels(new ItemModelList(BlocksAether.holystone_wall_mossy).add(0, "aether_wall/mossy_wall"));
		registerItemModels(new ItemModelList(BlocksAether.holystone_brick_wall).add(0, "aether_wall/holystone_brick_wall"));
		registerItemModels(new ItemModelList(BlocksAether.carved_stone_wall).add(0, "aether_wall/carved_wall"));
		registerItemModels(new ItemModelList(BlocksAether.skyroot_log_wall).add(0, "aether_wall/skyroot_log_wall"));
		registerItemModels(new ItemModelList(BlocksAether.icestone_wall).add(0, "aether_wall/icestone_wall"));
		registerItemModels(new ItemModelList(BlocksAether.aerogel_wall).add(0, "aether_wall/aerogel_wall"));
		registerItemModels(new ItemModelList(BlocksAether.divine_stone_wall).add(0, "aether_wall/divine_stone_wall"));
		registerItemModels(new ItemModelList(BlocksAether.sentry_stone_wall).add(0, "aether_wall/sentry_stone_wall"));
		registerItemModels(new ItemModelList(BlocksAether.divine_sentry_stone_wall).add(0, "aether_wall/divine_sentry_stone_wall"));
		
		registerItemModels(new ItemModelList(ItemsAether.aether_portal_frame).add(0, "aether_portal_frame"));

		ForgeHooksClient.registerTESRItemStack(getItem(BlocksAether.skyroot_chest), 0, TileEntitySkyrootChest.class);
	}

	private static void registerItemModels(ItemModelList list)
	{
		models.add(list);
	}

	private static void registerModels()
	{
		for (ItemModelList list : AetherItemModels.models)
		{
			Item item = list.getItem();

			Map<Integer, ResourceLocation> definitions = list.getRegistrations();

			for (Map.Entry<Integer, ResourceLocation> definition : definitions.entrySet())
			{
				ModelResourceLocation resource = new ModelResourceLocation(definition.getValue(), "inventory");

				ModelLoader.setCustomModelResourceLocation(item, definition.getKey(), resource);
			}
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
