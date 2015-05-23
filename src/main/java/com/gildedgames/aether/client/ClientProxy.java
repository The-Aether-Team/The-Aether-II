package com.gildedgames.aether.client;

import com.gildedgames.aether.client.models.ModelsAether;
import com.gildedgames.aether.common.CommonProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		ModelsAether.prepareModelLoader();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		ModelsAether.registerModels();
	}
}
