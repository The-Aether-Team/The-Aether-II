package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.AccessoryType;
import com.gildedgames.aether.common.items.ItemAccessory;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

public class SlotAccessory extends Slot
{
	@SideOnly(Side.CLIENT)
	private static HashMap<AccessoryType, TextureAtlasSprite> icons;

	private final AccessoryType type;

	public SlotAccessory(IInventory inventory, AccessoryType type, int index, int xPosition, int yPosition)
	{
		super(inventory, index, xPosition, yPosition);

		this.type = type;
	}

	@SideOnly(Side.CLIENT)
	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		icons = new HashMap<>();

		icons.put(AccessoryType.COMPANION, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_companion")));
		icons.put(AccessoryType.GLOVE, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_glove")));
		icons.put(AccessoryType.MISC, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_misc")));
		icons.put(AccessoryType.NECKWEAR, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_neckwear")));
		icons.put(AccessoryType.RING, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_ring")));
		icons.put(AccessoryType.SHIELD, event.map.registerSprite(AetherCore.getResource("gui/slots/slot_shield")));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return icons.get(this.getType());
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (stack == null)
		{
			return true;
		}

		if (stack.getItem() instanceof ItemAccessory)
		{
			ItemAccessory accessory = (ItemAccessory) stack.getItem();

			return accessory.getType() == this.getType();
		}

		return false;
	}

	public AccessoryType getType()
	{
		return this.type;
	}
}
