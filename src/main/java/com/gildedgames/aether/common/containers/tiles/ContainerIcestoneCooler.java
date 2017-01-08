package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ContainerIcestoneCooler extends Container
{

	private final IInventory tile;

	public ContainerIcestoneCooler(InventoryPlayer playerInventory, IInventory coolerInventory)
	{
		this.tile = coolerInventory;

		for (int i = 0; i < 2; ++i)
		{
			for (int j = 0; j < 2; ++j)
			{
				this.addSlotToContainer(new SlotInventory(this.tile, j + i * 2, 29 + j * 18, 26 + i * 18));
			}
		}

		this.addSlotToContainer(new SlotAmbrosium(this.tile, 4, 78, 52));
		this.addSlotToContainer(new SlotInventory(this.tile, 5, 78, 26));
		this.addSlotToContainer(new SlotInventory(this.tile, 6, 127, 39));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tile);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}

	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tile.isUseableByPlayer(playerIn);
	}

	/**
	 * Take a stack from the specified inventory slot.
	 */
	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		return super.transferStackInSlot(playerIn, index);
	}
}
