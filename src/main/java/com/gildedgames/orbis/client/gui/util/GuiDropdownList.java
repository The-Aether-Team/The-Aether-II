package com.gildedgames.orbis.client.gui.util;

import com.gildedgames.orbis.client.gui.data.IDropdownElement;
import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Pos2D;
import com.gildedgames.orbis.common.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GuiDropdownList extends GuiFrame
{
	private final List<IDropdownElement> elements = Lists.newArrayList();

	public GuiDropdownList(final Pos2D pos, final IDropdownElement... elements)
	{
		super(Dim2D.build().pos(pos).flush());

		this.elements.addAll(Arrays.asList(elements));
	}

	public void setDropdownElements(final Collection<IDropdownElement> elements)
	{
		this.clearChildren();

		this.elements.clear();
		this.elements.addAll(elements);

		this.init();
	}

	@Override
	public void init()
	{
		for (int i = 0; i < this.elements.size(); i++)
		{
			final IDropdownElement element = this.elements.get(i);
			final int y = 17 * i;

			final GuiTextLabel label = new GuiTextLabel(Dim2D.build().y(y).area(60, 10).flush(), element.text())
			{
				@Override
				protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
				{
					super.mouseReleased(mouseX, mouseY, mouseButton);

					if (InputHelper.isHovered(this) && GuiDropdownList.this.isVisible())
					{
						element.onClick(GuiDropdownList.this, Minecraft.getMinecraft().player);
					}
				}
			};

			this.addChild(label);
		}

		this.dim().mod().width(60).height(17 * this.elements.size()).flush();
	}
}
