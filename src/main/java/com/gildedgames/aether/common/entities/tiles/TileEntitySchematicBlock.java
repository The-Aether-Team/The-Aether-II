package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.orbis.lib.processing.BlockAccessExtendedWrapper;
import com.gildedgames.orbis.lib.processing.IBlockAccess;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class TileEntitySchematicBlock extends LockableTileEntity implements ITickableTileEntity, IInventory
{

	protected NonNullList<ItemStack> contents = NonNullList.withSize(27, ItemStack.EMPTY);

	protected boolean isMarkedForGeneration;

	private int ticksExisted;

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
			this.onSchematicGeneration(new BlockAccessExtendedWrapper(this.getWorld()), this.getWorld().rand);

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
				final double motionX = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat() * 0.01F;
				final double motionY = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat() * 0.01F;
				final double motionZ = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat() * 0.01F;

				this.world.spawnParticle(ParticleTypes.SPELL_MOB,
						this.getPos().getX() + 0.5D + motionX,
						this.getPos().getY() + 0.5D + motionY, this.getPos().getZ() + 0.5D + motionZ, 0.0D, 0.0D, 0.0D);
			}
		}

		this.ticksExisted++;
	}

	public boolean isMarkedForGeneration()
	{
		return this.isMarkedForGeneration;
	}

	public void setMarkedForGeneration(final boolean flag)
	{
		this.isMarkedForGeneration = flag;

		this.sync();
	}

	@Override
	public CompoundNBT writeToNBT(final CompoundNBT compound)
	{
		super.writeToNBT(compound);

		final ListNBT nbttaglist = new ListNBT();

		for (int i = 0; i < this.contents.size(); ++i)
		{
			if (!this.contents.get(i).isEmpty())
			{
				final CompoundNBT nbttagcompound = new CompoundNBT();
				nbttagcompound.putByte("Slot", (byte) i);

				this.contents.get(i).writeToNBT(nbttagcompound);

				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.put("Items", nbttaglist);

		compound.putBoolean("markedForGeneration", this.isMarkedForGeneration());

		return compound;
	}

	@Override
	public void readFromNBT(final CompoundNBT compound)
	{
		super.readFromNBT(compound);

		final ListNBT nbttaglist = compound.getTagList("Items", 10);
		this.contents = NonNullList.withSize(27, ItemStack.EMPTY);

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			final CompoundNBT nbttagcompound = nbttaglist.getCompoundAt(i);
			final int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < this.contents.size())
			{
				this.contents.set(j, new ItemStack(nbttagcompound));
			}
		}

		this.isMarkedForGeneration = compound.getBoolean("markedForGeneration");
	}

	/**
	 * Do not use this tile entity's world object for accessing blocks. Use the
	 * provided block access object.
	 * @param blockAccess
	 */
	public abstract void onSchematicGeneration(IBlockAccess blockAccess, Random rand);

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
	public Container createContainer(final PlayerInventory playerInventory, final PlayerEntity playerIn)
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
	public ItemStack getStackInSlot(final int index)
	{
		return this.contents.get(index);
	}

	@Override
	public ItemStack decrStackSize(final int index, final int count)
	{
		final ItemStack itemstack = ItemStackHelper.getAndSplit(this.contents, index, count);

		if (!itemstack.isEmpty())
		{
			this.markDirty();
		}

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(final int index)
	{
		return ItemStackHelper.getAndRemove(this.contents, index);
	}

	@Override
	public void setInventorySlotContents(final int index, final ItemStack stack)
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
	public boolean isUsableByPlayer(final PlayerEntity player)
	{
		return this.world.getTileEntity(this.pos) == this && (
				player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D)
						<= 64.0D && player.isCreative());
	}

	@Override
	public void openInventory(final PlayerEntity player)
	{

	}

	@Override
	public void closeInventory(final PlayerEntity player)
	{

	}

	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack)
	{
		return stack.getItem() instanceof BlockItem;
	}

	@Override
	public int getField(final int id)
	{
		return 0;
	}

	@Override
	public void setField(final int id, final int value)
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
		final BlockState state = this.world.getBlockState(this.pos);

		this.world.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		final CompoundNBT tag = super.getUpdateTag();

		this.writeToNBT(tag);

		return tag;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		final CompoundNBT compound = this.getUpdateTag();

		return new SPacketUpdateTileEntity(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(final NetworkManager networkManager, final SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}

	@Override
	public boolean isEmpty()
	{
		for (final ItemStack itemstack : this.contents)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

}
