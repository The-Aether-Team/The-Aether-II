package com.gildedgames.orbis.client.util.rect;

import com.gildedgames.orbis.client.util.rect.helpers.RectCollection;

public class RectBuilder
{

	protected float posX, posY;

	protected float width, height;

	protected boolean centeredX, centeredY;

	protected float scale = 1.0F;

	protected float degrees;

	protected float originX, originY;

	public RectBuilder()
	{

	}

	public RectBuilder(final RectHolder holder)
	{
		this(holder.dim());
	}

	public RectBuilder(final Rect rect)
	{
		this.set(rect);
	}

	public BuildWithRectHolder buildWith(final RectHolder holder)
	{
		return new BuildWithRectHolder(this, holder);
	}

	public BuildWithRectHolder buildWith(final Rect dim)
	{
		return this.buildWith(RectCollection.flush(dim));
	}

	public RectBuilder set(final Rect rect)
	{
		this.posX = rect.x();
		this.posY = rect.y();

		this.width = rect.width();
		this.height = rect.height();

		this.scale = rect.scale();

		this.centeredX = rect.isCenteredX();
		this.centeredY = rect.isCenteredY();

		this.degrees = rect.degrees();
		this.originX = rect.originX();
		this.originY = rect.originY();
		return this;
	}

	public RectBuilder degrees(final float degrees)
	{
		this.degrees = degrees;

		return this;
	}

	public RectBuilder origin(final float x, final float y)
	{
		this.originX = x;
		this.originY = y;

		return this;
	}

	public RectBuilder originX(final float x)
	{
		this.originX = x;

		return this;
	}

	public RectBuilder originY(final float y)
	{
		this.originY = y;

		return this;
	}

	public RectBuilder addDegrees(final float degrees)
	{
		this.degrees += degrees;

		return this;
	}

	public RectBuilder subtractDegrees(final float degrees)
	{
		this.degrees -= degrees;

		return this;
	}

	public RectBuilder resetPos()
	{
		this.posX = 0;
		this.posY = 0;

		return this;
	}

	public RectBuilder scale(final float scale)
	{
		this.scale = scale;

		return this;
	}

	public RectBuilder height(final float height)
	{
		this.height = height;

		return this;
	}

	public RectBuilder width(final float width)
	{
		this.width = width;

		return this;
	}

	public RectBuilder pos(final float x, final float y)
	{
		this.posX = x;
		this.posY = y;
		return this;
	}

	public RectBuilder pos(final Pos2D pos)
	{
		this.posX = pos.x();
		this.posY = pos.y();

		return this;
	}

	public RectBuilder center(final boolean centeredX, final boolean centeredY)
	{
		return this.centerX(centeredX).centerY(centeredY);
	}

	public RectBuilder centerX(final boolean centeredX)
	{
		this.centeredX = centeredX;

		return this;
	}

	public RectBuilder centerY(final boolean centeredY)
	{
		this.centeredY = centeredY;

		return this;
	}

	public RectBuilder area(final float area)
	{
		return this.area(area, area);
	}

	public RectBuilder area(final float width, final float height)
	{
		return this.width(width).height(height);
	}

	public RectBuilder y(final float y)
	{
		this.posY = y;
		return this;
	}

	public RectBuilder x(final float x)
	{
		this.posX = x;
		return this;
	}

	public RectBuilder center(final boolean centered)
	{
		return this.center(centered, centered);
	}

	public RectBuilder addScale(final float scale)
	{
		this.scale += scale;

		return this;
	}

	public RectBuilder addWidth(final float width)
	{
		return this.width(this.width + width);
	}

	public RectBuilder addHeight(final float height)
	{
		return this.area(this.width, this.height + height);
	}

	public RectBuilder addArea(final float width, final float height)
	{
		return this.addWidth(width).addHeight(height);
	}

	public RectBuilder addX(final float x)
	{
		this.posX += x;
		return this;
	}

	public RectBuilder addY(final float y)
	{
		this.posY += y;
		return this;
	}

	public RectBuilder addPos(final float x, final float y)
	{
		return this.addX(x).addY(y);
	}

	/**
	 * Finalise the state and return it. Some implementations
	 * automatically set it to its holder.
	 * @return
	 */
	public Rect flush()
	{
		return new Dim2D(this);
	}

}
