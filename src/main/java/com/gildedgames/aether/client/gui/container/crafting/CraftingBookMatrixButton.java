package com.gildedgames.aether.client.gui.container.crafting;

import com.gildedgames.aether.client.gui.container.GuiSkyrootWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CraftingBookMatrixButton extends CraftingBookStackButton
{
	protected final GuiSkyrootWorkbench container;

	public final Slot slot;

	public CraftingBookMatrixButton(GuiSkyrootWorkbench container, int id, Slot slot, List<ItemStack> stacks, int x, int y)
	{
		super(id, stacks, x, y);

		this.container = container;
		this.slot = slot;
	}

	@Override
	protected void renderItem(int mouseX, int mouseY, boolean hovered, boolean selected)
	{
		if (!this.slot.getHasStack())
		{
			super.renderItem(mouseX, mouseY, hovered, selected);

			this.zLevel = 180.0f;

			this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width,
					this.yPosition + this.height, 0x668b8b8b, 0xbb8b8b8b);

			this.zLevel = 0.0f;
		}
	}
}
