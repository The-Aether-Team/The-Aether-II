package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.containers.slots.masonry_bench.SlotMasonryInput;
import com.gildedgames.aether.common.containers.slots.masonry_bench.SlotMasonryOutput;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketMasonryOutputChanged;
import com.gildedgames.aether.common.recipes.MasonryRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ContainerMasonryBench extends Container
{
	private final IInventory tileMasonry;

	private ItemStack outputStack;

	private EntityPlayer player;

	public ItemStack getOutput()
	{
		return this.outputStack;
	}

	public void setOutput(ItemStack stack)
	{
		this.outputStack = stack;

		if (this.player.world.isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketMasonryOutputChanged(this.outputStack));
		}

		this.tileMasonry.setInventorySlotContents(1, getOutput());
	}

	public ContainerMasonryBench(final EntityPlayer player, final InventoryPlayer playerInventory, final IInventory masonryInventory)
	{
		this.tileMasonry = masonryInventory;
		this.addSlotToContainer(new SlotMasonryInput(masonryInventory, 0, 129, 9, 0));
		this.addSlotToContainer(new SlotMasonryOutput(masonryInventory, 1, 129, 58, 1));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 162));
		}

		this.player = player;
	}

	@Override
	public void addListener(final IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileMasonry);
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn)
	{
		return this.tileMasonry.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 1)
			{
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index != 0)
			{
				if (MasonryRecipes.instance().getOutput(itemstack1) != null)
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index < 29)
				{
					if (!this.mergeItemStack(itemstack1, 30, 38, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 2, 38, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}
}