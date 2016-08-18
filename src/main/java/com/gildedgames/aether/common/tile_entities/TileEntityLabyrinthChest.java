package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.blocks.dungeon.BlockLabyrinthChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityLabyrinthChest extends TileEntityLockable implements net.minecraft.util.ITickable, IInventory
{

	final private int CHEST_SIZE = 27;
	private ItemStack[] chestContents = new ItemStack[this.CHEST_SIZE];
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;
	private int ticksSinceSync;
	private String customName;

	private boolean isMimic, hasInit;

	public TileEntityLabyrinthChest()
	{

	}

	@Override
	public void onLoad()
	{
		if (!this.hasInit)
		{
			this.isMimic = this.worldObj.rand.nextBoolean();

			this.hasInit = true;
		}
	}

	public void setIsMimic(boolean flag)
	{
		this.isMimic = flag;
	}

	public boolean isMimic()
	{
		return this.isMimic;
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}

	public void update()
	{
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		++this.ticksSinceSync;

		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0)
		{
			this.numPlayersUsing = 0;
			float f = 5.0F;

			for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)i - f), (double)((float)j - f), (double)((float)k - f), (double)((float)(i + 1) + f), (double)((float)(j + 1) + f), (double)((float)(k + 1) + f))))
			{
				if (entityplayer.openContainer instanceof ContainerChest)
				{
					IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();

					if (iinventory == this)
					{
						++this.numPlayersUsing;
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		float f1 = 0.1F;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
		{
			double d1 = (double)i + 0.5D;
			double d2 = (double)k + 0.5D;

			this.worldObj.playSound(d1, (double)j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F, false);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
		{
			float f2 = this.lidAngle;

			if (this.numPlayersUsing > 0)
			{
				this.lidAngle += f1;
			}
			else
			{
				this.lidAngle -= f1;
			}

			if (this.lidAngle > 1.0F)
			{
				this.lidAngle = 1.0F;
			}

			float f3 = 0.5F;

			if (this.lidAngle < f3 && f2 >= f3)
			{
				double d3 = (double)i + 0.5D;
				double d0 = (double)k + 0.5D;

				this.worldObj.playSound(d3, (double)j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F, false);
			}

			if (this.lidAngle < 0.0F)
			{
				this.lidAngle = 0.0F;
			}
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID()
	{
		return "aether:labyrinth_chest";
	}

	@Override
	public int getSizeInventory()
	{
		return this.CHEST_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.chestContents[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.chestContents[index] != null)
		{
			if (this.chestContents[index].stackSize <= count)
			{
				ItemStack itemstack1 = this.chestContents[index];
				this.chestContents[index] = null;
				this.markDirty();
				return itemstack1;
			}
			else
			{
				ItemStack itemstack = this.chestContents[index].splitStack(count);

				if (this.chestContents[index].stackSize == 0)
				{
					this.chestContents[index] = null;
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
		if (this.chestContents[index] != null)
		{
			ItemStack itemstack = this.chestContents[index];
			this.chestContents[index] = null;
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
		this.chestContents[index] = stack;

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
		return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (this.numPlayersUsing < 0)
			{
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
		}
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator() && this.getBlockType() instanceof BlockLabyrinthChest)
		{
			--this.numPlayersUsing;
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
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
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			this.chestContents[i] = null;
		}
	}


	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.labyrinth_chest";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length)
			{
				this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		this.isMimic = compound.getBoolean("isMimic");
		this.hasInit = compound.getBoolean("hasInit");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i)
		{
			if (this.chestContents[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				this.chestContents[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}

		compound.setBoolean("isMimic", this.isMimic);
		compound.setBoolean("hasInit", this.hasInit);

		return compound;
	}

}
