package com.gildedgames.aether.common.containers.slots;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.common.AetherCapabilities;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.ItemEquipmentType;
import com.gildedgames.aether.common.items.ItemPropertiesBase;

public class SlotEquipment extends Slot
{
	@SideOnly(Side.CLIENT)
	private static HashMap<ItemEquipmentType, TextureAtlasSprite> icons;

	private final ItemEquipmentType type;

	public SlotEquipment(IInventory inventory, ItemEquipmentType type, int index, int xPosition, int yPosition)
	{
		super(inventory, index, xPosition, yPosition);

		this.type = type;
	}

	@SideOnly(Side.CLIENT)
	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		icons = new HashMap<>();

		icons.put(ItemEquipmentType.COMPANION, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_companion")));
		icons.put(ItemEquipmentType.HANDWEAR, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_handwear")));
		icons.put(ItemEquipmentType.RELIC, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_relic")));
		icons.put(ItemEquipmentType.NECKWEAR, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_neckwear")));
		icons.put(ItemEquipmentType.RING, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_ring")));
		icons.put(ItemEquipmentType.SHIELD, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_shield")));
		icons.put(ItemEquipmentType.CHARM, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_charm")));
		icons.put(ItemEquipmentType.ARTIFACT, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_artifact")));
		icons.put(ItemEquipmentType.AMMUNITION, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_ammunition")));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return icons.get(this.getEquipmentType());
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (stack == null)
		{
			return true;
		}

		if (stack.hasCapability(AetherCapabilities.ITEM_PROPERTIES, null))
		{
			ItemPropertiesBase props = stack.getCapability(AetherCapabilities.ITEM_PROPERTIES, null);
			
			if (props.isEquippable())
			{
				return props.getEquipmentType() == this.getEquipmentType();
			}
		}

		return false;
	}

	public ItemEquipmentType getEquipmentType()
	{
		return this.type;
	}
}
