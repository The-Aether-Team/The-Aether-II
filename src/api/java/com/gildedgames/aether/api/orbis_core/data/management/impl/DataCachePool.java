package com.gildedgames.aether.api.orbis_core.data.management.impl;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCache;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCachePool;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class DataCachePool implements IDataCachePool
{
	public static final String EXTENSION = "cache";

	private final File location;

	private Map<String, IDataCache> idToCache = Maps.newHashMap();

	public DataCachePool(final File location)
	{
		if (!location.exists() && !location.mkdirs())
		{
			throw new RuntimeException("Directory for DataCachePool cannot be created!");
		}

		if (!location.isDirectory())
		{
			throw new IllegalArgumentException("File passed into DataCachePool is not a directory!");
		}

		this.location = location;
	}

	private void readCacheFromDisk(final File file)
	{
		try (FileInputStream in = new FileInputStream(file))
		{
			final NBTTagCompound tag = CompressedStreamTools.readCompressed(in);

			final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

			final IDataCache cache = funnel.get(EXTENSION);

			this.registerCache(cache);
		}
		catch (final IOException e)
		{
			OrbisCore.LOGGER.catching(e);
		}
	}

	private void saveCacheToDisk(final IDataCache cache)
	{
		final File cacheFile = new File(this.location, cache.getCacheId() + "." + EXTENSION);

		try (FileOutputStream out = new FileOutputStream(cacheFile))
		{
			final NBTTagCompound tag = new NBTTagCompound();
			final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

			funnel.set(EXTENSION, cache);

			CompressedStreamTools.writeCompressed(tag, out);
		}
		catch (final IOException e)
		{
			OrbisCore.LOGGER.error("Failed to save IDataCache to disk", e);
		}
	}

	@Override
	public void flushToDisk()
	{
		this.idToCache.values().forEach(this::saveCacheToDisk);
	}

	@Override
	public void readFromDisk()
	{
		try (Stream<Path> paths = Files.walk(Paths.get(this.location.getPath())))
		{
			paths.forEach(p ->
			{
				final File file = p.toFile();

				final String extension = FilenameUtils.getExtension(file.getName());

				/** Prevents the path walking from including non-cache files **/
				if (!extension.equals(EXTENSION))
				{
					return;
				}

				this.readCacheFromDisk(file);
			});
		}
		catch (final IOException e)
		{
			OrbisCore.LOGGER.error(e);
		}
	}

	@Override
	public void registerCache(final IDataCache cache)
	{
		if (cache == null)
		{
			throw new IllegalArgumentException("The cache you're trying to register to this DataCachePool is null.");
		}

		this.idToCache.put(cache.getCacheId(), cache);
	}

	@Nullable
	@Override
	public <T extends IDataCache> T findCache(final String cacheID)
	{
		final IDataCache cache = this.idToCache.get(cacheID);

		if (cache == null)
		{
			OrbisCore.LOGGER.warn("The cache you attempted to find could not be found. Something might be wrong.");
		}

		return (T) cache;
	}

	@Override
	public NBTTagCompound writeCacheData()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.setStringMap("idToCache", this.idToCache);

		return tag;
	}

	@Override
	public void readCacheData(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.idToCache = funnel.getStringMap("idToCache");
	}
}
