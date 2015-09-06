package com.gildedgames.aether.common;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class AetherConfig
{
	private final Configuration configuration;

	private int aetherDimID;

	private int aetherBiomeID;

	public AetherConfig(File file)
	{
		this.configuration = new Configuration(file, true);
	}

	public void load()
	{
		this.configuration.load();

		ConfigCategory biomeIDs = this.configuration.getCategory("biomeIDs");
		this.aetherBiomeID = this.getInt(biomeIDs, "Aether Biome ID", 237);

		ConfigCategory dimIDs = this.configuration.getCategory("dimIDs");
		this.aetherDimID = this.getInt(dimIDs, "Aether Dimension ID", 3);

		this.configuration.save();
	}

	private int getInt(ConfigCategory category, String name, int defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getInt();
	}

	public int getAetherDimID()
	{
		return this.aetherDimID;
	}

	public int getAetherBiomeID()
	{
		return this.aetherBiomeID;
	}
}
