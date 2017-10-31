package com.gildedgames.orbis_core.data.management.impl;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis_core.data.management.*;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class OrbisProject implements IProject
{
	private final List<IProjectListener> listeners = Lists.newArrayList();

	private IProjectCache cache;

	private File location;

	private List<String> acceptedFileExtensions;

	private IProjectIdentifier identifier;

	private IProjectMetadata metadata;

	private OrbisProject()
	{
		this.cache = new OrbisProjectCache(this);
	}

	/**
	 * Should be used to load back an existing project.
	 * @param location
	 * @param acceptedFileExtensions
	 */
	private OrbisProject(final File location, final List<String> acceptedFileExtensions)
	{
		this();

		this.location = location;
		this.acceptedFileExtensions = acceptedFileExtensions;
	}

	/**
	 * Should be used to createTE a new project rather than an existing one.
	 * @param location The location of the project.
	 * @param identifier The unique identifier for the project.
	 * @param acceptedFileExtensions The file extensions that this project accepts.
	 */
	public OrbisProject(final File location, final IProjectIdentifier identifier, final List<String> acceptedFileExtensions)
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

		this.location = location;
		this.identifier = identifier;
		this.acceptedFileExtensions = acceptedFileExtensions;

		this.metadata = new ProjectMetadata();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setInteger("nextDataId", this.cache.getNextDataId());

		funnel.set("identifier", this.identifier);
		funnel.set("metadata", this.metadata);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.cache.setNextDataId(tag.getInteger("nextDataId"));

		this.identifier = funnel.get("identifier");
		this.metadata = funnel.get("metadata");
	}

	@Override
	public File getLocation()
	{
		return this.location;
	}

	@Override
	public void setLocation(final File file)
	{
		this.location = file;
	}

	@Override
	public void setAcceptedFileExtensions(final List<String> acceptedFileExtensions)
	{
		this.acceptedFileExtensions = acceptedFileExtensions;
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
	public void loadAndCacheData()
	{
		/** Clear any links that persist after saving the project
		 * Necessary so that projects send over packets contain their
		 * file locations **/
		//this.idToLocation.clear();

		try (Stream<Path> paths = Files.walk(Paths.get(this.location.getPath())))
		{
			paths.forEach(p ->
			{
				final File file = p.toFile();

				/** Prevents the path walking from including the project's location **/
				if (file.getPath().equals(this.location.getPath()))
				{
					return;
				}

				final String extension = FilenameUtils.getExtension(file.getName());

				/** Prevents the path walking from including the project data itself (hidden file) **/
				if (extension.equals("project"))
				{
					return;
				}

				/** Make sure the file has an extension type accepted by this project **/
				if (this.acceptedFileExtensions.contains(extension))
				{
					try (FileInputStream in = new FileInputStream(file))
					{
						final NBTTagCompound tag = CompressedStreamTools.readCompressed(in);

						final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

						final IData data = funnel.get("data");

						if (data != null)
						{
							final boolean fromOtherProject = !this.identifier.equals(data.getMetadata().getIdentifier().getProjectIdentifier());

							/** If the data file seems to be moved from another project, it'll reassign a new data id for it **/
							if (fromOtherProject)
							{
								data.getMetadata().setIdentifier(this.cache.createNextIdentifier());
							}

							final String location = file.getCanonicalPath().replace(this.getLocation().getCanonicalPath() + File.separator, "");

							/** Loads data from file then sets it to the cache **/
							this.cache.setData(data, location);
						}
						else
						{
							AetherCore.LOGGER.error("Failed to load back a data file from project.", file);
						}
					}
					catch (final IOException e)
					{
						AetherCore.LOGGER.error(e);
					}
				}
			});
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error(e);
		}
	}

	@Override
	public boolean areModDependenciesMet()
	{
		return true;
	}
}
