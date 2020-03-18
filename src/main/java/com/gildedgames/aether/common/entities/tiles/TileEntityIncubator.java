package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.containers.BlockIcestoneCooler;
import com.gildedgames.aether.common.blocks.containers.BlockIncubator;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.AnimalGender;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaNest;
import com.gildedgames.aether.common.items.other.ItemMoaEgg;
import com.gildedgames.aether.common.recipes.CoolerRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import java.util.Random;

public class TileEntityIncubator extends TileEntityLockable implements ITickable, ISidedInventory
{
	private static final int[] SLOTS_EAST = new int[] { 1 };

	private static final int[] SLOTS_WEST = new int[] { 0 };

	private NonNullList<ItemStack> incubatorItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);

	private int incubationTimeMax = 1600;

	private int incubationTime;

	private int heatingTimeMax = 800;

	private int heatingTime;

	private String incubatorCustomName;

	private final IItemHandler handlerEast = new SidedInvWrapper(this, net.minecraft.util.EnumFacing.EAST);

	private final IItemHandler handlerWest = new SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

	private final IItemHandler handlerNone = new SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);

	public static boolean isItemEgg(ItemStack stack)
	{
		return stack.getItem() == ItemsAether.moa_egg_item || stack.getItem() == ItemsAether.rainbow_moa_egg;
	}

	public static boolean isItemFuel(ItemStack stack)
	{
		return stack.getItem() == ItemsAether.ambrosium_chunk || stack.getItem() == ItemsAether.irradiated_dust;
	}

	@Override
	public int getSizeInventory()
	{
		return this.incubatorItemStacks.size();
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
		for (ItemStack itemstack : this.incubatorItemStacks)
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
		return this.incubatorItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.incubatorItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.incubatorItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.incubatorItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.incubatorItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag)
		{
			this.incubationTime = 0;
			this.markDirty();
			this.sendUpdatesToClients();
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
		if (index == 0)
		{
			return isItemFuel(stack);
		}
		else if (index == 1)
		{
			return isItemEgg(stack);
		}

		return false;
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.incubationTimeMax;
			case 1:
				return this.incubationTime;
			case 2:
				return this.heatingTimeMax;
			case 3:
				return this.heatingTime;
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
				this.incubationTimeMax = value;
				break;
			case 1:
				this.incubationTime = value;
				break;
			case 2:
				this.heatingTimeMax = value;
				break;
			case 3:
				this.heatingTime = value;
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
		this.incubatorItemStacks.clear();
	}

	@Override
	public void update()
	{
		this.sendUpdatesToClients();

		this.setCustomInventoryName(this.incubatorCustomName);

		boolean isHeating = this.isHeating();
		boolean isIncubating = this.isIncubating();
		boolean flag = false;

		boolean isHeatTimerOn = false;

		if (!this.world.isRemote)
		{
			final IBlockState state = this.world.getBlockState(this.pos);

			ItemStack fuelStack = this.incubatorItemStacks.get(0);
			ItemStack eggStack = this.incubatorItemStacks.get(1);

			if (isHeating)
			{
				if (this.heatingTime < this.heatingTimeMax)
				{
					++this.heatingTime;
				}
				else
				{
					this.heatingTime = 0;
				}
				flag = true;
			}

			if (!fuelStack.isEmpty())
			{
				if (!eggStack.isEmpty())
				{
					++this.incubationTime;
					++this.heatingTime;
				}
				else
				{
					this.incubationTime = 0;
				}

				if (this.heatingTime >= this.heatingTimeMax)
				{
					fuelStack.shrink(1);
					this.heatingTime = 0;
				}

				flag = true;

				isHeatTimerOn = true;
			}

			if (!eggStack.isEmpty())
			{
				if (isIncubating)
				{
					if (this.incubationTime >= this.incubationTimeMax)
					{
						Random rand = new Random();
						MoaGenePool stackGenes = ItemMoaEgg.getGenePool(eggStack);
						MoaNest familyNest = new MoaNest(this.world);
						EntityMoa moa = new EntityMoa(this.world, stackGenes.getStorage().getSeed());

						moa.setRaisedByPlayer(true);
						moa.setGrowingAge(-24000);
						moa.setFoodRequired(3);
						moa.setPosition(this.getPos().getX() + 0.5, this.getPos().getY() + 1.5, this.getPos().getZ() + 0.5);
						moa.setGender(rand.nextBoolean() ? AnimalGender.FEMALE : AnimalGender.MALE);
						moa.setAnimalPack(familyNest.getAnimalPack());
						moa.setIsHungry(true);

						this.world.spawnEntity(moa);

						eggStack.shrink(1);
						this.incubationTime = 0;
					}
				}

				if (isHeatTimerOn && fuelStack.isEmpty() && this.heatingTime == 0)
				{
					eggStack.shrink(1);
					this.incubationTime = 0;
				}

				flag = true;
			}

			if (state.getBlock() instanceof BlockIncubator && state.getValue(BlockIncubator.PROPERTY_IS_LIT) != isHeating)
			{
				this.markDirty();

				this.world.setBlockState(this.pos, state.withProperty(BlockIncubator.PROPERTY_IS_LIT, this.isHeating()));

				this.validate();
				this.world.setTileEntity(this.pos, this);
			}
		}

		if (flag)
		{
			this.markDirty();
			this.sendUpdatesToClients();
		}
	}

	@SideOnly(Side.CLIENT)
	public static boolean isIncubating(IInventory inventory)
	{
		return inventory.getField(1) > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isHeating(IInventory inventory)
	{
		return inventory.getField(3) > 0;
	}

	public boolean isIncubating()
	{
		return this.incubationTime > 0;
	}

	public boolean isHeating()
	{
		return this.heatingTime > 0;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerIncubator(playerInventory, this);
	}

	@Override
	public String getGuiID()
	{
		return "aether:incubator";
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.incubatorCustomName : "container.incubator";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.incubatorCustomName != null && !this.incubatorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_)
	{
		this.incubatorCustomName = p_145951_1_;
	}

	public void sendUpdatesToClients()
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
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.incubatorItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(compound, this.incubatorItemStacks);

		this.incubationTime = compound.getInteger("incubationTime");
		this.heatingTime = compound.getInteger("heatingTime");

		if (compound.hasKey("CustomName", 8))
		{
			this.incubatorCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("incubationTime", (short) this.incubationTime);
		compound.setInteger("heatingTime", (short) this.heatingTime);
		ItemStackHelper.saveAllItems(compound, this.incubatorItemStacks);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.incubatorCustomName);
		}

		return compound;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.EAST || side == EnumFacing.SOUTH ? SLOTS_EAST : (side == EnumFacing.WEST || side == EnumFacing.NORTH ? SLOTS_WEST : new int[] { });
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
	{
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (facing == EnumFacing.EAST || facing == EnumFacing.SOUTH)
			{
				return (T) this.handlerEast;
			}
			else if (facing == EnumFacing.WEST || facing == EnumFacing.NORTH)
			{
				return (T) this.handlerWest;
			}
			else
			{
				return (T) this.handlerNone;
			}
		}

		return super.getCapability(capability, facing);
	}
}
