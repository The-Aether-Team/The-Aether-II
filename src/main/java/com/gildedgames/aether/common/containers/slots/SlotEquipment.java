package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

public class SlotEquipment extends Slot
{
	@SideOnly(Side.CLIENT)
	private static HashMap<ItemEquipmentSlot, TextureAtlasSprite> icons;

	private final ItemEquipmentSlot type;

	public SlotEquipment(IInventory inventory, ItemEquipmentSlot type, int index, int xPosition, int yPosition)
	{
		super(inventory, index, xPosition, yPosition);

		this.type = type;
	}

	@SideOnly(Side.CLIENT)
	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		icons = new HashMap<>();

		icons.put(ItemEquipmentSlot.COMPANION, event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_companion")));
		icons.put(ItemEquipmentSlot.HANDWEAR, event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_handwear")));
		icons.put(ItemEquipmentSlot.RELIC, event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_relic")));
		icons.put(ItemEquipmentSlot.NECKWEAR, event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_neckwear")));
		icons.put(ItemEquipmentSlot.RING, event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_ring")));
		icons.put(ItemEquipmentSlot.CHARM, event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_charm")));
		icons.put(ItemEquipmentSlot.ARTIFACT, event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_artifact")));
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
		return this.inventory.isItemValidForSlot(this.getSlotIndex(), stack);
	}

	public ItemEquipmentSlot getEquipmentType()
	{
		return this.type;
	}
}
