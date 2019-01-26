package com.gildedgames.aether.client.gui.util;

import com.gildedgames.aether.client.gui.IExtendedGui;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis_api.client.rect.Rect;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;

public class GuiItemStack extends GuiElement
{
	private ItemStack stack;

	private boolean drawCount = true;

	public GuiItemStack(Rect rect)
	{
		super(rect, true);

		this.dim().mod().width(18).height(18).flush();
	}

	public void setDrawCount(boolean drawCount)
	{
		this.drawCount = drawCount;
	}

	public ItemStack getItemStack()
	{
		return this.stack;
	}

	public void setItemStack(ItemStack stack)
	{
		this.stack = stack.copy();
	}

	@Override
	public void onDraw(GuiElement element)
	{
		if (this.stack == null || this.stack == ItemStack.EMPTY)
		{
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableRescaleNormal();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.pushMatrix();

		//TODO: WHY IS THE LIGHTING REVERSED, EVEN WITH STANDARD GUI ITEM LIGHTING. WTF????
		RenderHelper.enableStandardItemLighting();

		GlStateManager.popMatrix();
		GlStateManager.disableLighting();

		FontRenderer font = this.stack.getItem().getFontRenderer(this.stack);

		if (font == null)
		{
			font = this.viewer().fontRenderer();
		}

		RenderItem renderitem = this.viewer().mc().getRenderItem();
		renderitem.zLevel = 200.0F;
		renderitem.renderItemAndEffectIntoGUI(this.viewer().mc().player, this.stack, (int) this.dim().x() + 1, (int) this.dim().y() + 1);

		RenderHelper.disableStandardItemLighting();

		renderitem.zLevel = 0.0F;

		if (this.drawCount)
		{
			int xOffset = (Math.max(String.valueOf(this.stack.getCount()).length() - 1, 0)) * -6;

			this.viewer().getActualScreen().drawString(font, String.valueOf(this.stack.getCount()), (int) this.dim().x() + 12 + xOffset,
					(int) this.dim().y() + (int) this.dim().height() + -8,
					0xFFFFFF);
		}

		if (InputHelper.isHovered(this))
		{
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;

			if (gui instanceof IExtendedGui)
			{
				IExtendedGui extendedGui = (IExtendedGui) gui;
				extendedGui.setHoveredDescription(this.stack, this.stack.getTooltip(Minecraft.getMinecraft().player,
						Minecraft.getMinecraft().gameSettings.advancedItemTooltips ?
								ITooltipFlag.TooltipFlags.ADVANCED :
								ITooltipFlag.TooltipFlags.NORMAL));
			}
		}

		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
	}
}
