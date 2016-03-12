package com.gildedgames.aether.client;

import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.lang.AetherLanguageManager;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.AetherItemModels;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.sound.AetherSounds;
import com.gildedgames.aether.client.sound.SoundEventHandler;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.util.modules.tab.TabModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
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
		AetherRenderers.preInit();

		AetherCreativeTabs.registerTabIcons();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		AetherRenderers.init();
		AetherSounds.init();

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new SoundEventHandler());

		TabModule.api().getInventoryGroup().getSide(Side.CLIENT).add(new TabEquipment());

		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(AetherLanguageManager.INSTANCE);
	}
}
