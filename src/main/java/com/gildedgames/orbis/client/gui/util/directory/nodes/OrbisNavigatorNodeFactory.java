package com.gildedgames.orbis.client.gui.util.directory.nodes;

import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNode;
import com.gildedgames.orbis.client.gui.data.directory.IDirectoryNodeFactory;

import java.io.File;

public class OrbisNavigatorNodeFactory implements IDirectoryNodeFactory
{
	private static final String BLUEPRINT = "blueprint";

	@Override
	public IDirectoryNode createFrom(final File file, final String extension)
	{
		final IDirectoryNode node;

		if (file.isDirectory())
		{
			node = new FolderNode(file);
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
