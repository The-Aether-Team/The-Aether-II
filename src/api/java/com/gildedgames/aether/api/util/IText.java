package com.gildedgames.aether.api.util;

import net.minecraft.util.text.ITextComponent;

public interface IText extends NBT
{
	ITextComponent component();

	float scaledHeight();

	float scaledWidth();

	float scale();

	float width();

	float height();
}
