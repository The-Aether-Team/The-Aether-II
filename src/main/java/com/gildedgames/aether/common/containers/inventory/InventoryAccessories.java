package com.gildedgames.aether.common.containers.inventory;

import com.gildedgames.aether.common.containers.slots.SlotAccessory;
import com.gildedgames.aether.common.items.accessories.AccessoryType;
import com.gildedgames.aether.common.items.accessories.ItemAccessory;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.core.nbt.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryAccessories implements IInventory, NBT
{
	private static final int INVENTORY_SIZE = 8;

	public static final AccessoryType[] slotTypes = new AccessoryType[]
			{
					AccessoryType.NECKWEAR,
					AccessoryType.COMPANION,
					AccessoryType.SHIELD,
					AccessoryType.MISC,

					AccessoryType.RING,
					AccessoryType.RING,
					AccessoryType.GLOVE,
					AccessoryType.MISC
			};

	private final PlayerAether aePlayer;

	private ItemStack[] inventory = new ItemStack[InventoryAccessories.INVENTORY_SIZE];

	private boolean isDirty;

	public InventoryAccessories(PlayerAether aePlayer)
	{
		this.aePlayer = aePlayer;
	}

	@Override
	public int getSizeInventory()
	{
		return InventoryAccessories.INVENTORY_SIZE;
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

			this.inventory[index] = null;

			return stack;
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.inventory[index] = stack;

		if (stack != null && stack.getItem() instanceof ItemAccessory)
		{
			((ItemAccessory) stack.getItem()).onAccessoryEquipped(this.aePlayer, stack);
		}

		this.markDirty();
	}

	public int getNextEmptySlotForType(AccessoryType type)
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

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void markDirty()
	{
		this.isDirty = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return !this.aePlayer.getPlayer().isDead && player.getDistanceSqToEntity(this.aePlayer.getPlayer()) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) { }

	@Override
	public void closeInventory(EntityPlayer player) { }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return false;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) { }

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
			this.inventory[i] = null;
		}
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
	public IChatComponent getDisplayName()
	{
		return new ChatComponentTranslation(this.getName());
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

		output.setTag("accessoriesInventory", list);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTTagList list = input.getTagList("accessoriesInventory", 10);

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
}
