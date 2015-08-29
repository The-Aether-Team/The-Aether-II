package com.gildedgames.aether.client.models.util;

import com.gildedgames.aether.common.AetherCore;

import java.util.HashMap;

public class ModelResourceList
{
	private HashMap<Integer, String> registrations = new HashMap<Integer, String>();

	private final String prefixRoot;

	public ModelResourceList()
	{
		this(null);
	}

	public ModelResourceList(String rootPath)
	{
		this.prefixRoot = rootPath;
	}

	public ModelResourceList add(int meta, String path)
	{
		this.registrations.put(meta, AetherCore.getResourcePath((this.prefixRoot != null ? this.prefixRoot : "") + path));

		return this;
	}

	public HashMap<Integer, String> getRegistrations()
	{
		return this.registrations;
	}
}
