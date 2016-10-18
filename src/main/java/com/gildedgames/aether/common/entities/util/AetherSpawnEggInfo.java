package com.gildedgames.aether.common.entities.util;

public class AetherSpawnEggInfo
{

	private final String spawnedID;

	private final int primaryColor, secondaryColor;

	public AetherSpawnEggInfo(String id, int primaryColor, int secondaryColor)
	{
		this.spawnedID = id;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
	}

	public String getSpawnedID()
	{
		return this.spawnedID;
	}

	public int getPrimaryColor()
	{
		return this.primaryColor;
	}

	public int getSecondaryColor()
	{
		return this.secondaryColor;
	}

}
