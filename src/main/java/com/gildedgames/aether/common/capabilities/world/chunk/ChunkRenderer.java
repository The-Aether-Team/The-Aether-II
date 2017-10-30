package com.gildedgames.aether.common.capabilities.world.chunk;

import com.gildedgames.aether.api.orbis.IChunkRenderer;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.orbis.util.RegionHelp;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ChunkRenderer implements IChunkRenderer
{
	private final Region boundingBox;

	private final List<IWorldRenderer> renderers = Lists.newArrayList();

	private boolean hasBeenLoaded;

	public ChunkRenderer(final int chunkX, final int chunkZ)
	{
		this.boundingBox = new Region(new BlockPos(chunkX << 4, 0, chunkZ << 4), new BlockPos((chunkX << 4) + 16, 256, (chunkZ << 4) + 16));
	}

	@Override
	public boolean hasBeenLoaded()
	{
		return this.hasBeenLoaded;
	}

	@Override
	public void load()
	{
		this.hasBeenLoaded = true;
	}

	@Override
	public void render(final World world, final float partialTicks)
	{
		for (final IWorldRenderer renderer : this.renderers)
		{
			this.render(world, renderer, partialTicks);
		}
	}

	private void render(final World world, final IWorldRenderer renderer, final float partialTicks)
	{
		renderer.startRendering(world, partialTicks);
		
		renderer.finishRendering(world);

		for (final IWorldRenderer subRenderer : renderer.getSubRenderers(world))
		{
			this.render(world, subRenderer, partialTicks);
		}
	}

	@Override
	public void addRenderer(final IWorldRenderer object)
	{
		this.renderers.add(object);
	}

	@Override
	public boolean removeRenderer(final IWorldRenderer object)
	{
		return this.renderers.remove(object);
	}

	@Override
	public boolean shouldHave(final IWorldRenderer renderer)
	{
		return RegionHelp.intersects(renderer.getBoundingBox(), this.boundingBox);
	}

	@Override
	public List<IWorldRenderer> getRenderers()
	{
		return this.renderers;
	}

	@Override
	public IRegion getBoundingBox()
	{
		return this.boundingBox;
	}

}
