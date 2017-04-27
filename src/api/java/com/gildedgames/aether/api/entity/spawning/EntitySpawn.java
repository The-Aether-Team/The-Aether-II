package com.gildedgames.aether.api.entity.spawning;

public class EntitySpawn
{

	private final int dim, areaX, areaZ;

	private final String spawnHandlerUniqueID;

	public EntitySpawn(String spawnHandlerUniqueID, int dim, int areaX, int areaZ)
	{
		this.spawnHandlerUniqueID = spawnHandlerUniqueID;

		this.dim = dim;

		this.areaX = areaX;
		this.areaZ = areaZ;
	}

	public String getSpawnHandlerUniqueID()
	{
		return this.spawnHandlerUniqueID;
	}

	public int getDim()
	{
		return this.dim;
	}

	public int getAreaX()
	{
		return this.areaX;
	}

	public int getAreaZ()
	{
		return this.areaZ;
	}

}
