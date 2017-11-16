package com.gildedgames.orbis.common;

import com.gildedgames.aether.api.io.Instantiator;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.data.management.IDataCachePool;
import com.gildedgames.aether.api.orbis_core.data.management.IProjectManager;
import com.gildedgames.aether.api.orbis_core.data.management.impl.DataCache;
import com.gildedgames.aether.api.orbis_core.data.management.impl.DataCachePool;
import com.gildedgames.aether.api.orbis_core.data.management.impl.OrbisProjectManager;
import com.gildedgames.aether.api.util.IClassSerializer;
import com.gildedgames.aether.api.world_object.IWorldObjectGroup;
import com.gildedgames.aether.common.capabilities.world.WorldObjectGroup;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.data.Text;
import com.gildedgames.orbis.client.renderers.RenderShape;
import com.gildedgames.orbis.common.data.BlueprintPalette;
import com.gildedgames.orbis.common.network.packets.PacketClearSelectedRegion;
import com.gildedgames.orbis.common.network.packets.PacketSendDataCachePool;
import com.gildedgames.orbis.common.network.packets.PacketWorldObjectManager;
import com.gildedgames.orbis.common.network.packets.PacketWorldObjectRemove;
import com.gildedgames.orbis.common.network.packets.projects.PacketSendProjectListing;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selection_types.SelectionTypeCuboid;
import com.gildedgames.orbis.common.player.godmode.selection_types.SelectionTypeLine;
import com.gildedgames.orbis.common.player.godmode.selection_types.SelectionTypeSphere;
import com.gildedgames.orbis.common.tiles.OrbisTileEntities;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.gildedgames.orbis.common.world_objects.WorldRegion;
import com.gildedgames.orbis.common.world_objects.WorldShape;
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

public class Orbis
{

	public static final String BLOCK_DATA_CONTAINERS_CACHE = "block_data_containers";

	private static IProjectManager projectManager;

	private static IDataCachePool dataCache;

	private static void clearSelection(final EntityPlayer player)
	{
		final World world = player.world;
		final PlayerOrbisModule module = PlayerOrbisModule.get(player);

		if (module.powers().getSelectPower().getSelectedRegion() != null && !world.isRemote)
		{
			final WorldObjectManager manager = WorldObjectManager.get(world);
			final IWorldObjectGroup group = manager.getGroup(0);

			NetworkingAether.sendPacketToServer(new PacketClearSelectedRegion());
			NetworkingAether.sendPacketToServer(new PacketWorldObjectRemove(world, group, module.powers().getSelectPower().getSelectedRegion()));

			module.powers().getSelectPower().setSelectedRegion(null);
		}
	}

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
			NetworkingAether.sendPacketToPlayer(new PacketSendDataCachePool(dataCache), (EntityPlayerMP) event.player);
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

	@SubscribeEvent
	public static void onPlayerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event)
	{
		clearSelection(event.player);
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event)
	{
		clearSelection(event.player);
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

	public static void startDataCache()
	{
		if (dataCache != null)
		{
			return;
		}

		if (isClient())
		{
			final ServerData data = Minecraft.getMinecraft().getCurrentServerData();

			if (data != null)
			{
				dataCache = new DataCachePool(
						new File(Minecraft.getMinecraft().mcDataDir, "/orbis/servers/" + data.serverIP.replace(":", "_") + "/cache/"));
			}
			else
			{
				dataCache = new DataCachePool(new File(Minecraft.getMinecraft().mcDataDir, "/orbis/local/cache/"));
			}
		}

		if (dataCache == null)
		{
			dataCache = new DataCachePool(new File(DimensionManager.getCurrentSaveRootDirectory(), "/orbis/cache/"));
		}

		dataCache.readFromDisk();

		if (dataCache.findCache(Orbis.BLOCK_DATA_CONTAINERS_CACHE) == null)
		{
			dataCache.registerCache(new DataCache(Orbis.BLOCK_DATA_CONTAINERS_CACHE));
		}
	}

	public static void stopDataCache()
	{
		if (dataCache != null)
		{
			dataCache.flushToDisk();
			dataCache = null;
		}
	}

	public static IDataCachePool getDataCache()
	{
		if (dataCache == null)
		{
			startDataCache();
		}

		return dataCache;
	}

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static void onServerStopping(final FMLServerStoppingEvent event)
	{
		stopProjectManager();
		startDataCache();
	}

	public static void onServerStarted(final FMLServerStartedEvent event)
	{
		startProjectManager();
		stopDataCache();
	}

	public static void preInit()
	{
		OrbisTileEntities.preInit();

		final IClassSerializer s = OrbisCore.io().getSerializer();

		s.register(2, RenderShape.class, new Instantiator<>(RenderShape.class));
		s.register(3, WorldRegion.class, new Instantiator<>(WorldRegion.class));
		s.register(4, WorldObjectGroup.class, new Instantiator<>(WorldObjectGroup.class));
		s.register(6, Blueprint.class, new Instantiator<>(Blueprint.class));

		s.register(8, SelectionTypeCuboid.class, new Instantiator<>(SelectionTypeCuboid.class));
		s.register(9, SelectionTypeSphere.class, new Instantiator<>(SelectionTypeSphere.class));
		s.register(11, SelectionTypeLine.class, new Instantiator<>(SelectionTypeLine.class));

		s.register(19, Text.class, new Instantiator<>(Text.class));

		s.register(26, WorldShape.class, new Instantiator<>(WorldShape.class));
		s.register(35, BlueprintPalette.class, new Instantiator<>(BlueprintPalette.class));
	}

}
