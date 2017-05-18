package com.gildedgames.aether.common.world.dimensions.aether.island.logic;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.util.helpers.NBTHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;

import java.util.HashSet;
import java.util.List;

public class IslandSector implements NBT
{

	public final static int CHUNK_WIDTH_PER_SECTOR = 40;

	private int sectorX, sectorY;

	private long seed;

	private IslandData[] data;

	private final HashSet<Long> loadedChunks = Sets.newHashSet();

	public IslandSector(NBTTagCompound tag)
	{
		this.read(tag);
	}

	public IslandSector(final int sectorX, final int sectorY, final long seed, final IslandData... data)
	{
		this.sectorX = sectorX;
		this.sectorY = sectorY;

		this.seed = seed;

		this.data = data;
	}

	public IslandData[] getAllIslandData()
	{
		return this.data;
	}

	public List<IslandData> getIslandDataAtBlockPos(final int x, final int y)
	{
		final List<IslandData> data = Lists.newArrayList();

		for (final IslandData island : this.data)
		{
			if (island != null && island.getBounds().intersects(x, y, 1, 1) && !data.contains(island))
			{
				data.add(island);
			}
		}

		return data;
	}

	public int getSectorX()
	{
		return this.sectorX;
	}

	public int getSectorY()
	{
		return this.sectorY;
	}

	public long getSeed()
	{
		return this.seed;
	}

	protected boolean isChunkLoaded(final int chunkX, final int chunkZ)
	{
		return this.loadedChunks.contains(ChunkPos.asLong(chunkX, chunkZ));
	}

	protected void trackLoadedChunk(final int chunkX, final int chunkZ)
	{
		final long hash = ChunkPos.asLong(chunkX, chunkZ);

		if (!this.loadedChunks.contains(hash))
		{
			this.loadedChunks.add(hash);
		}
	}

	protected boolean untrackLoadedChunk(final int chunkX, final int chunkZ)
	{
		return this.loadedChunks.remove(ChunkPos.asLong(chunkX, chunkZ));
	}

	protected boolean isLoadedChunksEmpty()
	{
		return this.loadedChunks.isEmpty();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setInteger("x", this.getSectorX());
		tag.setInteger("y", this.getSectorY());

		tag.setLong("seed", this.getSeed());

		NBTTagList islands = new NBTTagList();

		for (IslandData island : this.data)
		{
			NBTTagCompound islandData = new NBTTagCompound();
			island.write(islandData);

			islands.appendTag(islandData);
		}

		tag.setTag("islands", islands);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.sectorX = tag.getInteger("x");
		this.sectorY = tag.getInteger("y");

		this.seed = tag.getLong("seed");

		NBTTagList islands = tag.getTagList("islands", 10);

		this.data = new IslandData[islands.tagCount()];

		for (int i = 0; i < islands.tagCount(); i++)
		{
			NBTTagCompound islandData = islands.getCompoundTagAt(i);

			this.data[i] = new IslandData(islandData);
		}
	}
}
