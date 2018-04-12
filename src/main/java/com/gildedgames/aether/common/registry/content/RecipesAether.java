package com.gildedgames.aether.common.registry.content;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber
public class RecipesAether
{
	private static final Set<IRecipe> CRAFTABLE_RECIPES = new HashSet<>();

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

		OreDictionary.registerOre("holystone", BlocksAether.holystone);
		OreDictionary.registerOre("skyroot_decorative", BlocksAether.skyroot_decorative);
		OreDictionary.registerOre("icestone_bricks_decorative", BlocksAether.icestone_bricks_decorative);
		OreDictionary.registerOre("holystone_brick_decorative", BlocksAether.holystone_brick_decorative);
		OreDictionary.registerOre("faded_holystone_brick_decorative", BlocksAether.faded_holystone_brick_decorative);
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

	private static void registerSmeltingRecipe(final ItemStack input, final ItemStack output, final float xp)
	{
		GameRegistry.addSmelting(input, output, xp);
	}

	public static Collection<IRecipe> getCraftableRecipes()
	{
		return Collections.unmodifiableCollection(CRAFTABLE_RECIPES);
	}
}
