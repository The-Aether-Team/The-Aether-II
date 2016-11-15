package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.properties.TemperatureProperties;
import com.gildedgames.aether.common.blocks.containers.BlockIncubator;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.items.misc.ItemMoaEgg;
import com.gildedgames.aether.common.util.TickTimer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
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

    private static final int INVENTORY_SIZE = 5;

    private static final int MOA_EGG_INDEX = 4;

    private ItemStack[] inventory;

    private static final int REQ_TEMPERATURE_THRESHOLD = 5000;

    private int currentHeatingProgress;

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

        ItemStack itemToHeat = this.getMoaEgg();

        boolean hasFuelSlotsFilled = true;

        for (int count = 0; count < MOA_EGG_INDEX; count++)
        {
            ItemStack stack = this.getStackInSlot(count);

            if (stack == null)
            {
                hasFuelSlotsFilled = false;
                break;
            }
        }

        if (itemToHeat != null)
        {
            TemperatureProperties props = itemToHeat.getCapability(AetherCapabilities.ITEM_PROPERTIES, null).getTemperatureProperties();

            if (props != null && props.getTemperatureThreshold(itemToHeat) > 0 && this.getTotalHeatingItems() >= 4 && hasFuelSlotsFilled)
            {
                this.progress.tick();

                if (this.progress.isMultipleOfSeconds())
                {
                    this.currentHeatingProgress += 5;
                    this.sync();
                }

                if (this.currentHeatingProgress >= TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD)
                {
                    ItemStack result = props.getResultWhenHeated(this.worldObj, this.getPos(), itemToHeat, this.worldObj.rand);

                    this.setInventorySlotContents(MOA_EGG_INDEX, result);

                    if (props.shouldDecreaseStackSize(true))
                    {
                        this.decrStackSize(MOA_EGG_INDEX, 1);
                    }

                    int totalDecreased = 0;

                    for (int count = 0; count < MOA_EGG_INDEX; count++)
                    {
                        ItemStack stack = this.getStackInSlot(count);

                        if (stack == null)
                        {
                            continue;
                        }

                        totalDecreased += 1;

                        this.decrStackSize(count, 1);

                        if (totalDecreased >= 4)
                        {
                            break;
                        }
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
            this.progress.reset();
            this.sync();
        }

        final IBlockState state = this.worldObj.getBlockState(this.pos);

        if (state.getBlock() instanceof BlockIncubator && state.getValue(BlockIncubator.PROPERTY_IS_LIT) != this.isHeating())
        {
            this.markDirty();

            this.worldObj.setBlockState(this.pos, state.withProperty(BlockIncubator.PROPERTY_IS_LIT, this.isHeating()));

            this.validate();
            this.worldObj.setTileEntity(this.pos, this);
        }
    }

    public ItemStack getMoaEgg()
    {
        return this.getStackInSlot(MOA_EGG_INDEX);
    }

    public boolean areFuelSlotsFilled()
    {
        boolean hasFuelSlotsFilled = true;

        for (int count = 0; count < MOA_EGG_INDEX; count++)
        {
            ItemStack stack = this.getStackInSlot(count);

            if (stack == null)
            {
                hasFuelSlotsFilled = false;
                break;
            }
        }

        return hasFuelSlotsFilled;
    }

    public boolean hasStartedHeating()
    {
        return (this.currentHeatingProgress > 0 || (this.getTotalHeatingItems() >= 4 && this.areFuelSlotsFilled())) && this.getMoaEgg() != null;
    }

    public boolean isHeating()
    {
        return this.getMoaEgg() != null && this.getTotalHeatingItems() >= 4 && this.areFuelSlotsFilled();
    }

    public int getCurrentHeatingProgress()
    {
        return this.currentHeatingProgress;
    }

    public int getRequiredTemperatureThreshold()
    {
        return TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD;
    }

    public int getTotalHeatingItems()
    {
        int total = 0;

        for (int count = 0; count < MOA_EGG_INDEX; count++)
        {
            ItemStack stack = this.getStackInSlot(count);

            if (stack == null)
            {
                continue;
            }

            TemperatureProperties props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null).getTemperatureProperties();

            if (props != null && props.getTemperature(stack) > 0)
            {
                total += stack.stackSize;
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

        this.sync();
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

        if (index < MOA_EGG_INDEX)
        {
            if (props != null && props.getTemperature(stack) > 0)
            {
                return true;
            }
        }

        if (index == MOA_EGG_INDEX)
        {
            return stack.getItem() instanceof ItemMoaEgg;
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
        return new ContainerIcestoneCooler(playerInventory, this);
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

        this.currentHeatingProgress = compound.getInteger("currentHeatingProgress");
    }

}
