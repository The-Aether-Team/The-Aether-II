package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityHolystoneFurnace extends TileEntityLockable implements ITickable, ISidedInventory
{
	private static final int[]
			slotsTop = new int[] { 0 },
			slotsBottom = new int[] { 2, 1 },
			slotsSides = new int[] { 1 };

	private ItemStack[] containedItemStacks = new ItemStack[3];

	private String customName;

	private int burnTime;

	private int currentItemBurnTime, cookTime, totalCookTime;

	@Override
	public int getSizeInventory()
	{
		return this.containedItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.containedItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.containedItemStacks[index] != null)
		{
			ItemStack stack;

			if (this.containedItemStacks[index].stackSize <= count)
			{
				stack = this.containedItemStacks[index];

				this.containedItemStacks[index] = null;

				return stack;
			}
			else
			{
				stack = this.containedItemStacks[index].splitStack(count);

				if (this.containedItemStacks[index].stackSize == 0)
				{
					this.containedItemStacks[index] = null;
				}

				return stack;
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
		if (this.containedItemStacks[index] != null)
		{
			ItemStack itemstack = this.containedItemStacks[index];
			this.containedItemStacks[index] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int index)
	{
		if (this.containedItemStacks[index] != null)
		{
			ItemStack stack = this.containedItemStacks[index];

			this.containedItemStacks[index] = null;

			return stack;
		}
		else
		{
			return null;
		}
	}

	public void setInventorySlotContents(int index, ItemStack stack)
	{
		boolean isEqual = stack != null && stack.isItemEqual(this.containedItemStacks[index]) &&
				ItemStack.areItemStackTagsEqual(stack, this.containedItemStacks[index]);

		this.containedItemStacks[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
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

	public void setCustomInventoryName(String name)
	{
		this.customName = name;
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.containedItemStacks = new ItemStack[this.getSizeInventory()];

		NBTTagList stackList = compound.getTagList("Items", 10);

		for (int i = 0; i < stackList.tagCount(); ++i)
		{
			NBTTagCompound stack = stackList.getCompoundTagAt(i);

			byte slotPos = stack.getByte("Slot");

			if (slotPos >= 0 && slotPos < this.containedItemStacks.length)
			{
				this.containedItemStacks[slotPos] = ItemStack.loadItemStackFromNBT(stack);
			}
		}

		this.burnTime = compound.getShort("BurnTime");
		this.cookTime = compound.getShort("CookTime");
		this.totalCookTime = compound.getShort("CookTimeTotal");
		this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.containedItemStacks[1]);

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setShort("BurnTime", (short) this.burnTime);
		compound.setShort("CookTime", (short) this.cookTime);
		compound.setShort("CookTimeTotal", (short) this.totalCookTime);

		NBTTagList stackList = new NBTTagList();

		for (int i = 0; i < this.containedItemStacks.length; ++i)
		{
			if (this.containedItemStacks[i] != null)
			{
				NBTTagCompound stackNBT = new NBTTagCompound();

				stackNBT.setByte("Slot", (byte) i);

				this.containedItemStacks[i].writeToNBT(stackNBT);

				stackList.appendTag(stackNBT);
			}
		}

		compound.setTag("Items", stackList);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isBurning()
	{
		return this.burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory)
	{
		return inventory.getField(0) > 0;
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

		if (!this.worldObj.isRemote)
		{
			if (!this.isBurning() && (this.containedItemStacks[1] == null || this.containedItemStacks[0] == null))
			{
				if (!this.isBurning() && this.cookTime > 0)
				{
					this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
				}
			}
			else
			{
				if (!this.isBurning() && this.canSmelt())
				{
					this.currentItemBurnTime = this.burnTime = TileEntityFurnace
							.getItemBurnTime(this.containedItemStacks[1]);

					if (this.isBurning())
					{
						isDirty = true;

						if (this.containedItemStacks[1] != null)
						{
							--this.containedItemStacks[1].stackSize;

							if (this.containedItemStacks[1].stackSize == 0)
							{
								this.containedItemStacks[1] = this.containedItemStacks[1].getItem()
										.getContainerItem(this.containedItemStacks[1]);
							}
						}
					}
				}

				if (this.isBurning() && this.canSmelt())
				{
					++this.cookTime;

					if (this.cookTime == this.totalCookTime)
					{
						this.cookTime = 0;

						this.totalCookTime = this.getCookTimeForItem(this.containedItemStacks[0]);

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

				IBlockState state = this.worldObj.getBlockState(this.pos);

				this.worldObj.setBlockState(this.pos, state
						.withProperty(BlockHolystoneFurnace.PROPERTY_IS_LIT, this.isBurning()));

				this.validate();
				this.worldObj.setTileEntity(this.pos, this);
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
		if (this.containedItemStacks[0] == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.containedItemStacks[0]);

			if (itemstack == null)
			{
				return false;
			}

			if (this.containedItemStacks[2] == null)
			{
				return true;
			}

			if (!this.containedItemStacks[2].isItemEqual(itemstack))
			{
				return false;
			}

			int result = this.containedItemStacks[2].stackSize + itemstack.stackSize;

			return result <= this.getInventoryStackLimit() && result <= this.containedItemStacks[2].getMaxStackSize();
		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.containedItemStacks[0]);

			if (this.containedItemStacks[2] == null)
			{
				this.containedItemStacks[2] = itemstack.copy();
			}
			else if (this.containedItemStacks[2].getItem() == itemstack.getItem())
			{
				this.containedItemStacks[2].stackSize += itemstack.stackSize;
			}

			if (this.containedItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge)
					&& this.containedItemStacks[0].getMetadata() == 1 && this.containedItemStacks[1] != null
					&& this.containedItemStacks[1].getItem() == Items.bucket)
			{
				this.containedItemStacks[1] = new ItemStack(Items.water_bucket);
			}

			--this.containedItemStacks[0].stackSize;

			if (this.containedItemStacks[0].stackSize <= 0)
			{
				this.containedItemStacks[0] = null;
			}
		}
	}

	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) == this &&
				player.getDistanceSq(
						(double) this.pos.getX() + 0.5D,
						(double) this.pos.getY() + 0.5D,
						(double) this.pos.getZ() + 0.5D
				) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) { }

	public void closeInventory(EntityPlayer player) { }

	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return index != 2 && (index != 1 || TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack));
	}

	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (direction == EnumFacing.DOWN && index == 1)
		{
			Item item = stack.getItem();

			if (item != Items.water_bucket && item != Items.bucket)
			{
				return false;
			}
		}

		return true;
	}

	public String getGuiID()
	{
		return "minecraft:furnace";
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerFurnace(playerInventory, this);
	}

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

	public int getFieldCount()
	{
		return 4;
	}

	public void clear()
	{
		for (int i = 0; i < this.containedItemStacks.length; ++i)
		{
			this.containedItemStacks[i] = null;
		}
	}
}
