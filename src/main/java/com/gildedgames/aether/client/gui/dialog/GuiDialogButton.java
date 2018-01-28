package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.dialog.IDialogButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class GuiDialogButton extends GuiButton
{

	private final IDialogButton buttonData;

	public GuiDialogButton(final int buttonId, final int x, final int y, final IDialogButton buttonData)
	{
		super(buttonId, x, y, 0, 15, "");

		this.buttonData = buttonData;
	}

	public IDialogButton getButtonData()
	{
		return this.buttonData;
	}

	@Override
	public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks)
	{
		if (this.visible)
		{
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

			Gui.drawRect(this.x, this.y,
					this.x + this.width, this.y + this.height, this.hovered ? Integer.MAX_VALUE : Integer.MIN_VALUE);

			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
					&& mouseY < this.y + this.height;

			final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

			final String label = this.buttonData.getLocalizedLabel().getFormattedText();

			this.width = fontRenderer.getStringWidth(label) + 5;

			this.drawString(fontRenderer, label, this.x + 3, this.y + 3, 0xFFFFFF);
		}
	}

}
