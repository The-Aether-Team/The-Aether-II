package com.gildedgames.aether.client;

import com.gildedgames.aether.client.gui.tab.TabAccessories;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.AetherItemModels;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.util.tab.common.TabAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

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

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

		TabAPI.getInventoryGroup().getSide(Side.CLIENT).add(new TabAccessories());


	}
}
