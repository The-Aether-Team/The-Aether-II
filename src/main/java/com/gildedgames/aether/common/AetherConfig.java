package com.gildedgames.aether.common;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class AetherConfig
{

	public final Configuration configuration;

	private final ConfigCategory GENERAL, BIOMES, DIMENSIONS;

	public AetherConfig(File file)
	{
		this.configuration = new Configuration(file, true);

		this.configuration.load();

		this.GENERAL = this.configuration.getCategory(Configuration.CATEGORY_GENERAL);
		this.BIOMES = this.configuration.getCategory("Biome IDs");
		this.DIMENSIONS = this.configuration.getCategory("Dimension IDs");

		this.BIOMES.setRequiresMcRestart(true);
		this.DIMENSIONS.setRequiresMcRestart(true);

		this.configuration.save();
	}

	private int getInt(ConfigCategory category, String name, int defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getInt();
	}

	private boolean getBoolean(ConfigCategory category, String name, boolean defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getBoolean();
	}

	public int getAetherDimID()
	{
		return this.getInt(this.DIMENSIONS, "Aether Dimension ID", 3);
	}

	public int getSliderLabyrinthDimID()
	{
		return this.getInt(this.DIMENSIONS, "Slider's Labyrinth Dimension ID", 4);
	}

	public int getAetherBiomeID()
	{
		return this.getInt(this.BIOMES, "Aether Biome ID", 237);
	}

	public int getSliderLabyrinthBiomeID()
	{
		return this.getInt(this.BIOMES, "Slider Labyrinth Biome ID", 238);
	}

	public boolean getDisplayInventoryPattern() { return this.getBoolean(this.GENERAL, "Display Inventory Pattern", true); }

}
