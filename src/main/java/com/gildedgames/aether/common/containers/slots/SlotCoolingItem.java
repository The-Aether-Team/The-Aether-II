package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotCoolingItem extends SlotOffset
{

	private static TextureAtlasSprite sprite;

	public SlotCoolingItem(IInventory inventoryIn, int index, int xPosition, int yPosition, int trueIndex)
	{
		super(inventoryIn, index, xPosition, yPosition, trueIndex);
	}

	@SideOnly(Side.CLIENT)
	public static void registerIcons(TextureStitchEvent.Pre event)
	{
		sprite = event.getMap().registerSprite(AetherCore.getResource("gui/slots/slot_icestone"));
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return super.isItemValid(stack) && stack.getItem() == ItemsAether.icestone;
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 64;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return sprite;
	}
}
