package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.client.gui.container.IExtendedContainer;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiCraftingOption extends Button
{

	private static final ResourceLocation DARK_OVERLAY = AetherCore.getResource("textures/gui/inventory/dark_slot_overlay.png");

	private ISimpleRecipe recipe;

	public GuiCraftingOption(int x, int y, ISimpleRecipe recipe, IPressable callback)
	{
		super(x, y, 18, 18, "", callback);

		this.recipe = recipe;
	}

	public ISimpleRecipe getRecipe()
	{
		return this.recipe;
	}

	public void setRecipe(ISimpleRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		//super.drawButton(mc, mouseX, mouseY);

		if (this.visible && this.recipe != null)
		{
			this.isHovered =
					mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

			GlStateManager.enableRescaleNormal();
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableDepthTest();

			Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(this.recipe.getResult(), this.x + 1, this.y + 1);

			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();

			if (this.isHovered)
			{
				Screen gui = Minecraft.getInstance().currentScreen;

				if (gui instanceof IExtendedContainer)
				{
					IExtendedContainer extendedGui = (IExtendedContainer) gui;

					extendedGui.setHoveredDescription(this.recipe.getResult(),
							this.recipe.getResult().getTooltip(Minecraft.getInstance().player, Minecraft.getInstance().gameSettings.advancedItemTooltips ?
									ITooltipFlag.TooltipFlags.ADVANCED :
									ITooltipFlag.TooltipFlags.NORMAL));
				}
			}

			GlStateManager.pushMatrix();
			GlStateManager.translatef(0.0F, 0.0F, 300.0F);

			int timesCanCraft = RecipeUtil.getTotalTimesCanCraft(Minecraft.getInstance().player, this.recipe);

			if (timesCanCraft > 0)
			{
				String count = (timesCanCraft <= 0 ? TextFormatting.RED : "") + String.valueOf(Math.min(64, timesCanCraft));
				int xOffset = (Math.max(String.valueOf(timesCanCraft).length() - 1, 0)) * -6;

				//this.drawString(Minecraft.getInstance().fontRendererObj, count, this.x + 12 + xOffset, this.y + this.height - 8, 0xFFFFFF);
			}

			if (!RecipeUtil.canCraft(Minecraft.getInstance().player.inventory, this.recipe))
			{
				Minecraft.getInstance().getTextureManager().bindTexture(DARK_OVERLAY);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

				GlStateManager.enableBlend();
				GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
						GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				AbstractGui.blit(this.x + 1, this.y + 1, 0, 0, 16, 16, 16, 16);
			}

			GlStateManager.popMatrix();
		}
	}

}
