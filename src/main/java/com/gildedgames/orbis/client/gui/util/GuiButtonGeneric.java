package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.util.rect.Rect;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public class GuiButtonGeneric extends GuiFrame
{
	private final GuiButton button;

	public GuiButtonGeneric(final Rect rect)
	{
		super(rect);

		this.button = new GuiButton(0, (int) rect.x(), (int) rect.y(), (int) rect.width(), (int) rect.height(), "");
	}

	public GuiButton getInner()
	{
		return this.button;
	}

	@Override
	public void init()
	{

	}

	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		if (this.button.isMouseOver())
		{
			this.button.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
		}
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.button.mousePressed(this.mc, mouseX, mouseY))
		{
			this.button.playPressSound(this.mc.getSoundHandler());
			this.actionPerformed(this.button);
		}
	}

	@Override
	public void draw()
	{
		this.button.xPosition = (int) this.dim().x();
		this.button.yPosition = (int) this.dim().y();

		this.button.width = (int) this.dim().width();
		this.button.height = (int) this.dim().height();

		this.button.drawButton(Minecraft.getMinecraft(), (int) InputHelper.getMouseX(), (int) InputHelper.getMouseY());
	}
}
