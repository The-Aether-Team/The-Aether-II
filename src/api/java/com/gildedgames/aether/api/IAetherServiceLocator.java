package com.gildedgames.aether.api;

import com.gildedgames.aether.api.capabilites.instances.IInstanceRegistry;
import com.gildedgames.aether.api.registry.IEquipmentRegistry;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleCraftingRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;

public interface IAetherServiceLocator
{
	IAltarRecipeRegistry getAltarRecipeRegistry();

	ITabRegistry getTabRegistry();

	IInstanceRegistry getInstanceRegistry();

	ISimpleCraftingRegistry getSimpleCraftingRegistry();

	ISimpleCraftingRegistry getMasonryRegistry();

	IItemPropertiesRegistry getItemPropertiesRegistry();

	IEquipmentRegistry getEquipmentRegistry();
}
