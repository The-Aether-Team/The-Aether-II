package com.gildedgames.orbis.client.util.rect.helpers;

import com.gildedgames.orbis.client.util.rect.ModDim2D;
import com.gildedgames.orbis.client.util.rect.Rect;

public abstract class RectGetter<S> extends RectSeeker<S>
{

	public RectGetter()
	{

	}

	public RectGetter(final S seekFrom)
	{
		this.seekFrom = seekFrom;
	}

	@Override
	public final ModDim2D dim()
	{
		if ((super.dim() == null || this.shouldReassemble()))
		{
			super.dim().set(this.assembleRect());
		}

		return super.dim();
	}

	@Override
	public void updateState()
	{
		this.dim();
	}

	/**
	 * Will only be called when dimHasChanged() returns true.
	 * @return
	 */
	public abstract Rect assembleRect();

	/**
	 * Calculations should be kept small here to check if the Dim2D object that will be assembled is different.
	 * @return
	 */
	public abstract boolean shouldReassemble();

}
