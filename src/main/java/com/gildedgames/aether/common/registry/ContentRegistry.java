package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.dialog.IDialogManager;
import com.gildedgames.aether.api.recipes.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.api.registry.IEffectRegistry;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.registry.recipes.IRecipeIndexRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.capabilities.CapabilityManagerAether;
import com.gildedgames.aether.common.capabilities.item.EffectRegistry;
import com.gildedgames.aether.common.containers.tab.TabRegistry;
import com.gildedgames.aether.common.dialog.DialogManager;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.recipes.simple.RecipeIndexRegistry;
import com.gildedgames.aether.common.recipes.simple.ShapedRecipeWrapper;
import com.gildedgames.aether.common.recipes.simple.ShapelessRecipeWrapper;
import com.gildedgames.aether.common.registry.content.*;
import com.gildedgames.aether.common.tiles.TileEntitiesAether;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.commons.lang3.Validate;

import java.util.Collection;

public class ContentRegistry implements IContentRegistry
{
	private final AltarRegistry altarRegistry = new AltarRegistry();

	private final TabRegistry tabRegistry = new TabRegistry();

	private final ItemPropertiesRegistry itemRegistry = new ItemPropertiesRegistry();

	private final EffectRegistry effectRegistry = new EffectRegistry();

	private final DialogManager dialogManager = new DialogManager(true);

	private final RecipeIndexRegistry craftableItemsIndex = new RecipeIndexRegistry();

	private boolean hasPreInit = false, hasInit = false;

	public void preInit()
	{
		Validate.isTrue(!this.hasPreInit, "Already pre-initialized");

		BlocksAether.preInit();
		ItemsAether.preInit();
		TileEntitiesAether.preInit();

		DimensionsAether.preInit();
		BiomesAether.preInit();

		LootTablesAether.preInit();
		EntitiesAether.preInit();

		EquipmentContent.preInit();
		RecipesAether.preInit();
		SoundsAether.preInit();

		NetworkingAether.preInit();

		this.tabRegistry.getInventoryGroup().registerServerTab(new TabEquipment());
		this.tabRegistry.getInventoryGroup().registerServerTab(new TabBugReport());

		this.hasPreInit = true;
	}

	public void init()
	{
		Validate.isTrue(!this.hasInit, "Already initialized");

		CapabilityManagerAether.init();

		TemplatesAether.init();
		GenerationAether.init();

		this.rebuildIndexes();

		this.hasInit = true;
	}

	private void rebuildIndexes()
	{
		Collection<IRecipe> recipes = RecipesAether.getCraftableRecipes();

		AetherCore.LOGGER.info("Rebuilding recipe indexes for {} recipes", recipes.size());

		long start = System.nanoTime();

		for (IRecipe recipe : recipes)
		{
			if (recipe instanceof ShapedOreRecipe)
			{
				this.craftableItemsIndex.registerRecipe(new ShapedRecipeWrapper((ShapedOreRecipe) recipe));
			}
			else if (recipe instanceof ShapelessOreRecipe)
			{
				this.craftableItemsIndex.registerRecipe(new ShapelessRecipeWrapper((ShapelessOreRecipe) recipe));
			}
		}

		AetherCore.LOGGER.info("Created recipe index with {} indices in {}ms",
				this.craftableItemsIndex.getIndexSize(), ((System.nanoTime() - start) / 1000000));
	}

	@Override
	public IAltarRecipeRegistry altar()
	{
		return this.altarRegistry;
	}

	@Override
	public ITabRegistry tabs()
	{
		return this.tabRegistry;
	}

	@Override
	public IItemPropertiesRegistry items()
	{
		return this.itemRegistry;
	}

	@Override
	public IEffectRegistry effects()
	{
		return this.effectRegistry;
	}

	@Override
	public IDialogManager dialog()
	{
		return this.dialogManager;
	}

	@Override
	public IRecipeIndexRegistry craftable()
	{
		return this.craftableItemsIndex;
	}
}
