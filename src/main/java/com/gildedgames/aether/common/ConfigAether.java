package com.gildedgames.aether.common;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigAether
{
	public final ConfigCategory general, biomes, dimensions;

	private final Configuration configuration;

	private int aetherDimID, necromancerDimId;

	private int aetherBiomeID;

	private boolean analyticsEnabled;

	private boolean displayInventoryPattern;

	private boolean displayPerformanceIndicator;

	private boolean transparentArmorFace;

	public ConfigAether(final File file)
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
		this.necromancerDimId = this.getInt(this.dimensions, "Necromancer Tower Dimension ID", 4);
		this.aetherBiomeID = this.getInt(this.biomes, "Aether Biome ID", 237);

		this.analyticsEnabled = this.getBoolean(this.general, "Enable Analytics (client-side only)", true);

		this.displayInventoryPattern = this.getBoolean(this.general, "Display Inventory Pattern", true);
		this.displayPerformanceIndicator = this.getBoolean(this.general, "Display Performance Indicator", true);
		this.transparentArmorFace = this.getBoolean(this.general, "Display Transparent Face Armor", false);

		if (this.configuration.hasChanged())
		{
			this.configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(final OnConfigChangedEvent event)
	{
		if (event.getModID().equals(AetherCore.MOD_ID))
		{
			this.loadAndSync();
		}
	}

	private int getInt(final ConfigCategory category, final String name, final int defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getInt();
	}

	private boolean getBoolean(final ConfigCategory category, final String name, final boolean defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getBoolean();
	}

	public int getAetherDimID()
	{
		return this.aetherDimID;
	}

	public int getNecromancerDimID()
	{
		return this.necromancerDimId;
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

	public boolean isTransparentArmorFace()
	{
		return this.transparentArmorFace;
	}
}
