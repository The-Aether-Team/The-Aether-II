package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.containers.BlockIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.entities.TileEntityTypesAether;
import com.gildedgames.aether.common.recipes.CoolerRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityIcestoneCooler extends LockableTileEntity implements ITickableTileEntity, IInventory
{
	private final int totalCoolTime = 800;

	private final int itemCoolTime = 1600;

	private NonNullList<ItemStack> coolerItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);

	private int coolerCoolTime;

	private int currentItemCoolTime;

	private int coolTime;

	protected final IIntArray fields = new IIntArray()
	{
		@Override
		public int get(int id)
		{
			switch (id)
			{
				case 0:
					return TileEntityIcestoneCooler.this.coolerCoolTime;
				case 1:
					return TileEntityIcestoneCooler.this.currentItemCoolTime;
				case 2:
					return TileEntityIcestoneCooler.this.coolTime;
				case 3:
					return TileEntityIcestoneCooler.this.totalCoolTime;
				default:
					return 0;
			}
		}

		@Override
		public void set(int id, int value)
		{
			switch (id)
			{
				case 0:
					TileEntityIcestoneCooler.this.coolerCoolTime = value;
					break;
				case 1:
					TileEntityIcestoneCooler.this.currentItemCoolTime = value;
					break;
				case 2:
					TileEntityIcestoneCooler.this.coolTime = value;
			}
		}

		@Override
		public int size()
		{
			return 4;
		}
	};

	public TileEntityIcestoneCooler()
	{
		super(TileEntityTypesAether.ICESTONE_COOLER);
	}

	@OnlyIn(Dist.CLIENT)
	public static boolean isCooling(TileEntityIcestoneCooler tile)
	{
		return tile.coolerCoolTime > 0;
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

	public Direction getFacing()
	{
		BlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.icestone_cooler)
		{
			return state.get(BlockIcestoneCooler.PROPERTY_FACING);
		}

		return Direction.NORTH;
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
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return this.world.getTileEntity(this.pos) == this
				&& player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(PlayerEntity player)
	{

	}

	@Override
	public void closeInventory(PlayerEntity player)
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
	public void clear()
	{
		this.coolerItemStacks.clear();
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public void tick()
	{
		boolean flag = this.isCooling();
		boolean flag1 = false;

		if (this.isCooling())
		{
			--this.coolerCoolTime;
		}

		if (!this.world.isRemote())
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
	public Container createMenu(int id, PlayerInventory inventory)
	{
		return new ContainerIcestoneCooler(id,this, inventory);
	}

	@Override
	public ITextComponent getDefaultName()
	{
		return new StringTextComponent("Icestone Cooler");
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.coolerItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.coolerItemStacks);
		this.coolerCoolTime = compound.getInt("coolerCoolTime");
		this.coolTime = compound.getInt("coolTime");

	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		compound.putInt("coolerCoolTime", (short) this.coolerCoolTime);
		compound.putInt("coolTime", (short) this.coolTime);
		ItemStackHelper.saveAllItems(compound, this.coolerItemStacks);

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
