package com.gildedgames.orbis.client.util.rect.helpers;

import com.gildedgames.orbis.client.util.rect.ModDim2D;
import com.gildedgames.orbis.client.util.rect.Rect;
import com.gildedgames.orbis.client.util.rect.RectHolder;

public class RectCollection implements RectHolder
{

	private final ModDim2D dim = new ModDim2D();

	protected RectCollection()
	{

	}

	protected RectCollection(final Rect dim)
	{
		this.dim().set(dim);
	}

	public static RectCollectionBuilder build()
	{
		return new RectCollectionBuilder();
	}

	public static RectCollection flush(final Rect dim)
	{
		return new RectCollection(dim);
	}

	@Override
	public ModDim2D dim()
	{
		return this.dim;
	}

	@Override
	public String toString()
	{
		return this.dim().toString();
	}

	@Override
	public void updateState()
	{
	}

}
