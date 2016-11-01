package com.gildedgames.aether.client.ui.util;

import java.util.List;

public interface Font
{

	float getWidth(String text);

	float getHeight(String text);

	List<String> splitStringsIntoArea(String text, float width);

}
