package com.gildedgames.aether.client.ui.graphics;

import com.gildedgames.aether.client.ui.data.DrawingData;
import com.gildedgames.aether.client.ui.data.rect.Rect;

public interface Graphics2D
{

	void drawSprite(Sprite sprite, Rect dim, DrawingData data);

	/**
	 * @param text
	 * @param dim Only looks at the x and y coordinate, not the width and height. The scale does matter though
	 * @param data
	 */
	void drawText(String text, Rect dim, DrawingData data);

	void drawLine(int startX, int startY, int endX, int endY, DrawingData drawingData);

	void drawRectangle(Rect dim, DrawingData data);

	void drawGradientRectangle(Rect dim, DrawingData startColor, DrawingData endColor);

}
