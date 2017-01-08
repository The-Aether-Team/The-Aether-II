package com.gildedgames.aether.client;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.gui.tab.TabBugReport;
import com.gildedgames.aether.client.gui.tab.TabClientEvents;
import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.ItemModelsAether;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.client.renderer.items.*;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.gildedgames.aether.common.util.structure.StructureInjectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	private PlayerControllerAetherMP clientPlayerController;

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		AetherBlockModels.preInit();
		ItemModelsAether.preInit();
		AetherRenderers.preInit();

		CreativeTabsAether.registerTabIcons();

//		UiManager.inst().registerOverlay("worldAetherOptionsOverlay", WorldAetherOptionsOverlay::new, MinecraftGuiViewer.instance());
//		UiManager.inst().registerOverlay("aetherPortalOverlay", PortalOverlay::new, MinecraftGuiViewer.instance(), RenderGameOverlayEvent.ElementType.PORTAL);
//		UiManager.inst().registerOverlay("bossBattleOverlay", BossBattleOverlay::new, MinecraftGuiViewer.instance(), RenderGameOverlayEvent.ElementType.HOTBAR);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		AetherRenderers.init();

		MinecraftForge.EVENT_BUS.register(AetherMusicManager.INSTANCE);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ClientRenderHandler());
		MinecraftForge.EVENT_BUS.register(StructureInjectionEvents.class);

		MinecraftForge.EVENT_BUS.register(TabClientEvents.class);

		AetherAPI.tabs().getInventoryGroup().registerClientTab(new TabEquipment.Client());
		AetherAPI.tabs().getInventoryGroup().registerClientTab(new TabBugReport.Client());

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemMoaEggColorHandler(), ItemsAether.moa_egg);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LeatherGlovesColorHandler(), ItemsAether.leather_gloves);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new WrappingPaperColorHandler(), ItemsAether.wrapping_paper);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new AetherSpawnEggColorHandler(), ItemsAether.aether_spawn_egg);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new MoaFeatherColorHandler(), ItemsAether.moa_feather);
	}

	@Override
	public void setExtendedReachDistance(EntityPlayer entity, float distance)
	{
		if (entity.worldObj instanceof WorldClient)
		{
			this.clientPlayerController.setExtendedBlockReachDistance(distance);

			return;
		}

		super.setExtendedReachDistance(entity, distance);
	}

	@Override
	public void displayDismountMessage(EntityPlayer player)
	{
		if (player == Minecraft.getMinecraft().thePlayer)
		{
			Minecraft.getMinecraft().ingameGUI.setRecordPlaying(I18n.format("mount.onboard", Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName()), false);
		}
	}

	@Override
	public void onWorldLoaded(WorldEvent event)
	{
		if (event.getWorld() instanceof WorldClient)
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (!(mc.playerController instanceof PlayerControllerAetherMP))
			{
				Minecraft.getMinecraft().playerController = PlayerControllerAetherMP.create(mc.playerController);
			}

			this.clientPlayerController = (PlayerControllerAetherMP) mc.playerController;
		}
	}
}
