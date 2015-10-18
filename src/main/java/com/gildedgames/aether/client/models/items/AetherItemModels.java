package com.gildedgames.aether.client.models.items;

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
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AetherItemModels
{
	private static HashMap<Item, ItemModelList> models = new HashMap<Item, ItemModelList>();

	public static void preInit()
	{
		defineItemModels();
		prepareModels();
	}

	public static void init()
	{
		registerModels();
	}

	private static void defineItemModels()
	{
		registerItemModels(getItem(BlocksAether.aether_dirt), new ItemModelList().add(0, "aether_dirt"));

		registerItemModels(getItem(BlocksAether.aether_grass), new ItemModelList("aether_grass/")
				.add(BlockAetherGrass.AETHER_GRASS.getMeta(), "aether_grass")
				.add(BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta(), "enchanted_aether_grass"));

		registerItemModels(getItem(BlocksAether.holystone), new ItemModelList("holystone/")
				.add(BlockHolystone.NORMAL_HOLYSTONE.getMeta(), "holystone")
				.add(BlockHolystone.MOSSY_HOLYSTONE.getMeta(), "mossy_holystone"));

		registerItemModels(getItem(BlocksAether.aercloud), new ItemModelList("aercloud/")
				.add(BlockAercloud.COLD_AERCLOUD.getMeta(), "cold_aercloud")
				.add(BlockAercloud.BLUE_AERCLOUD.getMeta(), "blue_aercloud")
				.add(BlockAercloud.GOLDEN_AERCLOUD.getMeta(), "golden_aercloud")
				.add(BlockAercloud.GREEN_AERCLOUD.getMeta(), "green_aercloud")
				.add(BlockAercloud.STORM_AERCLOUD.getMeta(), "storm_aercloud")
				.add(BlockAercloud.PURPLE_AERCLOUD.getMeta(), "purple_aercloud"));

		registerItemModels(getItem(BlocksAether.skyroot_log), new ItemModelList("aether_log/").add(0, "skyroot_log"));

		registerItemModels(getItem(BlocksAether.golden_oak_log), new ItemModelList("aether_log/").add(0, "golden_oak_log"));

		registerItemModels(getItem(BlocksAether.blue_skyroot_leaves), new ItemModelList("aether_leaves/").add(0, "blue_skyroot_leaves"));
		registerItemModels(getItem(BlocksAether.green_skyroot_leaves), new ItemModelList("aether_leaves/").add(0, "green_skyroot_leaves"));
		registerItemModels(getItem(BlocksAether.dark_blue_skyroot_leaves), new ItemModelList("aether_leaves/").add(0, "dark_blue_skyroot_leaves"));
		registerItemModels(getItem(BlocksAether.golden_oak_leaves), new ItemModelList("aether_leaves/").add(0, "golden_oak_leaves"));
		registerItemModels(getItem(BlocksAether.purple_crystal_leaves), new ItemModelList("aether_leaves/").add(0, "purple_crystal_leaves"));
		registerItemModels(getItem(BlocksAether.purple_fruit_leaves), new ItemModelList("aether_leaves/").add(0, "purple_fruit_leaves"));

		registerItemModels(getItem(BlocksAether.blueberry_bush), new ItemModelList("blueberry_bush/")
				.add(BlockBlueberryBush.BERRY_BUSH_STEM, "blueberry_bush_stem")
				.add(BlockBlueberryBush.BERRY_BUSH_RIPE, "blueberry_bush_ripe"));

		registerItemModels(getItem(BlocksAether.aether_flower), new ItemModelList("aether_flower/")
				.add(BlockAetherFlower.WHITE_ROSE.getMeta(), "white_rose")
				.add(BlockAetherFlower.PURPLE_FLOWER.getMeta(), "purple_flower"));

		registerItemModels(getItem(BlocksAether.aether_sapling), new ItemModelList("aether_sapling/")
				.add(BlockAetherSapling.BLUE_SKYROOT_SAPLING.getMeta(), "blue_skyroot_sapling")
				.add(BlockAetherSapling.GREEN_SKYROOT_SAPLING.getMeta(), "green_skyroot_sapling")
				.add(BlockAetherSapling.DARK_BLUE_SKYROOT_SAPLING.getMeta(), "dark_blue_skyroot_sapling")
				.add(BlockAetherSapling.GOLDEN_OAK_SAPLING.getMeta(), "golden_oak_sapling")
				.add(BlockAetherSapling.PURPLE_CRYSTAL_SAPLING.getMeta(), "purple_crystal_sapling"));

		registerItemModels(getItem(BlocksAether.carved_stone), new ItemModelList("carved_stone/")
				.add(BlockDungeon.NORMAL, "carved_stone")
				.add(BlockDungeon.DIVINE, "divine_carved_stone"));

		registerItemModels(getItem(BlocksAether.sentry_stone), new ItemModelList("sentry_stone/")
				.add(BlockDungeon.NORMAL, "sentry_stone")
				.add(BlockDungeon.DIVINE, "divine_sentry_stone"));

		registerItemModels(getItem(BlocksAether.holystone_furnace), new ItemModelList("holystone_furnace/")
				.add(BlockHolystoneFurnace.LIT_META, "holystone_furnace_lit")
				.add(BlockHolystoneFurnace.UNLIT_META, "holystone_furnace_unlit"));

		registerItemModels(getItem(BlocksAether.ambrosium_ore), new ItemModelList("ores/").add(0, "ambrosium_ore"));
		registerItemModels(getItem(BlocksAether.zanite_ore), new ItemModelList("ores/").add(0, "zanite_ore"));
		registerItemModels(getItem(BlocksAether.gravitite_ore), new ItemModelList("ores/").add(0, "gravitite_ore"));
		registerItemModels(getItem(BlocksAether.continuum_ore), new ItemModelList("ores/").add(0, "continuum_ore"));

		registerItemModels(getItem(BlocksAether.aether_portal), new ItemModelList().add(0, "aether_portal"));
		registerItemModels(getItem(BlocksAether.quicksoil), new ItemModelList().add(0, "quicksoil"));
		registerItemModels(getItem(BlocksAether.icestone), new ItemModelList().add(0, "icestone"));
		registerItemModels(getItem(BlocksAether.aerogel), new ItemModelList().add(0, "aerogel"));

		registerItemModels(getItem(BlocksAether.altar), new ItemModelList().add(0, "altar"));
		registerItemModels(getItem(BlocksAether.skyroot_crafting_table), new ItemModelList().add(0, "skyroot_crafting_table"));

		registerItemModels(getItem(BlocksAether.tall_aether_grass), new ItemModelList().add(0, "tall_aether_grass"));
		registerItemModels(getItem(BlocksAether.orange_tree), new ItemModelList().add(0, "orange_tree"));

		registerItemModels(getItem(BlocksAether.zanite_block), new ItemModelList().add(0, "zanite_block"));
		registerItemModels(getItem(BlocksAether.enchanted_gravitite), new ItemModelList().add(0, "enchanted_gravitite"));

		registerItemModels(getItem(BlocksAether.skyroot_planks), new ItemModelList().add(0, "skyroot_planks"));
		registerItemModels(getItem(BlocksAether.holystone_brick), new ItemModelList().add(0, "holystone_brick"));

		registerItemModels(getItem(BlocksAether.quicksoil_glass), new ItemModelList().add(0, "quicksoil_glass"));

		registerItemModels(ItemsAether.skyroot_stick, new ItemModelList().add(0, "skyroot_stick"));
		registerItemModels(ItemsAether.golden_amber, new ItemModelList().add(0, "golden_amber"));

		registerItemModels(ItemsAether.ambrosium_shard, new ItemModelList().add(0, "ambrosium_shard"));
		registerItemModels(ItemsAether.continuum_orb, new ItemModelList().add(0, "continuum_orb"));
		registerItemModels(ItemsAether.zanite_gemstone, new ItemModelList().add(0, "zanite_gemstone"));

		registerItemModels(ItemsAether.skyroot_pickaxe, new ItemModelList("tools/").add(0, "skyroot_pickaxe"));
		registerItemModels(ItemsAether.skyroot_axe, new ItemModelList("tools/").add(0, "skyroot_axe"));
		registerItemModels(ItemsAether.skyroot_shovel, new ItemModelList("tools/").add(0, "skyroot_shovel"));
		registerItemModels(ItemsAether.skyroot_sword, new ItemModelList("weapons/").add(0, "skyroot_sword"));

		registerItemModels(ItemsAether.holystone_pickaxe, new ItemModelList("tools/").add(0, "holystone_pickaxe"));
		registerItemModels(ItemsAether.holystone_axe, new ItemModelList("tools/").add(0, "holystone_axe"));
		registerItemModels(ItemsAether.holystone_shovel, new ItemModelList("tools/").add(0, "holystone_shovel"));
		registerItemModels(ItemsAether.holystone_sword, new ItemModelList("weapons/").add(0, "holystone_sword"));

		registerItemModels(ItemsAether.zanite_pickaxe, new ItemModelList("tools/").add(0, "zanite_pickaxe"));
		registerItemModels(ItemsAether.zanite_axe, new ItemModelList("tools/").add(0, "zanite_axe"));
		registerItemModels(ItemsAether.zanite_shovel, new ItemModelList("tools/").add(0, "zanite_shovel"));
		registerItemModels(ItemsAether.zanite_sword, new ItemModelList("weapons/").add(0, "zanite_sword"));

		registerItemModels(ItemsAether.gravitite_pickaxe, new ItemModelList("tools/").add(0, "gravitite_pickaxe"));
		registerItemModels(ItemsAether.gravitite_axe, new ItemModelList("tools/").add(0, "gravitite_axe"));
		registerItemModels(ItemsAether.gravitite_shovel, new ItemModelList("tools/").add(0, "gravitite_shovel"));
		registerItemModels(ItemsAether.gravitite_sword, new ItemModelList("weapons/").add(0, "gravitite_sword"));

		registerItemModels(ItemsAether.zanite_helmet, new ItemModelList("armor/").add(0, "zanite_helmet"));
		registerItemModels(ItemsAether.zanite_chestplate, new ItemModelList("armor/").add(0, "zanite_chestplate"));
		registerItemModels(ItemsAether.zanite_leggings, new ItemModelList("armor/").add(0, "zanite_leggings"));
		registerItemModels(ItemsAether.zanite_boots, new ItemModelList("armor/").add(0, "zanite_boots"));

		registerItemModels(ItemsAether.gravitite_helmet, new ItemModelList("armor/").add(0, "gravitite_helmet"));
		registerItemModels(ItemsAether.gravitite_chestplate, new ItemModelList("armor/").add(0, "gravitite_chestplate"));
		registerItemModels(ItemsAether.gravitite_leggings, new ItemModelList("armor/").add(0, "gravitite_leggings"));
		registerItemModels(ItemsAether.gravitite_boots, new ItemModelList("armor/").add(0, "gravitite_boots"));

		registerItemModels(ItemsAether.obsidian_helmet, new ItemModelList("armor/").add(0, "obsidian_helmet"));
		registerItemModels(ItemsAether.obsidian_chestplate, new ItemModelList("armor/").add(0, "obsidian_chestplate"));
		registerItemModels(ItemsAether.obsidian_leggings, new ItemModelList("armor/").add(0, "obsidian_leggings"));
		registerItemModels(ItemsAether.obsidian_boots, new ItemModelList("armor/").add(0, "obsidian_boots"));

		registerItemModels(ItemsAether.neptune_helmet, new ItemModelList("armor/").add(0, "neptune_helmet"));
		registerItemModels(ItemsAether.neptune_chestplate, new ItemModelList("armor/").add(0, "neptune_chestplate"));
		registerItemModels(ItemsAether.neptune_leggings, new ItemModelList("armor/").add(0, "neptune_leggings"));
		registerItemModels(ItemsAether.neptune_boots, new ItemModelList("armor/").add(0, "neptune_boots"));

		registerItemModels(ItemsAether.phoenix_helmet, new ItemModelList("armor/").add(0, "phoenix_helmet"));
		registerItemModels(ItemsAether.phoenix_chestplate, new ItemModelList("armor/").add(0, "phoenix_chestplate"));
		registerItemModels(ItemsAether.phoenix_leggings, new ItemModelList("armor/").add(0, "phoenix_leggings"));
		registerItemModels(ItemsAether.phoenix_boots, new ItemModelList("armor/").add(0, "phoenix_boots"));

		registerItemModels(ItemsAether.valkyrie_helmet, new ItemModelList("armor/").add(0, "valkyrie_helmet"));
		registerItemModels(ItemsAether.valkyrie_chestplate, new ItemModelList("armor/").add(0, "valkyrie_chestplate"));
		registerItemModels(ItemsAether.valkyrie_leggings, new ItemModelList("armor/").add(0, "valkyrie_leggings"));
		registerItemModels(ItemsAether.valkyrie_boots, new ItemModelList("armor/").add(0, "valkyrie_boots"));

		registerItemModels(ItemsAether.blueberry, new ItemModelList().add(0, "blueberry"));
		registerItemModels(ItemsAether.orange, new ItemModelList().add(0, "orange"));
		registerItemModels(ItemsAether.healing_stone, new ItemModelList().add(0, "healing_stone"));
		registerItemModels(ItemsAether.wyndberry, new ItemModelList().add(0, "wyndberry"));
		registerItemModels(ItemsAether.rainbow_strawberry, new ItemModelList().add(0, "rainbow_strawberry"));
		registerItemModels(ItemsAether.candy_corn, new ItemModelList().add(0, "candy_corn"));
		registerItemModels(ItemsAether.cocoatrice, new ItemModelList().add(0, "cocoatrice"));
		registerItemModels(ItemsAether.wrapped_chocolates, new ItemModelList().add(0, "wrapped_chocolates"));
		registerItemModels(ItemsAether.jelly_pumpkin, new ItemModelList().add(0, "jelly_pumpkin"));
		registerItemModels(ItemsAether.stomper_pop, new ItemModelList().add(0, "stomper_pop"));
		registerItemModels(ItemsAether.blueberry_lollipop, new ItemModelList().add(0, "blueberry_lollipop"));
		registerItemModels(ItemsAether.orange_lollipop, new ItemModelList().add(0, "orange_lollipop"));
		registerItemModels(ItemsAether.icestone_poprocks, new ItemModelList().add(0, "icestone_poprocks"));
		registerItemModels(ItemsAether.ginger_bread_man, new ItemModelList().add(0, "ginger_bread_man"));
		registerItemModels(ItemsAether.candy_cane, new ItemModelList().add(0, "candy_cane"));

		registerItemModels(ItemsAether.swet_jelly, new ItemModelList("swet_jelly/")
				.add(ItemSwetJelly.JellyType.BLUE.ordinal(), "blue_swet_jelly")
				.add(ItemSwetJelly.JellyType.GOLDEN.ordinal(), "golden_swet_jelly")
				.add(ItemSwetJelly.JellyType.DARK.ordinal(), "dark_swet_jelly"));

		registerItemModels(ItemsAether.gummy_swet, new ItemModelList("gummy_swet/")
				.add(ItemGummySwet.GummyType.BLUE.ordinal(), "blue_gummy_swet")
				.add(ItemGummySwet.GummyType.GOLDEN.ordinal(), "golden_gummy_swet")
				.add(ItemGummySwet.GummyType.DARK.ordinal(), "dark_gummy_swet"));

		registerItemModels(ItemsAether.skyroot_bucket, new ItemModelList("skyroot_bucket/").add(0, "skyroot_bucket"));
		registerItemModels(ItemsAether.skyroot_water_bucket, new ItemModelList("skyroot_bucket/").add(0, "skyroot_water_bucket"));
		registerItemModels(ItemsAether.skyroot_milk_bucket, new ItemModelList("skyroot_bucket/").add(0, "skyroot_milk_bucket"));
		registerItemModels(ItemsAether.skyroot_poison_bucket, new ItemModelList("skyroot_bucket/").add(0, "skyroot_poison_bucket"));

		registerItemModels(ItemsAether.aerwhale_music_disc, new ItemModelList("records/").add(0, "aerwhale_music_disc"));
		registerItemModels(ItemsAether.labyrinth_music_disc, new ItemModelList("records/").add(0, "labyrinth_music_disc"));
		registerItemModels(ItemsAether.moa_music_disc, new ItemModelList("records/").add(0, "moa_music_disc"));
		registerItemModels(ItemsAether.valkyrie_music_disc, new ItemModelList("records/").add(0, "valkyrie_music_disc"));
		registerItemModels(ItemsAether.recording_892, new ItemModelList("records/").add(0, "recording_892"));

		registerItemModels(ItemsAether.dart_shooter, new ItemModelList("dart_shooter/")
				.add(ItemDartShooter.DartShooterType.GOLDEN.ordinal(), "golden_dart_shooter")
				.add(ItemDartShooter.DartShooterType.ENCHANTED.ordinal(), "enchanted_dart_shooter")
				.add(ItemDartShooter.DartShooterType.POISON.ordinal(), "poison_dart_shooter")
				.add(ItemDartShooter.DartShooterType.PHOENIX.ordinal(), "phoenix_dart_shooter"));

		registerItemModels(ItemsAether.dart, new ItemModelList("dart/")
				.add(ItemDartType.GOLDEN.ordinal(), "golden_dart")
				.add(ItemDartType.ENCHANTED.ordinal(), "enchanted_dart")
				.add(ItemDartType.POISON.ordinal(), "poison_dart"));

		registerItemModels(ItemsAether.flaming_sword, new ItemModelList().add(0, "weapons/flaming_sword"));
		registerItemModels(ItemsAether.holy_sword, new ItemModelList().add(0, "weapons/holy_sword"));
		registerItemModels(ItemsAether.lightning_sword, new ItemModelList().add(0, "weapons/lightning_sword"));

		registerItemModels(ItemsAether.pig_slayer, new ItemModelList().add(0, "weapons/pig_slayer"));
	}

	private static void registerItemModels(Item item, ItemModelList list)
	{
		models.put(item, list);
	}

	private static void prepareModels()
	{
		for (Map.Entry<Item, ItemModelList> entry : models.entrySet())
		{
			Item item = entry.getKey();

			Collection<String> registrations = entry.getValue().getRegistrations().values();

			for (String registration : registrations)
			{
				ModelBakery.addVariantName(item, registration);
			}
		}
	}

	private static void registerModels()
	{
		for (HashMap.Entry<Item, ItemModelList> entry : models.entrySet())
		{
			Item item = entry.getKey();

			HashMap<Integer, String> registrations = entry.getValue().getRegistrations();

			for (Map.Entry<Integer, String> registration : registrations.entrySet())
			{
				int meta = registration.getKey();
				String path = registration.getValue();

				ModelResourceLocation resource = new ModelResourceLocation(path, "inventory");

				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, resource);
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
