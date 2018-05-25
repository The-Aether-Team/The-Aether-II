package com.gildedgames.aether.api.world.islands.precipitation;

public enum PrecipitationStrength
{
	LIGHT("light"),
	HEAVY("heavy"),
	STORM("storm");

	public static final PrecipitationStrength[] VALUES = PrecipitationStrength.values();

	private final String id;

	PrecipitationStrength(String id)
	{
		this.id = id;
	}

	public static PrecipitationStrength lookup(String name)
	{
		for (PrecipitationStrength type : PrecipitationStrength.VALUES)
		{
			if (type.name().equals(name))
			{
				return type;
			}
		}

		return PrecipitationStrength.LIGHT;
	}

	public String getResourceId()
	{
		return this.id;
	}
}
