package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class SlotAmbrosiumChunk extends Slot
{

	private static TextureAtlasSprite sprite;

	public SlotAmbrosiumChunk(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@SideOnly(Side.CLIENT)
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
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return sprite;
	}

}
