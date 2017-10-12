package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.gui.data.IDropdownElement;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class GuiDropdownList extends GuiAdvanced
{
	private final IDropdownElement[] elements;

	public GuiDropdownList(final Pos2D pos, final IDropdownElement... elements)
	{
		super(Dim2D.build().pos(pos).flush());

		this.elements = elements;
	}

	@Override
	public void init()
	{
		for (int i = 0; i < this.elements.length; i++)
		{
			final IDropdownElement element = this.elements[i];
			final int y = 17 * i;

			final GuiTextLabel label = new GuiTextLabel(Dim2D.build().y(y).area(60, 10).flush(), element.text())
			{
				@Override
				protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
				{
					super.mouseReleased(mouseX, mouseY, mouseButton);

					if (InputHelper.isHovered(this))
					{
						element.onClick(Minecraft.getMinecraft().player);
					}
				}
			};

			this.addChild(label);
		}
	}
}
