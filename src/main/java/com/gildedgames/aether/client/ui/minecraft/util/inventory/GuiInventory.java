package com.gildedgames.aether.client.ui.minecraft.util.inventory;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.data.rect.RectModifier;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.IconParser;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.Inventory;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.InventoryElement;
import com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure.InventoryListener;
import com.gildedgames.aether.client.ui.util.GuiCollection;
import com.gildedgames.aether.client.ui.util.TextureElement;
import com.gildedgames.aether.client.ui.util.events.slots.SlotBehavior;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStack;
import com.gildedgames.aether.client.ui.util.transform.GuiPositioner;
import com.gildedgames.aether.client.ui.util.transform.GuiPositionerGrid;

public class GuiInventory<E extends NBT> extends GuiFrame
{

	private GuiCollection<GuiFrame> slots;

	private Inventory<E> inventory;

	private GuiPositioner positioner;

	private InventoryContent<E> content;

	private InventoryListener<E> listener;

	private IconParser<E> iconParser;

	public GuiInventory(Inventory<E> inventory, GuiPositioner positioner, InventoryContent<E> content, IconParser<E> iconParser)
	{
		this.inventory = inventory;
		this.positioner = positioner;
		this.content = content;
		this.iconParser = iconParser;
	}

	public static <E extends NBT> GuiInventory<E> createStandard(Inventory<E> inventory, IconParser<E> iconParser)
	{
		return new GuiInventory<E>(inventory, new GuiPositionerGrid(), InventoryContent.createStandard(inventory, iconParser), iconParser);
	}

	public SlotStack<InventoryElement> getSlotContents(int slotIndex)
	{
		TextureElement slot = this.slots.events().get("slot" + slotIndex, TextureElement.class);

		@SuppressWarnings("unchecked")
		SlotBehavior<InventoryElement> slotBehavior = slot.events().get("slotBehavior", SlotBehavior.class);

		return slotBehavior.getSlotContents();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initContent(InputProvider input)
	{
		this.slots = new GuiCollection<GuiFrame>(this.positioner, this.content);

		this.slots.dim().add(this, RectModifier.ModifierType.ALL);

		this.listener = new InventorySync<E>(this.slots.events(), this.iconParser);

		this.inventory.addListener(this.listener);

		this.content().set("slots", this.slots);
	}

	@Override
	public void onClose(InputProvider input)
	{
		super.onClose(input);

		this.inventory.removeListener(this.listener);
	}

}
