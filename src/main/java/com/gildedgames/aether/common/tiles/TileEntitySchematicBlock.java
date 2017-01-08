package com.gildedgames.aether.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public abstract class TileEntitySchematicBlock extends TileEntityLockable implements ITickable, IInventory
{

	protected ItemStack[] contents = new ItemStack[27];

	private int ticksExisted;

	protected boolean isMarkedForGeneration;

	@Override
	public String getName()
	{
		return "container.schematicBlock";
	}

	@Override
	public void update()
	{
		if (this.ticksExisted == 0 && this.isMarkedForGeneration())
		{
			this.onSchematicGeneration();

			if (this.shouldInvalidateTEOnGen())
			{
				this.invalidate();
			}

			return;
		}

		if (this.isMarkedForGeneration())
		{
			for (int u = 0; u < 2; ++u)
			{
				double motionX = (this.worldObj.rand.nextBoolean() ? 1.0D : -1.0D) * this.worldObj.rand.nextFloat() * 0.01F;
				double motionY = (this.worldObj.rand.nextBoolean() ? 1.0D : -1.0D) * this.worldObj.rand.nextFloat() * 0.01F;
				double motionZ = (this.worldObj.rand.nextBoolean() ? 1.0D : -1.0D) * this.worldObj.rand.nextFloat() * 0.01F;

				this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB,
						this.getPos().getX() + 0.5D + motionX,
						this.getPos().getY() + 0.5D + motionY, this.getPos().getZ() + 0.5D + motionZ, 0.0D, 0.0D, 0.0D);
			}
		}

		this.ticksExisted++;
	}

	public void setMarkedForGeneration(boolean flag)
	{
		this.isMarkedForGeneration = flag;

		this.sync();
	}

	public boolean isMarkedForGeneration()
	{
		return this.isMarkedForGeneration;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.contents.length; ++i)
		{
			if (this.contents[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.contents[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		compound.setBoolean("markedForGeneration", this.isMarkedForGeneration());

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.contents = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < this.contents.length)
			{
				this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		this.isMarkedForGeneration = compound.getBoolean("markedForGeneration");
	}

	public abstract void onSchematicGeneration();

	public abstract boolean shouldInvalidateTEOnGen();

	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID()
	{
		return "minecraft:chest";
	}

	@Override
	public int getSizeInventory()
	{
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.contents[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.contents[index] != null)
		{
			if (this.contents[index].stackSize <= count)
			{
				ItemStack itemstack1 = this.contents[index];
				this.contents[index] = null;
				this.markDirty();
				return itemstack1;
			}
			else
			{
				ItemStack itemstack = this.contents[index].splitStack(count);

				if (this.contents[index].stackSize == 0)
				{
					this.contents[index] = null;
				}

				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.contents[index] != null)
		{
			ItemStack itemstack = this.contents[index];
			this.contents[index] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.contents[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false :
				player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D)
						<= 64.0D && player.capabilities.isCreativeMode;
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
		return stack.getItem() instanceof ItemBlock;
	}

	public int getField(int id)
	{
		return 0;
	}

	public void setField(int id, int value)
	{
	}

	public int getFieldCount()
	{
		return 0;
	}

	public void clear()
	{
		for (int i = 0; i < this.contents.length; ++i)
		{
			this.contents[i] = null;
		}
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

}
