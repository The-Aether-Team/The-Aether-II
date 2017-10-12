package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.util.rect.Rect;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;

public class GuiText extends GuiAdvanced
{
	private final ITextComponent component;

	public GuiText(final Rect rect, final ITextComponent component)
	{
		super(rect);

		this.component = component;
	}

	@Override
	public void init()
	{

	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.pushMatrix();

		this.drawString(this.fontRenderer, this.component.getFormattedText(), (int) this.dim().x(), (int) this.dim().y(), 16777215);

		GlStateManager.popMatrix();
	}
}
