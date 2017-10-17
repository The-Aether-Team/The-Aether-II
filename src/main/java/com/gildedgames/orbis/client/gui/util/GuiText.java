package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.util.rect.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;

public class GuiText extends GuiAdvanced
{
	private ITextComponent component;

	public GuiText(final Rect rect, final ITextComponent component)
	{
		super(rect);

		this.component = component;
	}

	public void setText(final ITextComponent component)
	{
		this.component = component;
	}

	@Override
	public void init()
	{

	}

	@Override
	public void draw()
	{
		if (this.component != null)
		{
			this.dim().mod().width(Minecraft.getMinecraft().fontRenderer.getStringWidth(this.component.getFormattedText())).flush();

			GlStateManager.pushMatrix();

			this.drawString(this.fontRenderer, this.component.getFormattedText(), (int) this.dim().x(), (int) this.dim().y(), 16777215);

			GlStateManager.popMatrix();
		}
	}
}
