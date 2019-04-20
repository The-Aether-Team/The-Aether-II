package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.patron.PatronRewardRegistry;
import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.api.travellers_guidebook.ITGManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.CapabilityManagerAether;
import com.gildedgames.aether.common.capabilities.item.EffectRegistry;
import com.gildedgames.aether.common.containers.tab.TabRegistry;
import com.gildedgames.aether.common.dialog.DialogManager;
import com.gildedgames.aether.common.entities.EntitiesAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.patron.PatronRewards;
import com.gildedgames.aether.common.recipes.simple.RecipeIndexRegistry;
import com.gildedgames.aether.common.recipes.simple.RecipeWrapper;
import com.gildedgames.aether.common.registry.content.*;
import com.gildedgames.aether.common.shop.ShopManager;
import com.gildedgames.aether.common.tab.guidebook.TabGuidebook;
import com.gildedgames.aether.common.travellers_guidebook.TGManager;
import com.gildedgames.aether.common.util.helpers.PerfHelper;
import com.gildedgames.orbis.lib.IOrbisServicesListener;
import com.gildedgames.orbis.lib.OrbisLib;
import com.gildedgames.orbis.lib.data.management.IProjectManager;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ContentRegistry implements IContentRegistry, IOrbisServicesListener
{
	private final CurrencyRegistry currencyRegistry = new CurrencyRegistry();

	private final TemplateRegistry templateRegistry = new TemplateRegistry();

	private final AltarRecipeRegistry altarRegistry = new AltarRecipeRegistry();

	private final TabRegistry tabRegistry = new TabRegistry();

	private final ItemPropertiesRegistry itemRegistry = new ItemPropertiesRegistry();

	private final EffectRegistry effectRegistry = new EffectRegistry();

	private final DialogManager dialogManager = new DialogManager(true);

	private final TGManager tgManager = new TGManager();

	private final RecipeIndexRegistry craftableItemsIndex = new RecipeIndexRegistry();

	private final SimpleCraftingRegistry simpleCraftingRegistry = new SimpleCraftingRegistry();

	private final PatronRewardRegistry patronRewardRegistry = new PatronRewardRegistry();

	private final ShopManager shopManager = new ShopManager(true);

	/**
	 * Called when the game engine is initializing content.
	 */
	public void preInit()
	{
		OrbisLib.services().listen(this);

		PerfHelper.measure("Pre-initialize tiles", TileEntitiesAether::preInit);
		PerfHelper.measure("Pre-initialize dimensions", DimensionsAether::preInit);
		PerfHelper.measure("Pre-initialize loot tables", LootTablesAether::preInit);
		PerfHelper.measure("Pre-initialize entities", EntitiesAether::preInit);
		PerfHelper.measure("Pre-initialize networking", NetworkingAether::preInit);
		PerfHelper.measure("Pre-initialize patron rewards", PatronRewards::preInit);

		this.tabRegistry.getInventoryGroup().registerServerTab(new TabGuidebook());
		//this.tabRegistry.getInventoryGroup().registerServerTab(new TabBugReport());
		//this.tabRegistry.getInventoryGroup().registerServerTab(new TabPatronRewards());
	}

	/**
	 * Called after all game content has been registered in {@link ContentRegistry#preInit()}. This should be used
	 * to configure event handlers and systems that depend on game content being present.
	 */
	public void init()
	{
		PerfHelper.measure("Initialize equipment", EquipmentContent::init);
		PerfHelper.measure("Initialize capabilities", CapabilityManagerAether::init);
		PerfHelper.measure("Initialize templates", TemplatesAether::init);
		PerfHelper.measure("Initialize recipes", RecipesAether::init);
	}

	/**
	 * Called after all other mods are initialized. This should be used to interact with Orbis and other mods.
	 */
	public void postInit()
	{
		PerfHelper.measure("Register instances", InstancesAether::postInit);
	}

	/**
	 * Called when the server is initializing. This should be used to setup systems that depend on the IDs in a
	 * world or server instance.
	 */
	public void onServerAboutToStart()
	{
		PerfHelper.measure("Verify Orbis project manager", OrbisLib.services()::verifyProjectManagerStarted);
		PerfHelper.measure("Load generation", GenerationAether::load);
	}

	public void onServerStarting()
	{
		PerfHelper.measure("Re-build recipe indexes", this::rebuildIndexes);
		PerfHelper.measure("Initialize currency", CurrencyAether::onServerAboutToStart);
		PerfHelper.measure("Bake recipes", RecipesAether::bakeRecipes);
	}

	private void rebuildIndexes()
	{
		this.craftableItemsIndex.clearRegistrations();

		for (final IRecipe recipe : ForgeRegistries.RECIPES)
		{
			final ResourceLocation loc = Item.REGISTRY.getNameForObject(recipe.getRecipeOutput().getItem());

			if (loc != null && loc.getNamespace().equals(AetherCore.MOD_ID))
			{
				this.craftableItemsIndex.registerRecipe(new RecipeWrapper(recipe));
			}
		}
	}

	@Override
	public ITGManager tgManager()
	{
		return this.tgManager;
	}

	@Override
	public ShopManager shop()
	{
		return this.shopManager;
	}

	@Override
	public CurrencyRegistry currency()
	{
		return this.currencyRegistry;
	}

	@Override
	public TemplateRegistry templates()
	{
		return this.templateRegistry;
	}

	@Override
	public AltarRecipeRegistry altar()
	{
		return this.altarRegistry;
	}

	@Override
	public TabRegistry tabs()
	{
		return this.tabRegistry;
	}

	@Override
	public ItemPropertiesRegistry items()
	{
		return this.itemRegistry;
	}

	@Override
	public EffectRegistry effects()
	{
		return this.effectRegistry;
	}

	@Override
	public DialogManager dialog()
	{
		return this.dialogManager;
	}

	@Override
	public RecipeIndexRegistry craftable()
	{
		return this.craftableItemsIndex;
	}

	@Override
	public SimpleCraftingRegistry masonry()
	{
		return this.simpleCraftingRegistry;
	}

	@Override
	public PatronRewardRegistry patronRewards()
	{
		return this.patronRewardRegistry;
	}

	@Override
	public void onStartProjectManager(final IProjectManager projectManager)
	{
		BlueprintsAether.load(projectManager);
	}
}
