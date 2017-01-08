package com.gildedgames.aether.client.ui.minecraft.util.inventory;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.UIContainer;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.IconParser;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.InventoryListener;
import com.gildedgames.aether.client.ui.util.events.slots.SlotBehavior;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStack;

public class InventorySync<E extends NBT> implements InventoryListener<E>
{

	private UIContainer inventoryContent;

	private IconParser<E> iconParser;

	public InventorySync(UIContainer inventoryContent, IconParser<E> iconParser)
	{
		this.inventoryContent = inventoryContent;
		this.iconParser = iconParser;
	}

	@Override
	public void onChange(int slotIndex, E contents)
	{
		GuiFrame slot = this.inventoryContent.get("slot" + slotIndex, GuiFrame.class);

		if (slot == null)
		{
			return;
		}

		@SuppressWarnings("unchecked")
		SlotBehavior<E> slotBehavior = slot.events().get("slotBehavior", SlotBehavior.class);

		if (slotBehavior.getSlotContents() != null && slotBehavior.getSlotContents().getData() != contents)
		{
			if (contents == null)
			{
				slotBehavior.clearSlotContents();
			}
			else
			{
				slotBehavior.setSlotContents(new SlotStack<E>(this.iconParser.parse(contents), contents));
			}
		}
	}

}
