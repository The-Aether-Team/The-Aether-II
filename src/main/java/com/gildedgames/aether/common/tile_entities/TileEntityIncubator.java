package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.properties.TemperatureProperties;
import com.gildedgames.aether.common.containers.ContainerFrostpineCooler;
import com.gildedgames.aether.common.util.ItemUtil;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

public class TileEntityIncubator extends TileEntityLockable implements ITickable, IInventory
{

    private static final int INVENTORY_SIZE = 6;

    private static final int ITEM_TO_HEAT_INDEX = 4;

    private static final int FLINT_AND_STEEL_INDEX = 5;

    private ItemStack[] inventory;

    private int reqTemperatureThreshold, currentHeatingProgress;

    private TickTimer progress = new TickTimer();

    public TileEntityIncubator()
    {
        this.inventory = new ItemStack[INVENTORY_SIZE];
    }

    @Override
    public void update()
    {
        if (this.worldObj.isRemote)
        {
            return;
        }

        ItemStack itemToHeat = this.getItemToHeat();
        ItemStack flintAndSteel = this.getFlintAndSteel();

        if (itemToHeat != null)
        {
            TemperatureProperties props = itemToHeat.getCapability(AetherCapabilities.ITEM_PROPERTIES, null).getTemperatureProperties();

            if (props != null && props.getTemperatureThreshold(itemToHeat) > 0 && flintAndSteel != null)
            {
                this.progress.tick();

                if (this.progress.isMultipleOfSeconds())
                {
                    this.currentHeatingProgress += this.getTotalTemperature();
                    this.sync();
                }

                if (this.currentHeatingProgress >= this.reqTemperatureThreshold)
                {
                    ItemStack result = props.getResultWhenHeated(this.worldObj, this.getPos(), itemToHeat, this.worldObj.rand);

                    this.setInventorySlotContents(ITEM_TO_HEAT_INDEX, result);

                    if (props.shouldDecreaseStackSize(true))
                    {
                        this.decrStackSize(ITEM_TO_HEAT_INDEX, 1);
                    }

                    ItemUtil.damageItem(flintAndSteel, 15, this.worldObj.rand);

                    if (flintAndSteel.stackSize <= 0)
                    {
                        this.setInventorySlotContents(FLINT_AND_STEEL_INDEX, null);
                    }

                    this.currentHeatingProgress = 0;
                    this.progress.reset();
                    this.sync();
                }
            }
        }
        else
        {
            this.currentHeatingProgress = 0;
            this.reqTemperatureThreshold = 0;
            this.progress.reset();
            this.sync();
        }
    }

    public ItemStack getItemToHeat()
    {
        return this.getStackInSlot(ITEM_TO_HEAT_INDEX);
    }

    public ItemStack getFlintAndSteel() { return this.getStackInSlot(FLINT_AND_STEEL_INDEX); }

    public boolean hasStartedHeating()
    {
        return this.reqTemperatureThreshold > 0;
    }

    public boolean isHeating()
    {
        return this.getItemToHeat() != null && this.reqTemperatureThreshold > 0 && this.getItemToHeat() != null && this.getFlintAndSteel() != null;
    }

    public int getCurrentHeatingProgress()
    {
        return this.currentHeatingProgress;
    }

    public int getRequiredTemperatureThreshold()
    {
        return this.reqTemperatureThreshold;
    }

    public int getTotalTemperature()
    {
        int total = 0;

        for (int count = 0; count < ITEM_TO_HEAT_INDEX; count++)
        {
            ItemStack stack = this.getStackInSlot(count);

            if (stack == null)
            {
                continue;
            }

            TemperatureProperties props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null).getTemperatureProperties();

            if (props != null && props.getTemperature(stack) > 0)
            {
                total += props.getTemperature(stack) * stack.stackSize;
            }
        }

        return total;
    }

    @Override
    public int getSizeInventory()
    {
        return INVENTORY_SIZE;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index)
    {
        if (index >= this.getSizeInventory())
        {
            return null;
        }

        return this.inventory[index];
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack stack = this.getStackInSlot(index);

        if (stack == null)
        {
            return null;
        }

        ItemStack copiedStack;

        if (stack.stackSize <= count)
        {
            copiedStack = stack;

            this.setInventorySlotContents(index, null);

            return copiedStack;
        }
        else
        {
            copiedStack = stack.splitStack(count);

            if (stack.stackSize == 0)
            {
                this.setInventorySlotContents(index, null);
            }

            return copiedStack;
        }
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack stack = this.getStackInSlot(index);

        if (stack != null)
        {
            this.setInventorySlotContents(index, null);

            return stack;
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        if (index >= this.getSizeInventory())
        {
            return;
        }

        this.inventory[index] = stack;

        if (index == ITEM_TO_HEAT_INDEX)
        {
            if (stack == null)
            {
                this.reqTemperatureThreshold = 0;
            }
            else
            {
                TemperatureProperties props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null).getTemperatureProperties();

                if (props != null)
                {
                    this.reqTemperatureThreshold = props.getTemperatureThreshold(stack);
                }
            }
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (stack == null)
        {
            return false;
        }

        TemperatureProperties props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null).getTemperatureProperties();

        if (index < ITEM_TO_HEAT_INDEX)
        {
            if (props != null && props.getTemperature(stack) > 0)
            {
                return true;
            }
        }

        if (index == ITEM_TO_HEAT_INDEX)
        {
            return props != null && props.getTemperatureThreshold(stack) > 0;
        }

        if (index == FLINT_AND_STEEL_INDEX)
        {
            return stack.getItem() instanceof ItemFlintAndSteel;
        }

        return false;
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {

    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {
        this.inventory = new ItemStack[INVENTORY_SIZE];
    }

    @Override
    public String getName()
    {
        return "container.incubator";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    public void sync()
    {
        IBlockState state = this.worldObj.getBlockState(this.pos);

        this.worldObj.notifyBlockUpdate(this.pos, state, state, 3);

        this.markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();

        this.writeToNBT(tag);

        return tag;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound compound = this.getUpdateTag();

        return new SPacketUpdateTileEntity(this.pos, 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerFrostpineCooler(playerInventory, this);
    }

    @Override
    public String getGuiID()
    {
        return "aether:incubator";
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList stackList = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound stackNBT = new NBTTagCompound();

                stackNBT.setByte("slot", (byte) i);

                this.inventory[i].writeToNBT(stackNBT);

                stackList.appendTag(stackNBT);
            }
        }

        compound.setTag("inventory", stackList);

        compound.setInteger("reqTemperatureThreshold", this.reqTemperatureThreshold);
        compound.setInteger("currentHeatingProgress", this.currentHeatingProgress);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.inventory = new ItemStack[INVENTORY_SIZE];

        NBTTagList stackList = compound.getTagList("inventory", 10);

        for (int i = 0; i < stackList.tagCount(); ++i)
        {
            NBTTagCompound stack = stackList.getCompoundTagAt(i);

            byte slotPos = stack.getByte("slot");

            if (slotPos >= 0 && slotPos < this.inventory.length)
            {
                this.inventory[slotPos] = ItemStack.loadItemStackFromNBT(stack);
            }
        }

        this.reqTemperatureThreshold = compound.getInteger("reqTemperatureThreshold");
        this.currentHeatingProgress = compound.getInteger("currentHeatingProgress");
    }

}
