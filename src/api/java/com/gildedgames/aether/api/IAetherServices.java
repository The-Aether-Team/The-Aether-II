package com.gildedgames.aether.api;

import com.gildedgames.aether.api.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.api.registry.equipment.IEquipmentRegistry;

public interface IAetherServices
{
	IAltarRecipeRegistry getAltarRecipeRegistry();

	IEquipmentRegistry getEquipmentRegistry();
}
