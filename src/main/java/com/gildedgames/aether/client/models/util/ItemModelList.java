package com.gildedgames.aether.client.models.util;

import com.gildedgames.aether.common.AetherCore;

import java.util.HashMap;

public class ItemModelList
{
	private final HashMap<Integer, String> registrations = new HashMap<Integer, String>();

	private final String rootDirectory;

	public ItemModelList()
	{
		this.rootDirectory = null;
	}

	public ItemModelList(String resourceRoot)
	{
		if (resourceRoot.charAt(resourceRoot.length() - 1) != '/')
		{
			throw new RuntimeException("Resource root path must be relative! (end with '/')");
		}

		this.rootDirectory = AetherCore.getResourcePath(resourceRoot);
	}

	public ItemModelList add(int meta, String path)
	{
		this.registrations.put(meta, this.rootDirectory != null ? this.rootDirectory + path : AetherCore.getResourcePath(path));

		return this;
	}

	public HashMap<Integer, String> getRegistrations()
	{
		return this.registrations;
	}
}
