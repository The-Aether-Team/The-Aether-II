package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.properties.TemperatureProperties;
import com.gildedgames.aether.common.containers.slots.SlotAmbrosium;
import com.gildedgames.aether.common.containers.slots.SlotFlintAndSteel;
import com.gildedgames.aether.common.containers.slots.SlotInventory;
import com.gildedgames.aether.common.containers.slots.SlotMoaEgg;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ContainerIncubator extends Container
{

    private final IInventory tile;

    public ContainerIncubator(InventoryPlayer playerInventory, IInventory coolerInventory)
    {
        this.tile = coolerInventory;

        for (int i = 0; i < 4; ++i)
        {
            this.addSlotToContainer(new SlotInventory(this.tile, i, 53 + (i * 18), 52));
        }

        this.addSlotToContainer(new SlotMoaEgg(this.tile, 4, 80, 18));

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
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index > 4)
            {
                TemperatureProperties props = itemstack1.getCapability(AetherCapabilities.ITEM_PROPERTIES, null).getTemperatureProperties();

                if (props != null)
                {
                    if (props.getTemperature(itemstack1) > 0)
                    {
                        if (!this.mergeItemStack(itemstack1, 0, 4, false))
                        {
                            if (index < 32)
                            {
                                if (!this.mergeItemStack(itemstack1, 32, 41, false))
                                {
                                    return null;
                                }
                            }
                            else
                            {
                                if (!this.mergeItemStack(itemstack1, 5, 32, false))
                                {
                                    return null;
                                }
                            }
                        }
                    }
                    else if (props.getTemperatureThreshold(itemstack1) > 0)
                    {
                        if (!this.mergeItemStack(itemstack1, 4, 5, false))
                        {
                            if (index < 32)
                            {
                                if (!this.mergeItemStack(itemstack1, 32, 41, false))
                                {
                                    return null;
                                }
                            }
                            else
                            {
                                if (!this.mergeItemStack(itemstack1, 5, 32, false))
                                {
                                    return null;
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (index < 32)
                    {
                        if (!this.mergeItemStack(itemstack1, 32, 41, false))
                        {
                            return null;
                        }
                    }
                    else
                    {
                        if (!this.mergeItemStack(itemstack1, 5, 32, false))
                        {
                            return null;
                        }
                    }
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (!this.mergeItemStack(itemstack1, 5, 41, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }
}
