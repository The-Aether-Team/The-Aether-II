package com.gildedgames.aether.common;

import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetPlayerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigAether
{
	public final ConfigCategory skipIntroCategory, enableSkyboxCategory, displayInventoryPatternCategory, cutoutHelmetsCategory, helmetShadowCategory, displayPerformanceIndicatorCategory,
			analyticsEnabledCategory, acknowledgeFingerprintViolationCategory, aerwhaleCategory, aetherDimIDCategory, necromancerDimIdCategory;

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

	private boolean acknowledgeFingerprintViolation = false;

	private boolean hideXPBarInAether;

	private boolean enableSkybox = true;

	public ConfigAether(final File file)
	{
		this.configuration = new Configuration(file, true);

		this.skipIntroCategory = this.configuration.getCategory("Skip Intro");
		this.enableSkyboxCategory = this.configuration.getCategory("Enable Skybox");
		this.displayInventoryPatternCategory = this.configuration.getCategory("Display Inventory Pattern");
		this.cutoutHelmetsCategory = this.configuration.getCategory("Cutout Helmets");
		this.helmetShadowCategory = this.configuration.getCategory("Helmet Shadow");
		this.aetherDimIDCategory = this.configuration.getCategory("Aether Dimension ID");
		this.necromancerDimIdCategory = this.configuration.getCategory("Necromancer Dimension ID");
		this.displayPerformanceIndicatorCategory = this.configuration.getCategory("Display Performance Indicator");
		this.analyticsEnabledCategory = this.configuration.getCategory("Analytics Enabled");
		this.acknowledgeFingerprintViolationCategory = this.configuration.getCategory("Acknowledge Fingerprint Violation");
		this.aerwhaleCategory = this.configuration.getCategory("Aerwhale");

		this.aetherDimIDCategory.setRequiresMcRestart(true);
		this.necromancerDimIdCategory.setRequiresMcRestart(true);

		this.loadAndSync();
	}

	private void loadAndSync()
	{
		this.skipIntro = this.getBoolean(this.skipIntroCategory, I18n.format("config.aether.skip_intro.name"), true);
		this.enableSkybox = this.getBoolean(this.enableSkyboxCategory, "config.aether.enable_skybox.name", true);
		this.displayInventoryPattern = this.getBoolean(this.displayInventoryPatternCategory, "config.aether.display_inventory_pattern.name", true);
		this.cutoutHelmets = this.getBoolean(this.cutoutHelmetsCategory, "config.aether.cutout_helmets.name", true);
		this.helmetShadow = this.getBoolean(this.helmetShadowCategory, "config.aether.helmet_shadow.name", true);
		this.aetherDimID = this.getInt(this.aetherDimIDCategory, "config.aether.aether_dimension_id.name", 3);
		this.necromancerDimId = this.getInt(this.necromancerDimIdCategory, "config.aether.necromancer_dimension_id.name", 4);
		this.displayPerformanceIndicator = this.getBoolean(this.displayPerformanceIndicatorCategory, "config.aether.display_performance_indicator.name", false);
		this.analyticsEnabled = this.getBoolean(this.analyticsEnabledCategory, "config.aether.analytics_enabled.name", true);
		this.acknowledgeFingerprintViolation = this.getBoolean(this.acknowledgeFingerprintViolationCategory, "config.aether.fingerprint_violation.name", false);
		this.getBoolean(this.aerwhaleCategory, "config.aether.aerwhale.name", false);

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

	public boolean hasAckFingerprintViolation()
	{
		return this.acknowledgeFingerprintViolation;
	}

	public boolean isSkyboxRenderEnabled()
	{
		return this.enableSkybox;
	}

	public void markFingerprintViolationAck()
	{
		this.acknowledgeFingerprintViolation = true;

		this.configuration.get(this.acknowledgeFingerprintViolationCategory.getName(), "Acknowledge Fingerprint Violation", false).set(true);

		if (this.configuration.hasChanged())
		{
			this.configuration.save();
		}
	}
}
