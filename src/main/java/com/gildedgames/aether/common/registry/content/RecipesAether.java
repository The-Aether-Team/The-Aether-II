package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.decorative.BlockFadedHolystoneDecorative;
import com.gildedgames.aether.common.blocks.decorative.BlockHolystoneDecorative;
import com.gildedgames.aether.common.blocks.decorative.BlockIcestoneBricksDecorative;
import com.gildedgames.aether.common.blocks.decorative.BlockSkyrootDecorative;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.recipes.RecipePresentCrafting;
import com.gildedgames.aether.common.recipes.RecipeWrappingPaper;
import com.gildedgames.aether.common.recipes.altar.AltarEnchantRecipe;
import com.gildedgames.aether.common.recipes.altar.AltarRepairRecipe;
import com.gildedgames.aether.common.registry.minecraft.AetherFuelHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class RecipesAether
{
	public static void preInit()
	{
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

	public static void registerOreDictionary()
	{
		OreDictionary.registerOre("skyrootplanks", BlocksAether.skyroot_planks);
		OreDictionary.registerOre("skyrootplanks", BlocksAether.dark_skyroot_planks);
		OreDictionary.registerOre("skyrootplanks", BlocksAether.light_skyroot_planks);
		OreDictionary.registerOre("feather", ItemsAether.moa_feather);
		OreDictionary.registerOre("feather", ItemsAether.cockatrice_feather);
		OreDictionary.registerOre("aerleather", ItemsAether.taegore_hide);
		OreDictionary.registerOre("aerleather", ItemsAether.burrukai_pelt);
		OreDictionary.registerOre("sugar", ItemsAether.swet_sugar);

		OreDictionary.registerOre("holystone", new ItemStack(BlocksAether.holystone, 1, BlockHolystone.NORMAL_HOLYSTONE.getMeta()));
		OreDictionary.registerOre("holystone", new ItemStack(BlocksAether.holystone, 1, BlockHolystone.MOSSY_HOLYSTONE.getMeta()));

		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta()));
		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.BASE_BEAM.getMeta()));
		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.BASE_PLANKS.getMeta()));
		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.FLOORBOARDS.getMeta()));
		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TILES.getMeta()));
		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TILES_SMALL.getMeta()));
		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TOP_BEAM.getMeta()));
		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TOP_PLANKS.getMeta()));

		OreDictionary.registerOre("icestone_bricks_decorative",
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockIcestoneBricksDecorative.BASE_BRICKS.getMeta()));
		OreDictionary.registerOre("icestone_bricks_decorative",
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockIcestoneBricksDecorative.BASE_PILLAR.getMeta()));
		OreDictionary.registerOre("icestone_bricks_decorative",
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockIcestoneBricksDecorative.CAPSTONE_BRICKS.getMeta()));
		OreDictionary.registerOre("icestone_bricks_decorative",
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockIcestoneBricksDecorative.CAPSTONE_PILLAR.getMeta()));
		OreDictionary.registerOre("icestone_bricks_decorative",
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockIcestoneBricksDecorative.FLAGSTONES.getMeta()));
		OreDictionary.registerOre("icestone_bricks_decorative",
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockIcestoneBricksDecorative.KEYSTONE.getMeta()));

		OreDictionary.registerOre("holystone_brick_decorative",
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.BASE_BRICKS.getMeta()));
		OreDictionary.registerOre("holystone_brick_decorative",
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.BASE_PILLAR.getMeta()));
		OreDictionary.registerOre("holystone_brick_decorative",
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.CAPSTONE_BRICKS.getMeta()));
		OreDictionary.registerOre("holystone_brick_decorative",
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.CAPSTONE_PILLAR.getMeta()));
		OreDictionary.registerOre("holystone_brick_decorative",
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.FLAGSTONES.getMeta()));
		OreDictionary.registerOre("holystone_brick_decorative",
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.HEADSTONE.getMeta()));
		OreDictionary.registerOre("holystone_brick_decorative",
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.KEYSTONE.getMeta()));

		OreDictionary.registerOre("faded_holystone_brick_decorative",
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.BASE_BRICKS.getMeta()));
		OreDictionary.registerOre("faded_holystone_brick_decorative",
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.BASE_PILLAR.getMeta()));
		OreDictionary.registerOre("faded_holystone_brick_decorative",
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.CAPSTONE_BRICKS.getMeta()));
		OreDictionary.registerOre("faded_holystone_brick_decorative",
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.CAPSTONE_PILLAR.getMeta()));
		OreDictionary.registerOre("faded_holystone_brick_decorative",
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.FLAGSTONES.getMeta()));
		OreDictionary.registerOre("faded_holystone_brick_decorative",
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.HEADSTONE.getMeta()));
		OreDictionary.registerOre("faded_holystone_brick_decorative",
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.KEYSTONE.getMeta()));
	}

	public static void init()
	{
		registerOreDictionary();
		registerFurnaceRecipes();
		registerAltarRecipes();
		GameRegistry.registerFuelHandler(new AetherFuelHandler());
	}

	@SubscribeEvent
	public static void registerCraftingRecipes(RegistryEvent.Register<IRecipe> event)
	{
		IForgeRegistry<IRecipe> r = event.getRegistry();
		r.register(new RecipeWrappingPaper().setRegistryName(AetherCore.getResource("wrapping_paper")));
		r.register(new RecipePresentCrafting().setRegistryName(AetherCore.getResource("present_crafting")));

		//TODO:
		//RecipeSorter.register("aether:wrappingPaper", RecipeWrappingPaper.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");
		//RecipeSorter.register("aether:presentCrafting", RecipePresentCrafting.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped");

		//CraftingManager.getInstance().addRecipe(new RecipeWrappingPaper());
		//CraftingManager.getInstance().addRecipe(new RecipePresentCrafting());
	}

	private static void registerAltarRecipes()
	{
		// Enchanted Dart Shooter
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart_shooter, 1, ItemDartType.ENCHANTED.ordinal())));

		// Enchanted Darts
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(1, new ItemStack(ItemsAether.dart, 1, ItemDartType.GOLDEN.ordinal()),
				new ItemStack(ItemsAether.dart, 1, ItemDartType.ENCHANTED.ordinal())));

		// Enchanted Blueberry
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(2, new ItemStack(ItemsAether.blueberries),
				new ItemStack(ItemsAether.enchanted_blueberry)));

		// Enchanted Wyndberry
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(4, new ItemStack(ItemsAether.wyndberry),
				new ItemStack(ItemsAether.enchanted_wyndberry)));

		// Tool Repair Recipes
		AetherAPI.content().altar().registerAltarRecipe(new AltarRepairRecipe());

		// Healing Stone
		AetherAPI.content().altar().registerAltarRecipe(new AltarEnchantRecipe(5, new ItemStack(ItemsAether.healing_stone_depleted),
				new ItemStack(ItemsAether.healing_stone)));
	}

	private static void registerSmeltingRecipe(final ItemStack input, final ItemStack output, final float xp)
	{
		GameRegistry.addSmelting(input, output, xp);
	}
}
