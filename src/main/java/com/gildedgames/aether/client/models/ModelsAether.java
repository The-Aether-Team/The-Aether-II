package com.gildedgames.aether.client.models;

import com.gildedgames.aether.client.models.util.ModelResourceList;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import com.gildedgames.aether.common.blocks.dungeon.BlockDungeon;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockAetherGrass;
import com.gildedgames.aether.common.blocks.natural.BlockAetherLeaves;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherSapling;
import com.gildedgames.aether.common.blocks.natural.plants.BlockBlueberryBush;
import com.gildedgames.aether.common.blocks.natural.plants.BlockOrangeTree;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.consumables.ItemGummySwet;
import com.gildedgames.aether.common.items.consumables.ItemSwetJelly;
import com.gildedgames.aether.common.items.weapons.ItemDart;
import com.gildedgames.aether.common.items.weapons.ItemDartShooter;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModelsAether
{
	private static HashMap<Item, ModelResourceList> models = new HashMap<Item, ModelResourceList>();

	public static void preInit()
	{
		registerStateMappers();
		defineModels();

		prepareModels();
	}

	public static void init()
	{
		registerModels();
	}

	/**
	 * Used to configure how blockstate files are read(?). Called during pre-initialization phase.
	 */
	private static void registerStateMappers()
	{
		StateMap leavesMapper = new StateMap.Builder().addPropertiesToIgnore(BlockAetherLeaves.PROPERTY_CHECK_DECAY, BlockAetherLeaves.PROPERTY_DECAYABLE).build();

		ModelLoader.setCustomStateMapper(BlocksAether.blue_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.green_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.dark_blue_skyroot_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.golden_oak_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.purple_crystal_leaves, leavesMapper);
		ModelLoader.setCustomStateMapper(BlocksAether.purple_fruit_leaves, leavesMapper);

		ModelLoader.setCustomStateMapper(BlocksAether.aether_sapling, new StateMap.Builder().addPropertiesToIgnore(BlockAetherSapling.PROPERTY_STAGE).build());

		ModelLoader.setCustomStateMapper(BlocksAether.aercloud, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockAercloud.PROPERTY_VARIANT) != BlockAercloud.PURPLE_AERCLOUD)
				{
					mappings.remove(BlockAercloud.PROPERTY_FACING);
				}

				ResourceLocation resource = (ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resource, this.getPropertyString(mappings));
			}
		});

		ModelLoader.setCustomStateMapper(BlocksAether.orange_tree, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				LinkedHashMap mappings = Maps.newLinkedHashMap(state.getProperties());

				if ((Boolean) state.getValue(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK))
				{
					if ((Integer) state.getValue(BlockOrangeTree.PROPERTY_STAGE) < 3)
					{
						mappings.remove(BlockOrangeTree.PROPERTY_IS_TOP_BLOCK);
						mappings.remove(BlockOrangeTree.PROPERTY_STAGE);
					}
				}

				ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(state.getBlock());

				return new ModelResourceLocation(resourceLocation, this.getPropertyString(mappings));
			}
		});
	}

	private static void defineModels()
	{
		registerModelList(getItem(BlocksAether.aether_dirt), new ModelResourceList().add(0, "aether_dirt"));

		registerModelList(getItem(BlocksAether.aether_grass), new ModelResourceList("aether_grass/")
				.add(BlockAetherGrass.AETHER_GRASS.getMeta(), "aether_grass")
				.add(BlockAetherGrass.ENCHANTED_AETHER_GRASS.getMeta(), "enchanted_aether_grass"));

		registerModelList(getItem(BlocksAether.holystone), new ModelResourceList("holystone/")
				.add(BlockHolystone.NORMAL_HOLYSTONE.getMeta(), "holystone")
				.add(BlockHolystone.MOSSY_HOLYSTONE.getMeta(), "mossy_holystone"));

		registerModelList(getItem(BlocksAether.aercloud), new ModelResourceList("aercloud/")
				.add(BlockAercloud.COLD_AERCLOUD.getMeta(), "cold_aercloud")
				.add(BlockAercloud.BLUE_AERCLOUD.getMeta(), "blue_aercloud")
				.add(BlockAercloud.GOLDEN_AERCLOUD.getMeta(), "golden_aercloud")
				.add(BlockAercloud.GREEN_AERCLOUD.getMeta(), "green_aercloud")
				.add(BlockAercloud.STORM_AERCLOUD.getMeta(), "storm_aercloud")
				.add(BlockAercloud.PURPLE_AERCLOUD.getMeta(), "purple_aercloud"));

		registerModelList(getItem(BlocksAether.skyroot_log), new ModelResourceList("aether_log/").add(0, "skyroot_log"));
		registerModelList(getItem(BlocksAether.golden_oak_log), new ModelResourceList("aether_log/").add(0, "golden_oak_log"));

		registerModelList(getItem(BlocksAether.blue_skyroot_leaves), new ModelResourceList("aether_leaves/").add(0, "blue_skyroot_leaves"));
		registerModelList(getItem(BlocksAether.green_skyroot_leaves), new ModelResourceList("aether_leaves/").add(0, "green_skyroot_leaves"));
		registerModelList(getItem(BlocksAether.dark_blue_skyroot_leaves), new ModelResourceList("aether_leaves/").add(0, "dark_blue_skyroot_leaves"));
		registerModelList(getItem(BlocksAether.golden_oak_leaves), new ModelResourceList("aether_leaves/").add(0, "golden_oak_leaves"));
		registerModelList(getItem(BlocksAether.purple_crystal_leaves), new ModelResourceList("aether_leaves/").add(0, "purple_crystal_leaves"));
		registerModelList(getItem(BlocksAether.purple_fruit_leaves), new ModelResourceList("aether_leaves/").add(0, "purple_fruit_leaves"));

		registerModelList(getItem(BlocksAether.blueberry_bush), new ModelResourceList("blueberry_bush/")
				.add(BlockBlueberryBush.BERRY_BUSH_STEM, "blueberry_bush_stem")
				.add(BlockBlueberryBush.BERRY_BUSH_RIPE, "blueberry_bush_ripe"));

		registerModelList(getItem(BlocksAether.aether_flower), new ModelResourceList("aether_flower/")
				.add(BlockAetherFlower.WHITE_ROSE.getMeta(), "white_rose")
				.add(BlockAetherFlower.PURPLE_FLOWER.getMeta(), "purple_flower"));

		registerModelList(getItem(BlocksAether.aether_sapling), new ModelResourceList("aether_sapling/")
				.add(BlockAetherSapling.BLUE_SKYROOT_SAPLING.getMeta(), "blue_skyroot_sapling")
				.add(BlockAetherSapling.GREEN_SKYROOT_SAPLING.getMeta(), "green_skyroot_sapling")
				.add(BlockAetherSapling.DARK_BLUE_SKYROOT_SAPLING.getMeta(), "dark_blue_skyroot_sapling")
				.add(BlockAetherSapling.GOLDEN_OAK_SAPLING.getMeta(), "golden_oak_sapling")
				.add(BlockAetherSapling.PURPLE_CRYSTAL_SAPLING.getMeta(), "purple_crystal_sapling"));

		registerModelList(getItem(BlocksAether.carved_stone), new ModelResourceList("carved_stone/")
				.add(BlockDungeon.NORMAL, "carved_stone")
				.add(BlockDungeon.DIVINE, "divine_carved_stone"));

		registerModelList(getItem(BlocksAether.sentry_stone), new ModelResourceList("sentry_stone/")
				.add(BlockDungeon.NORMAL, "sentry_stone")
				.add(BlockDungeon.DIVINE, "divine_sentry_stone"));

		registerModelList(getItem(BlocksAether.holystone_furnace), new ModelResourceList("holystone_furnace/")
				.add(BlockHolystoneFurnace.LIT_META, "holystone_furnace_lit")
				.add(BlockHolystoneFurnace.UNLIT_META, "holystone_furnace_unlit"));

		registerModelList(getItem(BlocksAether.ambrosium_ore), new ModelResourceList("ores/").add(0, "ambrosium_ore"));
		registerModelList(getItem(BlocksAether.zanite_ore), new ModelResourceList("ores/").add(0, "zanite_ore"));
		registerModelList(getItem(BlocksAether.gravitite_ore), new ModelResourceList("ores/").add(0, "gravitite_ore"));
		registerModelList(getItem(BlocksAether.continuum_ore), new ModelResourceList("ores/").add(0, "continuum_ore"));

		registerModelList(getItem(BlocksAether.aether_portal), new ModelResourceList().add(0, "aether_portal"));
		registerModelList(getItem(BlocksAether.quicksoil), new ModelResourceList().add(0, "quicksoil"));
		registerModelList(getItem(BlocksAether.icestone), new ModelResourceList().add(0, "icestone"));
		registerModelList(getItem(BlocksAether.aerogel), new ModelResourceList().add(0, "aerogel"));

		registerModelList(getItem(BlocksAether.altar), new ModelResourceList().add(0, "altar"));
		registerModelList(getItem(BlocksAether.skyroot_crafting_table), new ModelResourceList().add(0, "skyroot_crafting_table"));

		registerModelList(getItem(BlocksAether.tall_aether_grass), new ModelResourceList().add(0, "tall_aether_grass"));
		registerModelList(getItem(BlocksAether.orange_tree), new ModelResourceList().add(0, "orange_tree"));

		registerModelList(getItem(BlocksAether.zanite_block), new ModelResourceList().add(0, "zanite_block"));
		registerModelList(getItem(BlocksAether.enchanted_gravitite), new ModelResourceList().add(0, "enchanted_gravitite"));

		registerModelList(getItem(BlocksAether.skyroot_planks), new ModelResourceList().add(0, "skyroot_planks"));
		registerModelList(getItem(BlocksAether.holystone_brick), new ModelResourceList().add(0, "holystone_brick"));

		registerModelList(getItem(BlocksAether.quicksoil_glass), new ModelResourceList().add(0, "quicksoil_glass"));

		registerModelList(ItemsAether.skyroot_stick, new ModelResourceList().add(0, "skyroot_stick"));
		registerModelList(ItemsAether.golden_amber, new ModelResourceList().add(0, "golden_amber"));

		registerModelList(ItemsAether.ambrosium_shard, new ModelResourceList().add(0, "ambrosium_shard"));
		registerModelList(ItemsAether.continuum_orb, new ModelResourceList().add(0, "continuum_orb"));
		registerModelList(ItemsAether.zanite_gemstone, new ModelResourceList().add(0, "zanite_gemstone"));

		registerModelList(ItemsAether.skyroot_pickaxe, new ModelResourceList("tools/").add(0, "skyroot_pickaxe"));
		registerModelList(ItemsAether.skyroot_axe, new ModelResourceList("tools/").add(0, "skyroot_axe"));
		registerModelList(ItemsAether.skyroot_shovel, new ModelResourceList("tools/").add(0, "skyroot_shovel"));
		registerModelList(ItemsAether.skyroot_sword, new ModelResourceList("weapons/").add(0, "skyroot_sword"));

		registerModelList(ItemsAether.holystone_pickaxe, new ModelResourceList("tools/").add(0, "holystone_pickaxe"));
		registerModelList(ItemsAether.holystone_axe, new ModelResourceList("tools/").add(0, "holystone_axe"));
		registerModelList(ItemsAether.holystone_shovel, new ModelResourceList("tools/").add(0, "holystone_shovel"));
		registerModelList(ItemsAether.holystone_sword, new ModelResourceList("weapons/").add(0, "holystone_sword"));

		registerModelList(ItemsAether.zanite_pickaxe, new ModelResourceList("tools/").add(0, "zanite_pickaxe"));
		registerModelList(ItemsAether.zanite_axe, new ModelResourceList("tools/").add(0, "zanite_axe"));
		registerModelList(ItemsAether.zanite_shovel, new ModelResourceList("tools/").add(0, "zanite_shovel"));
		registerModelList(ItemsAether.zanite_sword, new ModelResourceList("weapons/").add(0, "zanite_sword"));

		registerModelList(ItemsAether.gravitite_pickaxe, new ModelResourceList("tools/").add(0, "gravitite_pickaxe"));
		registerModelList(ItemsAether.gravitite_axe, new ModelResourceList("tools/").add(0, "gravitite_axe"));
		registerModelList(ItemsAether.gravitite_shovel, new ModelResourceList("tools/").add(0, "gravitite_shovel"));
		registerModelList(ItemsAether.gravitite_sword, new ModelResourceList("weapons/").add(0, "gravitite_sword"));

		registerModelList(ItemsAether.zanite_helmet, new ModelResourceList("armor/").add(0, "zanite_helmet"));
		registerModelList(ItemsAether.zanite_chestplate, new ModelResourceList("armor/").add(0, "zanite_chestplate"));
		registerModelList(ItemsAether.zanite_leggings, new ModelResourceList("armor/").add(0, "zanite_leggings"));
		registerModelList(ItemsAether.zanite_boots, new ModelResourceList("armor/").add(0, "zanite_boots"));

		registerModelList(ItemsAether.gravitite_helmet, new ModelResourceList("armor/").add(0, "gravitite_helmet"));
		registerModelList(ItemsAether.gravitite_chestplate, new ModelResourceList("armor/").add(0, "gravitite_chestplate"));
		registerModelList(ItemsAether.gravitite_leggings, new ModelResourceList("armor/").add(0, "gravitite_leggings"));
		registerModelList(ItemsAether.gravitite_boots, new ModelResourceList("armor/").add(0, "gravitite_boots"));

		registerModelList(ItemsAether.obsidian_helmet, new ModelResourceList("armor/").add(0, "obsidian_helmet"));
		registerModelList(ItemsAether.obsidian_chestplate, new ModelResourceList("armor/").add(0, "obsidian_chestplate"));
		registerModelList(ItemsAether.obsidian_leggings, new ModelResourceList("armor/").add(0, "obsidian_leggings"));
		registerModelList(ItemsAether.obsidian_boots, new ModelResourceList("armor/").add(0, "obsidian_boots"));

		registerModelList(ItemsAether.neptune_helmet, new ModelResourceList("armor/").add(0, "neptune_helmet"));
		registerModelList(ItemsAether.neptune_chestplate, new ModelResourceList("armor/").add(0, "neptune_chestplate"));
		registerModelList(ItemsAether.neptune_leggings, new ModelResourceList("armor/").add(0, "neptune_leggings"));
		registerModelList(ItemsAether.neptune_boots, new ModelResourceList("armor/").add(0, "neptune_boots"));

		registerModelList(ItemsAether.phoenix_helmet, new ModelResourceList("armor/").add(0, "phoenix_helmet"));
		registerModelList(ItemsAether.phoenix_chestplate, new ModelResourceList("armor/").add(0, "phoenix_chestplate"));
		registerModelList(ItemsAether.phoenix_leggings, new ModelResourceList("armor/").add(0, "phoenix_leggings"));
		registerModelList(ItemsAether.phoenix_boots, new ModelResourceList("armor/").add(0, "phoenix_boots"));

		registerModelList(ItemsAether.valkyrie_helmet, new ModelResourceList("armor/").add(0, "valkyrie_helmet"));
		registerModelList(ItemsAether.valkyrie_chestplate, new ModelResourceList("armor/").add(0, "valkyrie_chestplate"));
		registerModelList(ItemsAether.valkyrie_leggings, new ModelResourceList("armor/").add(0, "valkyrie_leggings"));
		registerModelList(ItemsAether.valkyrie_boots, new ModelResourceList("armor/").add(0, "valkyrie_boots"));

		registerModelList(ItemsAether.blueberry, new ModelResourceList().add(0, "blueberry"));
		registerModelList(ItemsAether.orange, new ModelResourceList().add(0, "orange"));
		registerModelList(ItemsAether.healing_stone, new ModelResourceList().add(0, "healing_stone"));
		registerModelList(ItemsAether.wyndberry, new ModelResourceList().add(0, "wyndberry"));
		registerModelList(ItemsAether.rainbow_strawberry, new ModelResourceList().add(0, "rainbow_strawberry"));
		registerModelList(ItemsAether.candy_corn, new ModelResourceList().add(0, "candy_corn"));
		registerModelList(ItemsAether.swet_jelly, new ModelResourceList()
			.add(ItemSwetJelly.JellyType.BLUE.ordinal(), "blue_swet_jelly")
			.add(ItemSwetJelly.JellyType.GOLDEN.ordinal(), "golden_swet_jelly")
			.add(ItemSwetJelly.JellyType.DARK.ordinal(), "dark_swet_jelly"));
		registerModelList(ItemsAether.gummy_swet, new ModelResourceList()
			.add(ItemGummySwet.GummyType.BLUE.ordinal(), "blue_gummy_swet")
			.add(ItemGummySwet.GummyType.GOLDEN.ordinal(), "golden_gummy_swet")
			.add(ItemGummySwet.GummyType.DARK.ordinal(), "dark_gummy_swet"));

		registerModelList(ItemsAether.skyroot_bucket, new ModelResourceList("skyroot_bucket/").add(0, "skyroot_bucket"));
		registerModelList(ItemsAether.skyroot_water_bucket, new ModelResourceList("skyroot_bucket/").add(0, "skyroot_water_bucket"));
		registerModelList(ItemsAether.skyroot_milk_bucket, new ModelResourceList("skyroot_bucket/").add(0, "skyroot_milk_bucket"));
		registerModelList(ItemsAether.skyroot_poison_bucket, new ModelResourceList("skyroot_bucket/").add(0, "skyroot_poison_bucket"));

		registerModelList(ItemsAether.aerwhale_music_disc, new ModelResourceList("records/").add(0, "aerwhale_music_disc"));
		registerModelList(ItemsAether.labyrinth_music_disc, new ModelResourceList("records/").add(0, "labyrinth_music_disc"));
		registerModelList(ItemsAether.moa_music_disc, new ModelResourceList("records/").add(0, "moa_music_disc"));
		registerModelList(ItemsAether.valkyrie_music_disc, new ModelResourceList("records/").add(0, "valkyrie_music_disc"));
		registerModelList(ItemsAether.recording_892, new ModelResourceList("records/").add(0, "recording_892"));

		registerModelList(ItemsAether.dart_shooter, new ModelResourceList("dart_shooter/")
				.add(ItemDartShooter.DartShooterType.GOLDEN.ordinal(), "golden_dart_shooter")
				.add(ItemDartShooter.DartShooterType.ENCHANTED.ordinal(), "enchanted_dart_shooter")
				.add(ItemDartShooter.DartShooterType.POISON.ordinal(), "poison_dart_shooter")
				.add(ItemDartShooter.DartShooterType.PHOENIX.ordinal(), "phoenix_dart_shooter"));

		registerModelList(ItemsAether.dart, new ModelResourceList("dart/")
				.add(ItemDart.DartType.GOLDEN.ordinal(), "golden_dart")
				.add(ItemDart.DartType.ENCHANTED.ordinal(), "enchanted_dart")
				.add(ItemDart.DartType.POISON.ordinal(), "poison_dart"));
	}

	private static void registerModelList(Item item, ModelResourceList list)
	{
		models.put(item, list);
	}

	private static void prepareModels()
	{
		for (Map.Entry<Item, ModelResourceList> entry : models.entrySet())
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
		for (HashMap.Entry<Item, ModelResourceList> entry : models.entrySet())
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
