package com.gildedgames.aether.common.recipes;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import com.gildedgames.aether.common.recipes.altar.AltarEnchantRecipe;
import com.gildedgames.aether.common.recipes.altar.AltarRepairRecipe;
import com.gildedgames.aether.api.registry.altar.IAltarRecipe;
import com.gildedgames.aether.common.recipes.dye.RecipeLeatherGlovesDyes;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesAether implements IAltarRecipeRegistry
{
	private final AltarRegistry altarRegistry = new AltarRegistry();

	public void preInit()
	{
		CraftingManager.getInstance().addRecipe(new RecipeLeatherGlovesDyes());

		registerCraftingRecipes();
		registerToolRecipes();
		registerArmorRecipes();
        registerAccessoryRecipes();
        registerConsumableRecipes();
		registerAltarRecipes();

		GameRegistry.registerFuelHandler(new AetherFuelHandler());
	}

	private void registerCraftingRecipes()
	{
		// Skyroot Planks
		registerShapelessRecipe(new ItemStack(BlocksAether.skyroot_planks, 4),
				new ItemStack(BlocksAether.skyroot_log));

		// Skyroot Sticks
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_stick, 4), "X", "X",
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Skyroot Crafting Table
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_crafting_table), "XX", "XX",
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Skyroot Chest
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_chest), "XXX", "X X", "XXX",
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Altar
        registerShapedRecipe(new ItemStack(BlocksAether.altar), "XXX", "XYX", "XXX",
				'X', new ItemStack(BlocksAether.holystone), 'Y', new ItemStack(ItemsAether.zanite_gemstone));

		// Skyroot Door
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_door, 3), "XX", "XX", "XX",
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Skyroot Trap Door
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_trapdoor, 2), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Skyroot Ladder
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_ladder, 3), "X X", "XXX", "X X",
				'X', new ItemStack(ItemsAether.skyroot_stick));

		// Skyroot Pressure Plate
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_pressure_plate), "XX ",
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Skyroot Button
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_button), "X",
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Holystone Pressure Plate
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_pressure_plate), "XX ",
				'X', new ItemStack(BlocksAether.holystone));

		// Holystone Button
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_button), "X",
				'X', new ItemStack(BlocksAether.holystone));

		// Golden Dart Shooter
		registerShapedRecipe(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), "X", "X", "Y",
				'X', new ItemStack(BlocksAether.skyroot_planks), 'Y', new ItemStack(ItemsAether.golden_amber));

		// Poison Dart Shooter
		registerShapelessRecipe(new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.POISON.ordinal()),
				new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()), new ItemStack(ItemsAether.skyroot_poison_bucket));

        // Golden Dart
        registerShapedRecipe(new ItemStack(ItemsAether.dart), " X", " Y", " Z",
                'X', new ItemStack(ItemsAether.golden_amber),
                'Y', new ItemStack(ItemsAether.skyroot_stick),
                'Z', new ItemStack(Items.feather));

        // Poison Dart
        registerShapedRecipe(new ItemStack(ItemsAether.dart, 2), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.dart),
                'Y', new ItemStack(ItemsAether.skyroot_poison_bucket));

        // Skyroot Bucket
        registerShapedRecipe(new ItemStack(ItemsAether.skyroot_bucket), "X X", " X ",
                'X', new ItemStack(BlocksAether.skyroot_planks));

		// Zanite Block
		registerShapedRecipe(new ItemStack(BlocksAether.zanite_block), "XXX", "XXX", "XXX",
				'X', new ItemStack(ItemsAether.zanite_gemstone));

        // Zanite Uncraft
        registerShapedRecipe(new ItemStack(BlocksAether.zanite_block), "X",
                'X', new ItemStack(ItemsAether.zanite_gemstone, 9));

		// Holystone Brick
		registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick, 4), "XX", "XX",
				'X', new ItemStack(BlocksAether.holystone));

		// Icestone Bricks
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_bricks, 4), "XX", "XX",
				'X', new ItemStack(ItemsAether.icestone));

		// Fences and Walls
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_fence, 3), "XYX", "XYX",
				'X', new ItemStack(BlocksAether.skyroot_planks),
				'Y', new ItemStack(ItemsAether.skyroot_stick));

		registerShapedRecipe(new ItemStack(BlocksAether.holystone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.holystone));
        registerShapedRecipe(new ItemStack(BlocksAether.holystone_wall_mossy, 6), "XXX", "XXX",
                'X', new ItemStack(BlocksAether.holystone));
        registerShapedRecipe(new ItemStack(BlocksAether.holystone_brick_wall, 6), "XXX", "XXX",
                'X', new ItemStack(BlocksAether.holystone));
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_log_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.skyroot_log));
		registerShapedRecipe(new ItemStack(BlocksAether.icestone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.icestone_bricks));
		registerShapedRecipe(new ItemStack(BlocksAether.carved_stone_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.carved_stone));
		registerShapedRecipe(new ItemStack(BlocksAether.aerogel_wall, 6), "XXX", "XXX",
				'X', new ItemStack(BlocksAether.aerogel));

		// Fences
        registerShapedRecipe(new ItemStack(BlocksAether.skyroot_fence, 3), "XYX", "XYX",
                'X', new ItemStack(ItemsAether.skyroot_stick),
                'Y', new ItemStack(BlocksAether.skyroot_planks));

        // Gates
		registerShapedRecipe(new ItemStack(BlocksAether.skyroot_fence_gate, 1), "YXY", "YXY",
				'Y', new ItemStack(ItemsAether.skyroot_stick),
				'X', new ItemStack(BlocksAether.skyroot_planks));

		// Sign
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_sign, 3), "XXX", "XXX", " Y ",
				'X', new ItemStack(BlocksAether.skyroot_planks),
				'Y', new ItemStack(ItemsAether.skyroot_stick));

        // Ambrosium Torch
        registerShapedRecipe(new ItemStack(BlocksAether.ambrosium_torch, 8), " Y", " X",
                'X', new ItemStack(ItemsAether.skyroot_stick),
                'Y', new ItemStack(ItemsAether.ambrosium_shard));

        // Holystone Furnace
        registerShapedRecipe(new ItemStack(BlocksAether.holystone_furnace, 1), "XXX", "X X", "XXX",
                'X', new ItemStack(BlocksAether.holystone));

		// Crossbow
		registerShapedRecipe(new ItemStack(ItemsAether.crossbow), " X ", " Y ", "ZZZ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(Items.string),
				'Z', new ItemStack(BlocksAether.skyroot_planks));

		// Crossbow Bolts
		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 4, ItemBoltType.STONE.ordinal()), " Y", "X ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(BlocksAether.holystone));
		registerShapedRecipe(new ItemStack(ItemsAether.bolt, 4, ItemBoltType.ZANITE.ordinal()), " Y", "X ",
				'X', new ItemStack(ItemsAether.skyroot_stick),
				'Y', new ItemStack(ItemsAether.zanite_gemstone));
	}

	private void registerToolRecipes()
	{
		ItemStack skyrootStick = new ItemStack(ItemsAether.skyroot_stick);

		ItemStack skyroot = new ItemStack(BlocksAether.skyroot_planks);
		ItemStack holystone = new ItemStack(BlocksAether.holystone);
		ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);
		ItemStack gravitite = new ItemStack(BlocksAether.enchanted_gravitite);

		// Skyroot Tools
		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_axe), "XX ", "XY ", " Y ",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_pickaxe), "XXX", " Y ", " Y ",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_shovel), "X", "Y", "Y",
				'X', skyroot, 'Y', skyrootStick);

		registerShapedRecipe(new ItemStack(ItemsAether.skyroot_sword), "X", "X", "Y",
				'X', skyroot, 'Y', skyrootStick);

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
	}

	private void registerArmorRecipes()
	{
		ItemStack zanite = new ItemStack(ItemsAether.zanite_gemstone);
		ItemStack gravitite = new ItemStack(BlocksAether.enchanted_gravitite);

		// Zanite Armor
		registerShapedRecipe(new ItemStack(ItemsAether.zanite_helmet), "XXX", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_chestplate), "X X", "XXX", "XXX",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_leggings), "XXX", "X X", "X X",
				'X', zanite);

		registerShapedRecipe(new ItemStack(ItemsAether.zanite_boots), "X X", "X X",
				'X', zanite);

		// Gravitite Armor
		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_helmet), "XXX", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_chestplate), "X X", "XXX", "XXX",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_leggings), "XXX", "X X", "X X",
				'X', gravitite);

		registerShapedRecipe(new ItemStack(ItemsAether.gravitite_boots), "X X", "X X",
				'X', gravitite);
	}

    private void registerAccessoryRecipes() {
        // Iron Ring
        registerShapedRecipe(new ItemStack(ItemsAether.iron_ring), " X ", "X X", " X ",
                'X', new ItemStack(Items.iron_ingot));
        // Iron Pendant
        registerShapedRecipe(new ItemStack(ItemsAether.iron_pendant), "XXX", "X X", " Y ",
                'X', new ItemStack(Items.string),
                'Y', new ItemStack(Items.iron_ingot));
        // Gold Ring
        registerShapedRecipe(new ItemStack(ItemsAether.gold_ring), " X ", "X X", " X ",
                'X', new ItemStack(Items.gold_ingot));
        // Gold Pendant
        registerShapedRecipe(new ItemStack(ItemsAether.gold_pendant), "XXX", "X X", " Y ",
                'X', new ItemStack(Items.string),
                'Y', new ItemStack(Items.gold_ingot));
        // Zanite Ring
        registerShapedRecipe(new ItemStack(ItemsAether.zanite_ring), " X ", "X X", " X ",
                'X', new ItemStack(ItemsAether.zanite_gemstone));
        // Zanite Pendant
        registerShapedRecipe(new ItemStack(ItemsAether.zanite_pendant), "XXX", "X X", " Y ",
                'X', new ItemStack(Items.string),
                'Y', new ItemStack(ItemsAether.zanite_gemstone));
    }

    private void registerConsumableRecipes()
    {
        // Blue Gummy Swet
        registerShapedRecipe(new ItemStack(ItemsAether.gummy_swet), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.swet_jelly),
                'Y', new ItemStack(Items.sugar));

        // Gold Gummy Swet
        registerShapedRecipe(new ItemStack(ItemsAether.gummy_swet, 1, 1), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.swet_jelly, 1, 1),
                'Y', new ItemStack(Items.sugar));

        // Dark Gummy Swet
        registerShapedRecipe(new ItemStack(ItemsAether.gummy_swet, 1, 2), "XXX", "XYX", "XXX",
                'X', new ItemStack(ItemsAether.swet_jelly, 1, 2),
                'Y', new ItemStack(Items.sugar));

        // Cocoatrice
        registerShapedRecipe(new ItemStack(ItemsAether.cocoatrice), "XY", "Z ",
                'X', new ItemStack(Items.sugar),
                'Y', new ItemStack(Items.dye, 1, 3),
                'Z', new ItemStack(ItemsAether.skyroot_milk_bucket));

        // Wrapped Chocolate
        registerShapedRecipe(new ItemStack(ItemsAether.wrapped_chocolates), "WX", "YZ",
                'W', new ItemStack(Items.sugar),
                'X', new ItemStack(Items.dye, 1, 3),
                'Y', new ItemStack(ItemsAether.skyroot_milk_bucket),
                'Z', new ItemStack(ItemsAether.aechor_petal));

//        // Jelly Pumpkin
//        registerShapedRecipe(new ItemStack(ItemsAether.cocoatrice), "XY", "Z ",
//                'X', ItemsAether.swet_jelly,
//                'Y', new ItemStack(ItemsAether.orange),
//                'Z', new ItemStack(Items.sugar));
//
//        // Stomper Pop
//        registerShapedRecipe(new ItemStack(ItemsAether.stomper_pop), " X", " Z",
//                'X', new ItemStack(ItemsAether.skyroot_stick),
//                'Y', new ItemStack(ItemsAether.baby_pink_swet),

        // Blueberry Lollipop
        registerShapedRecipe(new ItemStack(ItemsAether.blueberry_lollipop), "XY", "Z ",
                'X', new ItemStack(Items.sugar),
                'Y', new ItemStack(ItemsAether.blueberry),
                'Z', new ItemStack(ItemsAether.skyroot_stick));

        // Orange Lollipop
        registerShapedRecipe(new ItemStack(ItemsAether.orange), "XY", "Z ",
                'X', new ItemStack(Items.sugar),
                'Y', new ItemStack(ItemsAether.blueberry),
                'Z', new ItemStack(ItemsAether.skyroot_stick));

//        // Icestone Poprocks
//        registerShapelessRecipe(new ItemStack(ItemsAether.icestone_poprocks), "X", "Y",
//                'X', new ItemStack(Items.sugar),
//                'Y', new ItemStack(ItemsAether.icestone));
    }

	private void registerAltarRecipes()
	{
        // Quicksoil Glass
        registerAltarRecipe(new AltarEnchantRecipe(1, new ItemStack(BlocksAether.quicksoil),
                new ItemStack(BlocksAether.quicksoil_glass)));

        // Healing Stone
		registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(BlocksAether.holystone),
				new ItemStack(ItemsAether.healing_stone)));

		// Enchanted Gravitite
        registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(BlocksAether.gravitite_ore),
                new ItemStack(BlocksAether.enchanted_gravitite)));

		// Enchanted Dart Shooter
		registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.ENCHANTED.ordinal())));

		// Enchanted Darts
		registerAltarRecipe(new AltarEnchantRecipe(1, new ItemStack(ItemsAether.dart, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart, 1, ItemDartType.ENCHANTED.ordinal())));

        // Enchanted Strawberry
        registerAltarRecipe(new AltarEnchantRecipe(2, new ItemStack(ItemsAether.blueberry),
                new ItemStack(ItemsAether.enchanted_blueberry)));

        // Rainbow Strawberry
        registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.wyndberry),
                new ItemStack(ItemsAether.rainbow_strawberry)));

		// Tool Repair Recipes
		registerAltarRecipe(new AltarRepairRecipe());
	}

	private static void registerShapelessRecipe(ItemStack output, Object... stacks)
	{
		GameRegistry.addShapelessRecipe(output, stacks);
	}

	private static void registerShapedRecipe(ItemStack output, Object... params)
	{
		GameRegistry.addShapedRecipe(output, params);
	}

	@Override
	public void registerAltarEnchantment(ItemStack input, ItemStack output, int cost)
	{
		this.altarRegistry.addRecipe(new AltarEnchantRecipe(cost, input, output));
	}

	@Override
	public void registerAltarRecipe(IAltarRecipe recipe)
	{
		this.altarRegistry.addRecipe(recipe);
	}

	public AltarRegistry getAltarRegistry()
	{
		return this.altarRegistry;
	}
}
