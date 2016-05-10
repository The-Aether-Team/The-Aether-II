package com.gildedgames.aether;

import com.gildedgames.aether.registry.altar.IAltarRecipeRegistry;
import com.gildedgames.aether.registry.equipment.IEquipmentRegistry;

public interface IAetherServices
{
	IAltarRecipeRegistry getAltarRecipeRegistry();

	IEquipmentRegistry getEquipmentRegistry();
}
