package com.gildedgames.aether.common.containers.slots;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntityIncubator;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class SlotMoaEgg extends Slot
{

	private static TextureAtlasSprite sprite;

	public SlotMoaEgg(IInventory inventoryIn, int index, int xPosition, int yPosition)
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
		if (this.inventory instanceof TileEntityIncubator)
		{
			if (stack.getItem() == ItemsAether.moa_egg || stack.getItem() == ItemsAether.rainbow_moa_egg)
			{
				// Check if incubator is ready before allowing transfer
				if (this.inventory.getField(0) > TileEntityIncubator.REQ_TEMPERATURE_THRESHOLD - 500)
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getBackgroundSprite()
	{
		return sprite;
	}

}
