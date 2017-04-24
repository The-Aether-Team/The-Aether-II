package com.gildedgames.aether.api;

import com.gildedgames.aether.api.dialog.IDialogManager;
import com.gildedgames.aether.api.registry.IEquipmentRegistry;
import com.gildedgames.aether.api.registry.IItemPropertiesRegistry;
import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.tab.ITabRegistry;

public interface IAetherServiceLocator
{
	IAltarRecipeRegistry getAltarRecipeRegistry();

	ITabRegistry getTabRegistry();

	IItemPropertiesRegistry getItemPropertiesRegistry();

	IEquipmentRegistry getEquipmentRegistry();

	IDialogManager getDialogManager();
}
