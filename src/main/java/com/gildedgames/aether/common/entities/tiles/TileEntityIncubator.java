package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.containers.BlockIncubator;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.entities.TileEntityTypesAether;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.AnimalGender;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaNest;
import com.gildedgames.aether.common.items.other.ItemMoaEgg;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Random;

public class TileEntityIncubator extends LockableTileEntity implements ITickableTileEntity, IInventory
{

	public static final int REQ_TEMPERATURE_THRESHOLD = 2400;

	private static final int INVENTORY_SIZE = 2;

	private static final int AMBRO_CHUNK_INDEX = 0;

	private static final int MOA_EGG_INDEX = 1;

	private final int ambroDestroy = 1250; // # of ticks before an ambrosium chunk is destroyed.

	// increment and decrement values that can be adjusted to balance how fast or slow the incubator: heats, incubates, and cools.
	private final int coolingDecrement = 1;

	private final int heatingIncrement = 2;

	private final int eggTimerIncrement = 1;

	private final int eggTimerDecrement = 1;

	private final int ambroTimerIncrement = 2;

	private NonNullList<ItemStack> inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

	private int currentHeatingProgress;

	private int ambroTimer;

	private int eggTimer;

	private final IIntArray fields = new IIntArray()
	{
		@Override
		public int get(int id)
		{
			switch (id)
			{
				case 0:
					return TileEntityIncubator.this.currentHeatingProgress;
				case 1:
					return TileEntityIncubator.this.eggTimer;
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
					TileEntityIncubator.this.currentHeatingProgress = value;
				case 1:
					TileEntityIncubator.this.eggTimer = value;
			}
		}

		@Override
		public int size()
		{
			return 2;
		}
	};

	public TileEntityIncubator()
	{
		super(TileEntityTypesAether.INCUBATOR);
	}

	@Override
	public void tick()
	{
		if (this.world.isRemote())
		{
			return;
		}

		final BlockState state = this.world.getBlockState(this.pos);

		if (!this.isHeating() && this.currentHeatingProgress > 0)
		{
			this.currentHeatingProgress -= this.coolingDecrement;
		}

		if (state.getBlock() instanceof BlockIncubator && state.get(BlockIncubator.PROPERTY_IS_LIT) != this.isHeating())
		{
			this.markDirty();

			this.world.setBlockState(this.pos, state.with(BlockIncubator.PROPERTY_IS_LIT, this.isHeating()));

			this.validate();
			this.world.setTileEntity(this.pos, this);
		}

		ItemStack fuelstack = this.inventory.get(AMBRO_CHUNK_INDEX);
		ItemStack eggstack = this.getMoaEgg();

		if (state.getBlock() instanceof BlockIncubator && state.get(BlockIncubator.PROPERTY_IS_LIT))
		{
			if (this.canEggIncubate())
			{
				this.ambroTimer += (this.ambroTimerIncrement / 2); // slow down rate of decay for fuel when heat is max and above
			}
			else
			{
				this.ambroTimer += this.ambroTimerIncrement;
			}

			if (this.currentHeatingProgress < REQ_TEMPERATURE_THRESHOLD)
			{
				this.currentHeatingProgress += this.heatingIncrement;
			}

			if (this.ambroTimer >= this.ambroDestroy)
			{
				this.ambroTimer = 0;
				fuelstack.shrink(1);
			}
		}

		if (this.hasStartedHeating())
		{
			if (this.canEggIncubate())
			{
				this.eggTimer += this.eggTimerIncrement;
			}

			if (this.eggTimer >= this.getEggTimerMax())
			{
				//TODO: Baby Moas still don't spawn from incubator, but I think this is an issue w/ the Moas not an issue with the incubator
				Random rand = new Random();
				MoaGenePool stackGenes = ItemMoaEgg.getGenePool(eggstack);
				MoaNest familyNest = new MoaNest(this.world);
				EntityMoa moa = new EntityMoa(this.world, stackGenes.getStorage().getSeed());

				moa.setRaisedByPlayer(true);
				moa.setGrowingAge(-24000);
				moa.setFoodRequired(3);
				moa.setPosition(this.getPos().getX() + 0.5, this.getPos().getY() + 1.5, this.getPos().getZ() + 0.5);
				moa.setGender(rand.nextBoolean() ? AnimalGender.FEMALE : AnimalGender.MALE);
				moa.setAnimalPack(familyNest.getAnimalPack());

				this.world.addEntity(moa);

				eggstack.shrink(1);
				this.eggTimer = 0;
			}

		}

		if (!this.isHeating() && !this.getMoaEgg().isEmpty())
		{
			if (this.currentHeatingProgress == 0)
			{
				eggstack.shrink(1);    // kill egg if heat reaches 0.
			}
		}

		if (this.getMoaEgg().isEmpty())
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

	public boolean canEggIncubate()
	{
		return this.getCurrentHeatingProgress() >= TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD;
	}

	public int getEggTimer()
	{
		return this.eggTimer;
	}

	public int getEggTimerMax()
	{
		return REQ_TEMPERATURE_THRESHOLD;
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
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return this.world.getTileEntity(this.pos) == this
				&& player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D)
				<= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		// this code never seems to get called, commenting it out for reference in case I missed something during testing.
		//return stack.getItem() == ItemsAether.ambrosium_chunk || stack.getItem() == ItemsAether.rainbow_moa_egg;
		return false;
	}

	@Override
	public void clear()
	{
		this.inventory.clear();
	}

	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent( "container.incubator");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory playerInventory)
	{
		return new ContainerIncubator(id, playerInventory, this);
	}

	public void sync()
	{
		BlockState state = this.world.getBlockState(this.pos);

		this.world.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT tag = super.getUpdateTag();

		this.write(tag);

		return tag;
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT compound = this.getUpdateTag();

		return new SUpdateTileEntityPacket(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet)
	{
		this.read(packet.getNbtCompound());
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);

		ListNBT stackList = new ListNBT();

		for (int i = 0; i < this.inventory.size(); ++i)
		{
			if (!this.inventory.get(i).isEmpty())
			{
				CompoundNBT stackNBT = new CompoundNBT();

				stackNBT.putByte("slot", (byte) i);

				this.inventory.get(i).write(stackNBT);

				stackList.add(stackNBT);
			}
		}

		compound.put("inventory", stackList);

		compound.putFloat("currentHeatingProgress", this.currentHeatingProgress);
		compound.putInt("ambroTimer", this.ambroTimer);
		compound.putInt("eggTimer", this.eggTimer);

		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);

		this.inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

		ListNBT stackList = compound.getList("inventory", 10);

		for (int i = 0; i < stackList.size(); ++i)
		{
			CompoundNBT stack = stackList.getCompound(i);

			byte slotPos = stack.getByte("slot");

			if (slotPos >= 0 && slotPos < this.inventory.size())
			{
				this.inventory.set(slotPos, ItemStack.read(stack));
			}
		}

		this.currentHeatingProgress = compound.getInt("currentHeatingProgress");
		this.ambroTimer = compound.getInt("ambroTimer");
		this.eggTimer = compound.getInt("eggTimer");
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
