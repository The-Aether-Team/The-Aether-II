package com.gildedgames.orbis.client.gui.util.directory.nodes;

import com.gildedgames.aether.api.orbis_core.api.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis_core.data.management.IProject;
import com.gildedgames.aether.api.orbis_core.data.management.impl.OrbisProjectManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNodeFactory;
import com.gildedgames.orbis.common.Orbis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OrbisNavigatorNodeFactory implements IDirectoryNodeFactory
{
	private static final String BLUEPRINT = "blueprint";

	@Override
	public IDirectoryNode createFrom(final File file, final String extension)
	{
		IDirectoryNode node = null;

		try
		{
			if (Files.getAttribute(Paths.get(file.getPath()), "dos:hidden") == Boolean.TRUE)
			{
				return null;
			}
		}
		catch (final IOException e)
		{
			AetherCore.LOGGER.error(e);
		}

		if (file.isDirectory())
		{
			if (OrbisProjectManager.isProjectDirectory(file))
			{
				Orbis.getProjectManager().refreshCache();

				try
				{
					final IProject project = Orbis.getProjectManager().findProject(file.getName());

					if (project != null)
					{
						// TODO: Refresh cache in case contents of project changed outside of game.
						//project.loadAndCacheData();

						node = new ProjectNode(file, project);
					}
				}
				catch (final OrbisMissingProjectException e)
				{
					AetherCore.LOGGER.error("Project couldn't be found in cache, skipping node!", e);
				}
			}
			else
			{
				node = new FolderNode(file);
			}
		}
		else
		{
			switch (extension)
			{
				case BLUEPRINT:
					node = new BlueprintNode(file);
					break;
				default:
					node = new FileNode(file);
			}
		}

		return node;
	}
}
