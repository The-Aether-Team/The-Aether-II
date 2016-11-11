package com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.common.GuiFrame;

public interface InventoryElement extends NBT
{

	String name();

	GuiFrame createIcon();

}
