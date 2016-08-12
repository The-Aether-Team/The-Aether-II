package com.gildedgames.aether.common.entities.util;

public enum AnimalGender
{

	MALE, FEMALE;

	public static AnimalGender get(String name)
	{
		for (AnimalGender type : values())
		{
			if (type.name().equalsIgnoreCase(name))
			{
				return type;
			}
		}

		return null;
	}

}
