package com.gildedgames.orbis.common;

import com.gildedgames.aether.api.orbis.management.IProjectManager;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.data.management.OrbisProjectManager;
import com.gildedgames.orbis.common.network.packets.PacketWorldObjectManager;
import com.gildedgames.orbis.common.network.packets.projects.PacketSendProjectListing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.io.File;

public class OrbisCore
{

	private static final GameRegistrar GAME_REGISTRAR = new GameRegistrar();

	private static IProjectManager projectManager;

	@SubscribeEvent
	public static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event)
	{
		/**
		 * Will only send if the server is a dedicated server.
		 * This ensures that on singleplayer, the client uses the same
		 * directory/data as the integrated server (instead of having to
		 * "download" data from the integrated server).
		 */
		if (!event.player.world.isRemote && event.player.getServer() != null && event.player.getServer().isDedicatedServer())
		{
			NetworkingAether.sendPacketToPlayer(new PacketSendProjectListing(), (EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(final EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			final EntityPlayer player = (EntityPlayer) event.getEntity();
			final World world = player.world;

			if (!world.isRemote)
			{
				final WorldObjectManager manager = WorldObjectManager.get(player.getServer().worldServerForDimension(world.provider.getDimension()));

				NetworkingAether.sendPacketToPlayer(new PacketWorldObjectManager(manager), (EntityPlayerMP) player);
			}
		}
	}

	public synchronized static void startProjectManager()
	{
		if (projectManager != null)
		{
			return;
		}

		if (isClient())
		{
			final ServerData data = Minecraft.getMinecraft().getCurrentServerData();

			if (data != null)
			{
				projectManager = new OrbisProjectManager(
						new File(Minecraft.getMinecraft().mcDataDir, "/orbis/servers/" + data.serverIP.replace(":", "_") + "/projects/"));
			}
			else
			{
				projectManager = new OrbisProjectManager(new File(Minecraft.getMinecraft().mcDataDir, "/orbis/local/projects/"));
			}
		}

		if (projectManager == null)
		{
			projectManager = new OrbisProjectManager(new File(DimensionManager.getCurrentSaveRootDirectory(), "/orbis/projects/"));
		}

		projectManager.scanAndCacheProjects();
	}

	public synchronized static void stopProjectManager()
	{
		if (projectManager != null)
		{
			projectManager.flushProjects();
			projectManager = null;
		}
	}

	public synchronized static IProjectManager getProjectManager()
	{
		if (projectManager == null)
		{
			startProjectManager();
		}

		return projectManager;
	}

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static GameRegistrar getRegistrar()
	{
		return OrbisCore.GAME_REGISTRAR;
	}

	public static void onServerStopping(final FMLServerStoppingEvent event)
	{
		stopProjectManager();
	}

	public static void onServerStarted(final FMLServerStartedEvent event)
	{
		startProjectManager();
	}
}
