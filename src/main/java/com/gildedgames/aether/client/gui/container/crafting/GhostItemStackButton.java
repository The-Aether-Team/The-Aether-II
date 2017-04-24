package com.gildedgames.aether.client.gui.container.crafting;

import com.gildedgames.aether.client.gui.container.GuiSkyrootWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GhostItemStackButton extends ItemStackButton
{
	protected final GuiSkyrootWorkbench container;

	public final Slot slot;

	public GhostItemStackButton(GuiSkyrootWorkbench container, int id, Slot slot, List<ItemStack> stacks, int x, int y)
	{
		super(id, stacks, x, y);

		this.container = container;
		this.slot = slot;
	}

	@Override
	protected void renderItem(int mouseX, int mouseY, boolean hovered, boolean selected)
	{
		if (!this.slot.getHasStack() && !this.container.isDragging(this.slot))
		{
			super.renderItem(mouseX, mouseY, hovered, selected);

			this.zLevel = 1000.0f;

			this.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, 0x668b8b8b, 0xbb8b8b8b);

			this.zLevel = 0.0f;
		}
	}
}
