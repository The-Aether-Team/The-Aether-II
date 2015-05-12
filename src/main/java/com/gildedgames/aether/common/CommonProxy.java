package com.gildedgames.aether.common;

import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.common.CommonEvents;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
	public void preInit()
	{

	}

	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
	}

	public ModelsAether getModels()
	{
		return null;
	}
}
