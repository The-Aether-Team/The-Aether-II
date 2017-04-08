package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.common.blocks.containers.BlockIncubator;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.util.TickTimer;
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
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class TileEntityIncubator extends TileEntityLockable implements ITickable, IInventory
{

	private static final int INVENTORY_SIZE = 5;

	private static final int MOA_EGG_INDEX = 4;

	private NonNullList<ItemStack> inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

	private static final int REQ_TEMPERATURE_THRESHOLD = 5000;

	private int currentHeatingProgress;

	private TickTimer progress = new TickTimer();

	@Override
	public void update()
	{
		if (this.world.isRemote)
		{
			return;
		}

		// TODO: Re-implement

		final IBlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() instanceof BlockIncubator && state.getValue(BlockIncubator.PROPERTY_IS_LIT) != this.isHeating())
		{
			this.markDirty();

			this.world.setBlockState(this.pos, state.withProperty(BlockIncubator.PROPERTY_IS_LIT, this.isHeating()));

			this.validate();
			this.world.setTileEntity(this.pos, this);
		}
	}

	@Nonnull
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

			if (stack == ItemStack.EMPTY)
			{
				hasFuelSlotsFilled = false;

				break;
			}
		}

		return hasFuelSlotsFilled;
	}

	public boolean hasStartedHeating()
	{
		return (this.currentHeatingProgress > 0 || (this.getTotalHeatingItems() >= 4 && this.areFuelSlotsFilled()))
				&& this.getMoaEgg() != ItemStack.EMPTY;
	}

	public boolean isHeating()
	{
		return this.getMoaEgg() != ItemStack.EMPTY && this.getTotalHeatingItems() >= 4 && this.areFuelSlotsFilled();
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
		// Re-implement
		return 0;
	}

	@Override
	public int getSizeInventory()
	{
		return INVENTORY_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index >= this.getSizeInventory())
		{
			return ItemStack.EMPTY;
		}

		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventory, index, count);

		if (!itemstack.isEmpty())
		{
			this.markDirty();
		}

		return itemstack;
	}

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
		this.sync();
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
		// TODO: Re-implement
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
		return "container.incubator";
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
		return "aether:incubator";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList stackList = new NBTTagList();

		for (int i = 0; i < this.inventory.size(); ++i)
		{
			if (this.inventory.get(i) != ItemStack.EMPTY)
			{
				NBTTagCompound stackNBT = new NBTTagCompound();

				stackNBT.setByte("slot", (byte) i);

				this.inventory.get(i).writeToNBT(stackNBT);

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

		this.currentHeatingProgress = compound.getInteger("currentHeatingProgress");
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
