package com.gildedgames.aether.client.gui.container.crafting;

import com.gildedgames.aether.client.gui.container.GuiSkyrootWorkbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.creativetab.CreativeTabs;

public class CraftingBookTabButton extends CraftingBookPageButton
{
	private final GuiSkyrootWorkbench parentWorkbench;

	private final CreativeTabs tab;

	public CraftingBookTabButton(GuiSkyrootWorkbench workbench, CreativeTabs tab, int x, int y)
	{
		super(-1, x, y, "");

		this.width = 28;
		this.height = 24;

		this.tab = tab;
		this.parentWorkbench = workbench;
	}

	@Override
	public void drawButtonContent(Minecraft mc, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		int margin = 0;

		if (this.parentWorkbench.getSelectedTab() == this.tab)
		{
			margin = -4;

			this.drawTexturedModalRect(this.xPosition - 4, this.yPosition, 150, 0, 32, 24);
		}
		else
		{
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 150, 25, 28, 24);
		}

		RenderItem itemRenderer = mc.getRenderItem();

		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();

		itemRenderer.renderItemAndEffectIntoGUI(mc.player, this.tab.getIconItemStack(), this.xPosition + 5 + margin, this.yPosition + 2);

		GlStateManager.disableDepth();
		RenderHelper.disableStandardItemLighting();
	}

	public CreativeTabs getTab()
	{
		return this.tab;
	}
}
