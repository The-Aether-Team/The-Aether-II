package com.gildedgames.aether.client.gui.container.crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

public class ItemStackButton extends Gui
{
	protected final Minecraft mc;

	protected final RenderItem itemRenderer;

	public final int id;

	public final int x, y;

	public final int width = 16, height = 16;

	public final ItemStack stack;

	public ItemStackButton(int id, ItemStack stack, int x, int y)
	{
		this.id = id;

		this.mc = Minecraft.getMinecraft();
		this.itemRenderer = this.mc.getRenderItem();

		this.x = x;
		this.y = y;

		this.stack = stack;
	}

	public void drawButton(int mouseX, int mouseY, boolean hovered, boolean selected)
	{
		if (hovered || selected)
		{
			if (hovered)
			{
				drawRect(this.x, this.y, this.x + 16, this.y + 16, selected ? 0x9FFFFFBF : 0x7FFFFFFF);
			}
			else
			{
				drawRect(this.x, this.y, this.x + 16, this.y + 16, 0x7FFFFF7F);
			}
		}

		this.renderItem(mouseX, mouseY, hovered, selected);
	}

	protected void renderItem(int mouseX, int mouseY, boolean hovered, boolean selected)
	{
		this.itemRenderer.zLevel = -10.0F;

		this.itemRenderer.renderItemAndEffectIntoGUI(this.mc.player, this.stack, this.x, this.y);

		this.itemRenderer.zLevel = 0.0F;
	}

	public void performClick(int mouseX, int mouseY)
	{
		this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}
}
