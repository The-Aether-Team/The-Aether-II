package com.gildedgames.aether.common.util;

import java.util.Arrays;
import java.util.List;

public class StringUtil
{
	public static List<String> splitNewlines(String string)
	{
		return Arrays.asList(string.split("\n"));
	}
}
