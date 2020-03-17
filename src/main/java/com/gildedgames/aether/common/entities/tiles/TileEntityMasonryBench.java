package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockMasonryBench;
import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityMasonryBench extends TileEntityLockable implements ITickable, IInventory
{
	private NonNullList<ItemStack> masonryItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);

	private String masonryCustomName;

	private EntityPlayer player;

	@Override
	public int getSizeInventory()
	{
		return this.masonryItemStacks.size();
	}

	public EnumFacing getFacing()
	{
		IBlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.masonry_bench)
		{
			return state.getValue(BlockMasonryBench.PROPERTY_FACING);
		}

		return EnumFacing.NORTH;
	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.masonryItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.masonryItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.masonryItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.masonryItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.masonryItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag)
		{
			this.markDirty();
		}
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
				&& player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		this.player = player;
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
		this.masonryItemStacks.clear();
	}

	@Override
	public void update()
	{

	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerMasonryBench(this.player, playerInventory, this);
	}

	@Override
	public String getGuiID()
	{
		return "aether:masonry_bench";
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.masonryCustomName : "container.masonry_bench";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.masonryCustomName != null && !this.masonryCustomName.isEmpty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
        this.masonryItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.masonryItemStacks);

        if (compound.hasKey("customeName", 8))
        {
            this.masonryCustomName = compound.getString("customName");
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.masonryItemStacks);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.masonryCustomName);
        }

		return compound;
	}
}
