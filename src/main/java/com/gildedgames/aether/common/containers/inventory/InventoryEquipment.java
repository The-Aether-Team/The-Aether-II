package com.gildedgames.aether.common.containers.inventory;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.items.IItemProperties;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
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
					ItemEquipmentSlot.ARTIFACT,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM,
					ItemEquipmentSlot.CHARM
			};

	private final IPlayerAether aePlayer;

	private NonNullList<ItemStack> inventory = NonNullList.withSize(14, ItemStack.EMPTY);

	public InventoryEquipment(IPlayerAether aePlayer)
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
	public ItemStack getStackInSlot(int index)
	{
		return index >= 0 && index < this.inventory.size() ? this.inventory.get(index) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack itemstack = this.inventory.get(index);

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
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.inventory.set(index, stack);

		this.markDirty();
	}

	@Override
	public int getNextEmptySlotForType(ItemEquipmentSlot type)
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
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return !this.aePlayer.getEntity().isDead && player.getDistanceSqToEntity(this.aePlayer.getEntity()) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(@Nonnull EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack)
	{
		ItemEquipmentSlot slot = AetherAPI.content().items().getProperties(stack.getItem()).getEquipmentSlot();

		return slot == SLOT_TYPES[index];
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
	public void write(NBTTagCompound output)
	{
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.inventory.size(); ++i)
		{
			ItemStack stack = this.inventory.get(i);

			if (!stack.isEmpty())
			{
				NBTTagCompound stackCompound = new NBTTagCompound();
				stackCompound.setByte("Slot", (byte) i);

				stack.writeToNBT(stackCompound);

				list.appendTag(stackCompound);
			}
		}

		output.setTag("Items", list);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTTagList list = input.getTagList("Items", 10);

		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound compound = list.getCompoundTagAt(i);

			int slot = compound.getByte("Slot") & 255;

			this.inventory.set(slot, new ItemStack(compound));
		}
	}

}
