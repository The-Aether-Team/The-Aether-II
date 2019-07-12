package com.gildedgames.aether.client.gui;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiConfigAether extends GuiConfig
{

	public GuiConfigAether(Screen parent)
	{
		super(parent, GuiConfigAether.getConfigElements(), AetherCore.MOD_ID, false, false, "Aether II Options");
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<>();

		list.addAll(new ConfigElement(AetherCore.CONFIG.gameplay).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.controls).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.general).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.dimensions).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.misc).getChildElements());
		list.addAll(new ConfigElement(AetherCore.CONFIG.client).getChildElements());

		return list;
	}

	@Override
	public void drawDefaultBackground()
	{

	}

}
