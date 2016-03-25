package com.gildedgames.aether.common.containers.inventory;

import com.gildedgames.aether.common.AetherCapabilities;
import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.ItemEffectsBase;
import com.gildedgames.aether.common.items.ItemEquipmentType;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.core.nbt.NBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.tuple.Pair;

public class InventoryEquipment implements IInventory, NBT
{
	private static final int INVENTORY_SIZE = 8;

	public static final ItemEquipmentType[] slotTypes = new ItemEquipmentType[]
			{
					ItemEquipmentType.RELIC,
					ItemEquipmentType.RELIC,
					ItemEquipmentType.HANDWEAR,
					//ItemEquipmentType.SHIELD,
					ItemEquipmentType.RING,
					ItemEquipmentType.RING,
					ItemEquipmentType.NECKWEAR,
					ItemEquipmentType.AMMUNITION,
					ItemEquipmentType.COMPANION,
			/*ItemEquipmentType.ARTIFACT,
			ItemEquipmentType.CHARM,
			ItemEquipmentType.CHARM,
			ItemEquipmentType.CHARM,
			ItemEquipmentType.CHARM,
			ItemEquipmentType.CHARM,
			ItemEquipmentType.CHARM*/
			};

	private final PlayerAether aePlayer;

	private ItemStack[] inventory = new ItemStack[InventoryEquipment.INVENTORY_SIZE];

	public InventoryEquipment(PlayerAether aePlayer)
	{
		this.aePlayer = aePlayer;
	}

	public ItemStack[] getInventory()
	{
		return inventory;
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

<<<<<<< 5c341e7ca8244f2964aa87c05d0574967460672c
		if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
		{
			EntityEffects effects = EntityEffects.get(this.aePlayer.getEntity());
			ItemEffectsBase itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

			if (itemEffects != null)
			{
				for (Pair<EffectProcessor, EffectInstance> effect : itemEffects.getEffectPairs())
				{
					EffectProcessor processor = effect.getLeft();
					EffectInstance instance = effect.getRight();

					effects.put(processor, instance);
				}
			}
		}

=======
>>>>>>> d5f20224b326f2f2568ee73a4e79dfc92466b165
		this.markDirty();
	}

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
		return 999;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return !this.aePlayer.getEntity().isDead && player.getDistanceSqToEntity(this.aePlayer.getEntity()) <= 64.0D;
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
			ItemStack stack = this.inventory[i];

			if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
			{
				EntityEffects effects = EntityEffects.get(this.aePlayer.getEntity());
				ItemEffectsBase itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

				if (itemEffects != null)
				{
					for (Pair<EffectProcessor, EffectInstance> effect : itemEffects.getEffectPairs())
					{
						EffectProcessor processor = effect.getLeft();
						EffectInstance instance = effect.getRight();

						effects.removeInstance(processor, instance);
					}
				}
			}

			this.inventory[i] = null;
		}
	}

	public void dropAllItems()
	{
		for (int i = 0; i < this.inventory.length; ++i)
		{
			if (this.inventory[i] != null)
			{
				ItemStack stack = this.inventory[i];

				if (stack != null && stack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
				{
					EntityEffects effects = EntityEffects.get(this.aePlayer.getEntity());
					ItemEffectsBase itemEffects = stack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

					if (itemEffects != null)
					{
						for (Pair<EffectProcessor, EffectInstance> effect : itemEffects.getEffectPairs())
						{
							EffectProcessor processor = effect.getLeft();
							EffectInstance instance = effect.getRight();

							effects.removeInstance(processor, instance);
						}
					}
				}

				this.aePlayer.getEntity().dropItem(stack, true, false);
				this.inventory[i] = null;
			}
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
