package com.gildedgames.aether.client.ui.data.rect;

import com.gildedgames.aether.client.ui.data.Pos2D;
import com.gildedgames.aether.client.ui.input.InputProvider;
import com.gildedgames.aether.client.ui.util.rect.RectCollection;

import java.util.Arrays;
import java.util.List;

public class Dim2D implements Rect
{

	final float posX, posY, maxPosX, maxPosY;

	final float degrees;

	final float originX, originY;

	final float width, height;

	final boolean centeredX, centeredY;

	final Pos2D center;

	final float scale;

	Dim2D()
	{
		this(new RectBuilder());
	}

	Dim2D(RectBuilder builder)
	{
		this.posX = builder.posX;
		this.posY = builder.posY;

		this.width = builder.width;
		this.height = builder.height;

		this.scale = builder.scale;

		this.centeredX = builder.centeredX;
		this.centeredY = builder.centeredY;

		this.degrees = builder.degrees;

		this.originX = builder.originX;
		this.originY = builder.originY;

		this.maxPosX = this.posX + this.width;
		this.maxPosY = this.posY + this.height;

		this.center = Pos2D.flush(this.posX + (this.width / 2), this.posY + (this.height / 2));
	}

	@Override
	public RectBuilder rebuild()
	{
		return Dim2D.build(this);
	}

	@Override
	public float degrees()
	{
		return this.degrees;
	}

	@Override
	public float originX()
	{
		return this.originX;
	}

	@Override
	public float originY()
	{
		return this.originY;
	}

	@Override
	public float scale()
	{
		return this.scale;
	}

	@Override
	public float maxX()
	{
		return this.maxPosX;
	}

	@Override
	public float maxY()
	{
		return this.maxPosY;
	}

	@Override
	public float x()
	{
		return this.posX;
	}

	@Override
	public float y()
	{
		return this.posY;
	}

	@Override
	public float width()
	{
		return this.width;
	}

	@Override
	public float height()
	{
		return this.height;
	}

	@Override
	public boolean isCenteredX()
	{
		return this.centeredX;
	}

	@Override
	public boolean isCenteredY()
	{
		return this.centeredY;
	}

	@Override
	public boolean intersects(float x, float y)
	{
		return x >= this.x() && y >= this.y() && x < this.maxX() && y < this.maxY();
	}

	@Override
	public boolean intersects(Rect dim)
	{
		return dim.maxX() >= this.x() && dim.maxY() >= this.y() && dim.x() < this.maxX() && dim.y() < this.maxY();
	}

	@Override
	public boolean isHovered(InputProvider input)
	{
		return this.intersects(input.getMouseX(), input.getMouseY());
	}

	@Override
	public RectBuilder clone()
	{
		return new RectBuilder(this);
	}

	public RectHolder toHolder()
	{
		return RectCollection.flush(this);
	}

	/**
	 * Creates an empty IRect object with default values
	 * @return
	 */
	public static Rect flush()
	{
		return new Dim2D();
	}

	public static RectBuilder build()
	{
		return new RectBuilder();
	}

	public static RectBuilder build(RectHolder holder)
	{
		return new RectBuilder(holder);
	}

	public static RectBuilder build(Rect dim)
	{
		return new RectBuilder(dim);
	}

	public static Rect combine(Rect... dimensions)
	{
		return Dim2D.combine(Arrays.asList(dimensions));
	}

	public static Rect combine(List<Rect> dimensions)
	{
		RectBuilder result = new RectBuilder();

		if (dimensions.isEmpty())
		{
			throw new IllegalArgumentException();
		}

		float overallScale = 0.0F;

		int validDimensions = 0;

		Rect rect1 = dimensions.get(0);

		result.pos(rect1.x(), rect1.y()).area(rect1.maxX() - rect1.x(), rect1.maxY() - rect1.y());

		for (Rect dim : dimensions)
		{
			if (dim != null)
			{
				Rect preview = result.flush();

				float minX = Math.min(preview.x(), dim.x());
				float minY = Math.min(preview.y(), dim.y());

				float maxX = Math.max(preview.x() + preview.width(), dim.x() + dim.width());
				float maxY = Math.max(preview.y() + preview.height(), dim.y() + dim.height());

				result.pos(minX, minY).area(maxX - minX, maxY - minY);

				overallScale += dim.scale();

				validDimensions++;
			}
		}

		result.scale(overallScale / validDimensions);

		return result.flush();
	}

	@Override
	public String toString()
	{
		String link = ", ";

		return "X " + this.posX + " Y " + this.posY + link + "Area() Width: '" + this.width() + "', Height: '" + this.height() + "'" + link + "Centered() X: '" + this.centeredX + "', Y: '" + this.centeredY + "'" + link + "Scale() Value: '" + this.scale() + "'";
	}

	@Override
	public boolean equals(Object obj)
	{
		Rect dim;

		if (obj instanceof Rect)
		{
			dim = (Rect) obj;
		}
		else if (obj instanceof RectHolder)
		{
			RectHolder holder = (RectHolder) obj;

			dim = holder.dim();
		}
		else
		{
			return false;
		}

		return !(dim.x() != this.x() || dim.y() != this.y() || dim.scale() != this.scale() || dim.isCenteredX() != this.centeredX
				|| dim.isCenteredY() != this.centeredY || dim.width() != this.width() || dim.height() != this.height()
				|| dim.degrees() != this.degrees() || dim.originX() != this.originX() || dim.originY() != this.originY());
	}

	@Override
	public float centerX()
	{
		return this.center.x();
	}

	@Override
	public float centerY()
	{
		return this.center.y();
	}

	@Override
	public Pos2D center()
	{
		return this.center;
	}

}
