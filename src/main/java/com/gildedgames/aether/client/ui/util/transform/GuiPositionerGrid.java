package com.gildedgames.aether.client.ui.util.transform;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.data.rect.Rect;

import java.util.List;

public class GuiPositionerGrid implements GuiPositioner
{

	private final int paddingX, paddingY;

	public GuiPositionerGrid()
	{
		this(0);
	}

	public GuiPositionerGrid(int padding)
	{
		this(padding, padding);
	}

	public GuiPositionerGrid(int paddingX, int paddingY)
	{
		this.paddingX = paddingX;
		this.paddingY = paddingY;
	}

	@Override
	public List<Gui> positionList(List<Gui> guis, Rect listDimensions)
	{
		int currentX = 0;
		int currentY = 0;

		for (Gui gui : guis)
		{
			gui.dim().mod().pos(currentX, currentY).center(false).flush();

			currentX += gui.dim().width() + this.paddingX;
			if (currentX + gui.dim().width() + this.paddingX > listDimensions.width())
			{
				double maxHeight = gui.dim().height();
				for (Gui view1 : guis)
				{
					if (view1.dim().y() == currentY)
					{
						maxHeight = Math.max(view1.dim().height(), maxHeight);
					}
				}
				currentY += maxHeight + this.paddingY;
				currentX = 0;
			}
		}

		return guis;
	}
}
