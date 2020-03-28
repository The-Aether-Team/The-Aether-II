package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiConfigAether extends GuiConfig
{

	public GuiConfigAether(GuiScreen parent)
	{
		super(parent, GuiConfigAether.getConfigElements(), AetherCore.MOD_ID, false, false, "Aether II Options");
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<>();

		list.addAll(new ConfigElement(AetherCore.CONFIG.skipIntroCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.enableSkyboxCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.displayInventoryPatternCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.cutoutHelmetsCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.helmetShadowCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.aetherDimIDCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.necromancerDimIdCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.displayPerformanceIndicatorCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.analyticsEnabledCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.acknowledgeFingerprintViolationCategory).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.aerwhaleCategory).getChildElements());

		return list;
	}

	@Override
	public void drawDefaultBackground()
	{

	}

}
