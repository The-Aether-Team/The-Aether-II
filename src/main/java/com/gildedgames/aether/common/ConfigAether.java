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
	@Config.Comment("")
	public static boolean skipIntro = true;

	//@Config.LangKey(LANG_PREFIX + "enable_skybox." + "name")
	//@Config.Comment("")
	//public static boolean enableSkybox = true;

	@Config.LangKey(LANG_PREFIX + "cutout_helmets." + "name")
	@Config.Comment("")
	public static boolean cutoutHelmets = true;

	@Config.LangKey(LANG_PREFIX + "helmet_shadow." + "name")
	@Config.Comment("")
	public static boolean helmetShadow = true;

	@Config.LangKey(LANG_PREFIX + "aether_dimension_id." + "name")
	@Config.Comment("")
	@Config.RequiresMcRestart
	public static int aetherDimID = 3;

	@Config.LangKey(LANG_PREFIX + "necromancer_dimension_id." + "name")
	@Config.Comment("")
	@Config.RequiresMcRestart
	public static int necromancerDimId = 4;

	@Config.LangKey(LANG_PREFIX + "display_performance_indicator." + "name")
	@Config.Comment("")
	public static boolean displayPerformanceIndicator = false;

	@Config.LangKey(LANG_PREFIX + "analytics_enabled." + "name")
	@Config.Comment("")
	public static boolean analyticsEnabled = true;

	@Config.LangKey(LANG_PREFIX + "fingerprint_violation." + "name")
	@Config.Comment("")
	public static boolean acknowledgeFingerprintViolation = false;

	@Config.LangKey(LANG_PREFIX + "aerwhale_riding." + "name")
	@Config.Comment("")
	public static boolean rideableAerwhale = false;
}