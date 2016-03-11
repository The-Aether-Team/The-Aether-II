package com.gildedgames.aether.common.containers;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.inventory.InventoryAccessories;
import com.gildedgames.aether.common.containers.slots.SlotAccessory;
import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.entities.effects.ItemEffects;
import com.gildedgames.aether.common.items.AccessoryType;
import com.gildedgames.aether.common.items.ItemAccessory;
import com.gildedgames.aether.common.player.PlayerAether;

public class ContainerAccessories extends ContainerPlayer
{
	/** See {@link GuiContainerCreative#field_147060_v} **/
	private static InventoryBasic dumbInventory = new InventoryBasic("fake", true, 46);

	private final PlayerAether aePlayer;

	private final InventoryAccessories inventoryAccessories;

	private Slot binSlot;

	public ContainerAccessories(PlayerAether aePlayer)
	{
		super(aePlayer.getEntity().inventory, false, aePlayer.getEntity());

		this.aePlayer = aePlayer;
		this.inventoryAccessories = aePlayer.getInventoryAccessories();

		this.createSlots();
	}

	private void createSlots()
	{
		int widthOffset = 180;
		int heightOffset = -10;

		for (Slot slot : this.inventorySlots)
		{
			slot.xDisplayPosition += widthOffset;
			slot.yDisplayPosition += heightOffset;
		}
		
		Slot helmet = this.inventorySlots.get(5);
		Slot chestplate = this.inventorySlots.get(6);
		Slot leggings = this.inventorySlots.get(7);
		Slot boots = this.inventorySlots.get(8);

		helmet.xDisplayPosition = 37;
		helmet.yDisplayPosition = 7;
		
		chestplate.xDisplayPosition = 37;
		chestplate.yDisplayPosition = 28;
		
		leggings.xDisplayPosition = 37;
		leggings.yDisplayPosition = 49;
		
		boots.xDisplayPosition = 37;
		boots.yDisplayPosition = 70;
		
		this.binSlot = new Slot(ContainerAccessories.dumbInventory, this.inventorySlots.size(), 228, 25);

		if (this.aePlayer.getEntity().capabilities.isCreativeMode)
		{
			this.addSlotToContainer(this.binSlot);
		}
		
		int slotID = 0;
		
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.RELIC, slotID++, 12, 7));
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.RELIC, slotID++, 62, 7));
		
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.HANDWEAR, slotID++, 62, 28));
		
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.SHIELD, slotID++, 62, 49));

		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.RING, slotID++, 12, 49));
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.RING, slotID++, 12, 70));
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.NECKWEAR, slotID++, 12, 28));
		
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.AMMUNITION, slotID++, 62, 70));

		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.COMPANION, slotID++, 19, 101));
		this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.ARTIFACT, slotID++, 55, 101));
		
		for (int x = 0; x < 6; x++)
		{
			int x1 = 35 + (x * 18);
			int y1 = 132;

			this.addSlotToContainer(new SlotAccessory(this.inventoryAccessories, AccessoryType.CHARM, slotID, x1, y1));
			slotID++;
		}
	}

	@Override
	public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player)
	{
		if (slotId == this.binSlot.slotNumber && player.capabilities.isCreativeMode)
		{
			this.aePlayer.getEntity().inventory.setItemStack(null);
		}
		
		if (slotId < this.inventorySlots.size() && slotId > 0)
		{
			Slot slot = this.inventorySlots.get(slotId);
			
			if (slot != null && slot.getHasStack())
			{
				ItemStack stack = slot.getStack();
				
				if (slot instanceof SlotAccessory && stack.hasCapability(AetherCore.ITEM_EFFECTS_CAPABILITY, null))
				{
					EntityEffects effects = EntityEffects.get(this.aePlayer.getEntity());
					ItemEffects itemEffects = stack.getCapability(AetherCore.ITEM_EFFECTS_CAPABILITY, null);

					for (Pair<EffectProcessor, EffectInstance> effect : itemEffects.getEffectPairs())
					{
						EffectProcessor processor = effect.getLeft();
						EffectInstance instance = effect.getRight();
						
						effects.removeInstance(processor, instance);
					}
				}
			}
		}

		return super.slotClick(slotId, clickedButton, mode, player);
	}

	private int getNextEmptySlot(AccessoryType type)
	{
		for (int i = 0; i < this.inventorySlots.size(); i++)
		{
			Slot slot = this.inventorySlots.get(i);

			if (slot.getStack() == null && slot instanceof SlotAccessory)
			{
				if (((SlotAccessory) slot).getType() == type)
				{
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		Slot slot = this.inventorySlots.get(slotNumber);

		if (slotNumber == this.binSlot.slotNumber && this.aePlayer.getEntity().capabilities.isCreativeMode)
		{
			this.aePlayer.getEntity().inventory.clear();
			this.aePlayer.getInventoryAccessories().clear();
		}

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();

			if (!(slot instanceof SlotAccessory) && !(slot instanceof SlotCrafting))
			{
				int destIndex = -1;

				if (stack.getItem() instanceof ItemAccessory)
				{
					ItemAccessory accessory = (ItemAccessory) stack.getItem();

					destIndex = this.getNextEmptySlot(accessory.getType());
				}

				if (destIndex != -1)
				{
					Slot accessorySlot = this.inventorySlots.get(destIndex);
					accessorySlot.putStack(stack);

					slot.putStack(null);

					return stack;
				}
			}
			else if (slot instanceof SlotAccessory && stack.hasCapability(AetherCore.ITEM_EFFECTS_CAPABILITY, null))
			{
				EntityEffects effects = EntityEffects.get(this.aePlayer.getEntity());
				ItemEffects itemEffects = stack.getCapability(AetherCore.ITEM_EFFECTS_CAPABILITY, null);

				for (Pair<EffectProcessor, EffectInstance> effect : itemEffects.getEffectPairs())
				{
					EffectProcessor processor = effect.getLeft();
					EffectInstance instance = effect.getRight();
					
					effects.removeInstance(processor, instance);
				}
			}
		}

		return super.transferStackInSlot(player, slotNumber);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}
