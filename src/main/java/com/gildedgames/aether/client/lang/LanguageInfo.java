package com.gildedgames.aether.client.lang;

import com.google.gson.annotations.SerializedName;

public class LanguageInfo
{
	@SerializedName("version")
	private int version;

	public int getVersion()
	{
		return this.version;
	}
}
