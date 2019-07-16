package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.ContainerTypesAether;
import com.gildedgames.aether.common.containers.slots.SlotCoolerOutput;
import com.gildedgames.aether.common.containers.slots.SlotCoolingItem;
import com.gildedgames.aether.common.containers.slots.SlotIrradiatedItem;
import com.gildedgames.aether.common.entities.tiles.TileEntityIcestoneCooler;
import com.gildedgames.aether.common.recipes.CoolerRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ContainerIcestoneCooler extends Container
{
	private final IInventory tileCooler;

	private final IIntArray fields;

	public ContainerIcestoneCooler(int id, PlayerInventory playerInventory, IInventory coolerInventory, IIntArray fields)
	{
		super(ContainerTypesAether.ICESTONE_COOLER, id);

		this.fields = fields;

		this.func_216961_a(fields);

		this.tileCooler = coolerInventory;

		this.addSlot(new SlotIrradiatedItem(coolerInventory, 0, 56, 17, 0));
		this.addSlot(new SlotCoolingItem(coolerInventory, 1, 56, 53, 1));
		this.addSlot(new SlotCoolerOutput(coolerInventory, 2, 116, 35, 2));

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int i = 0; i < 9; ++i)
		{
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	public ContainerIcestoneCooler(int id, PlayerInventory playerInventory)
	{
		this(id, playerInventory, new Inventory(3), new IntArray(4));
	}

	@Override
	public boolean canInteractWith(final PlayerEntity playerIn)
	{
		return this.tileCooler.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(final PlayerEntity playerIn, final int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			final ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index != 1 && index != 0)
			{
				if (!CoolerRecipes.instance().getCoolingResult(itemstack1).isEmpty())
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityIcestoneCooler.isItemCooling(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index < 30)
				{
					if (!this.mergeItemStack(itemstack1, 30, 39, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
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

	public boolean isCooling()
	{
		return this.getCoolerCoolTime() > 0;
	}

	public int getCoolerCoolTime()
	{
		return this.fields.get(0);
	}

	public int getCurrentItemCoolTime()
	{
		return this.fields.get(1);
	}

	public int getCoolTime()
	{
		return this.fields.get(2);
	}

	public int getTotalCoolTime()
	{
		return this.fields.get(3);
	}
}
