package com.gildedgames.aether.client.ui.util.rect;

import com.gildedgames.aether.client.ui.data.rect.ModDim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;

public class RectCollection implements RectHolder
{

	private ModDim2D dim = new ModDim2D();

	protected RectCollection()
	{

	}

	protected RectCollection(Rect dim)
	{
		this.dim().set(dim);
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

	public static RectCollectionBuilder build()
	{
		return new RectCollectionBuilder();
	}

	public static RectCollection flush(Rect dim)
	{
		return new RectCollection(dim);
	}

	@Override
	public void updateState()
	{
	}

}
