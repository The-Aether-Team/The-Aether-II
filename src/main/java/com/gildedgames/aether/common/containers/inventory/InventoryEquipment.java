package com.gildedgames.aether.common.containers.inventory;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class InventoryEquipment implements IInventoryEquipment
{
	private static final ItemEquipmentSlot[] SLOT_TYPES = new ItemEquipmentSlot[]
			{
					ItemEquipmentSlot.RELIC,
					ItemEquipmentSlot.RELIC,
					ItemEquipmentSlot.HANDWEAR,
					ItemEquipmentSlot.RING,
					ItemEquipmentSlot.RING,
					ItemEquipmentSlot.NECKWEAR,
					ItemEquipmentSlot.COMPANION,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM
			};

	private final IPlayerAether aePlayer;

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(14, ItemStack.EMPTY);

	public InventoryEquipment(final IPlayerAether aePlayer)
	{
		this.aePlayer = aePlayer;
	}

	@Override
	public int getSizeInventory()
	{
		return this.inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (final ItemStack itemstack : this.inventory)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(final int index)
	{
		return index >= 0 && index < this.inventory.size() ? this.inventory.get(index) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(final int index, final int count)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public ItemStack removeStackFromSlot(final int index)
	{
		final ItemStack itemstack = this.inventory.get(index);

		if (itemstack.isEmpty())
		{
			return ItemStack.EMPTY;
		}
		else
		{
			this.inventory.set(index, ItemStack.EMPTY);

			return itemstack;
		}
	}

	@Override
	public void setInventorySlotContents(final int index, final ItemStack stack)
	{
		this.inventory.set(index, stack);

		this.markDirty();
	}

	@Override
	public int getNextEmptySlotForType(final ItemEquipmentSlot type)
	{
		for (int i = 0; i < this.inventory.size(); i++)
		{
			if (this.inventory.get(i).isEmpty() && SLOT_TYPES[i] == type)
			{
				return i;
			}
		}

		return -1;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer player)
	{
		return !this.aePlayer.getEntity().isDead && player.getDistanceSq(this.aePlayer.getEntity()) <= 64.0D;
	}

	@Override
	public void openInventory(final EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(@Nonnull final EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(final int index, @Nonnull final ItemStack stack)
	{
		final ItemEquipmentSlot slot = AetherAPI.content().items().getProperties(stack.getItem()).getEquipmentSlot();

		return slot == SLOT_TYPES[index];
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
		this.inventory.clear();
	}

	@Override
	public String getName()
	{
		return "inventory.accessories";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public TextComponentBase getDisplayName()
	{
		return new TextComponentTranslation(this.getName());
	}

	@Override
	public void write(final NBTTagCompound output)
	{
		final NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.inventory.size(); ++i)
		{
			final ItemStack stack = this.inventory.get(i);

			if (!stack.isEmpty())
			{
				final NBTTagCompound stackCompound = new NBTTagCompound();
				stackCompound.setByte("Slot", (byte) i);

				stack.writeToNBT(stackCompound);

				list.appendTag(stackCompound);
			}
		}

		output.setTag("Items", list);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final NBTTagList list = input.getTagList("Items", 10);

		for (int i = 0; i < list.tagCount(); i++)
		{
			final NBTTagCompound compound = list.getCompoundTagAt(i);

			final int slot = compound.getByte("Slot") & 255;

			this.inventory.set(slot, new ItemStack(compound));
		}
	}

}
