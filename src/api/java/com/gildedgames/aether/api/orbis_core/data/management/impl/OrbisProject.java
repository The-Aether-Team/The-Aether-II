package com.gildedgames.aether.api.orbis_core.data.management.impl;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.management.*;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class OrbisProject implements IProject
{
	private final List<IProjectListener> listeners = Lists.newArrayList();

	private final List<String> acceptedFileExtensions = Lists.newArrayList();

	private IProjectCache cache;

	private URI jarLocation;

	private File locationFile;

	private IProjectIdentifier identifier;

	private IProjectMetadata metadata;

	private OrbisProject()
	{
		this.cache = new OrbisProjectCache(this);

		this.acceptedFileExtensions.add("blueprint");
	}

	/**
	 * Should be used to load back an existing project.
	 * @param location
	 */
	private OrbisProject(final File location)
	{
		this();

		this.jarLocation = location.toURI();
		this.locationFile = location;
	}

	/**
	 * Should be used to createTE a new project rather than an existing one.
	 * @param location The jarLocation of the project.
	 * @param identifier The unique identifier for the project.
	 */
	public OrbisProject(final File location, final IProjectIdentifier identifier)
	{
		this();

		if (!location.exists() && !location.mkdirs())
		{
			throw new RuntimeException("Location for OrbisProject cannot be created!");
		}

		if (!location.isDirectory())
		{
			throw new IllegalArgumentException("Location file passed into OrbisProject is not a directory!");
		}

		this.jarLocation = location.toURI();
		this.locationFile = location;

		this.identifier = identifier;

		this.metadata = new ProjectMetadata();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		tag.setInteger("nextDataId", this.cache.getNextDataId());

		funnel.set("identifier", this.identifier);
		funnel.set("metadata", this.metadata);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		this.cache.setNextDataId(tag.getInteger("nextDataId"));

		this.identifier = funnel.get("identifier");
		this.metadata = funnel.get("metadata");
	}

	@Nullable
	@Override
	public File getLocationAsFile()
	{
		return this.locationFile;
	}

	@Override
	public void setLocationAsFile(final File location)
	{
		this.locationFile = location;
	}

	@Override
	public URI getJarLocation()
	{
		return this.jarLocation;
	}

	@Override
	public void setJarLocation(final URI uri)
	{
		this.jarLocation = uri;
	}

	@Override
	public void addListener(final IProjectListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public boolean removeListener(final IProjectListener listener)
	{
		return this.listeners.remove(listener);
	}

	@Override
	public IProjectIdentifier getProjectIdentifier()
	{
		return this.identifier;
	}

	@Override
	public IProjectMetadata getMetadata()
	{
		return this.metadata;
	}

	@Override
	public IProjectCache getCache()
	{
		return this.cache;
	}

	@Override
	public void setCache(final IProjectCache cache)
	{
		cache.setProject(this);

		this.cache = cache;
	}

	@Override
	public void writeData(final IData data, final File file)
	{
		try (FileOutputStream out = new FileOutputStream(file))
		{
			final NBTTagCompound tag = new NBTTagCompound();
			final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

			funnel.set("data", data);

			CompressedStreamTools.writeCompressed(tag, out);
		}
		catch (final IOException e)
		{
			OrbisCore.LOGGER.error("Failed to save project data to disk", e);
		}
	}

	@Override
	public void loadAndCacheData()
	{
		try
		{
			/**
			 * Uses the file jarLocation if accessible (used on Orbis client)
			 * Otherwise, uses URI and accesses from MC server resource
			 * so it works when stored in a mod jar.
			 */
			final URI projectUri = this.jarLocation != null ? MinecraftServer.class.getResource(this.jarLocation.getPath()).toURI() : null;
			final Path path = this.locationFile != null ? Paths.get(this.locationFile.getPath()) : Paths.get(projectUri);

			try (Stream<Path> paths = Files.walk(path))
			{
				paths.forEach(p ->
				{
					final URI uri = p.toUri();

					/** Prevents the path walking from including the project's jarLocation **/
					if (uri.getPath().equals(this.locationFile != null ? this.locationFile.getPath() : this.jarLocation.getPath()))
					{
						return;
					}

					final String extension = FilenameUtils.getExtension(uri.getPath());

					/** Prevents the path walking from including the project data itself (hidden file) **/
					if (extension.equals("project"))
					{
						return;
					}

					/** Make sure the file has an extension type accepted by this project **/
					if (this.acceptedFileExtensions.contains(extension))
					{
						try
						{
							final File file = new File(uri);
							final String loc = file.exists() ? file.getCanonicalPath() : uri.getPath();

							try (InputStream in = !file.exists() ? MinecraftServer.class.getResourceAsStream(loc) : new FileInputStream(file))
							{
								final NBTTagCompound tag = CompressedStreamTools.readCompressed(in);

								final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

								final IData data = funnel.get("data");

								if (data != null)
								{
									final boolean fromOtherProject = !this.identifier.equals(data.getMetadata().getIdentifier().getProjectIdentifier());

									/** If the data file seems to be moved from another project, it'll reassign a new data id for it **/
									if (fromOtherProject)
									{
										data.getMetadata().setIdentifier(this.cache.createNextIdentifier());
									}

									final String location = loc.replace(
											(this.locationFile != null ? this.locationFile.getCanonicalPath() : this.jarLocation.getPath()) + File.separator,
											"");

									/**
									 * This will determine if a new identifier will be created
									 * when the data is set.
									 */
									final boolean shouldSaveAfter = data.getMetadata().getIdentifier() == null;

									/** Loads data from file then sets it to the cache **/
									this.cache.setData(data, location);

									/**
									 * Save the data to disk to ensure it doesn't keep creating
									 * new identifiers each time the project is loaded.
									 */
									if (this.locationFile != null && (shouldSaveAfter || fromOtherProject))
									{
										this.writeData(data, file);
									}
								}
								else
								{
									OrbisCore.LOGGER.error("Failed to load back a data file from project.", uri);
								}
							}
							catch (final IOException e)
							{
								OrbisCore.LOGGER.error(e);
							}
						}
						catch (final IOException e)
						{
							OrbisCore.LOGGER.error(e);
						}
					}
				});
			}
			catch (final IOException e)
			{
				OrbisCore.LOGGER.error(e);
			}
		}
		catch (final URISyntaxException e)
		{
			OrbisCore.LOGGER.error(e);
		}
	}

	@Override
	public boolean areModDependenciesMet()
	{
		return true;
	}
}
