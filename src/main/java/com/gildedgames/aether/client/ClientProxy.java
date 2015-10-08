package com.gildedgames.aether.client;

import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gildedgames.aether.client.models.items.AetherItemModels;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		AetherBlockModels.preInit();
		AetherItemModels.preInit();

		AetherCreativeTabs.registerTabIcons();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		AetherItemModels.init();
		AetherRenderers.init();
	}
}
