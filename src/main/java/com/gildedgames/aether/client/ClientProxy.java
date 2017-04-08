package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabClientEvents;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.ItemModelsAether;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.client.renderer.items.ItemMoaEggColorHandler;
import com.gildedgames.aether.client.renderer.items.LeatherGlovesColorHandler;
import com.gildedgames.aether.client.renderer.items.MoaFeatherColorHandler;
import com.gildedgames.aether.client.renderer.items.WrappingPaperColorHandler;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		AetherBlockModels.preInit();
		ItemModelsAether.preInit();
		AetherRenderers.preInit();

		CreativeTabsAether.registerTabIcons();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		AetherRenderers.init();

		MinecraftForge.EVENT_BUS.register(AetherMusicManager.INSTANCE);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ClientRenderHandler());

		MinecraftForge.EVENT_BUS.register(TabClientEvents.class);

		AetherAPI.tabs().getInventoryGroup().registerClientTab(new TabEquipment.Client());
		AetherAPI.tabs().getInventoryGroup().registerClientTab(new TabBugReport.Client());

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemMoaEggColorHandler(), ItemsAether.moa_egg);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LeatherGlovesColorHandler(), ItemsAether.leather_gloves);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new WrappingPaperColorHandler(), ItemsAether.wrapping_paper);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new MoaFeatherColorHandler(), ItemsAether.moa_feather);
	}

	@Override
	public void displayDismountMessage(EntityPlayer player)
	{
		if (player == Minecraft.getMinecraft().player)
		{
			Minecraft.getMinecraft().ingameGUI.setOverlayMessage(I18n.format("mount.onboard", Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName()), false);
		}
	}
}
