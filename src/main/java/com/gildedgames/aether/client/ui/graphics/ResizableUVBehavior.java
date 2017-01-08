package com.gildedgames.aether.client.ui.graphics;

import com.gildedgames.aether.client.ui.data.rect.Dim2D;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.data.rect.RectHolder;

import java.util.ArrayList;
import java.util.List;

public class ResizableUVBehavior implements UVBehavior
{

	private final Sprite.UV topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;

	private final Sprite.UV leftSide, topSide, rightSide, bottomSide;

	private final Sprite.UV center;

	private final List<UVDimPair> drawnUVs = new ArrayList<>();

	private Rect lastAreaToDraw;

	public ResizableUVBehavior(Sprite.UV cornersArea, Sprite.UV verticalArea, Sprite.UV horizontalArea)
	{
		this.topLeftCorner = cornersArea.clone().min(0, 0).flush();
		this.topRightCorner = cornersArea.clone().min(this.topLeftCorner.width() + horizontalArea.width(), 0).flush();
		this.bottomLeftCorner = cornersArea.clone().min(0, this.topLeftCorner.height() + verticalArea.height()).flush();
		this.bottomRightCorner = cornersArea.clone().min(
				this.topLeftCorner.width() + horizontalArea.width(), this.topLeftCorner.height() + verticalArea.height()).flush();

		this.leftSide = verticalArea.clone().min(0, this.topLeftCorner.height()).flush();
		this.rightSide = verticalArea.clone().min(
				this.topLeftCorner.width() + horizontalArea.width(), this.topRightCorner.height()).flush();

		this.topSide = horizontalArea.clone().min(this.topLeftCorner.width(), 0).flush();
		this.bottomSide = horizontalArea.clone().min(this.topLeftCorner.width(),
				this.topLeftCorner.height() + verticalArea.height()).flush();

		float centerWidth = this.topRightCorner.minU() - this.topLeftCorner.maxU();
		float centerHeight = this.bottomRightCorner.minV() - this.topLeftCorner.maxV();

		this.center = Sprite.UV.build().min(this.topLeftCorner.maxU(), this.topLeftCorner.maxV()).area(centerWidth, centerHeight).flush();
	}

	@Override
	public List<UVDimPair> getDrawnUVsFor(Sprite sprite, RectHolder areaToDraw)
	{
		return this.drawnUVs;
	}

	@Override
	public boolean shouldRecalculateUVs(Sprite sprite, RectHolder areaToDraw)
	{
		return this.lastAreaToDraw == null || this.lastAreaToDraw.width() != areaToDraw.dim().width()
				|| this.lastAreaToDraw.height() != areaToDraw.dim().height();
	}

	@Override
	public void recalculateUVs(Sprite sprite, RectHolder areaToDraw)
	{
		this.drawnUVs.clear();

		Rect area = areaToDraw.dim();

		this.drawnUVs.add(new UVDimPair(this.topLeftCorner, Dim2D.build().flush()));
		this.drawnUVs.add(new UVDimPair(this.topRightCorner, Dim2D.build().x(area.width() - this.topRightCorner.width()).flush()));

		this.drawnUVs.add(new UVDimPair(this.bottomLeftCorner, Dim2D.build().y(area.height() - this.topLeftCorner.height()).flush()));
		this.drawnUVs.add(new UVDimPair(this.bottomRightCorner, Dim2D.build().x(area.width() - this.topRightCorner.width()).y(
				area.height() - this.topLeftCorner.height()).flush()));

		for (float xStart = this.topLeftCorner.width(); xStart < area.width(); xStart += this.topSide.width())
		{
			float width = Math.min(this.topSide.width(), area.width() - xStart - this.topRightCorner.width());

			this.drawnUVs.add(new UVDimPair(this.topSide.clone().width(width).flush(), Dim2D.build().x(xStart).flush()));
			this.drawnUVs.add(new UVDimPair(this.bottomSide.clone().width(width).flush(), Dim2D.build().y(
					area.height() - this.topLeftCorner.height()).x(xStart).flush()));
		}

		for (float yStart = this.topLeftCorner.height(); yStart < area.height(); yStart += this.leftSide.height())
		{
			float height = Math.min(this.leftSide.height(), area.height() - yStart - this.bottomLeftCorner.height());

			this.drawnUVs.add(new UVDimPair(this.leftSide.clone().height(height).flush(), Dim2D.build().y(yStart).flush()));
			this.drawnUVs.add(new UVDimPair(this.rightSide.clone().height(height).flush(), Dim2D.build().x(
					area.width() - this.topLeftCorner.width()).y(yStart).flush()));
		}

		for (float xStart = this.topLeftCorner.width(); xStart < area.width(); xStart += this.center.width())
		{
			for (float yStart = this.topLeftCorner.height(); yStart < area.height(); yStart += this.center.height())
			{
				float width = Math.min(this.center.width(), area.width() - xStart - this.topRightCorner.width());
				float height = Math.min(this.center.height(), area.height() - yStart - this.bottomLeftCorner.height());

				this.drawnUVs.add(new UVDimPair(this.center.clone().width(width).height(height).flush(), Dim2D.build().x(xStart).y(yStart).flush()));
			}
		}

		this.lastAreaToDraw = areaToDraw.dim();
	}

}
