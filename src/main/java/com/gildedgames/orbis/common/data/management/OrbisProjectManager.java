package com.gildedgames.orbis.common.data.management;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis.management.*;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class OrbisProjectManager implements IProjectManager
{
	private final File baseDirectory;

	private final Map<IProjectIdentifier, IProject> idToProject = Maps.newHashMap();

	public OrbisProjectManager(final File baseDirectory)
	{
		if (!baseDirectory.isDirectory())
		{
			throw new IllegalArgumentException("File passed into OrbisProjectManager is not a directory!");
		}

		if (!baseDirectory.exists() && !baseDirectory.mkdirs())
		{
			throw new RuntimeException("Base directory for OrbisProjectManager cannot be created!");
		}

		this.baseDirectory = baseDirectory;
	}

	private void cacheProject(final IProject project)
	{
		this.idToProject.put(project.getProjectIdentifier(), project);
	}

	@Override
	public void scanAndCacheProjects()
	{
		final File[] files = this.baseDirectory.listFiles();

		if (files == null)
		{
			throw new RuntimeException("Base directory in OrbisProjectManager cannot list any files inside!");
		}

		for (final File file : files)
		{
			/** Once we've found a directory, fetch files inside project directory **/
			if (file != null && file.isDirectory())
			{
				final File[] innerFiles = file.listFiles();

				if (innerFiles != null)
				{
					/** Attempt to find the hidden .project file that contains
					 * the metadata for the project **/
					for (final File innerFile : innerFiles)
					{
						if (innerFile != null && !innerFile.isDirectory() && innerFile.getName().equals(".project"))
						{
							/** When found, load and cache the project into memory **/
							try (FileInputStream in = new FileInputStream(innerFile))
							{
								final NBTTagCompound tag = CompressedStreamTools.readCompressed(in);

								final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

								final IProject project = funnel.get("project");

								this.cacheProject(project);
							}
							catch (final IOException e)
							{
								AetherCore.LOGGER.catching(e);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Collection<IProject> getCachedProjects()
	{
		return this.idToProject.values();
	}

	@Nullable
	@Override
	public IProject findProject(final IProjectIdentifier identifier) throws OrbisMissingProjectException
	{
		final IProject project = this.idToProject.get(identifier);

		if (project == null)
		{
			throw new OrbisMissingProjectException(identifier);
		}

		return project;
	}

	@Nullable
	@Override
	public IProjectData findData(final IDataIdentifier identifier) throws OrbisMissingDataException, OrbisMissingProjectException
	{
		final IProject project = this.findProject(identifier.getProjectIdentifier());

		if (project == null)
		{
			throw new NullPointerException("Project is null when trying to find data!");
		}

		final IProjectData data = project.getCachedData(identifier.getDataId());

		if (data == null)
		{
			throw new OrbisMissingDataException(identifier);
		}

		return data;
	}

	@Override
	public IProject createProject(final String name, final IProjectIdentifier identifier)
	{
		final File file = new File(this.baseDirectory, name);
		final IProject project = new OrbisProject(file, identifier, null);

		this.cacheProject(project);

		return project;
	}
}
