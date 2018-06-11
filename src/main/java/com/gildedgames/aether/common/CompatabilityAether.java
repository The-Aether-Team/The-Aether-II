package com.gildedgames.aether.common;

import net.minecraftforge.fml.common.Loader;

public class CompatabilityAether
{
	private static boolean isAetherLegacyInstalled;

	public static void locateInstalledMods()
	{
		isAetherLegacyInstalled = Loader.isModLoaded("aether_legacy");
	}

	public static boolean isAetherLegacyInstalled()
	{
		return isAetherLegacyInstalled;
	}
}
