package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
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

		List<IConfigElement> general = new ConfigElement(AetherCore.CONFIG.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements();
		List<IConfigElement> biomeIDs = new ConfigElement(AetherCore.CONFIG.configuration.getCategory("Biome IDs")).getChildElements();
		List<IConfigElement> dimensionIDs = new ConfigElement(AetherCore.CONFIG.configuration.getCategory("Dimension IDs")).getChildElements();

		list.addAll(general);
		list.addAll(biomeIDs);
		list.addAll(dimensionIDs);

		return list;
	}

	@Override
	public void drawDefaultBackground()
	{

	}

}
