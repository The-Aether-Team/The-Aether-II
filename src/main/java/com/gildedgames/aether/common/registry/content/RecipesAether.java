package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.decorative.BlockRockGlassDecorative;
import com.gildedgames.aether.common.blocks.natural.BlockAercloud;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.natural.plants.BlockWovenSticks;
import com.gildedgames.aether.common.entities.blocks.EntityParachute;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.recipes.RecipePresentCrafting;
import com.gildedgames.aether.common.recipes.RecipeWrappingPaper;
import com.gildedgames.aether.common.recipes.altar.AltarEnchantRecipe;
import com.gildedgames.aether.common.recipes.altar.AltarRepairRecipe;
import com.gildedgames.aether.common.registry.minecraft.AetherFuelHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RecipesAether
{
	private static final Set<IRecipe> CRAFTABLE_RECIPES = new HashSet<>();

	public static void preInit()
	{
		OreDictionary.registerOre("skyrootplanks", BlocksAether.skyroot_planks);
		OreDictionary.registerOre("skyrootplanks", BlocksAether.dark_skyroot_planks);
		OreDictionary.registerOre("skyrootplanks", BlocksAether.light_skyroot_planks);
		OreDictionary.registerOre("feather", ItemsAether.moa_feather);
		OreDictionary.registerOre("feather", ItemsAether.cockatrice_feather);
		OreDictionary.registerOre("aerleather", ItemsAether.taegore_hide);
		OreDictionary.registerOre("aerleather", ItemsAether.burrukai_pelt);
		OreDictionary.registerOre("sugar", ItemsAether.swet_sugar);
	}

	public static void init()
	{
		registerFurnaceRecipes();
		registerCraftingRecipes();
		registerToolRecipes();
		registerArmorRecipes();
		registerAccessoryRecipes();
		registerConsumableRecipes();
		registerAltarRecipes();

		GameRegistry.registerFuelHandler(new AetherFuelHandler());
	}

	private static void registerFurnaceRecipes()
	{
		registerSmeltingRecipe(new ItemStack(BlocksAether.holystone), new ItemStack(BlocksAether.agiosite), 0.1f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.arkenium_ore), new ItemStack(ItemsAether.arkenium), 0.85f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.gravitite_ore), new ItemStack(ItemsAether.gravitite_plate), 1.0f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.quicksoil), new ItemStack(BlocksAether.quicksoil_glass), 0.1f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.moa_egg), new ItemStack(ItemsAether.fried_moa_egg), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.rainbow_moa_egg), new ItemStack(ItemsAether.fried_moa_egg), 0.4f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.crude_scatterglass), new ItemStack(BlocksAether.scatterglass), 0.1f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.raw_taegore_meat), new ItemStack(ItemsAether.taegore_steak), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.burrukai_rib_cut), new ItemStack(ItemsAether.burrukai_ribs), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.kirrid_loin), new ItemStack(ItemsAether.kirrid_cutlet), 0.4f);
	}

	private static void registerCraftingRecipes()
	{
		RecipeSorter.register("aether:wrappingPaper", RecipeWrappingPaper.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
		RecipeSorter.register("aether:presentCrafting", RecipePresentCrafting.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");

		CraftingManager.getInstance().addRecipe(new RecipeWrappingPaper());
		CraftingManager.getInstance().addRecipe(new RecipePresentCrafting());

		// Parachutes

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.COLD.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.COLD_AERCLOUD.getMeta()), 'S', ItemsAether.cloudtwine);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.BLUE.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.BLUE_AERCLOUD.getMeta()), 'S', ItemsAether.cloudtwine);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.GOLDEN.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.GOLDEN_AERCLOUD.getMeta()), 'S', ItemsAether.cloudtwine);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.PURPLE.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.PURPLE_AERCLOUD.getMeta()), 'S', ItemsAether.cloudtwine);

		registerShapedRecipe(new ItemStack(ItemsAether.cloud_parachute, 1, EntityParachute.Type.GREEN.ordinal()), "XXX", " S ",
				'X', new ItemStack(BlocksAether.aercloud, 1, BlockAercloud.GREEN_AERCLOUD.getMeta()), 'S', ItemsAether.cloudtwine);

		// Glass Panes

		registerShapedRecipe(new ItemStack(BlocksAether.quicksoil_glass_pane, 16), "XXX", "XXX",
				'X', BlocksAether.quicksoil_glass);

		registerShapedRecipe(new ItemStack(BlocksAether.quicksoil_glass_pane_decorative, 16, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.quicksoil_glass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.quicksoil_glass_pane_decorative, 16, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.quicksoil_glass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_pane, 16), "XXX", "XXX",
				'X', BlocksAether.scatterglass);

		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_pane_decorative, 16, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.scatterglass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_pane_decorative, 16, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.scatterglass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.crude_scatterglass_pane, 16), "XXX", "XXX",
				'X', BlocksAether.crude_scatterglass);

		registerShapedRecipe(new ItemStack(BlocksAether.crude_scatterglass_pane_decorative, 16, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()), "XXX",
				"XXX",
				'X', new ItemStack(BlocksAether.crude_scatterglass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.crude_scatterglass_pane_decorative, 16, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.crude_scatterglass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.cloudwool_carpet, 3), "XX",
				'X', BlocksAether.cloudwool_block);

		// Aether Saddle
		registerShapedRecipe(new ItemStack(ItemsAether.aether_saddle, 1), "XXX", "XZX",
				'X', "aerleather", 'Z', new ItemStack(ItemsAether.cloudtwine));

		// Skyroot Planks
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_planks, 4), "X",
				'X', new ItemStack(BlocksAether.skyroot_log));

		//Skyroot Planks Decorative Revert
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_planks, 1), "X",
				'X', new ItemStack(BlocksAether.skyroot_decorative));

		// Dark Skyroot Planks
		registerShapedRecipe(new ItemStack(BlocksAether.dark_skyroot_planks, 4), "X",
				'X', new ItemStack(BlocksAether.dark_skyroot_log));

		// Light Skyroot Planks
		registerShapedRecipe(new ItemStack(BlocksAether.light_skyroot_planks, 4), "X",
				'X', new ItemStack(BlocksAether.light_skyroot_log));

		// Skyroot Sticks
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_stick, 4), "X", "X",
				'X', "skyrootplanks");

		registerShapelessRecipe(new ItemStack(ItemsAether.skyroot_stick, 4),
				new ItemStack(BlocksAether.woven_sticks, 1, BlockWovenSticks.SKYROOT.getMeta()));

		// Woven Skyroot Sticks
		registerShapedRecipe(new ItemStack(BlocksAether.woven_sticks, 2, BlockWovenSticks.SKYROOT.getMeta()), "XX", "XX",
				'X', new ItemStack(ItemsAether.skyroot_stick));

		// Skyroot Crafting Table
		registerShapedRecipe(new ItemStack(BlocksAether.aether_crafting_table), "XX", "XX",
				'X', "skyrootplanks");

		// Skyroot Bed
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_bed), "XXX", "YYY",
				'X', new ItemStack(BlocksAether.cloudwool_block),
				'Y', "skyrootplanks");

		// Skyroot Chest
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_chest), "XXX", "X X", "XXX",
				'X', "skyrootplanks");

		// Altar
		registerShapedRecipe(new ItemStack(BlocksAether.altar), "AZA", " H ", "HHH",
				'H', new ItemStack(BlocksAether.holystone), 'Z', new ItemStack(ItemsAether.zanite_gemstone), 'A', new ItemStack(ItemsAether.arkenium));

		// Icestone Cooler
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_cooler), "AAA", "HIH", "SSS",
				'H', new ItemStack(BlocksAether.holystone), 'A', new ItemStack(ItemsAether.arkenium), 'S', "skyrootplanks", 'I',
				new ItemStack(ItemsAether.icestone));

		// Masonry Bench
		registerShapedRecipe(new ItemStack(BlocksAether.masonry_bench), "AAA", "SSS", "HHH",
				'H', new ItemStack(BlocksAether.holystone), 'A', new ItemStack(ItemsAether.arkenium), 'S', "skyrootplanks");

		// Arkenium Strip
		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_strip, 4), "X", "X",
				'X', new ItemStack(ItemsAether.arkenium));

		// Skyroot Door
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_door, 3), "XX", "XX", "XX",
				'X', "skyrootplanks");

		// Arkenium Door
		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_door, 3), "XX", "XX", "XX",
				'X', new ItemStack(ItemsAether.arkenium));

		// Skyroot Trap Door
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_trapdoor, 2), "XXX", "XXX",
				'X', "skyrootplanks");

		// Skyroot Ladder
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_ladder, 3), "X X", "XXX", "X X",
				'X', new ItemStack(ItemsAether.skyroot_stick));

		// Skyroot Pressure Plate
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_pressure_plate), "XX ",
				'X', "skyrootplanks");

		// Skyroot GuiAbstractButton
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_button), "X",
				'X', "skyrootplanks");

		// Holystone Pressure Plate
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_pressure_plate), "XX ",
				'X', new ItemStack(BlocksAether.holystone));

		// Holystone GuiAbstractButton
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_button), "X",
				'X', new ItemStack(BlocksAether.holystone));

		// Golden Dart Shooter
		registerShapedRecipe(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), "X", "X", "Y",
				'X', "skyrootplanks", 'Y', new ItemStack(ItemsAether.golden_amber));

		// Poison Dart Shooter
		registerShapelessRecipe(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.POISON.ordinal()),
				new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), new ItemStack(ItemsAether.skyroot_poison_bucket));

		// Golden Dart
		registerShapedRecipe(new ItemStack(ItemsAether.dart, 8, ItemDartType.GOLDEN.ordinal()), "X ", " Y",
				'X', new ItemStack(ItemsAether.golden_amber),
				'Y', new ItemStack(ItemsAether.skyroot_stick));

		// Poison Dart
		registerShapedRecipe(new ItemStack(ItemsAether.dart, 8, ItemDartType.POISON.ordinal()), "XXX", "XYX", "XXX",
				'X', new ItemStack(ItemsAether.dart),
				'Y', new ItemStack(ItemsAether.skyroot_poison_bucket));

		// Skyroot Bucket
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_bucket), "X X", " X ",
				'X', "skyrootplanks");

		// Zanite Block
		registerShapedRecipe(new ItemStack(BlocksAether.zanite_block), "XXX", "XXX", "XXX",
				'X', new ItemStack(ItemsAether.zanite_gemstone));

		// Gravitite Block
		registerShapedRecipe(new ItemStack(BlocksAether.gravitite_block), "XXX", "XXX", "XXX",
				'X', new ItemStack(ItemsAether.gravitite_plate));

		// Zanite Uncraft
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_gemstone, 9), "X",
				'X', new ItemStack(BlocksAether.zanite_block));

		// Gravitite Uncraft
		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_plate, 9), "X",
				'X', new ItemStack(BlocksAether.gravitite_block));

		// Holystone Brick
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick, 4), "XX", "XX",
				'X', new ItemStack(BlocksAether.holystone));

		// Holystone Brick Decorative Uncraft
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick, 1), "X",
				'X', new ItemStack(BlocksAether.holystone_brick_decorative));

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick, 1), "X",
				'X', new ItemStack(BlocksAether.faded_holystone_brick));

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick, 1), "X",
				'X', new ItemStack(BlocksAether.faded_holystone_brick_decorative));

		// Agiosite Brick
		registerShapedRecipe(new ItemStack(BlocksAether.agiosite_brick, 4), "XX", "XX",
				'X', new ItemStack(BlocksAether.agiosite));

		// Agiosite Brick Decorative Uncraft
		registerShapedRecipe(new ItemStack(BlocksAether.agiosite_brick, 1), "X",
				'X', new ItemStack(BlocksAether.agiosite_brick_decorative));

		// Icestone Bricks
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_bricks, 4), "XX", "XX",
				'X', new ItemStack(ItemsAether.icestone));

		// Icestone Bricks Uncraft
		registerShapedRecipe(new ItemStack(ItemsAether.icestone,4), "X",
				'X', new ItemStack(BlocksAether.icestone_bricks));

		// Icestone Bricks Decorative Uncraft
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_bricks, 1), "X",
				'X', new ItemStack(BlocksAether.icestone_bricks_decorative));

		// Skyroot Bookshelf
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_bookshelf), "XXX", "YYY", "XXX",
				'X', "skyrootplanks",
				'Y', new ItemStack(Items.BOOK));

		// Holystone Bookshelf
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_bookshelf), "XXX", "YYY", "XXX",
				'X', new ItemStack(BlocksAether.holystone_brick),
				'Y', new ItemStack(Items.BOOK));

		// Walls

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.holystone));
		registerShapedRecipe(new ItemStack(BlocksAether.mossy_holystone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.holystone, 1, BlockHolystone.MOSSY_HOLYSTONE.getMeta()));
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.holystone_brick));
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_log_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.skyroot_log));
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.icestone_bricks));
		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.scatterglass));

		// Slabs

		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_slab, 6), "XXX",
				'X', "skyrootplanks");
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.holystone));
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.holystone_brick));
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.icestone_bricks));
		registerShapedRecipe(new ItemStack(BlocksAether.agiosite_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.agiosite));
		registerShapedRecipe(new ItemStack(BlocksAether.agiosite_brick_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.agiosite_brick));
		registerShapedRecipe(new ItemStack(BlocksAether.mossy_holystone_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.holystone, 1, BlockHolystone.MOSSY_HOLYSTONE.getMeta()));
		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_slab, 6), "XXX",
				'X', new ItemStack(BlocksAether.scatterglass));

		// Gates
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_fence_gate, 1), "XYX", "XYX",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', "skyrootplanks");

		// Fences
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_fence, 3), "YXY", "YXY",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', "skyrootplanks");

		// Sign
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_sign, 3), "XXX", "XXX", " Y ",
				'X', "skyrootplanks",
				'Y', new ItemStack(ItemsAether.skyroot_stick));

		// Ambrosium Torch
		registerShapedRecipe(new ItemStack(BlocksAether.ambrosium_torch, 8), " Y", " X",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.ambrosium_shard));

		// Ambrosium Chunk
		registerShapedRecipe(new ItemStack(ItemsAether.ambrosium_chunk, 1), "XX", "XX",
				'X', new ItemStack(ItemsAether.ambrosium_shard));

		// Ambrosium Chunk Uncraft
		registerShapedRecipe(new ItemStack(ItemsAether.ambrosium_shard, 4), "X",
				'X', new ItemStack(ItemsAether.ambrosium_chunk));

		// Depleted Healing Stone
		registerShapedRecipe(new ItemStack(ItemsAether.healing_stone_depleted, 1), " X ", "ZXZ", " Y ",
				'X', new ItemStack(ItemsAether.ambrosium_chunk),
				'Y', new ItemStack(ItemsAether.ambrosium_shard),
				'Z', new ItemStack(BlocksAether.holystone));

		// Holystone Furnace
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_furnace, 1), "XXX", "X X", "XXX",
				'X', new ItemStack(BlocksAether.holystone));

		// Incubator
		registerShapedRecipe(new ItemStack(BlocksAether.incubator), "XXX", "XYX", "ZZZ",
				'X', new ItemStack(BlocksAether.holystone),
				'Y', new ItemStack(ItemsAether.ambrosium_chunk),
				'Z', "skyrootplanks");

		// Crossbow
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(ItemsAether.cloudtwine),
				'Y', "skyrootplanks",
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(ItemsAether.cloudtwine),
				'Y', new ItemStack(BlocksAether.holystone),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(ItemsAether.cloudtwine),
				'Y', new ItemStack(ItemsAether.zanite_gemstone),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(ItemsAether.cloudtwine),
				'Y', new ItemStack(ItemsAether.arkenium),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_crossbow), "ZZY", "XYZ", "WXZ",
				'W', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(ItemsAether.cloudtwine),
				'Y', new ItemStack(ItemsAether.gravitite_plate),
				'Z', new ItemStack(ItemsAether.arkenium_strip));

		// Crossbow Bolts

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 4, ItemBoltType.SKYROOT.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', "skyrootplanks",
				'Z', "feather");

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 4, ItemBoltType.HOLYSTONE.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(BlocksAether.holystone),
				'Z', "feather");

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 4, ItemBoltType.SCATTERGLASS.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(BlocksAether.scatterglass),
				'Z', "feather");

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ZANITE.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone),
				'Z', "feather");

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 8, ItemBoltType.ARKENIUM.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.arkenium_strip),
				'Z', "feather");

		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 16, ItemBoltType.GRAVITITE.ordinal()), "  Z", " X ", "Y  ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.gravitite_plate),
				'Z', "feather");

		// Shields
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', "skyrootplanks");

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(BlocksAether.holystone));

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone));

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.arkenium_strip),
				'Y', new ItemStack(ItemsAether.arkenium));

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_shield), "YXY", "YYY", " Y ",
				'X', new ItemStack(ItemsAether.arkenium_strip),
				'Y', new ItemStack(ItemsAether.gravitite_plate));

		// Stairs

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.holystone));

		registerShapedRecipe(new ItemStack(BlocksAether.mossy_holystone_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.holystone, 1, BlockHolystone.MOSSY_HOLYSTONE.getMeta()));

		registerShapedRecipe(new ItemStack(BlocksAether.icestone_brick_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.icestone_bricks));

		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_stairs, 4), "U  ", "UU ", "UUU",
				'U', "skyrootplanks");

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.holystone_brick));

		registerShapedRecipe(new ItemStack(BlocksAether.scatterglass_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.scatterglass));

		registerShapedRecipe(new ItemStack(BlocksAether.agiosite_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.agiosite));

		registerShapedRecipe(new ItemStack(BlocksAether.agiosite_brick_stairs, 4), "U  ", "UU ", "UUU",
				'U', new ItemStack(BlocksAether.agiosite_brick));

	}

	private static void registerToolRecipes()
	{
		final ItemStack skyrootStick = new ItemStack(ItemsAether.skyroot_stick);

		final ItemStack skyroot = new ItemStack(BlocksAether.skyroot_planks);
		final ItemStack holystone = new ItemStack(BlocksAether.holystone);
		final ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);
		final ItemStack gravitite = new ItemStack(ItemsAether.gravitite_plate);
		final ItemStack arkenium = new ItemStack(ItemsAether.arkenium);
		final ItemStack ark_strip = new ItemStack(ItemsAether.arkenium_strip);

		// Skyroot Tools
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_axe), "XX ", "XY ", " Y ",
				'X', "skyrootplanks", 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_pickaxe), "XXX", " Y ", " Y ",
				'X', "skyrootplanks", 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_shovel), "X", "Y", "Y",
				'X', "skyrootplanks", 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_sword), "X", "X", "Y",
				'X', "skyrootplanks", 'Y', skyrootStick);

		// Holystone Tools
		registerShapedRecipe(new ItemStack(ItemsAether.holystone_axe), "XX ", "XY ", " Y ",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_pickaxe), "XXX", " Y ", " Y ",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_shovel), "X", "Y", "Y",
				'X', holystone, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.holystone_sword), "X", "X", "Y",
				'X', holystone, 'Y', skyrootStick);

		// Zanite Tools
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_axe), "XX ", "XY ", " Y ",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_pickaxe), "XXX", " Y ", " Y ",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_shovel), "X", "Y", "Y",
				'X', zanite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_sword), "X", "X", "Y",
				'X', zanite, 'Y', skyrootStick);

		// Gravitite Tools
		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_axe), "XX ", "XY ", " Y ",
				'X', gravitite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_pickaxe), "XXX", " Y ", " Y ",
				'X', gravitite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_shovel), "X", "Y", "Y",
				'X', gravitite, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_sword), "X", "X", "Y",
				'X', gravitite, 'Y', skyrootStick);

		// Arkenium Tools
		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_axe), "XX ", "XY ", " Y ",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_pickaxe), "XXX", " Y ", " Y ",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_shovel), "X", "Y", "Y",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_sword), "X", "X", "Y",
				'X', arkenium, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_shears), "X ", " X",
				'X', ark_strip);

	}

	private static void registerArmorRecipes()
	{
		final ItemStack taegorehide = new ItemStack(ItemsAether.taegore_hide);
		final ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);
		final ItemStack arkenium = new ItemStack(ItemsAether.arkenium);
		final ItemStack gravitite = new ItemStack(ItemsAether.gravitite_plate);

		// Taegore Hide Armor
		registerShapedRecipe(new ItemStack(ItemsAether.taegore_hide_helmet), "XXX", "X X",
				'X', taegorehide);

		registerShapedRecipe(new ItemStack(ItemsAether.taegore_hide_chestplate), "X X", "XXX", "XXX",
				'X', taegorehide);

		registerShapedRecipe(new ItemStack(ItemsAether.taegore_hide_leggings), "XXX", "X X", "X X",
				'X', taegorehide);

		registerShapedRecipe(new ItemStack(ItemsAether.taegore_hide_boots), "X X", "X X",
				'X', taegorehide);

		registerShapedRecipe(new ItemStack(ItemsAether.taegore_hide_gloves), "X X",
				'X', taegorehide);

		// Zanite Armor
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_helmet), "XXX", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_chestplate), "X X", "XXX", "XXX",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_leggings), "XXX", "X X", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_boots), "X X", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_gloves), "X X",
				'X', zanite);

		// Arkenium Armor
		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_helmet), "XXX", "X X",
				'X', arkenium);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_chestplate), "X X", "XXX", "XXX",
				'X', arkenium);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_leggings), "XXX", "X X", "X X",
				'X', arkenium);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_boots), "X X", "X X",
				'X', arkenium);

		registerShapedRecipe(new ItemStack(ItemsAether.arkenium_gloves), "X X",
				'X', arkenium);

		// Gravitite Armor
		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_helmet), "XXX", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_chestplate), "X X", "XXX", "XXX",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_leggings), "XXX", "X X", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_boots), "X X", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_gloves), "X X",
				'X', gravitite);
	}

	private static void registerAccessoryRecipes()
	{
		// Zanite Ring
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_ring), " X ", "X X", " X ",
				'X', new ItemStack(ItemsAether.zanite_gemstone));
		// Zanite Pendant
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_pendant), "XXX", "X X", " Y ",
				'X', new ItemStack(ItemsAether.cloudtwine),
				'Y', new ItemStack(ItemsAether.zanite_gemstone));
	}

	private static void registerConsumableRecipes()
	{
		// Blue Swet Jelly
		registerShapelessRecipe(new ItemStack(ItemsAether.swet_jelly, 1, 0),
				new ItemStack(ItemsAether.swet_gel, 1, 0), "sugar");

		// Green Swet Jelly
		registerShapelessRecipe(new ItemStack(ItemsAether.swet_jelly, 1, 1),
				new ItemStack(ItemsAether.swet_gel, 1, 1), "sugar");

		// Purple Swet Jelly
		registerShapelessRecipe(new ItemStack(ItemsAether.swet_jelly, 1, 2),
				new ItemStack(ItemsAether.swet_gel, 1, 2), "sugar");

		// Cocoatrice
		registerShapedRecipe(new ItemStack(ItemsAether.cocoatrice), "XY", "Z ",
				'X', "sugar",
				'Y', new ItemStack(Items.DYE, 1, 3),
				'Z', new ItemStack(ItemsAether.skyroot_milk_bucket));

		// Wrapped Chocolate
		registerShapedRecipe(new ItemStack(ItemsAether.wrapped_chocolates), "WX", "YZ",
				'W', "sugar",
				'X', new ItemStack(Items.DYE, 1, 3),
				'Y', new ItemStack(ItemsAether.skyroot_milk_bucket),
				'Z', new ItemStack(ItemsAether.aechor_petal));

		// Jelly Pumpkin
		registerShapedRecipe(new ItemStack(ItemsAether.jelly_pumpkin), "XY", "Z ",
				'X', ItemsAether.swet_jelly,
				'Y', new ItemStack(ItemsAether.orange),
				'Z', "sugar");

		// Stomper Pop
		registerShapedRecipe(new ItemStack(ItemsAether.stomper_pop), " X", " Z",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Z', new ItemStack(ItemsAether.pink_baby_swet));

		registerShapedRecipe(new ItemStack(ItemsAether.stomper_pop), " Z", " X",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Z', new ItemStack(ItemsAether.pink_baby_swet));

		// Blueberry Lollipop
		registerShapedRecipe(new ItemStack(ItemsAether.blueberry_lollipop), "XY", "Z ",
				'X', "sugar",
				'Y', new ItemStack(ItemsAether.blueberries),
				'Z', new ItemStack(ItemsAether.skyroot_stick));

		// Orange Lollipop
		registerShapedRecipe(new ItemStack(ItemsAether.orange_lollipop), "XY", "Z ",
				'X', "sugar",
				'Y', new ItemStack(ItemsAether.orange),
				'Z', new ItemStack(ItemsAether.skyroot_stick));

		// Icestone Poprocks
		registerShapelessRecipe(new ItemStack(ItemsAether.icestone_poprocks, 1),
				"sugar", new ItemStack(ItemsAether.icestone));
	}

	private static void registerAltarRecipes()
	{
		// Enchanted Dart Shooter
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.ENCHANTED.ordinal())));

		// Enchanted Darts
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(1, new ItemStack(ItemsAether.dart, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart, 1, ItemDartType.ENCHANTED.ordinal())));

		// Enchanted Strawberry
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(2, new ItemStack(ItemsAether.blueberries),
				new ItemStack(ItemsAether.enchanted_blueberry)));

		// Rainbow Strawberry
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.wyndberry),
				new ItemStack(ItemsAether.enchanted_wyndberry)));

		// Tool Repair Recipes
		AetherAPI.content().altar().registerAltarRecipe(new AltarRepairRecipe());

		// Healing Stone
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(5, new ItemStack(ItemsAether.healing_stone_depleted),
				new ItemStack(ItemsAether.healing_stone)));
	}

	private static void registerShapelessRecipe(final ItemStack output, final Object... stacks)
	{
		registerRecipe(new ShapelessOreRecipe(output, stacks), true);
	}

	private static void registerShapedRecipe(final ItemStack output, final Object... params)
	{
		registerRecipe(new ShapedOreRecipe(output, params), true);
	}

	/**
	 * Registers a recipe.
	 *
	 * @param recipe The recipe to register
	 * @param index Whether or not this recipe can be indexed
	 */
	private static void registerRecipe(final IRecipe recipe, final boolean index)
	{
		if (index)
		{
			CRAFTABLE_RECIPES.add(recipe);
		}

		GameRegistry.addRecipe(recipe);
	}

	private static void registerSmeltingRecipe(final ItemStack input, final ItemStack output, final float xp)
	{
		GameRegistry.addSmelting(input, output, xp);
	}

	public static Collection<IRecipe> getCraftableRecipes()
	{
		return Collections.unmodifiableCollection(CRAFTABLE_RECIPES);
	}
}
