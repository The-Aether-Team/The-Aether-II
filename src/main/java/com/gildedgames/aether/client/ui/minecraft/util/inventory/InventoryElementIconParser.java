package com.gildedgames.aether.client.ui.minecraft.util.inventory;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.IconParser;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.InventoryElement;

public class InventoryElementIconParser implements IconParser<InventoryElement>
{
	
	public InventoryElementIconParser()
	{
		
	}

	@Override
	public GuiFrame parse(InventoryElement element)
	{
		return element.createIcon();
	}
	
}
