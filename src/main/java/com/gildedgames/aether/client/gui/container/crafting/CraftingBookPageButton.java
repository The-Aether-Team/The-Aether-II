package com.gildedgames.aether.client.gui.container.crafting;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CraftingBookPageButton extends GuiButton
{
	private static final ResourceLocation BUTTON_TEXTURES = AetherCore.getResource("textures/gui/inventory/book_recipes.png");

	public CraftingBookPageButton(int buttonId, int x, int y, String text)
	{
		super(buttonId, x, y, text);

		this.width = 20;
		this.height = 16;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (this.visible)
		{
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition &&
					mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

			mc.getTextureManager().bindTexture(CraftingBookPageButton.BUTTON_TEXTURES);

			this.drawButtonContent(mc, mouseX, mouseY);
			this.mouseDragged(mc, mouseX, mouseY);
		}
	}

	protected void drawButtonContent(Minecraft mc, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		this.drawTexturedModalRect(this.xPosition, this.yPosition, 150, 50 + (this.enabled ? 0 : 25), this.width, this.height);

		int color = this.enabled ? (this.hovered ? 0xFFFFA0 : 0x423b31) : 0x423b31;

		float x = this.xPosition + (this.width / 2);

		mc.fontRenderer.drawString(this.displayString, (int) x - mc.fontRenderer.getStringWidth(this.displayString) / 2, this.yPosition + 4, color);
	}
}
