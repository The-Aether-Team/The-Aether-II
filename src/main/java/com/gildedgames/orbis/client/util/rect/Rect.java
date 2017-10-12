package com.gildedgames.orbis.client.util.rect;

public interface Rect
{

	RectBuilder rebuild();

	float degrees();

	float originX();

	float originY();

	float scale();

	float maxX();

	float maxY();

	float centerX();

	float centerY();

	Pos2D center();

	float x();

	float y();

	float width();

	float height();

	boolean isCenteredX();

	boolean isCenteredY();

	boolean intersects(float x, float y);

	boolean intersects(Rect dim);

}
