package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class SlotFlintAndSteel extends Slot
{

	private static TextureAtlasSprite sprite;

	public SlotFlintAndSteel(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		sprite = event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_flint_and_steel"));
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return this.inventory.isItemValidForSlot(this.getSlotIndex(), stack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return sprite;
	}

}
