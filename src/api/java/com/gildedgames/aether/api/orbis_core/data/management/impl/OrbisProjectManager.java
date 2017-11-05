package com.gildedgames.aether.api.orbis_core.data.management.impl;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingDataException;
import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.management.*;
import com.gildedgames.aether.api.util.FileHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class OrbisProjectManager implements IProjectManager
{
	private final File baseDirectory;

	private final Map<IProjectIdentifier, IProject> idToProject = Maps.newHashMap();

	private final Map<String, IProject> nameToProject = Maps.newHashMap();

	public OrbisProjectManager(final File baseDirectory)
	{
		if (!baseDirectory.exists() && !baseDirectory.mkdirs())
		{
			throw new RuntimeException("Base directory for OrbisProjectManager cannot be created!");
		}

		if (!baseDirectory.isDirectory())
		{
			throw new IllegalArgumentException("File passed into OrbisProjectManager is not a directory!");
		}

		this.baseDirectory = baseDirectory;
	}

	public static boolean isProjectDirectory(final File file)
	{
		final File[] innerFiles = file.listFiles();

		if (innerFiles != null)
		{
			/** Attempt to find the hidden .project file that contains
			 * the metadata for the project **/
			for (final File innerFile : innerFiles)
			{
				if (innerFile != null && !innerFile.isDirectory() && innerFile.getName().equals("project_data.project"))
				{
					return true;
				}
			}
		}

		return false;
	}

	private void cacheProject(final String folderName, final IProject project)
	{
		this.nameToProject.put(folderName, project);
		this.idToProject.put(project.getProjectIdentifier(), project);
	}

	@Override
	public File getLocation()
	{
		return this.baseDirectory;
	}

	@Override
	public void flushProjects()
	{
		this.idToProject.values().forEach(this::saveProjectToDisk);
	}

	private void walkProjects(final BiConsumer<File, File> action)
	{
		final File[] files = this.baseDirectory.listFiles();

		if (files == null)
		{
			return;
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
						if (innerFile != null && !innerFile.isDirectory() && innerFile.getName().equals("project_data.project"))
						{
							action.accept(innerFile, file);
						}
					}
				}
			}
		}
	}

	@Override
	public void refreshCache()
	{
		final List<IProjectIdentifier> foundProjects = Lists.newArrayList();

		this.walkProjects((innerFile, file) ->
		{
			try (FileInputStream in = new FileInputStream(innerFile))
			{
				final NBTTagCompound tag = CompressedStreamTools.readCompressed(in);
				final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

				final IProject project = funnel.get("project");

				foundProjects.add(project.getProjectIdentifier());

				if (!this.idToProject.keySet().contains(project.getProjectIdentifier()))
				{
					project.setLocationAsFile(file);

					project.loadAndCacheData();

					this.cacheProject(file.getName(), project);
				}
			}
			catch (final IOException e)
			{
				OrbisCore.LOGGER.catching(e);
			}
		});

		final List<IProjectIdentifier> projectsToRemove = Lists.newArrayList();

		this.idToProject.keySet().forEach(p ->
		{
			if (!foundProjects.contains(p))
			{
				projectsToRemove.add(p);
			}
		});

		projectsToRemove.forEach(this.idToProject::remove);
	}

	@Override
	public void scanAndCacheProjects()
	{
		this.walkProjects((innerFile, file) ->
		{
			/** When found, load and cache the project into memory **/
			try (FileInputStream in = new FileInputStream(innerFile))
			{
				final NBTTagCompound tag = CompressedStreamTools.readCompressed(in);

				final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

				final IProject project = funnel.get("project");

				project.setLocationAsFile(file);

				project.loadAndCacheData();

				this.cacheProject(file.getName(), project);
			}
			catch (final IOException e)
			{
				OrbisCore.LOGGER.catching(e);
			}
		});
	}

	@Override
	public Collection<IProject> getCachedProjects()
	{
		return this.idToProject.values();
	}

	@Nullable
	@Override
	public <T extends IProject> T findProject(final String folderName) throws OrbisMissingProjectException
	{
		final IProject project = this.nameToProject.get(folderName);

		if (project == null)
		{
			throw new OrbisMissingProjectException(folderName);
		}

		return (T) project;
	}

	@Nullable
	@Override
	public <T extends IProject> T findProject(final IProjectIdentifier identifier) throws OrbisMissingProjectException
	{
		final IProject project = this.idToProject.get(identifier);

		if (project == null)
		{
			throw new OrbisMissingProjectException(identifier);
		}

		return (T) project;
	}

	@Nullable
	@Override
	public <T extends IData> T findData(final IProject project, final File file) throws OrbisMissingDataException, OrbisMissingProjectException
	{
		try
		{
			final boolean isInProject = file.getCanonicalPath().startsWith(project.getLocationAsFile().getCanonicalPath() + File.separator);

			if (isInProject)
			{
				final String dataLocation = file.getCanonicalPath().replace(project.getLocationAsFile().getCanonicalPath() + File.separator, "");

				final int dataId = project.getCache().getDataId(dataLocation);

				final IData data = project.getCache().getData(dataId);

				return (T) data;
			}
		}
		catch (final IOException e)
		{
			OrbisCore.LOGGER.error(e);
		}

		return null;
	}

	@Nullable
	@Override
	public <T extends IData> T findData(final IDataIdentifier identifier) throws OrbisMissingDataException, OrbisMissingProjectException
	{
		final IProject project = this.findProject(identifier.getProjectIdentifier());

		if (project == null)
		{
			throw new NullPointerException("Project is null when trying to find data!");
		}

		final IData data = project.getCache().getData(identifier.getDataId());

		if (data == null)
		{
			throw new OrbisMissingDataException(identifier);
		}

		return (T) data;
	}

	@Nullable
	@Override
	public <T extends IDataMetadata> T findMetadata(final IDataIdentifier identifier) throws OrbisMissingDataException, OrbisMissingProjectException
	{
		final IProject project = this.findProject(identifier.getProjectIdentifier());

		if (project == null)
		{
			throw new NullPointerException("Project is null when trying to find data!");
		}

		final IDataMetadata data = project.getCache().getMetadata(identifier.getDataId());

		if (data == null)
		{
			throw new OrbisMissingDataException(identifier);
		}

		return (T) data;
	}

	@Override
	public <T extends IProject> T createAndSaveProject(final String name, final IProjectIdentifier identifier)
	{
		final File file = new File(this.baseDirectory, name);
		final IProject project = new OrbisProject(file, identifier);

		this.saveProjectToDisk(project);
		this.cacheProject(name, project);

		return (T) project;
	}

	@Override
	public <T extends IProject> T saveProjectIfDoesntExist(final String name, final IProject project)
	{
		final File location = new File(this.baseDirectory, name);

		final IProject existing = this.idToProject.get(project.getProjectIdentifier());

		if (existing != null && existing.getLocationAsFile().exists())
		{
			/**
			 * Check if project already exists and it has the same
			 * "last changed" date. If so, move that project to the
			 * requested folder.
			 *
			 * Then return so new project instance isn't cached.
			 */
			if (existing.getMetadata().getLastChanged().equals(project.getMetadata().getLastChanged()))
			{
				if (!existing.getLocationAsFile().equals(location))
				{
					if (!location.exists() || location.delete())
					{
						if (existing.getLocationAsFile().renameTo(location))
						{
							this.nameToProject.remove(existing.getLocationAsFile().getName());

							if (existing.getLocationAsFile().delete())
							{
								existing.setLocationAsFile(location);

								this.nameToProject.put(name, existing);
							}
						}
						else
						{
							throw new RuntimeException("Could not rename project folder. Abort!");
						}
					}
				}

				return (T) existing;
			}
		}

		if (!location.exists())
		{
			if (!location.mkdirs())
			{
				throw new RuntimeException("Location for Project cannot be created!");
			}
		}

		project.setLocationAsFile(location);

		this.saveProjectToDisk(project);
		this.cacheProject(name, project);

		return (T) existing;
	}

	@Override
	public boolean projectNameExists(final String name)
	{
		return this.nameToProject.containsKey(name);
	}

	@Override
	public boolean projectExists(final IProjectIdentifier id)
	{
		return this.idToProject.containsKey(id);
	}

	private void saveProjectToDisk(final IProject project)
	{
		final File projectFile = new File(project.getLocationAsFile(), "project_data.project");

		try
		{
			if (projectFile.exists())
			{
				FileHelper.unhide(projectFile);
			}

			try (FileOutputStream out = new FileOutputStream(projectFile))
			{
				final NBTTagCompound tag = new NBTTagCompound();
				final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

				funnel.set("project", project);

				CompressedStreamTools.writeCompressed(tag, out);
			}
			catch (final IOException e)
			{
				OrbisCore.LOGGER.error("Failed to save Project to disk", e);
			}

			FileHelper.hide(projectFile);
		}
		catch (final IOException e)
		{
			OrbisCore.LOGGER.error(e);
		}
	}

}
