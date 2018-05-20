package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.dialog.IDialogManager;
import com.gildedgames.aether.api.patron.PatronRewardRegistry;
import com.gildedgames.aether.api.recipes.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.recipes.simple.ISimpleCraftingRegistry;
import com.gildedgames.aether.api.registry.IContentRegistry;
import com.gildedgames.aether.api.registry.IEffectRegistry;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.registry.recipes.IRecipeIndexRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;
import com.gildedgames.aether.api.shop.ICurrencyRegistry;
import com.gildedgames.aether.api.shop.IShopManager;
import com.gildedgames.aether.api.world.generation.ITemplateRegistry;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.gui.tab.TabPatronRewards;
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
import com.gildedgames.aether.common.util.helpers.PerfHelper;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.Validate;

public class ContentRegistry implements IContentRegistry
{
	private final CurrencyRegistry currencyRegistry = new CurrencyRegistry();

	private final TemplateRegistry templateRegistry = new TemplateRegistry();

	private final AltarRegistry altarRegistry = new AltarRegistry();

	private final TabRegistry tabRegistry = new TabRegistry();

	private final ItemPropertiesRegistry itemRegistry = new ItemPropertiesRegistry();

	private final EffectRegistry effectRegistry = new EffectRegistry();

	private final DialogManager dialogManager = new DialogManager(true);

	private final RecipeIndexRegistry craftableItemsIndex = new RecipeIndexRegistry();

	private final SimpleCraftingRegistry simpleCraftingRegistry = new SimpleCraftingRegistry();

	private final PatronRewardRegistry patronRewardRegistry = new PatronRewardRegistry();

	private final ShopManager shopManager = new ShopManager(true);

	private boolean hasPreInit = false, hasInit = false;

	public void preInit()
	{
		Validate.isTrue(!this.hasPreInit, "Already pre-initialized");

		PerfHelper.measure("Pre-initialize tiles", TileEntitiesAether::preInit);

		PerfHelper.measure("Pre-initialize dimensions", DimensionsAether::preInit);

		PerfHelper.measure("Pre-initialize loot tables", LootTablesAether::preInit);
		PerfHelper.measure("Pre-initialize entities", EntitiesAether::preInit);

		PerfHelper.measure("Pre-initialize recipes", RecipesAether::preInit);

		PerfHelper.measure("Pre-initialize networking", NetworkingAether::preInit);

		PerfHelper.measure("Pre-initialize patron rewards", PatronRewards::preInit);

		this.tabRegistry.getInventoryGroup().registerServerTab(new TabEquipment());
		this.tabRegistry.getInventoryGroup().registerServerTab(new TabBugReport());
		this.tabRegistry.getInventoryGroup().registerServerTab(new TabPatronRewards());

		this.hasPreInit = true;
	}

	public void init()
	{
		Validate.isTrue(!this.hasInit, "Already initialized");

		PerfHelper.measure("Initialize equipment", EquipmentContent::init);
		PerfHelper.measure("Initialize capabilities", CapabilityManagerAether::init);
		PerfHelper.measure("Initialize templates", TemplatesAether::init);
		PerfHelper.measure("Initialize blueprints", BlueprintsAether::init);
		PerfHelper.measure("Initialize generations", GenerationAether::init);
		PerfHelper.measure("Initialize recipes", RecipesAether::init);
		PerfHelper.measure("Initialize instances", InstancesAether::init);
		PerfHelper.measure("Initialize simple recipes", SimpleRecipesAether::postInit);

		this.hasInit = true;
	}

	public void rebuildIndexes()
	{
		this.craftableItemsIndex.clearRegistrations();

		for (IRecipe recipe : ForgeRegistries.RECIPES)
		{
			ResourceLocation loc = Item.REGISTRY.getNameForObject(recipe.getRecipeOutput().getItem());

			if (loc != null && loc.getResourceDomain().equals("aether"))
			{
				this.craftableItemsIndex.registerRecipe(new RecipeWrapper(recipe));
			}
		}
	}

	@Override
	public IShopManager shop()
	{
		return this.shopManager;
	}

	@Override
	public ICurrencyRegistry currency()
	{
		return this.currencyRegistry;
	}

	@Override
	public ITemplateRegistry templates()
	{
		return this.templateRegistry;
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

	@Override
	public ISimpleCraftingRegistry masonry()
	{
		return this.simpleCraftingRegistry;
	}

	@Override
	public PatronRewardRegistry patronRewards()
	{
		return this.patronRewardRegistry;
	}
}
