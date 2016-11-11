package com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.UIContainerMutable;
import com.gildedgames.aether.client.ui.util.events.slots.SlotBehavior;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStack;

public class BasicInventoryListener<E extends InventoryElement> implements InventoryListener<E>
{

	private UIContainerMutable frameContent;

	public BasicInventoryListener(UIContainerMutable frameContent)
	{
		this.frameContent = frameContent;
	}

	@Override
	public void onChange(int slotIndex, E contents)
	{
		GuiFrame slot = this.frameContent.get("slot" + slotIndex, GuiFrame.class);

		@SuppressWarnings("unchecked")
		SlotBehavior<InventoryElement> slotBehavior = slot.events().get("slotBehavior", SlotBehavior.class);

		if (slotBehavior != null && slotBehavior.getSlotContents() != null && slotBehavior.getSlotContents().getData() != contents)
		{
			if (contents == null)
			{
				slotBehavior.clearSlotContents();
			}
			else
			{
				slotBehavior.setSlotContents(new SlotStack<>(contents.createIcon(), contents));
			}
		}
	}

}
