package com.gildedgames.orbis.client.util.rect;

public class BuildWithRectHolder
{

	protected RectBuilder builder;

	protected RectHolder buildWith;

	BuildWithRectHolder(final RectBuilder builder, final RectHolder buildWith)
	{
		this.builder = builder;
		this.buildWith = buildWith;
	}

	public BuildWithRectHolder rotation()
	{
		this.builder.degrees = this.buildWith.dim().originalState().degrees();
		this.builder.originX = this.buildWith.dim().originalState().originX();
		this.builder.originY = this.buildWith.dim().originalState().originY();

		return this;
	}

	public BuildWithRectHolder degrees()
	{
		this.builder.degrees = this.buildWith.dim().originalState().degrees();

		return this;
	}

	public BuildWithRectHolder origin()
	{
		this.builder.originX = this.buildWith.dim().originalState().originX();
		this.builder.originY = this.buildWith.dim().originalState().originY();

		return this;
	}

	public BuildWithRectHolder originX()
	{
		this.builder.originX = this.buildWith.dim().originalState().originX();

		return this;
	}

	public BuildWithRectHolder originY()
	{
		this.builder.originY = this.buildWith.dim().originalState().originY();

		return this;
	}

	public BuildWithRectHolder rotateCW()
	{
		this.builder.degrees += this.buildWith.dim().originalState().degrees();

		return this;
	}

	public BuildWithRectHolder rotateCCW()
	{
		this.builder.degrees -= this.buildWith.dim().originalState().degrees();

		return this;
	}

	public BuildWithRectHolder scale()
	{
		this.builder.scale = this.buildWith.dim().originalState().scale();

		return this;
	}

	public BuildWithRectHolder height()
	{
		this.builder.height = this.buildWith.dim().originalState().height();

		return this;
	}

	public BuildWithRectHolder width()
	{
		this.builder.width = this.buildWith.dim().originalState().width();

		return this;
	}

	public BuildWithRectHolder pos()
	{
		this.builder.posX = this.buildWith.dim().originalState().x();
		this.builder.posY = this.buildWith.dim().originalState().y();

		return this;
	}

	public BuildWithRectHolder center()
	{
		this.builder.centerX(this.buildWith.dim().originalState().isCenteredX()).centerY(this.buildWith.dim().originalState().isCenteredY());

		return this;
	}

	public BuildWithRectHolder centerX()
	{
		this.builder.centeredX = this.buildWith.dim().originalState().isCenteredX();

		return this;
	}

	public BuildWithRectHolder centerY()
	{
		this.builder.centeredY = this.buildWith.dim().originalState().isCenteredY();

		return this;
	}

	public BuildWithRectHolder area()
	{
		this.builder.width(this.buildWith.dim().originalState().width()).height(this.buildWith.dim().originalState().height());

		return this;
	}

	public BuildWithRectHolder y()
	{
		this.builder.posY = this.buildWith.dim().originalState().y();

		return this;
	}

	public BuildWithRectHolder x()
	{
		this.builder.posX = this.buildWith.dim().originalState().x();

		return this;
	}

	public BuildWithRectHolder addScale()
	{
		this.builder.scale += this.buildWith.dim().originalState().scale();

		return this;
	}

	public BuildWithRectHolder addWidth()
	{
		this.builder.width(this.builder.width + this.buildWith.dim().originalState().width());

		return this;
	}

	public BuildWithRectHolder addHeight()
	{
		this.builder.area(this.builder.width, this.builder.height + this.buildWith.dim().originalState().height());

		return this;
	}

	public BuildWithRectHolder addArea()
	{
		this.builder.addWidth(this.buildWith.dim().originalState().width()).addHeight(this.buildWith.dim().originalState().height());

		return this;
	}

	public BuildWithRectHolder addX()
	{
		this.builder.posX += this.buildWith.dim().originalState().x();

		return this;
	}

	public BuildWithRectHolder addY()
	{
		this.builder.posY += this.buildWith.dim().originalState().y();

		return this;
	}

	public BuildWithRectHolder addPos()
	{
		this.builder.addX(this.buildWith.dim().originalState().x()).addY(this.buildWith.dim().originalState().y());

		return this;
	}

	public RectBuilder rebuild()
	{
		return this.builder;
	}

	public Rect flush()
	{
		return this.builder.flush();
	}

}
