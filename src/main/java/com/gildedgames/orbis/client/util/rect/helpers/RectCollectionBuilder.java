package com.gildedgames.orbis.client.util.rect.helpers;

import com.gildedgames.orbis.client.util.rect.Dim2D;
import com.gildedgames.orbis.client.util.rect.Rect;
import com.gildedgames.orbis.client.util.rect.RectHolder;
import com.google.common.collect.Lists;

import java.util.List;

public class RectCollectionBuilder
{

	private final List<RectHolder> holders = Lists.newArrayList();

	private final List<RectSeekable> seekables = Lists.newArrayList();

	protected RectCollectionBuilder()
	{

	}

	public RectCollectionBuilder addDim(final Rect dim)
	{
		this.holders.add(new RectCollection(dim));

		return this;
	}

	public RectCollectionBuilder removeDim(final Rect dim)
	{
		this.holders.remove(new RectCollection(dim));

		return this;
	}

	public RectCollectionBuilder addHolder(final RectHolder holder)
	{
		this.holders.add(holder);

		return this;
	}

	public RectCollectionBuilder removeHolder(final RectHolder holder)
	{
		this.holders.remove(holder);

		return this;
	}

	public RectCollectionBuilder addSeekable(final RectSeekable seekable)
	{
		this.seekables.add(seekable);

		return this;
	}

	public RectCollectionBuilder removeSeekable(final RectSeekable seekable)
	{
		this.seekables.remove(seekable);

		return this;
	}

	public RectCollection flush()
	{
		final RectCollection resultHolder = new RectCollection();
		if (this.holders.isEmpty())
		{
			return resultHolder;
		}
		resultHolder.dim().set(this.holders.get(0));

		for (final RectHolder holder : this.holders)
		{
			if (holder != null && holder.dim() != null)
			{
				resultHolder.dim().set(Dim2D.combine(resultHolder.dim(), holder.dim()));
			}
		}

		for (final RectSeekable seekable : this.seekables)
		{
			for (final RectSeeker seeker : seekable.getRectSeekers())
			{
				if (seeker != null && seeker.dim() != null)
				{
					resultHolder.dim().set(Dim2D.combine(resultHolder.dim(), seeker.dim()));
				}
			}
		}

		return resultHolder;
	}

}
