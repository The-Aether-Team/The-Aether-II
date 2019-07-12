package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.containers.BlockHolystoneFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityHolystoneFurnace extends LockableTileEntity implements ITickableTileEntity, ISidedInventory
{
	private static final int[] SLOTS_TOP = new int[] { 0 };

	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };

	private static final int[] SLOTS_SIDES = new int[] { 1 };

	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);

	private int furnaceBurnTime;

	private int currentItemBurnTime;

	private int cookTime;

	private int totalCookTime;

	private String furnaceCustomName;

	private final IItemHandler handlerTop = new SidedInvWrapper(this, Direction.UP);

	private final IItemHandler handlerBottom = new SidedInvWrapper(this, Direction.DOWN);

	private final IItemHandler handlerSide = new SidedInvWrapper(this, Direction.WEST);

	public static int getItemBurnTime(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return 0;
		}
		else
		{
			Item item = stack.getItem();

			if (item instanceof BlockItem && Block.getBlockFromItem(item) != Blocks.AIR)
			{
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.WOODEN_SLAB)
				{
					return 150;
				}

				if (block.getDefaultState().getMaterial() == Material.WOOD)
				{
					return 300;
				}

				if (block == Blocks.COAL_BLOCK)
				{
					return 16000;
				}
			}

			if (item instanceof ToolItem && "WOOD".equals(((ToolItem) item).getToolMaterialName()))
			{
				return 200;
			}
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName()))
			{
				return 200;
			}
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName()))
			{
				return 200;
			}
			if (item == Items.STICK)
			{
				return 100;
			}
			if (item == Items.COAL)
			{
				return 1600;
			}
			if (item == Items.LAVA_BUCKET)
			{
				return 20000;
			}
			if (item == Item.getItemFromBlock(Blocks.SAPLING))
			{
				return 100;
			}
			if (item == Items.BLAZE_ROD)
			{
				return 2400;
			}

			return ForgeEventFactory.getItemBurnTime(stack);
		}
	}

	public static boolean isItemFuel(ItemStack stack)
	{
		return getItemBurnTime(stack) > 0;
	}

	@Override
	public int getSizeInventory()
	{
		return this.furnaceItemStacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.furnaceItemStacks)
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
		return this.furnaceItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.furnaceItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.furnaceItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag)
		{
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.furnaceCustomName : "container.holystone_furnace";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_)
	{
		this.furnaceCustomName = p_145951_1_;
	}

	@Override
	public void readFromNBT(CompoundNBT compound)
	{
		super.readFromNBT(compound);

		this.furnaceItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);

		this.furnaceBurnTime = compound.getInt("BurnTime");
		this.cookTime = compound.getInt("CookTime");
		this.totalCookTime = compound.getInt("CookTimeTotal");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));

		if (compound.contains("CustomName", 8))
		{
			this.furnaceCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound)
	{
		super.writeToNBT(compound);

		compound.putInt("BurnTime", (short) this.furnaceBurnTime);
		compound.putInt("CookTime", (short) this.cookTime);
		compound.putInt("CookTimeTotal", (short) this.totalCookTime);

		ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

		if (this.hasCustomName())
		{
			compound.putString("CustomName", this.furnaceCustomName);
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
		return this.furnaceBurnTime > 0;
	}

	@Override
	public void update()
	{
		boolean prevBurning = this.isBurning();
		boolean isBurning = false;

		if (this.isBurning())
		{
			--this.furnaceBurnTime;
		}

		if (!this.world.isRemote)
		{
			ItemStack fuelStack = this.furnaceItemStacks.get(1);

			if (this.isBurning() || !fuelStack.isEmpty() && !this.furnaceItemStacks.get(0).isEmpty())
			{
				if (!this.isBurning() && this.canSmelt())
				{
					this.furnaceBurnTime = getItemBurnTime(fuelStack);
					this.currentItemBurnTime = this.furnaceBurnTime;

					if (this.isBurning())
					{
						isBurning = true;

						if (!fuelStack.isEmpty())
						{
							Item item = fuelStack.getItem();
							fuelStack.shrink(1);

							if (fuelStack.isEmpty())
							{
								this.furnaceItemStacks.set(1, item.getContainerItem(fuelStack));
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
						this.totalCookTime = this.getCookTime(this.furnaceItemStacks.get(0));
						this.smeltItem();
						isBurning = true;
					}
				}
				else
				{
					this.cookTime = 0;
				}
			}
			else if (!this.isBurning() && this.cookTime > 0)
			{
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}

			if (prevBurning != this.isBurning() && !this.world.isRemote)
			{
				isBurning = true;

				this.world.setBlockState(this.pos, this.world.getBlockState(this.pos)
						.withProperty(BlockHolystoneFurnace.PROPERTY_IS_LIT, this.isBurning()), 3);

				this.validate();

				this.world.setTileEntity(this.pos, this);
			}
		}

		if (isBurning)
		{
			this.markDirty();
		}
	}

	public int getCookTime(ItemStack stack)
	{
		return 200;
	}

	private boolean canSmelt()
	{
		if (this.furnaceItemStacks.get(0).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks.get(0));

			if (resultStack.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack existingResultStack = this.furnaceItemStacks.get(2);

				if (existingResultStack.isEmpty())
				{
					return true;
				}

				if (!existingResultStack.isItemEqual(resultStack))
				{
					return false;
				}

				int result = existingResultStack.getCount() + resultStack.getCount();

				return result <= this.getInventoryStackLimit() && result <= existingResultStack.getMaxStackSize();
			}
		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack smeltingStack = this.furnaceItemStacks.get(0);
			ItemStack smeltingResultStack = FurnaceRecipes.instance().getSmeltingResult(smeltingStack);
			ItemStack existingResultStack = this.furnaceItemStacks.get(2);

			if (existingResultStack.isEmpty())
			{
				this.furnaceItemStacks.set(2, smeltingResultStack.copy());
			}
			else if (existingResultStack.getItem() == smeltingResultStack.getItem())
			{
				existingResultStack.grow(smeltingResultStack.getCount());
			}

			if (smeltingStack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && smeltingStack.getMetadata() == 1 && !this.furnaceItemStacks.get(1).isEmpty()
					&& this.furnaceItemStacks.get(1).getItem() == Items.BUCKET)
			{
				this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
			}

			smeltingStack.shrink(1);
		}
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
		else if (index != 1)
		{
			return true;
		}
		else
		{
			ItemStack itemstack = this.furnaceItemStacks.get(1);
			return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
		}
	}

	@Override
	public int[] getSlotsForFace(Direction side)
	{
		return side == Direction.DOWN ? SLOTS_BOTTOM : (side == Direction.UP ? SLOTS_TOP : SLOTS_SIDES);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction)
	{
		if (direction == Direction.DOWN && index == 1)
		{
			Item item = stack.getItem();

			return item == Items.WATER_BUCKET || item == Items.BUCKET;
		}

		return true;
	}

	@Override
	public String getGuiID()
	{
		return "minecraft:furnace";
	}

	@Override
	public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn)
	{
		return new ContainerFurnace(playerInventory, this);
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.furnaceBurnTime;
			case 1:
				return this.currentItemBurnTime;
			case 2:
				return this.cookTime;
			case 3:
				return this.totalCookTime;
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
				this.furnaceBurnTime = value;
				break;
			case 1:
				this.currentItemBurnTime = value;
				break;
			case 2:
				this.cookTime = value;
				break;
			case 3:
				this.totalCookTime = value;
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
		this.furnaceItemStacks.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable Direction facing)
	{
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (facing == Direction.DOWN)
			{
				return (T) this.handlerBottom;
			}
			else if (facing == Direction.UP)
			{
				return (T) this.handlerTop;
			}
			else
			{
				return (T) this.handlerSide;
			}
		}

		return super.getCapability(capability, facing);
	}
}
