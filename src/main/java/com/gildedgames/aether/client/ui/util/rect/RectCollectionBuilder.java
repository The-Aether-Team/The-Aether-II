package com.gildedgames.aether.client.ui.util.rect;

import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;

import java.util.ArrayList;
import java.util.List;

public class RectCollectionBuilder
{

	private List<RectHolder> holders = new ArrayList<>();

	private List<RectSeekable> seekables = new ArrayList<>();

	protected RectCollectionBuilder()
	{

	}

	public RectCollectionBuilder addDim(Rect dim)
	{
		this.holders.add(new RectCollection(dim));

		return this;
	}

	public RectCollectionBuilder removeDim(Rect dim)
	{
		this.holders.remove(new RectCollection(dim));

		return this;
	}

	public RectCollectionBuilder addHolder(RectHolder holder)
	{
		this.holders.add(holder);

		return this;
	}

	public RectCollectionBuilder removeHolder(RectHolder holder)
	{
		this.holders.remove(holder);

		return this;
	}

	public RectCollectionBuilder addSeekable(RectSeekable seekable)
	{
		this.seekables.add(seekable);

		return this;
	}

	public RectCollectionBuilder removeSeekable(RectSeekable seekable)
	{
		this.seekables.remove(seekable);

		return this;
	}

	public RectCollection flush()
	{
		RectCollection resultHolder = new RectCollection();
		if (this.holders.isEmpty())
		{
			return resultHolder;
		}
		resultHolder.dim().set(this.holders.get(0));

		for (RectHolder holder : this.holders)
		{
			if (holder != null && holder.dim() != null)
			{
				resultHolder.dim().set(Dim2D.combine(resultHolder.dim(), holder.dim()));
			}
		}

		for (RectSeekable seekable : this.seekables)
		{
			for (RectSeeker seeker : seekable.getRectSeekers())
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
