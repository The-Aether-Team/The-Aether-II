package com.gildedgames.orbis.client.gui.data;

import net.minecraft.util.text.ITextComponent;

public interface IText
{
	ITextComponent component();

	float scaledHeight();

	float scaledWidth();

	float scale();

	float width();

	float height();
}
