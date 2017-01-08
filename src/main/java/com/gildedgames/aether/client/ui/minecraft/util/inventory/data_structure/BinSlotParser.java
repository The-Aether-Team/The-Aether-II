package com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInputPool;
import com.gildedgames.aether.client.ui.util.events.slots.SlotBehavior;
import com.gildedgames.aether.client.ui.util.events.slots.SlotParser;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStack;
import org.lwjgl.input.Keyboard;

public class BinSlotParser<E extends NBT> implements SlotParser<E>
{

	private Inventory<E> inventory;

	public BinSlotParser(Inventory<E> inventory)
	{
		this.inventory = inventory;
	}

	@Override
	public boolean isAllowed(SlotStack<E> state)
	{
		return true;
	}

	@Override
	public void onContentsChange(SlotBehavior<E> slot, SlotStack<E> newContents)
	{
		if (newContents == null)
		{
			return;
		}

		slot.clearSlotContents();
	}

	@Override
	public boolean onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			this.inventory.clear();

			return true;
		}

		return false;
	}

}
