package com.gildedgames.aether.api.damagetypes;

public enum AetherDamageType
{
	SLASH("aether.damagetype.slash"), PIERCE("aether.damagetype.pierce"), IMPACT("aether.damagetype.impact");

	private final String localizationKey;

	AetherDamageType(String name)
	{
		this.localizationKey = name;
	}

	public String getLocalizationKey()
	{
		return this.localizationKey;
	}
}
