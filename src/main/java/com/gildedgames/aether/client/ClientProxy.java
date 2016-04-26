package com.gildedgames.aether.client;

import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.lang.AetherLanguageManager;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.AetherItemModels;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.client.sound.AetherSounds;
import com.gildedgames.aether.client.sound.SoundEventHandler;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.util.modules.tab.TabModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	public static PlayerControllerAetherMP clientPlayerController;

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
		MinecraftForge.EVENT_BUS.register(new ClientRenderHandler());
		MinecraftForge.EVENT_BUS.register(new SoundEventHandler());

		TabModule.api().getInventoryGroup().registerClientTab(new TabEquipment.Client());

		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(AetherLanguageManager.INSTANCE);

		ClientRenderHandler.init();
	}

	@Override
	public void setExtendedReachDistance(EntityPlayer entity, float distance)
	{
		if (entity.worldObj instanceof WorldClient)
		{
			ClientProxy.clientPlayerController.setExtendedBlockReachDistance(distance);

			return;
		}

		super.setExtendedReachDistance(entity, distance);
	}
}
