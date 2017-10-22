package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.util.rect.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;

public class GuiInput extends GuiFrame
{
	private final GuiTextField field;

	public GuiInput(final Rect rect)
	{
		super(rect);

		this.field = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer, (int) rect.x(), (int) rect.y(), (int) rect.width(), (int) rect.height());
	}

	public GuiTextField getInner()
	{
		return this.field;
	}

	@Override
	public void init()
	{

	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.field.getVisible())
		{
			this.field.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);

		if (this.field.getVisible())
		{
			this.field.textboxKeyTyped(typedChar, keyCode);
		}
	}

	@Override
	public void draw()
	{
		this.field.xPosition = (int) this.dim().x();
		this.field.yPosition = (int) this.dim().y();

		this.field.width = (int) this.dim().width();
		this.field.height = (int) this.dim().height();

		if (this.field.getVisible())
		{
			this.field.drawTextBox();
		}
	}

	@Override
	public void updateScreen()
	{
		this.field.updateCursorCounter();
	}
}
