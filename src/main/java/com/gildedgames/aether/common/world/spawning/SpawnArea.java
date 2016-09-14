package com.gildedgames.aether.common.world.spawning;

import net.minecraft.util.math.ChunkPos;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SpawnArea
{

	private ChunkPos min, max;

	private int areaX, areaZ;

	private int entityCount;

	private boolean hasPlayerInside;

	public SpawnArea(int chunkArea, int areaX, int areaZ)
	{
		this.min = new ChunkPos(areaX * chunkArea, areaZ * chunkArea);
		this.max = new ChunkPos(this.min.chunkXPos + chunkArea, this.min.chunkZPos + chunkArea);

		this.areaX = areaX;
		this.areaZ = areaZ;
	}

	public void setEntityCount(int entityCount)
	{
		this.entityCount = entityCount;
	}

	public ChunkPos getMinChunkPos()
	{
		return this.min;
	}

	public ChunkPos getMaxChunkPos()
	{
		return this.max;
	}

	public int getAreaX()
	{
		return this.areaX;
	}

	public int getAreaZ()
	{
		return this.areaZ;
	}

	public void addToEntityCount(int count)
	{
		this.entityCount += count;

		this.entityCount = Math.max(0, this.entityCount);
	}

	public int getEntityCount()
	{
		return this.entityCount;
	}

	public boolean hasPlayerInside() { return this.hasPlayerInside; }

	public void setInPlayersRenderDistance(boolean flag) { this.hasPlayerInside = flag; }

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof SpawnArea)
		{
			SpawnArea area = (SpawnArea)obj;

			if (area.min.equals(this.min) && area.max.equals(this.max))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.min);
		builder.append(this.max);

		return builder.toHashCode();
	}

}
