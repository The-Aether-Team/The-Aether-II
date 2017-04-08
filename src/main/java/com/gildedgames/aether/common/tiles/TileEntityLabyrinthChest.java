package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.api.loot.LootGenerator;
import com.gildedgames.aether.common.blocks.containers.BlockLabyrinthChest;
import com.gildedgames.aether.common.registry.LootDefinitions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;

public class TileEntityLabyrinthChest extends TileEntityLockable implements net.minecraft.util.ITickable, IInventory
{

	final private int CHEST_SIZE = 27;

	private NonNullList<ItemStack> chestContents = NonNullList.withSize(this.CHEST_SIZE, ItemStack.EMPTY);

	public float lidAngle;

	public float prevLidAngle;

	public int numPlayersUsing;

	private int ticksSinceSync;

	private String customName;

	private boolean isMimic, generateLoot, hasGeneratedLoot;

	public TileEntityLabyrinthChest()
	{

	}

	public void setIsMimic(boolean flag)
	{
		this.isMimic = flag;
	}

	public boolean isMimic()
	{
		return this.isMimic;
	}

	public boolean generatesLoot()
	{
		return this.generateLoot;
	}

	public void setGenerateLoot(boolean flag)
	{
		this.generateLoot = flag;
		this.hasGeneratedLoot = false;
	}

	public void generateLootInside()
	{
		if (!this.generateLoot)
		{
			return;
		}

		if (!this.hasGeneratedLoot)
		{
			this.isMimic = this.world.rand.nextInt(5) == 0;

			if (this.isMimic)
			{
				this.world.setBlockToAir(this.pos);

				return;
			}

			int commonCount = 2 + this.world.rand.nextInt(3);

			for (int i = 0; i < commonCount; i++)
			{
				int slotID = this.world.rand.nextInt(this.getSizeInventory());

				while (this.getStackInSlot(slotID) != ItemStack.EMPTY)
				{
					slotID = this.world.rand.nextInt(this.getSizeInventory());
				}

				ItemStack stack = LootGenerator.generate(LootDefinitions.LABYRINTH_TRASH, this.world.rand);

				this.setInventorySlotContentsWithoutMarking(slotID, stack);
			}

			this.markDirty();

			this.hasGeneratedLoot = true;
		}
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	@Override
	public void update()
	{
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		++this.ticksSinceSync;

		if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0)
		{
			this.numPlayersUsing = 0;
			float f = 5.0F;

			for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double) ((float) i
					- f), (double) ((float) j - f), (double) ((float) k - f), (double) ((float) (i + 1) + f), (double) ((float) (j + 1)
					+ f), (double) ((float) (k + 1) + f))))
			{
				if (entityplayer.openContainer instanceof ContainerChest)
				{
					IInventory iinventory = ((ContainerChest) entityplayer.openContainer).getLowerChestInventory();

					if (iinventory == this)
					{
						++this.numPlayersUsing;
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		float f1 = 0.1F;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
		{
			double d1 = (double) i + 0.5D;
			double d2 = (double) k + 0.5D;

			this.world.playSound(d1,
					(double) j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F,
					this.world.rand.nextFloat() * 0.1F + 0.9F, false);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
		{
			float f2 = this.lidAngle;

			if (this.numPlayersUsing > 0)
			{
				this.lidAngle += f1;
			}
			else
			{
				this.lidAngle -= f1;
			}

			if (this.lidAngle > 1.0F)
			{
				this.lidAngle = 1.0F;
			}

			float f3 = 0.5F;

			if (this.lidAngle < f3 && f2 >= f3)
			{
				double d3 = (double) i + 0.5D;
				double d0 = (double) k + 0.5D;

				this.world.playSound(d3,
						(double) j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F,
						this.world.rand.nextFloat() * 0.1F + 0.9F, false);
			}

			if (this.lidAngle < 0.0F)
			{
				this.lidAngle = 0.0F;
			}
		}

		if (this.generatesLoot())
		{
			for (int u = 0; u < 2; ++u)
			{
				double motionX = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat();
				double motionY = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat();
				double motionZ = (this.world.rand.nextBoolean() ? 1.0D : -1.0D) * this.world.rand.nextFloat();

				this.world.spawnParticle(EnumParticleTypes.SPELL_MOB,
						this.getPos().getX() + motionX,
						this.getPos().getY() + 0.5D + motionY, this.getPos().getZ() + motionZ, 0.1D, 0.1D, 0.1D);
			}
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID()
	{
		return "aether:labyrinth_chest";
	}

	@Override
	public int getSizeInventory()
	{
		return this.CHEST_SIZE;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.chestContents)
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
		return this.chestContents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.chestContents, index, count);

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
		return ItemStackHelper.getAndRemove(this.chestContents, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.chestContents.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	public void setInventorySlotContentsWithoutMarking(int index, ItemStack stack)
	{
		this.chestContents.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
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
		return this.world.getTileEntity(this.pos) == this &&
				player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (this.numPlayersUsing < 0)
			{
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;

			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
		}
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator() && this.getBlockType() instanceof BlockLabyrinthChest)
		{
			--this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
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
		this.chestContents.clear();
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.labyrinth_chest";
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

		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);

			int j = nbttagcompound.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.size())
			{
				this.chestContents.set(j, new ItemStack(nbttagcompound));
			}
		}

		this.isMimic = compound.getBoolean("isMimic");
		this.hasGeneratedLoot = compound.getBoolean("hasGeneratedLoot");
		this.generateLoot = compound.getBoolean("generateLoot");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.size(); ++i)
		{
			if (this.chestContents.get(i) != ItemStack.EMPTY)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);

				this.chestContents.get(i).writeToNBT(nbttagcompound);

				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}

		compound.setBoolean("isMimic", this.isMimic);
		compound.setBoolean("hasGeneratedLoot", this.hasGeneratedLoot);
		compound.setBoolean("generateLoot", this.generateLoot);

		return compound;
	}

}
