package com.gildedgames.aether.common.init;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.decorative.*;
import com.gildedgames.aether.common.blocks.natural.BlockHolystone;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.items.weapons.ItemDartType;
import com.gildedgames.aether.common.recipes.CoolerRecipes;
import com.gildedgames.aether.common.recipes.MasonryRecipes;
import com.gildedgames.aether.common.recipes.RecipePresentCrafting;
import com.gildedgames.aether.common.recipes.RecipeWrappingPaper;
import com.gildedgames.aether.common.recipes.altar.AltarEnchantRecipe;
import com.gildedgames.aether.common.recipes.altar.AltarRepairRecipe;
import com.gildedgames.aether.common.recipes.simple.SimpleRecipe;
import com.gildedgames.aether.common.util.helpers.MapGen;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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
	public static void init()
	{
		registerOreDictionary();
		registerFurnaceRecipes();
		registerAltarRecipes();
		registerMasonryRecipes();
		registerCoolerRecipes();
	}

	private static void registerFurnaceRecipes()
	{
		registerSmeltingRecipe(new ItemStack(BlocksAether.holystone), new ItemStack(BlocksAether.agiosite), 0.1f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.arkenium_ore), new ItemStack(ItemsAether.arkenium), 0.85f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.gravitite_ore), new ItemStack(ItemsAether.gravitite_plate), 1.0f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.quicksoil), new ItemStack(BlocksAether.quicksoil_glass), 0.1f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.moa_egg_item), new ItemStack(ItemsAether.fried_moa_egg), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.rainbow_moa_egg), new ItemStack(ItemsAether.fried_moa_egg), 0.4f);
		registerSmeltingRecipe(new ItemStack(BlocksAether.crude_scatterglass), new ItemStack(BlocksAether.scatterglass), 0.1f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.raw_taegore_meat), new ItemStack(ItemsAether.taegore_steak), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.burrukai_rib_cut), new ItemStack(ItemsAether.burrukai_ribs), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.kirrid_loin), new ItemStack(ItemsAether.kirrid_cutlet), 0.4f);
		registerSmeltingRecipe(new ItemStack(ItemsAether.skyroot_lizard_stick), new ItemStack(ItemsAether.skyroot_lizard_stick_roasted), 0.65f);
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

		OreDictionary.registerOre("holystone", new ItemStack(BlocksAether.holystone, 1, 0));
		OreDictionary.registerOre("holystone", new ItemStack(BlocksAether.holystone, 1, 1));

		for (BlockVariant variant : BlockSkyrootDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("skyroot_decorative", new ItemStack(BlocksAether.skyroot_beam, 1));

		for (BlockVariant variant : BlockSkyrootDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("wisproot_decorative", new ItemStack(BlocksAether.light_skyroot_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("wisproot_decorative", new ItemStack(BlocksAether.light_skyroot_beam, 1));

		for (BlockVariant variant : BlockSkyrootDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("greatroot_decorative", new ItemStack(BlocksAether.dark_skyroot_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("greatroot_decorative", new ItemStack(BlocksAether.dark_skyroot_beam, 1));

		for (BlockVariant variant : BlockTherawoodDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("therawood_decorative", new ItemStack(BlocksAether.therawood_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("therawood_decorative", new ItemStack(BlocksAether.therawood_beam, 1));

		for (BlockVariant variant : BlockIcestoneBricksDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("icestone_bricks_decorative", new ItemStack(BlocksAether.icestone_bricks_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("icestone_bricks_decorative", new ItemStack(BlocksAether.icestone_pillar, 1));

		for (BlockVariant variant : BlockHolystoneDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("holystone_brick_decorative", new ItemStack(BlocksAether.holystone_brick_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("holystone_brick_decorative", new ItemStack(BlocksAether.holystone_pillar, 1));

		for (BlockVariant variant : BlockFadedHolystoneDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("faded_holystone_brick_decorative", new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("faded_holystone_brick_decorative", new ItemStack(BlocksAether.faded_holystone_pillar, 1));

		OreDictionary.registerOre("faded_holystone_brick", new ItemStack(BlocksAether.faded_holystone_brick, 1));

		for (BlockVariant variant : BlockAgiositeDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("agiosite_brick_decorative", new ItemStack(BlocksAether.agiosite_brick_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("agiosite_brick_decorative", new ItemStack(BlocksAether.agiosite_pillar, 1));

		for (BlockVariant variant : BlockSentrystoneDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("sentrystone_brick_decorative", new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("sentrystone_brick_decorative", new ItemStack(BlocksAether.sentrystone_pillar, 1));

		for (BlockVariant variant : BlockSentrystoneDecorativeLit.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("sentrystone_brick_decorative", new ItemStack(BlocksAether.sentrystone_brick_decorative_lit, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("sentrystone_brick_decorative", new ItemStack(BlocksAether.sentrystone_pillar_lit, 1));

		for (BlockVariant variant : BlockHellfirestoneDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("hellfirestone_brick_decorative", new ItemStack(BlocksAether.hellfirestone_brick_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("hellfirestone_brick_decorative", new ItemStack(BlocksAether.hellfirestone_pillar, 1));

		OreDictionary.registerOre("hellfirestone_brick_decorative", new ItemStack(BlocksAether.hellfirestone_lantern, 1));

		for (BlockVariant variant : BlockTherastoneDecorative.PROPERTY_VARIANT.getAllowedValues())
		{
			OreDictionary.registerOre("therastone_brick_decorative", new ItemStack(BlocksAether.therastone_brick_decorative, 1, variant.getMeta()));
		}

		OreDictionary.registerOre("therastone_brick_decorative", new ItemStack(BlocksAether.therastone_pillar, 1));

		OreDictionary.registerOre("canHarvestAetherAmber", ItemsAether.zanite_axe);
		OreDictionary.registerOre("canHarvestAetherAmber", ItemsAether.arkenium_axe);
		OreDictionary.registerOre("canHarvestAetherAmber", ItemsAether.gravitite_axe);
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

	private static void registerMasonryRecipes()
	{
		MasonryRecipes.instance().addMasonry(BlocksAether.holystone_brick,
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.HEADSTONE.getMeta()),
				new ItemStack(BlocksAether.holystone_brick_decorative, 1, BlockHolystoneDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.holystone_pillar, 1),
				new ItemStack(BlocksAether.faded_holystone_brick, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.sentrystone_brick,
				new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, BlockSentrystoneDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, BlockSentrystoneDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, BlockSentrystoneDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, BlockSentrystoneDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, BlockSentrystoneDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, BlockSentrystoneDecorative.LIGHTSTONE.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative, 1, BlockSentrystoneDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.sentrystone_pillar, 1),
				new ItemStack(BlocksAether.sentrystone_brick_decorative_lit, 1, BlockSentrystoneDecorativeLit.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative_lit, 1, BlockSentrystoneDecorativeLit.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative_lit, 1, BlockSentrystoneDecorativeLit.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative_lit, 1, BlockSentrystoneDecorativeLit.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative_lit, 1, BlockSentrystoneDecorativeLit.LIGHTSTONE.getMeta()),
				new ItemStack(BlocksAether.sentrystone_brick_decorative_lit, 1, BlockSentrystoneDecorativeLit.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.sentrystone_pillar_lit, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.hellfirestone_brick,
				new ItemStack(BlocksAether.hellfirestone_brick_decorative, 1, BlockHellfirestoneDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.hellfirestone_brick_decorative, 1, BlockHellfirestoneDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.hellfirestone_brick_decorative, 1, BlockHellfirestoneDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.hellfirestone_brick_decorative, 1, BlockHellfirestoneDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.hellfirestone_brick_decorative, 1, BlockHellfirestoneDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.hellfirestone_lantern, 1, 0),
				new ItemStack(BlocksAether.hellfirestone_brick_decorative, 1, BlockHellfirestoneDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.hellfirestone_pillar, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.faded_holystone_brick,
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.HEADSTONE.getMeta()),
				new ItemStack(BlocksAether.faded_holystone_brick_decorative, 1, BlockFadedHolystoneDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.faded_holystone_pillar, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.agiosite_brick,
				new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.agiosite_brick_decorative, 1, BlockAgiositeDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.agiosite_pillar, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.icestone_bricks,
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockAgiositeDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockAgiositeDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockAgiositeDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockAgiositeDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockAgiositeDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.icestone_bricks_decorative, 1, BlockAgiositeDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.icestone_pillar, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.skyroot_planks,
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.BASE_PLANKS.getMeta()),
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.BASE_BEAM.getMeta()),
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TOP_PLANKS.getMeta()),
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TOP_BEAM.getMeta()),
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.FLOORBOARDS.getMeta()),
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta()),
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TILES.getMeta()),
				new ItemStack(BlocksAether.skyroot_decorative, 1, BlockSkyrootDecorative.TILES_SMALL.getMeta()),
				new ItemStack(BlocksAether.skyroot_beam, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.light_skyroot_planks,
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.BASE_PLANKS.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.BASE_BEAM.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.TOP_PLANKS.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.TOP_BEAM.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.FLOORBOARDS.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.TILES.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_decorative, 1, BlockSkyrootDecorative.TILES_SMALL.getMeta()),
				new ItemStack(BlocksAether.light_skyroot_beam, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.dark_skyroot_planks,
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.BASE_PLANKS.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.BASE_BEAM.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.TOP_PLANKS.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.TOP_BEAM.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.FLOORBOARDS.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.HIGHLIGHT.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.TILES.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_decorative, 1, BlockSkyrootDecorative.TILES_SMALL.getMeta()),
				new ItemStack(BlocksAether.dark_skyroot_beam, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.therawood_planks,
				new ItemStack(BlocksAether.therawood_decorative, 1, BlockTherawoodDecorative.BASE_PLANKS.getMeta()),
				new ItemStack(BlocksAether.therawood_decorative, 1, BlockTherawoodDecorative.BASE_BEAM.getMeta()),
				new ItemStack(BlocksAether.therawood_decorative, 1, BlockTherawoodDecorative.TOP_PLANKS.getMeta()),
				new ItemStack(BlocksAether.therawood_decorative, 1, BlockTherawoodDecorative.TOP_BEAM.getMeta()),
				new ItemStack(BlocksAether.therawood_decorative, 1, BlockTherawoodDecorative.FLOORBOARDS.getMeta()),
				new ItemStack(BlocksAether.therawood_decorative, 1, BlockTherawoodDecorative.HIGHLIGHT.getMeta()),
				new ItemStack(BlocksAether.therawood_beam, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.quicksoil_glass,
				new ItemStack(BlocksAether.quicksoil_glass_decorative, 1, BlockQuicksoilGlassDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.quicksoil_glass_decorative, 1, BlockQuicksoilGlassDecorative.ARKENIUM_FRAME.getMeta())
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.scatterglass,
				new ItemStack(BlocksAether.scatterglass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.scatterglass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta())
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.crude_scatterglass,
				new ItemStack(BlocksAether.crude_scatterglass_decorative, 1, BlockRockGlassDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.crude_scatterglass_decorative, 1, BlockRockGlassDecorative.ARKENIUM_FRAME.getMeta())
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.quicksoil_glass_pane,
				new ItemStack(BlocksAether.quicksoil_glass_pane_decorative, 1, BlockQuicksoilGlassPaneDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.quicksoil_glass_pane_decorative, 1, BlockQuicksoilGlassPaneDecorative.ARKENIUM_FRAME.getMeta())
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.scatterglass_pane,
				new ItemStack(BlocksAether.scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.ARKENIUM_FRAME.getMeta())
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.crude_scatterglass_pane,
				new ItemStack(BlocksAether.crude_scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.SKYROOT_FRAME.getMeta()),
				new ItemStack(BlocksAether.crude_scatterglass_pane_decorative, 1, BlockRockGlassPaneDecorative.ARKENIUM_FRAME.getMeta())
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.therastone_brick,
				new ItemStack(BlocksAether.therastone_brick_decorative, 1, BlockTherastoneDecorative.BASE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.therastone_brick_decorative, 1, BlockTherastoneDecorative.BASE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.therastone_brick_decorative, 1, BlockTherastoneDecorative.CAPSTONE_BRICKS.getMeta()),
				new ItemStack(BlocksAether.therastone_brick_decorative, 1, BlockTherastoneDecorative.CAPSTONE_PILLAR.getMeta()),
				new ItemStack(BlocksAether.therastone_brick_decorative, 1, BlockTherastoneDecorative.FLAGSTONES.getMeta()),
				new ItemStack(BlocksAether.therastone_brick_decorative, 1, BlockTherastoneDecorative.KEYSTONE.getMeta()),
				new ItemStack(BlocksAether.therastone_pillar, 1)
		);

		MasonryRecipes.instance().addMasonryItem(ItemsAether.skyroot_door_item,
				new ItemStack(ItemsAether.secret_skyroot_door_item, 1)
		);

		MasonryRecipes.instance().addMasonry(BlocksAether.skyroot_trapdoor,
				new ItemStack(BlocksAether.secret_skyroot_trapdoor, 1)
		);
	}

	@SuppressWarnings("unchecked")
	private static void registerCoolerRecipes()
	{
		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.irradiated_armor, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				MapGen.genMap(new ItemStack(ItemsAether.irradiated_dust), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.irradiated_charm, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				MapGen.genMap(new ItemStack(ItemsAether.irradiated_dust), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.irradiated_neckwear, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				MapGen.genMap(new ItemStack(ItemsAether.irradiated_dust), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.irradiated_ring, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				MapGen.genMap(new ItemStack(ItemsAether.irradiated_dust), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.irradiated_sword, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				MapGen.genMap(new ItemStack(ItemsAether.irradiated_dust), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.irradiated_tool, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.irradiated_dust),
				MapGen.genMap(new ItemStack(ItemsAether.irradiated_dust), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.skyroot_bucket, Item.getItemFromBlock(BlocksAether.aercloud), null,
				MapGen.genMap(new ItemStack(ItemsAether.skyroot_water_bucket), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.water_vial, ItemsAether.valkyrie_wings, null,
				MapGen.genMap(new ItemStack(ItemsAether.valkyrie_tea), 100));

		CoolerRecipes.instance().addCoolingFromItem(ItemsAether.skyroot_water_bucket, Item.getItemFromBlock(Blocks.AIR), new ItemStack(ItemsAether.skyroot_bucket),
				MapGen.genMap(new ItemStack(BlocksAether.highlands_ice), 100));
	}

	private static void addMasonry(ItemStack result, Object... required)
	{
		AetherAPI.content().masonry().registerRecipe(new SimpleRecipe(result, required));
	}

	private static void registerSmeltingRecipe(final ItemStack input, final ItemStack output, final float xp)
	{
		GameRegistry.addSmelting(input, output, xp);
	}

	public static void bakeRecipes()
	{
		AetherCore.PROXY.content().masonry().finalizeRecipes();
	}
}
