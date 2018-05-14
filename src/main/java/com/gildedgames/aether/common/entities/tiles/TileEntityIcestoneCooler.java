package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.crafting.CoolerRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityIcestoneCooler extends TileEntityLockable implements ITickable, IInventory
{

	private final int totalCoolTime = 800;

	private final int itemCoolTime = 1600;

	private NonNullList<ItemStack> coolerItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);

	private int coolerCoolTime;

	private int currentItemCoolTime;

	private int coolTime;

	private String coolerCustomeName;

	@SideOnly(Side.CLIENT)
	public static boolean isCooling(IInventory inventory)
	{
		return inventory.getField(0) > 0;
	}

	public static boolean isItemCooling(ItemStack stack)
	{
		return stack.getItem() == ItemsAether.icestone;
	}

	public static boolean isItemIrradiated(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == ItemsAether.irradiated_chunk || item == ItemsAether.irradiated_armor || item == ItemsAether.irradiated_charm
				|| item == ItemsAether.irradiated_neckwear
				|| item == ItemsAether.irradiated_ring || item == ItemsAether.irradiated_sword || item == ItemsAether.irradiated_tool;
	}

	@Override
	public int getSizeInventory()
	{
		return this.coolerItemStacks.size();
	}

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
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.coolerItemStacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.coolerItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.coolerItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.coolerItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.coolerItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.coolerItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag)
		{
			this.coolTime = 0;
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

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 2)
		{
			return false;
		}
		else
		{
			if (index == 0)
			{
				return isItemIrradiated(stack);
			}

			if (index == 1)
			{
				return isItemCooling(stack);
			}
		}

		return false;
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.coolerCoolTime;
			case 1:
				return this.currentItemCoolTime;
			case 2:
				return this.coolTime;
			case 3:
				return this.totalCoolTime;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				this.coolerCoolTime = value;
				break;
			case 1:
				this.currentItemCoolTime = value;
				break;
			case 2:
				this.coolTime = value;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 4;
	}

	@Override
	public void clear()
	{
		this.coolerItemStacks.clear();
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public void update()
	{
		boolean flag = this.isCooling();
		boolean flag1 = false;

		if (this.isCooling())
		{
			--this.coolerCoolTime;
		}

		if (!this.world.isRemote)
		{
			ItemStack itemstack = this.coolerItemStacks.get(1);

			if (this.isCooling() || !itemstack.isEmpty() && !this.coolerItemStacks.get(0).isEmpty())
			{
				if (!this.isCooling() && this.canCool())
				{
					this.coolerCoolTime = this.itemCoolTime;
					this.currentItemCoolTime = this.coolerCoolTime;

					if (this.isCooling())
					{
						flag1 = true;

						if (!itemstack.isEmpty())
						{
							Item item = itemstack.getItem();
							itemstack.shrink(1);

							if (itemstack.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(itemstack);
								this.coolerItemStacks.set(1, item1);
							}
						}
					}
				}

				if (this.isCooling() && this.canCool())
				{
					++this.coolTime;

					if (this.coolTime == this.totalCoolTime)
					{
						this.coolTime = 0;
						this.coolItem();
						flag1 = true;
					}
				}
				else
				{
					this.coolTime = 0;
				}
			}
			else if (!this.isCooling() && this.coolTime > 0)
			{
				this.coolTime = MathHelper.clamp(this.coolTime - 2, 0, this.totalCoolTime);
			}

			if (flag != this.isCooling())
			{
				flag1 = true;
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerIcestoneCooler(playerInventory, this);
	}

	@Override
	public String getGuiID()
	{
		return "aether:coooler";
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.coolerCustomeName : "Icestone Cooler";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.coolerCustomeName != null && !this.coolerCustomeName.isEmpty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.coolerItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.coolerItemStacks);
		this.coolerCoolTime = compound.getInteger("coolerCoolTime");
		this.coolTime = compound.getInteger("coolTime");

		if (compound.hasKey("customeName", 8))
		{
			this.coolerCustomeName = compound.getString("customName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("coolerCoolTime", (short) this.coolerCoolTime);
		compound.setInteger("coolTime", (short) this.coolTime);
		ItemStackHelper.saveAllItems(compound, this.coolerItemStacks);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.coolerCustomeName);
		}

		return compound;
	}

	public boolean isCooling()
	{
		return this.coolerCoolTime > 0;
	}

	private boolean canCool()
	{
		if (this.coolerItemStacks.get(0).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack itemstack = CoolerRecipes.instance().getCoolingResult(this.coolerItemStacks.get(0));

			if (itemstack.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack itemstack1 = this.coolerItemStacks.get(2);

				if (itemstack1.isEmpty())
				{
					return true;
				}
				if (!itemstack1.isItemEqual(itemstack))
				{
					return false;
				}
				int result = itemstack1.getCount() + itemstack.getCount();
				return result <= this.getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
			}
		}
	}

	public int getCoolTime(ItemStack stack)
	{
		return this.totalCoolTime;
	}

	public void coolItem()
	{
		if (this.canCool())
		{
			ItemStack itemstack = this.coolerItemStacks.get(0);
			ItemStack itemstack1 = CoolerRecipes.instance().getCoolingResult(itemstack);
			ItemStack itemStack2 = this.coolerItemStacks.get(2);

			if (itemStack2.isEmpty())
			{
				this.coolerItemStacks.set(2, itemstack1.copy());
			}
			else if (itemStack2.getItem() == itemstack1.getItem())
			{
				itemStack2.grow(itemstack1.getCount());
			}

			itemstack.shrink(1);
		}
	}
}
