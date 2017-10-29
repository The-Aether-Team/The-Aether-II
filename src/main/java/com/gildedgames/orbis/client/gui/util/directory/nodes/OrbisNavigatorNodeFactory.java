package com.gildedgames.orbis.client.gui.util.directory.nodes;

import com.gildedgames.aether.api.orbis.management.IProject;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNodeFactory;
import com.gildedgames.orbis.common.OrbisCore;
import com.gildedgames.orbis.common.data.management.OrbisProjectManager;

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
		final IDirectoryNode node;

		try
		{
			if (Files.getAttribute(Paths.get(file.getPath()), "dos:hidden") == Boolean.TRUE)
			{
				return null;
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		if (file.isDirectory())
		{
			if (OrbisProjectManager.isProjectDirectory(file))
			{
				final IProject project = OrbisCore.getProjectManager().findProject(file.getName());

				node = new ProjectNode(file, project);
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
