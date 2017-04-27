package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class TileEntityIcestoneCooler extends TileEntityLockable implements ITickable, IInventory
{

	private static final int INVENTORY_SIZE = 7;

	private static final int AMBROSIUM_INDEX = 4;

	private static final int ITEM_TO_COOL_INDEX = 5;

	private static final int IRRADIATED_DUST_INDEX = 6;

	private NonNullList<ItemStack> inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

	private int reqTemperatureThreshold, currentCoolingProgress;

	public EnumFacing getFacing()
	{
		IBlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.icestone_cooler)
		{
			return state.getValue(BlockIcestoneCooler.PROPERTY_FACING);
		}

		return EnumFacing.NORTH;
	}

	@Override
	public void update()
	{
		if (this.world.isRemote)
		{
			return;
		}

		// Re-implement
		//        this.currentCoolingProgress = 0;
		//        this.reqTemperatureThreshold = 0;
		//        this.progress.reset();
		//        this.sync();
	}

	@Nonnull
	public ItemStack getItemToCool()
	{
		return this.getStackInSlot(ITEM_TO_COOL_INDEX);
	}

	public boolean isCooling()
	{
		return !this.getItemToCool().isEmpty() && this.reqTemperatureThreshold < 0 && this.getStackInSlot(IRRADIATED_DUST_INDEX).isEmpty()
				&& !this.getStackInSlot(AMBROSIUM_INDEX).isEmpty() && this.getStackInSlot(AMBROSIUM_INDEX).getCount() >= 4;
	}

	public int getCurrentCoolingProgress()
	{
		return this.currentCoolingProgress;
	}

	public int getRequiredTemperatureThreshold()
	{
		return this.reqTemperatureThreshold;
	}

	public int getTotalTemperature()
	{
		return 0;
	}

	@Override
	public int getSizeInventory()
	{
		return INVENTORY_SIZE;
	}

	@Nonnull
	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index >= this.getSizeInventory())
		{
			return ItemStack.EMPTY;
		}

		return this.inventory.get(index);
	}

	@Nonnull
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventory, index, count);

		if (!itemstack.isEmpty())
		{
			//			this.markDirty();
		}

		return itemstack;
	}

	@Nonnull
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
	{
		if (index >= this.getSizeInventory())
		{
			return;
		}

		this.inventory.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) == this
				&& player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D)
				<= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
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
		this.inventory.clear();
	}

	@Override
	public String getName()
	{
		return "container.icestone_cooler";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	public void sync()
	{
		IBlockState state = this.world.getBlockState(this.pos);

		this.world.notifyBlockUpdate(this.pos, state, state, 3);

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
		return "aether:icestone_cooler";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList stackList = new NBTTagList();

		for (int i = 0; i < this.inventory.size(); ++i)
		{
			if (!this.inventory.get(i).isEmpty())
			{
				NBTTagCompound stackNBT = new NBTTagCompound();

				stackNBT.setByte("slot", (byte) i);

				this.inventory.get(i).writeToNBT(stackNBT);

				stackList.appendTag(stackNBT);
			}
		}

		compound.setTag("inventory", stackList);

		compound.setInteger("reqTemperatureThreshold", this.reqTemperatureThreshold);
		compound.setInteger("currentCoolingProgress", this.currentCoolingProgress);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

		NBTTagList stackList = compound.getTagList("inventory", 10);

		for (int i = 0; i < stackList.tagCount(); ++i)
		{
			NBTTagCompound stack = stackList.getCompoundTagAt(i);

			byte slotPos = stack.getByte("slot");

			if (slotPos >= 0 && slotPos < this.inventory.size())
			{
				this.inventory.set(slotPos, new ItemStack(stack));
			}
		}

		this.reqTemperatureThreshold = compound.getInteger("reqTemperatureThreshold");
		this.currentCoolingProgress = compound.getInteger("currentCoolingProgress");
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.inventory)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}
}
