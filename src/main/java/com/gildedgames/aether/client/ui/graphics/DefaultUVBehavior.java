package com.gildedgames.aether.client.ui.graphics;

import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;
import com.google.common.collect.Lists;

import java.util.List;

public class DefaultUVBehavior implements UVBehavior
{

	@Override
	public List<UVDimPair> getDrawnUVsFor(Sprite sprite, RectHolder areaToDraw)
	{
		return Lists.newArrayList(new UVDimPair(sprite.getUV(), Dim2D.flush()));
	}

	@Override
	public boolean shouldRecalculateUVs(Sprite sprite, RectHolder areaToDraw)
	{
		return false;
	}

	@Override
	public void recalculateUVs(Sprite sprite, RectHolder areaToDraw)
	{

	}

}
