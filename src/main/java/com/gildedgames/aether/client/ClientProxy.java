package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabClientEvents;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.ItemModelsAether;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.analytics.GameAnalytics;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.gildedgames.aether.common.util.helpers.PerfHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInit(final FMLPreInitializationEvent event)
	{
		super.preInit(event);

		PerfHelper.measure("Pre-initialize block models", AetherBlockModels::preInit);
		PerfHelper.measure("Pre-initialize aether item models", ItemModelsAether::preInit);
		PerfHelper.measure("Pre-initialize special renders", AetherRenderers::preInit);

		CreativeTabsAether.registerTabIcons();
	}

	@Override
	public void init(final FMLInitializationEvent event)
	{
		super.init(event);

		PerfHelper.measure("Initialize analytics", () ->
		{
			AetherCore.ANALYTICS = AetherCore.isInsideDevEnvironment() ? new GameAnalytics() :
					new GameAnalytics("c8e4d94251ce253e138ae8a702e20301", "1ba3cb91e03cbb578b97c26f872e812dd05f5bbb");

			if (Minecraft.getMinecraft().isSnooperEnabled() && AetherCore.CONFIG.isAnalyticsEnabled())
			{
				AetherCore.ANALYTICS.setup();
			}
			else
			{
				AetherCore.LOGGER.info("GameAnalytics disabled by user preference (by config or vanilla snooper settings)");

				AetherCore.ANALYTICS.disable();
			}
		});

		PerfHelper.measure("Initialize special renders", AetherRenderers::init);

		MinecraftForge.EVENT_BUS.register(AetherMusicManager.INSTANCE);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ClientRenderHandler());

		MinecraftForge.EVENT_BUS.register(new SessionEventHandler());

		MinecraftForge.EVENT_BUS.register(TabClientEvents.class);

		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabEquipment.Client());
		AetherAPI.content().tabs().getInventoryGroup().registerClientTab(new TabBugReport.Client());
	}

	@Override
	public void displayDismountMessage(final EntityPlayer player)
	{
		if (player == Minecraft.getMinecraft().player)
		{
			Minecraft.getMinecraft().ingameGUI
					.setOverlayMessage(I18n.format("mount.onboard", Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName()), false);
		}
	}

	@Override
	public void modifyEntityQuicksoil(final EntityLivingBase entity)
	{
		if (entity == Minecraft.getMinecraft().player)
		{
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
		}

		super.modifyEntityQuicksoil(entity);
	}
}
