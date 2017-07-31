package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.containers.BlockIncubator;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import com.gildedgames.aether.common.entities.util.MoaNest;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemMoaEgg;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class TileEntityIncubator extends TileEntityLockable implements ITickable, IInventory
{

	private static final int INVENTORY_SIZE = 2;
	private static final int AMBRO_CHUNK_INDEX = 0;
	private static final int MOA_EGG_INDEX = 1;

	private NonNullList<ItemStack> inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

	public static final int REQ_TEMPERATURE_THRESHOLD = 5000;
	private final int ambroDestroy = 1250; // # of ticks before an ambrosium chunk is destroyed.

	// increment and decrement values that can be adjusted to balance how fast or slow the incubator: heats, incubates, and cools.
	private final float coolingDecrement = 1.0F;
	private final float heatingIncrement = 2.0F;
	private final int eggTimerIncrement = 1;
	private final int eggtimerDecrement = 1;
	private final float ambroTimerInecremt = 2.0F; // this should remain the same as heating increment.

	private float currentHeatingProgress;
	private int ambroTimer;
	private int eggTimer;

	@Override
	public void update()
	{
		if (this.world.isRemote)
		{
			return;
		}

		final IBlockState state = this.world.getBlockState(this.pos);

		if (!isHeating() && this.currentHeatingProgress > 0)
		{
			this.currentHeatingProgress -= this.coolingDecrement;
		}

		if (state.getBlock() instanceof BlockIncubator && state.getValue(BlockIncubator.PROPERTY_IS_LIT) != this.isHeating())
		{
			this.markDirty();

			this.world.setBlockState(this.pos, state.withProperty(BlockIncubator.PROPERTY_IS_LIT, this.isHeating()));

			this.validate();
			this.world.setTileEntity(this.pos, this);
		}


		ItemStack fuelstack = this.inventory.get(AMBRO_CHUNK_INDEX);
		ItemStack eggstack = this.getMoaEgg();
		if (state.getBlock() instanceof BlockIncubator && state.getValue(BlockIncubator.PROPERTY_IS_LIT))
		{
			this.ambroTimer += this.ambroTimerInecremt;
			if (this.currentHeatingProgress < REQ_TEMPERATURE_THRESHOLD)
			{
				this.currentHeatingProgress += this.heatingIncrement;
			}

			if (this.ambroTimer >= this.ambroDestroy) {
				this.ambroTimer = 0;
				fuelstack.shrink(1);
			}
		}
		if (hasStartedHeating())
		{
			if (this.canEggIncubate())
			{
				this.eggTimer += this.eggTimerIncrement;
			}
			else
			{
				if (this.eggTimer > 0) {
					this.eggTimer -= this.eggtimerDecrement;
				}
			}

			if (eggTimer >= (REQ_TEMPERATURE_THRESHOLD / 2))
			{
				Random rand = new Random();
				MoaGenePool stackGenes = ItemMoaEgg.getGenePool(eggstack);
				MoaNest familyNest = new MoaNest(this.world);
				EntityMoa moa = new EntityMoa(this.world, stackGenes.getStorage().getSeed());

				moa.setRaisedByPlayer(true);
				moa.setGrowingAge(-24000);
				moa.setPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
				moa.setGender(rand.nextBoolean() ? AnimalGender.FEMALE : AnimalGender.MALE);
				moa.setAnimalPack(familyNest.getAnimalPack());

				this.world.spawnEntity(moa);

				eggstack.shrink(1);
				this.eggTimer = 0;
			}
		}
		else
		{
			this.eggTimer = 0;
		}
	}

	@Nonnull
	public ItemStack getMoaEgg()
	{
		return this.getStackInSlot(MOA_EGG_INDEX);
	}

	public boolean areFuelSlotsFilled()
	{
		ItemStack stack = this.getStackInSlot(AMBRO_CHUNK_INDEX);
		return !stack.isEmpty();
	}

	public boolean hasStartedHeating()
	{
		return (this.currentHeatingProgress > 0 || this.areFuelSlotsFilled()) && !this.getMoaEgg().isEmpty();
	}

	public boolean isHeating()
	{
		return (this.areFuelSlotsFilled());
	}

	public float getCurrentHeatingProgress()
	{
		return this.currentHeatingProgress;
	}

	public int getRequiredTemperatureThreshold()
	{
		return TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD;
	}

	public boolean canEggIncubate()
	{
		return this.getCurrentHeatingProgress() >= 2500;
	}

	public int getEggTimer()
	{
		return this.eggTimer;
	}

	@Override
	public int getSizeInventory()
	{
		return INVENTORY_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index >= this.getSizeInventory())
		{
			return ItemStack.EMPTY;
		}

		return this.inventory.get(index);
	}

	@Override
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
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
	{
		if (index >= this.getSizeInventory())
		{
			return;
		}

		this.inventory.set(index, stack);
		this.sync();
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
				&& player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D)
				<= 64.0D;
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
		return stack.getItem() == ItemsAether.ambrosium_chunk || stack.getItem() == ItemsAether.rainbow_moa_egg;
	}


	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return (int)this.currentHeatingProgress;
		case 1:
			return this.eggTimer;
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
			this.currentHeatingProgress = value;
		case 1:
			this.eggTimer = value;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 2;
	}


	@Override
	public void clear()
	{
		this.inventory.clear();
	}

	@Override
	public String getName()
	{
		return "container.incubator";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList stackList = new NBTTagList();

		for (int i = 0; i < this.inventory.size(); ++i)
		{
			if (!this.inventory.get(i).isEmpty())
			{
				NBTTagCompound stackNBT = new NBTTagCompound();

				stackNBT.setByte("slot", (byte) i);

				this.inventory.get(i).writeToNBT(stackNBT);

				stackList.appendTag(stackNBT);
			}
		}

		compound.setTag("inventory", stackList);

		compound.setFloat("currentHeatingProgress", this.currentHeatingProgress);
		compound.setInteger("ambroTimer", this.ambroTimer);
		compound.setInteger("eggTimer", this.eggTimer);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

		NBTTagList stackList = compound.getTagList("inventory", 10);

		for (int i = 0; i < stackList.tagCount(); ++i)
		{
			NBTTagCompound stack = stackList.getCompoundTagAt(i);

			byte slotPos = stack.getByte("slot");

			if (slotPos >= 0 && slotPos < this.inventory.size())
			{
				this.inventory.set(slotPos, new ItemStack(stack));
			}
		}

		this.currentHeatingProgress = compound.getInteger("currentHeatingProgress");
		this.ambroTimer = compound.getInteger("ambroTimer");
		this.eggTimer = compound.getInteger("eggTimer");
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
}
