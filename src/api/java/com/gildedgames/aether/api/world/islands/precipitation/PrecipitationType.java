package com.gildedgames.aether.api.world.islands.precipitation;

public enum PrecipitationType
{
	NONE("none"),
	RAIN("rain"),
	SNOW("snow");

	private static final PrecipitationType[] VALUES = PrecipitationType.values();

	private final String id;

	PrecipitationType(String id)
	{
		this.id = id;
	}

	public static PrecipitationType lookup(byte ordinal)
	{
		return PrecipitationType.VALUES[ordinal];
	}

	public static PrecipitationType lookup(String name)
	{
		for (PrecipitationType type : PrecipitationType.VALUES)
		{
			if (type.name().equals(name))
			{
				return type;
			}
		}

		return null;
	}

	public String getResourceId()
	{
		return this.id;
	}
}
