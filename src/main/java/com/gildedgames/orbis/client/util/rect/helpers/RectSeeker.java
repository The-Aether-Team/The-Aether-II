package com.gildedgames.orbis.client.util.rect.helpers;

import com.gildedgames.orbis.client.util.rect.ModDim2D;
import com.gildedgames.orbis.client.util.rect.RectHolder;

public abstract class RectSeeker<S> implements RectHolder
{

	protected S seekFrom;

	private ModDim2D dim;

	public RectSeeker()
	{
		this.dim = new ModDim2D();
	}

	public RectSeeker(final S seekFrom)
	{
		this.seekFrom = seekFrom;
	}

	@Override
	public ModDim2D dim()
	{
		return this.dim;
	}

	@Override
	public void updateState()
	{

	}

}
