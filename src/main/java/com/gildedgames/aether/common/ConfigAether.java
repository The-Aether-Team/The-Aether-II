package com.gildedgames.aether.common;

import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetPlayerConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigAether
{
	public final ConfigCategory general, dimensions, controls, gameplay;

	private final Configuration configuration;

	private int aetherDimID, necromancerDimId;

	private boolean analyticsEnabled;

	private boolean displayInventoryPattern;

	private boolean displayPerformanceIndicator;

	private boolean cutoutHelmets;

	private boolean helmetShadow;

	private double rollCameraTilt;

	private double rollCameraHeightLower;

	private double rollFOV;

	private boolean skipIntro;

	private boolean separateInventories;

	public ConfigAether(final File file)
	{
		this.configuration = new Configuration(file, true);

		this.controls = this.configuration.getCategory("Controls");
		this.general = this.configuration.getCategory(Configuration.CATEGORY_GENERAL);
		this.dimensions = this.configuration.getCategory("Dimension IDs");
		this.gameplay = this.configuration.getCategory("Gameplay");

		this.dimensions.setRequiresMcRestart(true);

		this.loadAndSync();
	}

	private void loadAndSync()
	{
		this.aetherDimID = this.getInt(this.dimensions, "Aether Dimension ID", 3);
		this.necromancerDimId = this.getInt(this.dimensions, "Necromancer Tower Dimension ID", 4);

		this.analyticsEnabled = this.getBoolean(this.general, "Enable Analytics (client-side only)", true);

		this.displayInventoryPattern = this.getBoolean(this.general, "Display Inventory Pattern", true);
		this.displayPerformanceIndicator = this.getBoolean(this.general, "Display Performance Indicator", true);

		this.helmetShadow = this.getBoolean(this.general, "Helmet Shadow", true);
		this.cutoutHelmets = this.getBoolean(this.general, "Transparent Helmets", true);

		this.rollCameraTilt = this.getDouble(this.controls, "Roll Camera Tilt", 12.5D);
		this.rollCameraHeightLower = this.getDouble(this.controls, "Roll Camera Height Lower", 0.5D);
		this.rollFOV = this.getDouble(this.controls, "Roll FOV", 2.0D);

		this.skipIntro = this.getBoolean(this.gameplay, "Skip Intro", false);
		this.separateInventories = this.getBoolean(this.gameplay, "Separate Inventories", true);

		this.getBoolean(this.gameplay, "Rideable Aerwhales", false);

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

			if (AetherCore.isClient() && Minecraft.getMinecraft().world != null)
			{
				NetworkingAether.sendPacketToServer(new PacketSetPlayerConfig(this));
			}
		}
	}

	private double getDouble(final ConfigCategory category, final String name, final double defaultValue)
	{
		return this.configuration.get(category.getName(), name, defaultValue).getDouble();
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

	public boolean hasCutoutHelmets()
	{
		return this.cutoutHelmets;
	}

	public boolean hasHelmetShadow()
	{
		return this.helmetShadow;
	}

	public double getRollCameraTilt()
	{
		return this.rollCameraTilt;
	}

	public double getRollCameraHeightLower()
	{
		return this.rollCameraHeightLower;
	}

	public double getRollFOV()
	{
		return this.rollFOV;
	}

	public boolean skipIntro()
	{
		return this.skipIntro;
	}

	public boolean separateInventories()
	{
		return this.separateInventories;
	}
}
