package com.gildedgames.aether.common;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = AetherCore.MOD_ID)
public class ConfigAether
{
	@Config.Ignore
	private static final String LANG_PREFIX = "config." + AetherCore.MOD_ID + ".";

	@SubscribeEvent
	public void onConfigChangedEvent(OnConfigChangedEvent event)
	{
		if (event.getModID().equals(AetherCore.MOD_ID))
		{
			ConfigManager.sync(AetherCore.MOD_ID, Config.Type.INSTANCE);
		}
	}

	@Config.LangKey(LANG_PREFIX + "skip_intro." + "name")
	@Config.Comment("Skips the Necromancer's Tower introduction sequence when entering the Aether for the first time.")
	public static boolean skipIntro = true;

	//@Config.LangKey(LANG_PREFIX + "enable_skybox." + "name")
	//@Config.Comment("")
	//public static boolean enableSkybox = true;

	@Config.LangKey(LANG_PREFIX + "cutout_helmets." + "name")
	@Config.Comment("Enables the player's skin to be seen through gaps in Aether helmets. Setting to false shows an opaque dark texture instead.")
	public static boolean cutoutHelmets = true;

	@Config.LangKey(LANG_PREFIX + "helmet_shadow." + "name")
	@Config.Comment("Renders a shadow layer ovet the player's skin when transparent helmets are enabled.")
	public static boolean helmetShadow = true;

	@Config.LangKey(LANG_PREFIX + "aether_dimension_id." + "name")
	@Config.Comment("The Dimension ID for the main Aether II dimension, change this value to fix conflicts with other dimensions.")
	@Config.RequiresMcRestart
	public static int aetherDimID = 3;

	@Config.LangKey(LANG_PREFIX + "necromancer_dimension_id." + "name")
	@Config.Comment("The Dimension ID for the introduction sequence for Aether II, change this value to fix conflicts with other dimensions.")
	@Config.RequiresMcRestart
	public static int necromancerDimId = 4;

	@Config.LangKey(LANG_PREFIX + "display_performance_indicator." + "name")
	@Config.Comment("Enables a small UI that shows performance of the internal server, used for debugging.")
	public static boolean displayPerformanceIndicator = false;

	@Config.LangKey(LANG_PREFIX + "analytics_enabled." + "name")
	@Config.Comment("Enables us to collect generic data while you play Aether II, no personal data is recorded.")
	public static boolean analyticsEnabled = true;

	@Config.LangKey(LANG_PREFIX + "fingerprint_violation." + "name")
	@Config.Comment("If the version of the Aether II you are using sets off any flags that it might not be from the official source, a warning will appear when the game launches. Enabling this config disables that warning.")
	public static boolean acknowledgeFingerprintViolation = false;

	@Config.LangKey(LANG_PREFIX + "aerwhale_riding." + "name")
	@Config.Comment("Definitely not a joke.")
	public static boolean rideableAerwhale = false;
}