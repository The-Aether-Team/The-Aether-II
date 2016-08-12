package com.gildedgames.aether.common;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class AetherConfig
{

	public final Configuration configuration;

	private final ConfigCategory GENERAL, BIOMES, DIMENSIONS;

	private int sliderLabyrinthDimID, aetherDimID;

	private int aetherBiomeID, sliderLabyrinthBiomeID;

	private boolean displayInventoryPattern;

	public AetherConfig(File file)
	{
		this.configuration = new Configuration(file, true);

		this.GENERAL = this.configuration.getCategory(Configuration.CATEGORY_GENERAL);
		this.BIOMES = this.configuration.getCategory("Biome IDs");
		this.DIMENSIONS = this.configuration.getCategory("Dimension IDs");

		this.BIOMES.setRequiresMcRestart(true);
		this.DIMENSIONS.setRequiresMcRestart(true);

		this.loadAndSync();
	}

	private void loadAndSync()
	{
		this.aetherDimID = this.getInt(this.DIMENSIONS, "Aether Dimension ID", 3);
		this.sliderLabyrinthDimID = this.getInt(this.DIMENSIONS, "Slider's Labyrinth Dimension ID", 4);

		this.aetherBiomeID = this.getInt(this.BIOMES, "Aether Biome ID", 237);
		this.sliderLabyrinthBiomeID = this.getInt(this.BIOMES, "Slider Labyrinth Biome ID", 238);

		this.displayInventoryPattern = this.getBoolean(this.GENERAL, "Display Inventory Pattern", true);

		if (this.configuration.hasChanged())
		{
			this.configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.getModID().equals(AetherCore.MOD_ID))
		{
			this.loadAndSync();
		}
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
		return this.aetherDimID;
	}

	public int getSliderLabyrinthDimID()
	{
		return this.sliderLabyrinthDimID;
	}

	public int getAetherBiomeID()
	{
		return this.aetherBiomeID;
	}

	public int getSliderLabyrinthBiomeID()
	{
		return this.sliderLabyrinthBiomeID;
	}

	public boolean getDisplayInventoryPattern() { return this.displayInventoryPattern; }

}
