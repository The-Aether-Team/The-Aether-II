package com.gildedgames.aether.common;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigAether
{
	private final Configuration configuration;

	public final ConfigCategory general, biomes, dimensions;

	private int aetherDimID;

	private int aetherBiomeID;

	private boolean analyticsEnabled;

	private boolean displayInventoryPattern;

	private boolean displayPerformanceIndicator;

	public ConfigAether(File file)
	{
		this.configuration = new Configuration(file, true);

		this.general = this.configuration.getCategory(Configuration.CATEGORY_GENERAL);
		this.biomes = this.configuration.getCategory("Biome IDs");
		this.dimensions = this.configuration.getCategory("Dimension IDs");

		this.biomes.setRequiresMcRestart(true);
		this.dimensions.setRequiresMcRestart(true);

		this.loadAndSync();
	}

	private void loadAndSync()
	{
		this.aetherDimID = this.getInt(this.dimensions, "Aether Dimension ID", 3);
		this.aetherBiomeID = this.getInt(this.biomes, "Aether Biome ID", 237);

		this.analyticsEnabled = this.getBoolean(this.general, "Enable Analytics (client-side only)", true);

		this.displayInventoryPattern = this.getBoolean(this.general, "Display Inventory Pattern", true);
		this.displayPerformanceIndicator = this.getBoolean(this.general, "Display Performance Indicator", true);

		if (this.configuration.hasChanged())
		{
			this.configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.getModID().equals(AetherCore.MOD_ID))
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

	public int getAetherBiomeID()
	{
		return this.aetherBiomeID;
	}

	public boolean getDisplayInventoryPattern()
	{
		return this.displayInventoryPattern;
	}

	public boolean getDisplayPerformanceIndicator()
	{
		return this.displayPerformanceIndicator;
	}

	public boolean isAnalyticsEnabled()
	{
		return this.analyticsEnabled;
	}
}
