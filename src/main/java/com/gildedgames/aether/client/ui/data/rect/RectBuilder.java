package com.gildedgames.aether.client.ui.data.rect;

import com.gildedgames.aether.client.ui.data.Pos2D;
import com.gildedgames.aether.client.ui.util.rect.RectCollection;

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

	public RectBuilder(RectHolder holder)
	{
		this(holder.dim());
	}

	public RectBuilder(Rect rect)
	{
		this.set(rect);
	}

	public BuildWithRectHolder buildWith(RectHolder holder)
	{
		return new BuildWithRectHolder(this, holder);
	}

	public BuildWithRectHolder buildWith(Rect dim)
	{
		return this.buildWith(RectCollection.flush(dim));
	}

	public RectBuilder set(Rect rect)
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

	public RectBuilder degrees(float degrees)
	{
		this.degrees = degrees;

		return this;
	}

	public RectBuilder origin(float x, float y)
	{
		this.originX = x;
		this.originY = y;

		return this;
	}

	public RectBuilder originX(float x)
	{
		this.originX = x;

		return this;
	}

	public RectBuilder originY(float y)
	{
		this.originY = y;

		return this;
	}

	public RectBuilder addDegrees(float degrees)
	{
		this.degrees += degrees;

		return this;
	}

	public RectBuilder subtractDegrees(float degrees)
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

	public RectBuilder scale(float scale)
	{
		this.scale = scale;

		return this;
	}

	public RectBuilder height(float height)
	{
		this.height = height;

		return this;
	}

	public RectBuilder width(float width)
	{
		this.width = width;

		return this;
	}

	public RectBuilder pos(float x, float y)
	{
		this.posX = x;
		this.posY = y;
		return this;
	}

	public RectBuilder pos(Pos2D pos)
	{
		this.posX = pos.x();
		this.posY = pos.y();

		return this;
	}

	public RectBuilder center(boolean centeredX, boolean centeredY)
	{
		return this.centerX(centeredX).centerY(centeredY);
	}

	public RectBuilder centerX(boolean centeredX)
	{
		this.centeredX = centeredX;

		return this;
	}

	public RectBuilder centerY(boolean centeredY)
	{
		this.centeredY = centeredY;

		return this;
	}

	public RectBuilder area(float width, float height)
	{
		return this.width(width).height(height);
	}

	public RectBuilder y(float y)
	{
		this.posY = y;
		return this;
	}

	public RectBuilder x(float x)
	{
		this.posX = x;
		return this;
	}

	public RectBuilder center(boolean centered)
	{
		return this.center(centered, centered);
	}

	public RectBuilder addScale(float scale)
	{
		this.scale += scale;

		return this;
	}

	public RectBuilder addWidth(float width)
	{
		return this.width(this.width + width);
	}

	public RectBuilder addHeight(float height)
	{
		return this.area(this.width, this.height + height);
	}

	public RectBuilder addArea(float width, float height)
	{
		return this.addWidth(width).addHeight(height);
	}

	public RectBuilder addX(float x)
	{
		this.posX += x;
		return this;
	}

	public RectBuilder addY(float y)
	{
		this.posY += y;
		return this;
	}

	public RectBuilder addPos(float x, float y)
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
