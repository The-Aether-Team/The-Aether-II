package com.gildedgames.aether.common.entities.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public abstract class TileEntitySchematicBlock extends TileEntityLockable implements ITickable, IInventory
{

	protected NonNullList<ItemStack> contents = NonNullList.withSize(27, ItemStack.EMPTY);

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
				double motionX = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat() * 0.01F;
				double motionY = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat() * 0.01F;
				double motionZ = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat() * 0.01F;

				this.world.spawnParticle(EnumParticleTypes.SPELL_MOB,
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

		for (int i = 0; i < this.contents.size(); ++i)
		{
			if (!this.contents.get(i).isEmpty())
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);

				this.contents.get(i).writeToNBT(nbttagcompound);

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
		this.contents = NonNullList.withSize(27, ItemStack.EMPTY);

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < this.contents.size())
			{
				this.contents.set(j, new ItemStack(nbttagcompound));
			}
		}

		this.isMarkedForGeneration = compound.getBoolean("markedForGeneration");
	}

	public abstract void onSchematicGeneration();

	public abstract boolean shouldInvalidateTEOnGen();

	@Override
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
	@Nonnull
	public ItemStack getStackInSlot(int index)
	{
		return this.contents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.contents, index, count);

		if (!itemstack.isEmpty())
		{
			this.markDirty();
		}

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.contents, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.contents.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) == this && (
				player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D)
						<= 64.0D && player.capabilities.isCreativeMode);
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
		this.contents.clear();
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
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.contents)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

}
