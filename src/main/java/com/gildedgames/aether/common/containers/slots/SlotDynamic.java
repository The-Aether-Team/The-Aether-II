package com.gildedgames.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotDynamic extends Slot
{

	private boolean enabled;

	public SlotDynamic(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
		this.enabled = true;
	}

	@Override
	public ItemStack getStack()
	{
		return super.getStack();
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return super.getItemStackLimit(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isEnabled()
	{
		return (this.getHasStack() || this.enabled);
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}

