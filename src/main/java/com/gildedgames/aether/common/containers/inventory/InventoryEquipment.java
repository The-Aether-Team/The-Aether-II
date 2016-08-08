package com.gildedgames.aether.common.containers.inventory;

import com.gildedgames.aether.api.items.properties.ItemEquipmentType;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.HashSet;
import java.util.Set;

public class InventoryEquipment implements IInventoryEquipment
{
	private static final int INVENTORY_SIZE = 14;

	public static final ItemEquipmentType[] slotTypes = new ItemEquipmentType[]
			{
					ItemEquipmentType.RELIC,
					ItemEquipmentType.RELIC,
					ItemEquipmentType.HANDWEAR,
					ItemEquipmentType.RING,
					ItemEquipmentType.RING,
					ItemEquipmentType.NECKWEAR,
					ItemEquipmentType.COMPANION,
					ItemEquipmentType.ARTIFACT,
					ItemEquipmentType.CHARM,
					ItemEquipmentType.CHARM,
					ItemEquipmentType.CHARM,
					ItemEquipmentType.CHARM,
					ItemEquipmentType.CHARM,
					ItemEquipmentType.CHARM
			};

	private final IPlayerAetherCapability aePlayer;

	private ItemStack[] inventory = new ItemStack[InventoryEquipment.INVENTORY_SIZE];

	private Set<PendingItemChange> dirties = new HashSet<>();

	public InventoryEquipment(IPlayerAetherCapability aePlayer)
	{
		this.aePlayer = aePlayer;
	}

	public ItemStack[] getInventory()
	{
		return this.inventory;
	}

	@Override
	public int getSizeInventory()
	{
		return InventoryEquipment.INVENTORY_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return index >= 0 && index < this.inventory.length ? this.inventory[index] : null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.inventory[index] != null)
		{
			ItemStack stack;

			if (this.inventory[index].stackSize <= count)
			{
				stack = this.inventory[index];

				this.inventory[index] = null;
			}
			else
			{
				stack = this.inventory[index].splitStack(count);

				if (this.inventory[index].stackSize == 0)
				{
					this.inventory[index] = null;
				}
			}

			this.dirties.add(new PendingItemChange(index, stack, this.inventory[index]));

			this.markDirty();

			return stack;
		}

		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.inventory[index] != null)
		{
			ItemStack stack = this.inventory[index];

			this.setInventorySlotContents(index, null);

			return stack;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.dirties.add(new PendingItemChange(index, this.inventory[index], stack));

		this.inventory[index] = stack;

		this.markDirty();
	}

	@Override
	public int getNextEmptySlotForType(ItemEquipmentType type)
	{
		for (int i = 0; i < this.inventory.length; i++)
		{
			if (this.inventory[i] == null && slotTypes[i] == type)
			{
				return i;
			}
		}

		return -1;
	}

	public boolean isItemEquipped(Item item)
	{
		return this.getCountOfEquippedAccessory(item) > 0;
	}

	public int getCountOfEquippedAccessory(Item item)
	{
		int count = 0;

		for (ItemStack stack : this.inventory)
		{
			if (stack != null && stack.getItem() == item)
			{
				count++;
			}
		}

		return count;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return !this.aePlayer.getPlayer().isDead && player.getDistanceSqToEntity(this.aePlayer.getPlayer()) <= 64.0D;
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
		for (int i = 0; i < this.inventory.length; i++)
		{
			this.setInventorySlotContents(i, null);
		}
	}

	public void dropAllItems()
	{
		for (ItemStack stack : this.inventory)
		{
			if (stack != null)
			{
				this.aePlayer.getPlayer().dropItem(stack, true, false);
			}
		}

		this.clear();
	}

	public Set<PendingItemChange> getDirties()
	{
		return this.dirties;
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
	public void write(NBTTagCompound output)
	{
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i)
		{
			ItemStack stack = this.inventory[i];

			if (stack != null)
			{
				NBTTagCompound stackCompound = new NBTTagCompound();
				stackCompound.setByte("Slot", (byte) i);

				stack.writeToNBT(stackCompound);

				list.appendTag(stackCompound);
			}
		}

		output.setTag("items", list);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTTagList list = input.getTagList("items", 10);

		if (list != null)
		{
			for (int i = 0; i < list.tagCount(); i++)
			{
				NBTTagCompound compound = list.getCompoundTagAt(i);

				ItemStack stack = ItemStack.loadItemStackFromNBT(compound);

				if (stack != null)
				{
					int slot = compound.getByte("Slot") & 255;

					this.inventory[slot] = stack;
				}
			}
		}
	}

	public static class PendingItemChange
	{
		private final int slot;

		private final ItemStack before, after;

		public PendingItemChange(int slot, ItemStack before, ItemStack after)
		{
			this.slot = slot;
			this.before = before;
			this.after = after;
		}

		public int getSlot()
		{
			return this.slot;
		}

		public ItemStack getBefore()
		{
			return this.before;
		}

		public ItemStack getAfter()
		{
			return this.after;
		}
	}
}
