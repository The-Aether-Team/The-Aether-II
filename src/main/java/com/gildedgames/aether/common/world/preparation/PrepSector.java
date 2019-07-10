package com.gildedgames.aether.common.world.preparation;

import com.gildedgames.aether.api.world.preparation.IPrepSector;
import com.gildedgames.aether.api.world.preparation.IPrepSectorData;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.util.math.ChunkPos;

public class PrepSector implements IPrepSector
{
	private final LongOpenHashSet watchingChunks = new LongOpenHashSet();

	private final IntOpenHashSet watchingPlayers = new IntOpenHashSet();

	private IPrepSectorData data;

	private int dormantTicks;

	public PrepSector(IPrepSectorData data)
	{
		this.data = data;
	}

	@Override
	public IPrepSectorData getData()
	{
		return this.data;
	}

	@Override
	public boolean addWatchingChunk(final int chunkX, final int chunkZ)
	{
		return this.watchingChunks.add(ChunkPos.asLong(chunkX, chunkZ));
	}

	@Override
	public boolean removeWatchingChunk(final int chunkX, final int chunkZ)
	{
		return this.watchingChunks.remove(ChunkPos.asLong(chunkX, chunkZ));
	}

	@Override
	public void addWatchingPlayer(int entityId)
	{
		this.watchingPlayers.add(entityId);
	}

	@Override
	public void removeWatchingPlayer(int entityId)
	{
		this.watchingPlayers.remove(entityId);
	}

	@Override
	public boolean hasWatchers()
	{
		return !this.watchingChunks.isEmpty() || !this.watchingPlayers.isEmpty();
	}

	@Override
	public int getDormantTicks()
	{
		return this.dormantTicks;
	}

	@Override
	public void tick()
	{
		if (this.hasWatchers())
		{
			this.dormantTicks = 0;
		}
		else
		{
			this.dormantTicks++;
		}
	}
}
