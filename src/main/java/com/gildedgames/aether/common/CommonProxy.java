package com.gildedgames.aether.common;

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
}
