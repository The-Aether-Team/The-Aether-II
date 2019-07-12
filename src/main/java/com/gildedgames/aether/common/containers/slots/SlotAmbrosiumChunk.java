package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;

import javax.annotation.Nonnull;

public class SlotAmbrosiumChunk extends Slot
{

	private static TextureAtlasSprite sprite;

	public SlotAmbrosiumChunk(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		sprite = event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_moa_egg"));
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return stack.getItem() == ItemsAether.ambrosium_chunk;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return sprite;
	}

}
