package com.gildedgames.aether.common.containers.tiles;

import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

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

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tile);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
	{
		return this.tile.isUsableByPlayer(playerIn);
	}

}
