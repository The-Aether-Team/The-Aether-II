package com.gildedgames.aether.client.ui.util.transform;

import com.gildedgames.aether.client.ui.common.Gui;
import com.gildedgames.aether.client.ui.data.rect.Rect;

import java.util.List;

public class GuiPositionerList implements GuiPositioner
{

	private final int paddingY;

	public GuiPositionerList(int paddingY)
	{
		this.paddingY = paddingY;
	}

	@Override
	public List<Gui> positionList(List<Gui> guis, Rect collectionDim)
	{
		int currentY = 0;

		for (Gui view : guis)
		{
			view.dim().mod().y(currentY).center(false).flush();

			currentY += view.dim().height() + this.paddingY;
		}

		return guis;
	}

}
