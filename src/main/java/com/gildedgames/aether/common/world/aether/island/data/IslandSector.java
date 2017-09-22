package com.gildedgames.aether.common.world.aether.island.data;

import com.gildedgames.aether.api.world.ISector;
import com.gildedgames.aether.api.world.islands.IIslandData;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class IslandSector implements ISector
{
	public static final int CHUNK_SIZE = 40;

	private final World world;

	private final TLongSet watching = new TLongHashSet();

	private final Collection<IIslandData> data = new ArrayList<>();

	private int sectorX, sectorZ;

	private long seed;

	private boolean dirty;

	public IslandSector(final World world, final NBTTagCompound tag)
	{
		this.world = world;
		this.read(tag);
	}

	public IslandSector(final World world, final int sectorX, final int sectorZ, final long seed, final Collection<IIslandData> islands)
	{
		this.world = world;
		this.sectorX = sectorX;
		this.sectorZ = sectorZ;

		this.seed = seed;

		this.data.addAll(islands);
	}

	public Collection<IIslandData> getIslands()
	{
		return Collections.unmodifiableCollection(this.data);
	}

	@Override
	public Collection<IIslandData> getIslandsForRegion(final int x, final int y, final int z, final int width, final int height, final int length)
	{
		final HashSet<IIslandData> matching = new HashSet<>();

		for (final IIslandData island : this.data)
		{
			if (island.getBounds().intersects(x, y, z, x + width, y + height, z + length))
			{
				matching.add(island);
			}
		}

		return matching;
	}

	@Override
	public int getX()
	{
		return this.sectorX;
	}

	@Override
	public int getZ()
	{
		return this.sectorZ;
	}

	@Override
	public long getSeed()
	{
		return this.seed;
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
	public void addWatchingChunk(final int chunkX, final int chunkZ)
	{
		this.watching.add(ChunkPos.asLong(chunkX, chunkZ));
	}

	@Override
	public void removeWatchingChunk(final int chunkX, final int chunkZ)
	{
		this.watching.remove(ChunkPos.asLong(chunkX, chunkZ));
	}

	@Override
	public boolean hasWatchers()
	{
		return this.watching.isEmpty();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setInteger("XPos", this.getX());
		tag.setInteger("ZPos", this.getZ());

		tag.setLong("Seed", this.getSeed());

		final NBTTagList islands = new NBTTagList();

		for (final IIslandData island : this.data)
		{
			final NBTTagCompound islandData = new NBTTagCompound();
			island.write(islandData);

			islands.appendTag(islandData);
		}

		tag.setTag("Islands", islands);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.sectorX = tag.getInteger("XPos");
		this.sectorZ = tag.getInteger("ZPos");

		this.seed = tag.getLong("Seed");

		final NBTTagList islands = tag.getTagList("Islands", 10);

		this.data.clear();

		for (int i = 0; i < islands.tagCount(); i++)
		{
			final NBTTagCompound islandData = islands.getCompoundTagAt(i);

			this.data.add(new IslandData(this.world, islandData));
		}
	}
}
