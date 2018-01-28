package com.gildedgames.aether.client.gui.dialog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public abstract class GuiElement extends GuiButton
{

	protected boolean hasInit;

	public GuiElement(final int elementId, final int x, final int y)
	{
		super(elementId, x, y, "");
	}

	@Override
	public final void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		if (!this.hasInit)
		{
			this.init(fontRenderer);
			this.hasInit = true;
		}

		if (this.visible)
		{
			this.draw(fontRenderer);
		}
	}

	public void markForReInit()
	{
		this.hasInit = false;
	}

	public abstract void init(FontRenderer fontRenderer);

	public abstract void draw(FontRenderer fontRenderer);

}
