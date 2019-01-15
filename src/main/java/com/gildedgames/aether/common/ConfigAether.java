package com.gildedgames.aether.common;

import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetPlayerConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigAether
{
	public final ConfigCategory general, dimensions, controls, gameplay, misc;

	private final Configuration configuration;

	private int aetherDimID, necromancerDimId;

	private boolean analyticsEnabled;

	private boolean displayInventoryPattern;

	private boolean displayPerformanceIndicator;

	private boolean cutoutHelmets;

	private boolean helmetShadow;

	private double rollCameraTilt;

	private double rollFOV;

	private boolean skipIntro;

	private boolean separateInventories;

	private boolean acknowledgeFingerprintViolation = false;

	private boolean hideXPBarInAether;

	public ConfigAether(final File file)
	{
		this.configuration = new Configuration(file, true);

		this.controls = this.configuration.getCategory("Controls");
		this.general = this.configuration.getCategory(Configuration.CATEGORY_GENERAL);
		this.dimensions = this.configuration.getCategory("Dimension IDs");
		this.gameplay = this.configuration.getCategory("Gameplay");
		this.misc = this.configuration.getCategory("Miscellaneous");

		this.dimensions.setRequiresMcRestart(true);

		this.loadAndSync();
	}

	private void loadAndSync()
	{
		this.aetherDimID = this.getInt(this.dimensions, "Aether Dimension ID", 3);
		this.necromancerDimId = this.getInt(this.dimensions, "Necromancer Tower Dimension ID", 4);

		this.analyticsEnabled = this.getBoolean(this.general, "Enable Analytics (client-side only)", true);

		this.displayInventoryPattern = this.getBoolean(this.general, "Display Inventory Pattern", true);
		this.displayPerformanceIndicator = this.getBoolean(this.general, "Display Performance Indicator Overlay", false);

		this.helmetShadow = this.getBoolean(this.general, "Helmet Shadow", true);
		this.cutoutHelmets = this.getBoolean(this.general, "Transparent Helmets", true);
		this.hideXPBarInAether = this.getBoolean(this.general, "Hide XP Bar In Aether", false);

		this.rollCameraTilt = this.getDouble(this.controls, "Roll Camera Tilt", 3.5D);
		this.rollFOV = this.getDouble(this.controls, "Roll FOV", 1.0D);

		this.skipIntro = this.getBoolean(this.gameplay, "Skip Intro", false);
		this.separateInventories = this.getBoolean(this.gameplay, "Separate Inventories In Aether", false);

		this.acknowledgeFingerprintViolation = this.getBoolean(this.misc, "Acknowledge Fingerprint Violation", false);

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

	public boolean hideXPBarInAether()
	{
		return this.hideXPBarInAether;
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

	public boolean hasAckFingerprintViolation()
	{
		return this.acknowledgeFingerprintViolation;
	}

	public void markFingerprintViolationAck()
	{
		this.acknowledgeFingerprintViolation = true;

		this.configuration.get(this.misc.getName(), "Acknowledge Fingerprint Violation", false).set(true);

		if (this.configuration.hasChanged())
		{
			this.configuration.save();
		}
	}
}
