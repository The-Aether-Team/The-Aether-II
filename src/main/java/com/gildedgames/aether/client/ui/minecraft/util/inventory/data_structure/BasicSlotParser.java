package com.gildedgames.aether.client.ui.minecraft.util.inventory.data_structure;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.input.MouseInputPool;
import com.gildedgames.aether.client.ui.util.events.slots.SlotBehavior;
import com.gildedgames.aether.client.ui.util.events.slots.SlotParser;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStack;
import org.lwjgl.input.Keyboard;

public class BasicSlotParser<E extends NBT> implements SlotParser<E>
{
	
	private Inventory<E> inventory;
	
	private int slotIndex;
	
	public BasicSlotParser(Inventory<E> inventory, int slotIndex)
	{
		this.inventory = inventory;
		this.slotIndex = slotIndex;
	}

	@Override
	public boolean isAllowed(SlotStack<E> stack)
	{
		return true;
	}

	@Override
	public void onContentsChange(SlotBehavior<E> slot, SlotStack<E> newContents)
	{
		if (newContents == null)
		{
			this.inventory.setElement(null, this.slotIndex);
			
			return;
		}
		
		E element = newContents.getData();
		
		this.inventory.setElement(element, this.slotIndex);
	}
	
	@Override
	public boolean onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			this.inventory.setElement(null, this.slotIndex);
			
			return true;
		}
		
		return false;
	}
	
}
