package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.math.ChunkPos;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SpawnArea
{

	private final ChunkPos min;

	private final ChunkPos max;

	private final int areaX;

	private final int areaZ;

	private int entityCount;

	private boolean hasPlayerInside;

	public SpawnArea(final int chunkArea, final int areaX, final int areaZ)
	{
		this.min = new ChunkPos(areaX * chunkArea, areaZ * chunkArea);
		this.max = new ChunkPos(this.min.x + chunkArea, this.min.z + chunkArea);

		this.areaX = areaX;
		this.areaZ = areaZ;
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

	public void addToEntityCount(final int count)
	{
		this.entityCount += count;

		if (this.entityCount < 0)
		{
			AetherCore.LOGGER
					.warn("Something has gone horribly wrong! The entity count in a SpawnArea object has become negative. Please warn the devs so they can fix this bug.");
		}

		this.entityCount = Math.max(0, this.entityCount);
	}

	public int getEntityCount()
	{
		return this.entityCount;
	}

	public void setEntityCount(final int entityCount)
	{
		this.entityCount = entityCount;
	}

	public boolean hasPlayerInside()
	{
		return this.hasPlayerInside;
	}

	public void setInPlayersRenderDistance(final boolean flag)
	{
		this.hasPlayerInside = flag;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof SpawnArea)
		{
			final SpawnArea area = (SpawnArea) obj;

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
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.min);
		builder.append(this.max);

		return builder.toHashCode();
	}

}
