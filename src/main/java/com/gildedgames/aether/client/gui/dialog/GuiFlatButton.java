package com.gildedgames.aether.client.gui.dialog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;

import java.awt.*;

public class GuiFlatButton extends Button
{

	public GuiFlatButton(final int x, final int y, int widthIn, int heightIn, String displayString, IPressable callback)
	{
		super(x, y, widthIn, heightIn, displayString, callback);
	}

	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks)
	{
		if (this.visible)
		{
			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

			AbstractGui.fill(this.x, this.y,
					this.x + this.width, this.y + this.height, this.active ? (this.isHovered ? Integer.MAX_VALUE : Integer.MIN_VALUE) : new Color(100, 100, 100, 100).getRGB());

			this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

			final FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

			final String label = this.getMessage();

			this.width = fontRenderer.getStringWidth(label) + 5;

			this.drawString(fontRenderer, label, this.x + 3, this.y + 3, this.active ? 0xFFFFFF : 0x2D2D2D);
		}
	}

}
