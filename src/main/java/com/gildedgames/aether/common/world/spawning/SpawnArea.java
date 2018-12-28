package com.gildedgames.aether.common.world.spawning;

import com.gildedgames.aether.api.world.ISpawnArea;
import com.gildedgames.aether.common.AetherCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

public class SpawnArea implements ISpawnArea
{
	private final ChunkPos min;

	private final ChunkPos max;

	private final int areaX;

	private final int areaZ;

	private int entityCount;

	private boolean hasPlayerInside;

	private boolean dirty = false;

	public SpawnArea(final int chunkArea, final int areaX, final int areaZ)
	{
		this.min = new ChunkPos(areaX * chunkArea, areaZ * chunkArea);
		this.max = new ChunkPos(this.min.x + chunkArea, this.min.z + chunkArea);

		this.areaX = areaX;
		this.areaZ = areaZ;
	}

	@Override
	public ChunkPos getMinChunkPos()
	{
		return this.min;
	}

	@Override
	public ChunkPos getMaxChunkPos()
	{
		return this.max;
	}

	@Override
	public int getAreaX()
	{
		return this.areaX;
	}

	@Override
	public int getAreaZ()
	{
		return this.areaZ;
	}

	@Override
	public void addToEntityCount(final int count)
	{
		int entityCount = this.entityCount;
		entityCount += count;

		if (entityCount < 0)
		{
			AetherCore.LOGGER
					.warn("Something has gone horribly wrong! The entity count in a SpawnArea object has become negative. Please warn the devs so they can fix this bug.");
		}

		this.entityCount = Math.max(0, entityCount);

		this.markDirty();
	}

	@Override
	public int getEntityCount()
	{
		return this.entityCount;
	}

	@Override
	public void setEntityCount(int entityCount)
	{
		this.entityCount = entityCount;

		this.markDirty();
	}

	@Override
	public boolean hasPlayerInside()
	{
		return this.hasPlayerInside;
	}

	@Override
	public void setInPlayersRenderDistance(final boolean flag)
	{
		this.hasPlayerInside = flag;
	}

	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}

	@Override
	public void markDirty()
	{
		this.dirty = true;
	}

	@Override
	public void markClean()
	{
		this.dirty = false;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (o == null || this.getClass() != o.getClass())
		{
			return false;
		}

		SpawnArea area = (SpawnArea) o;

		return this.areaX == area.areaX &&
				this.areaZ == area.areaZ &&
				this.entityCount == area.entityCount &&
				this.hasPlayerInside == area.hasPlayerInside &&
				this.dirty == area.dirty &&
				Objects.equals(this.min, area.min) &&
				Objects.equals(this.max, area.max);
	}

	@Override

	public int hashCode()
	{
		final HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(this.min);
		builder.append(this.max);

		return builder.toHashCode();
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("EntityCount", this.entityCount);

		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.entityCount = nbt.getInteger("EntityCount");
	}
}
