package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.management.*;
import com.gildedgames.aether.api.util.IText;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;
import java.util.stream.Stream;

public class OrbisProject implements IProject
{
	private final List<IProjectListener> listeners = Lists.newArrayList();

	private final Map<Integer, IProjectMetadata> idToMetadata = Maps.newHashMap();

	private final Map<Integer, String> idToLocation = Maps.newHashMap();

	private final BiMap<Integer, IProjectData> idToData = HashBiMap.create();

	private File location;

	private List<String> acceptedFileExtensions;

	private IProjectIdentifier identifier;

	private int nextDataId;

	private List<IText> display;

	private OrbisProject()
	{

	}

	/**
	 * Should be used to load back an existing project.
	 * @param location
	 * @param acceptedFileExtensions
	 */
	private OrbisProject(final File location, final List<String> acceptedFileExtensions)
	{
		this.location = location;
		this.acceptedFileExtensions = acceptedFileExtensions;
	}

	/**
	 * Should be used to create a new project rather than an existing one.
	 * @param location The location of the project.
	 * @param identifier The unique identifier for the project.
	 * @param acceptedFileExtensions The file extensions that this project accepts.
	 */
	public OrbisProject(final File location, final IProjectIdentifier identifier, final List<String> acceptedFileExtensions)
	{
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

		this.display = Lists.newArrayList();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		tag.setInteger("nextDataId", this.nextDataId);

		funnel.set("identifier", this.identifier);
		funnel.setList("display", this.display);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		this.nextDataId = tag.getInteger("nextDataId");

		this.identifier = funnel.get("identifier");
		this.display = funnel.getList("display");
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
	public List<IText> getMetadataDisplay()
	{
		return this.display;
	}

	@Override
	public IProjectData getCachedData(final int dataId)
	{
		final IProjectData data = this.idToData.get(dataId);

		if (data == null)
		{
			throw new RuntimeException("Attempted to fetch data that either hasn't been loaded yet, or doesn't exist in this project! Something's wrong!");
		}

		return data;
	}

	@Override
	public IProjectMetadata getCachedMetadata(final int dataId)
	{
		final IProjectMetadata data = this.idToMetadata.get(dataId);

		if (data == null)
		{
			throw new RuntimeException("Attempted to fetch metadata that either hasn't been loaded yet, or doesn't exist in this project! Something's wrong!");
		}

		return data;
	}

	@Override
	public void saveNewData(final IProjectData data, final String location)
	{
		data.getMetadata().setIdentifier(new DataIdentifier(this.identifier, this.nextDataId++));

		this.setData(data, location);
	}

	/**
	 * Internal method to set data and its metadata to the cache,
	 * as well as allocating the correct project identifier.
	 * @param data The data itself.
	 * @param location The location for the data within the project.
	 */
	private void setData(final IProjectData data, final String location)
	{
		final int id = data.getMetadata().getIdentifier().getDataId();

		this.idToData.put(id, data);
		this.idToMetadata.put(id, data.getMetadata());

		this.setDataLocation(id, location);
	}

	@Override
	public void setDataLocation(final int dataId, final String location)
	{
		this.idToLocation.put(dataId, location);
	}

	@Override
	public void loadAndCacheData()
	{
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

						final IProjectData data = funnel.get("data");

						final boolean fromOtherProject = !this.identifier.equals(data.getMetadata().getIdentifier().getProjectIdentifier());

						/** If the data file seems to be moved from another project, it'll reassign a new data id for it **/
						if (fromOtherProject)
						{
							data.getMetadata().setIdentifier(new DataIdentifier(this.identifier, this.nextDataId++));
						}

						/** Loads data from file then sets it to the cache **/
						this.setData(data, file.getCanonicalPath());
					}
					catch (final IOException e)
					{
						AetherCore.LOGGER.catching(e);
					}
				}
			});
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.catching(e);
		}
	}

	@Override
	public void clearDataCache()
	{
		this.idToData.clear();
		this.idToLocation.clear();
	}

	@Override
	public boolean areModDependenciesMet()
	{
		return true;
	}
}
