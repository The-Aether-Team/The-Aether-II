package com.gildedgames.aether.client.gui.dialog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public abstract class GuiElement extends GuiButton
{

	private boolean hasInit;

	public GuiElement(int elementId, int x, int y)
	{
		super(elementId, x, y, "");
	}

	public final void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

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
