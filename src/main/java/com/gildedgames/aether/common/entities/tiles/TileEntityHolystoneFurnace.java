package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class TileEntityHolystoneFurnace extends TileEntityLockable implements ITickable, ISidedInventory
{
	private static final int[]
			slotsTop = new int[] { 0 },
			slotsBottom = new int[] { 2, 1 },
			slotsSides = new int[] { 1 };

	private NonNullList<ItemStack> inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

	private String customName;

	private int burnTime;

	private int currentItemBurnTime, cookTime, totalCookTime;

	@Override
	public int getSizeInventory()
	{
		return 3;
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

	@Override
	@Nonnull
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory.get(index);
	}

	@Override
	@Nonnull
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
	@Nonnull
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
	{
		boolean isEqual = stack.isItemEqual(this.inventory.get(index));

		this.inventory.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !isEqual)
		{
			this.totalCookTime = this.getCookTimeForItem(stack);
			this.cookTime = 0;

			this.markDirty();
		}
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.holystone_furnace";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

		NBTTagList stackList = compound.getTagList("Items", 10);

		for (int i = 0; i < stackList.tagCount(); ++i)
		{
			NBTTagCompound stack = stackList.getCompoundTagAt(i);

			byte slotPos = stack.getByte("Slot");

			if (slotPos >= 0 && slotPos < this.inventory.size())
			{
				this.inventory.set(slotPos, new ItemStack(stack));
			}
		}

		this.burnTime = compound.getShort("BurnTime");
		this.cookTime = compound.getShort("CookTime");
		this.totalCookTime = compound.getShort("CookTimeTotal");
		this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.inventory.get(1));

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setShort("BurnTime", (short) this.burnTime);
		compound.setShort("CookTime", (short) this.cookTime);
		compound.setShort("CookTimeTotal", (short) this.totalCookTime);

		NBTTagList stackList = new NBTTagList();

		for (int i = 0; i < this.inventory.size(); ++i)
		{
			if (!this.inventory.get(i).isEmpty())
			{
				NBTTagCompound stackNBT = new NBTTagCompound();

				stackNBT.setByte("Slot", (byte) i);

				this.inventory.get(i).writeToNBT(stackNBT);

				stackList.appendTag(stackNBT);
			}
		}

		compound.setTag("Items", stackList);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isBurning()
	{
		return this.burnTime > 0;
	}

	@Override
	public void update()
	{
		boolean isBurning = this.isBurning();

		boolean isDirty = false;

		if (this.isBurning())
		{
			this.burnTime--;
		}

		if (!this.world.isRemote)
		{
			if (!this.isBurning() && (this.inventory.get(1).isEmpty() || this.inventory.get(0).isEmpty()))
			{
				if (!this.isBurning() && this.cookTime > 0)
				{
					this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
				}
			}
			else
			{
				if (!this.isBurning() && this.canSmelt())
				{
					this.currentItemBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(this.inventory.get(1));

					if (this.isBurning())
					{
						isDirty = true;

						this.inventory.get(1).shrink(1);

						if (this.inventory.get(1).getCount() == 0)
						{
							this.inventory.set(1, this.inventory.get(1).getItem().getContainerItem(this.inventory.get(1)));
						}
					}
				}

				if (this.isBurning() && this.canSmelt())
				{
					++this.cookTime;

					if (this.cookTime == this.totalCookTime)
					{
						this.cookTime = 0;

						this.totalCookTime = this.getCookTimeForItem(this.inventory.get(0));

						this.smeltItem();

						isDirty = true;
					}
				}
				else
				{
					this.cookTime = 0;
				}
			}

			if (isBurning != this.isBurning())
			{
				isDirty = true;

				IBlockState state = this.world.getBlockState(this.pos);

				this.world.setBlockState(this.pos, state
						.withProperty(BlockHolystoneFurnace.PROPERTY_IS_LIT, this.isBurning()));

				this.validate();
				this.world.setTileEntity(this.pos, this);
			}
		}

		if (isDirty)
		{
			this.markDirty();
		}
	}

	public int getCookTimeForItem(ItemStack stack)
	{
		return 200;
	}

	private boolean canSmelt()
	{
		ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.get(0));

		if (this.inventory.get(2).isEmpty())
		{
			return true;
		}

		if (!this.inventory.get(2).isItemEqual(itemstack))
		{
			return false;
		}

		int result = this.inventory.get(2).getCount() + itemstack.getCount();

		return result <= this.getInventoryStackLimit() && result <= this.inventory.get(2).getMaxStackSize();
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.get(0));

			if (this.inventory.get(2).isEmpty())
			{
				this.inventory.set(2, itemstack.copy());
			}
			else if (this.inventory.get(2).getItem() == itemstack.getItem())
			{
				this.inventory.get(2).grow(itemstack.getCount());
			}

			if (this.inventory.get(0).getItem() == Item.getItemFromBlock(Blocks.SPONGE) &&
					this.inventory.get(0).getMetadata() == 1 && this.inventory.get(1).getItem() == Items.BUCKET)
			{
				this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
			}

			this.inventory.get(0).shrink(1);

			if (this.inventory.get(0).getCount() <= 0)
			{
				this.inventory.set(0, ItemStack.EMPTY);
			}
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) == this &&
				player.getDistanceSq(
						(double) this.pos.getX() + 0.5D,
						(double) this.pos.getY() + 0.5D,
						(double) this.pos.getZ() + 0.5D
				) <= 64.0D;
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
		return index != 2 && (index != 1 || TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack));
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (direction == EnumFacing.DOWN && index == 1)
		{
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public String getGuiID()
	{
		return "minecraft:furnace";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerFurnace(playerInventory, this);
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.burnTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		}

		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentItemBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
			break;
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
		this.inventory.clear();
	}
}
