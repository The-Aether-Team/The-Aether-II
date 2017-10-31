package com.gildedgames.aether.common.capabilities.world.chunk;

import com.gildedgames.aether.api.orbis.IChunkRendererCapability;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis_core.data.region.Region;
import com.gildedgames.aether.api.orbis_core.util.RegionHelp;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class ChunkRenderer implements IChunkRendererCapability
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

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}

	public static class Storage implements Capability.IStorage<IChunkRendererCapability>
	{
		@Override
		public NBTBase writeNBT(final Capability<IChunkRendererCapability> capability, final IChunkRendererCapability instance, final EnumFacing side)
		{
			final NBTTagCompound out = new NBTTagCompound();
			instance.write(out);

			return out;
		}

		@Override
		public void readNBT(final Capability<IChunkRendererCapability> capability, final IChunkRendererCapability instance, final EnumFacing side,
				final NBTBase nbt)
		{
			final NBTTagCompound input = (NBTTagCompound) nbt;
			instance.read(input);
		}
	}

}
