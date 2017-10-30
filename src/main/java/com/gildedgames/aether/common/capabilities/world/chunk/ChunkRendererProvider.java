package com.gildedgames.aether.common.capabilities.world.chunk;

import com.gildedgames.aether.api.orbis.IChunkRenderer;
import com.gildedgames.aether.api.orbis.IChunkRendererProvider;
import com.google.common.collect.Maps;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class ChunkRendererProvider implements IChunkRendererProvider
{
	private final Map<ChunkPos, IChunkRenderer> posToRenderer = Maps.newHashMap();

	private final int dimension;

	public ChunkRendererProvider(final int dimension)
	{
		this.dimension = dimension;
	}

	@Override
	public IChunkRenderer get(final int chunkX, final int chunkZ)
	{
		return this.posToRenderer.get(new ChunkPos(chunkX, chunkZ));
	}

	@SubscribeEvent
	public void onChunkLoad(final ChunkEvent.Load event)
	{
		if (event.getWorld().provider.getDimension() == this.dimension)
		{
			final ChunkPos pos = event.getChunk().getPos();

			this.posToRenderer.put(pos, new ChunkRenderer(pos.chunkXPos, pos.chunkZPos));
		}
	}

	@SubscribeEvent
	public void onChunkUnload(final ChunkEvent.Unload event)
	{
		if (event.getWorld().provider.getDimension() == this.dimension)
		{
			final ChunkPos pos = event.getChunk().getPos();

			this.posToRenderer.remove(pos);
		}
	}

}
