package com.gildedgames.aether.client.gui.container.crafting;

import com.gildedgames.aether.client.gui.container.GuiSkyrootWorkbench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GhostItemStackButton extends ItemStackButton
{
	protected final GuiSkyrootWorkbench container;

	public final Slot slot;

	public GhostItemStackButton(GuiSkyrootWorkbench container, int id, Slot slot, ItemStack stack, int x, int y)
	{
		super(id, stack, x, y);

		this.container = container;
		this.slot = slot;
	}

	@Override
	protected void renderItem(int mouseX, int mouseY, boolean hovered, boolean selected)
	{
		if (!this.slot.getHasStack() && !this.container.isDragging(this.slot))
		{
			this.itemRenderer.renderItemAndEffectIntoGUI(this.mc.player, this.stack, this.x, this.y);

			this.zLevel = 1000.0f;

			this.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, 0x668b8b8b, 0xbb8b8b8b);

			this.zLevel = 0.0f;
		}
	}
}
