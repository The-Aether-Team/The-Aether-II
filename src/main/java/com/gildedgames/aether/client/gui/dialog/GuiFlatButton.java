package com.gildedgames.aether.client.gui.dialog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class GuiFlatButton extends GuiButton
{

	public GuiFlatButton(final int buttonId, final int x, final int y, int widthIn, int heightIn, String displayString)
	{
		super(buttonId, x, y, widthIn, heightIn, displayString);
	}

	public void setText(String text)
	{
		this.displayString = text;
	}

	public String getText()
	{
		return this.displayString;
	}

	@Override
	public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		if (this.visible)
		{
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

			Gui.drawRect(this.x, this.y,
					this.x + this.width, this.y + this.height, this.enabled ? (this.hovered ? Integer.MAX_VALUE : Integer.MIN_VALUE) : new Color(100, 100, 100, 100).getRGB());

			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

			final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

			final String label = this.getText();

			this.width = fontRenderer.getStringWidth(label) + 5;

			this.drawString(fontRenderer, label, this.x + 3, this.y + 3, this.enabled ? 0xFFFFFF : 0x2D2D2D);
		}
	}

}
