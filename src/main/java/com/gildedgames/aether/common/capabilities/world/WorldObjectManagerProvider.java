package com.gildedgames.aether.common.capabilities.world;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IChunkRendererProvider;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.orbis.IWorldObjectManagerProvider;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.world.chunk.ChunkRendererProvider;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class WorldObjectManagerProvider implements IWorldObjectManagerProvider
{
	private final Map<Integer, IChunkRendererProvider> dimensionToRenderer = Maps.newHashMap();

	private Map<Integer, IWorldObjectManager> dimensionToManager = Maps.newHashMap();

	private File writeTo;

	private WorldObjectManagerProvider()
	{

	}

	public WorldObjectManagerProvider(final File writeTo) throws IOException
	{
		if (!writeTo.exists() && !writeTo.mkdirs())
		{
			throw new IOException("Something went wrong trying to create the world object manager provider directory!");
		}

		this.writeTo = writeTo;
	}

	@Override
	public IChunkRendererProvider getRendererForDimension(final int dimension)
	{
		if (!this.dimensionToRenderer.containsKey(dimension))
		{
			final ChunkRendererProvider provider = new ChunkRendererProvider(dimension);

			MinecraftForge.EVENT_BUS.register(provider);

			this.dimensionToRenderer.put(dimension, provider);
		}

		return this.dimensionToRenderer.get(dimension);
	}

	@Override
	public IChunkRendererProvider getRendererForWorld(final World world)
	{
		return this.getRendererForDimension(world.provider.getDimension());
	}

	@Override
	public IChunkRendererProvider getRendererForDimension(final DimensionType type)
	{
		return this.getRendererForDimension(type.getId());
	}

	@Override
	public IWorldObjectManager getForWorld(final World world)
	{
		return this.getForDimension(world.provider.getDimension());
	}

	@Override
	public IWorldObjectManager getForDimension(final int dimension)
	{
		if (!this.dimensionToManager.containsKey(dimension))
		{
			this.dimensionToManager.put(dimension, new WorldObjectManager(DimensionManager.getWorld(dimension)));
		}

		return this.dimensionToManager.get(dimension);
	}

	@Override
	public IWorldObjectManager getForDimension(final DimensionType type)
	{
		return this.getForDimension(type.getId());
	}

	@Override
	public void write()
	{
		final File file = new File(this.writeTo, "world_objects.nbt");

		final NBTTagCompound tag = new NBTTagCompound();

		try (FileOutputStream out = new FileOutputStream(file))
		{
			final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

			funnel.setIntMap("data", this.dimensionToManager);

			CompressedStreamTools.writeCompressed(tag, out);
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to save sector to disk", e);
		}

		for (final IChunkRendererProvider provider : this.dimensionToRenderer.values())
		{
			MinecraftForge.EVENT_BUS.unregister(provider);
		}

		this.dimensionToRenderer.clear();
		this.dimensionToManager.clear();
	}

	@Override
	public void read()
	{
		final File file = new File(this.writeTo, "world_objects.nbt");

		if (!file.exists())
		{
			return;
		}

		try (FileInputStream stream = new FileInputStream(file))
		{
			final NBTTagCompound tag = CompressedStreamTools.readCompressed(stream);

			final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

			this.dimensionToManager = funnel.getIntMap("data");
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error("Failed to read sector from disk", e);
		}
	}
}
