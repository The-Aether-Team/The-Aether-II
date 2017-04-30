package com.gildedgames.aether.client.gui.container.crafting;

import com.gildedgames.aether.api.recipes.IIndexableRecipe;
import com.gildedgames.aether.client.gui.container.GuiSkyrootWorkbench;

public class CraftingBookRecipeButton extends CraftingBookStackButton
{
	private final GuiSkyrootWorkbench workbench;

	private final IIndexableRecipe recipe;

	public final boolean complete;

	public CraftingBookRecipeButton(int id, GuiSkyrootWorkbench workbench, IIndexableRecipe recipe, int x, int y, boolean complete)
	{
		super(id, recipe.getCraftingResult(), x, y);

		this.workbench = workbench;
		this.recipe = recipe;
		this.complete = complete;
	}

	@Override
	protected void renderItem(int mouseX, int mouseY, boolean hovered, boolean selected)
	{
		super.renderItem(mouseX, mouseY, hovered, selected);

		this.zLevel = 100.0f;

		if (this.workbench.getSelectedRecipe() == this.recipe)
		{
			this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width,
					this.yPosition + this.height, 0x66fff999, 0xbbfff999);
		}
		else if (!this.complete)
		{
			this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width,
					this.yPosition + this.height, 0x99484036, 0x99484036);
		}

		this.zLevel = 0.0f;
	}
}
