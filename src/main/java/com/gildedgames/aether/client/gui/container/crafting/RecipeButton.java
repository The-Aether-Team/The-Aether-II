package com.gildedgames.aether.client.gui.container.crafting;

import com.gildedgames.aether.api.registry.recipes.IIndexableRecipe;
import com.gildedgames.aether.client.gui.container.GuiSkyrootWorkbench;

public class RecipeButton extends ItemStackButton
{
	private final GuiSkyrootWorkbench workbench;

	private final IIndexableRecipe recipe;

	public final boolean complete;

	public RecipeButton(int id, GuiSkyrootWorkbench workbench, IIndexableRecipe recipe, int x, int y, boolean complete)
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

		if (this.workbench.getSelectedRecipe() == this.recipe)
		{
			this.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, 0x66fff999, 0xbbfff999);
		}
		else if (!this.complete)
		{
			this.zLevel = 1000.0f;

			this.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, 0x99484036, 0x99484036);
		}

		this.zLevel = 0.0f;
	}
}
