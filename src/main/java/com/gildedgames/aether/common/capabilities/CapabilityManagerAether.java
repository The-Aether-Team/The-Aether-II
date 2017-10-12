package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.chunk.IChunkAttachment;
import com.gildedgames.aether.api.chunk.IChunkRendererCapability;
import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.orbis.IWorldObjectManager;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherProvider;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfo;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfoProvider;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManagerProvider;
import com.gildedgames.aether.common.capabilities.world.chunk.*;
import com.gildedgames.aether.common.capabilities.world.sectors.IslandSectorAccessFlatFile;
import com.gildedgames.aether.common.capabilities.world.sectors.SectorStorageProvider;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.orbis.common.network.packets.PacketOrbisWorldObjectManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityManagerAether
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(CapabilityManagerAether.class);

		// Register capability implementations
		CapabilityManager.INSTANCE.register(ISectorAccess.class, new IslandSectorAccessFlatFile.Storage(), IslandSectorAccessFlatFile.class);

		CapabilityManager.INSTANCE.register(IChunkAttachment.class, new ChunkAttachment.Storage(), ChunkAttachment.class);
		CapabilityManager.INSTANCE.register(IPlacementFlagCapability.class, new PlacementFlagCapability.Storage(), PlacementFlagCapability.class);

		CapabilityManager.INSTANCE.register(IWorldObjectManager.class, new WorldObjectManager.Storage(), WorldObjectManager.class);
		CapabilityManager.INSTANCE.register(IChunkRendererCapability.class, new ChunkRendererCapability.Storage(), ChunkRendererCapability.class);

		CapabilityManager.INSTANCE.register(IPlayerAether.class, new PlayerAether.Storage(), PlayerAether.class);
		CapabilityManager.INSTANCE.register(ISpawningInfo.class, new EntitySpawningInfo.Storage(), EntitySpawningInfo.class);
	}

	@SubscribeEvent
	public static void onEntityLoad(final AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() == null)
		{
			return;
		}

		event.addCapability(AetherCore.getResource("EntitySpawningInfo"), new EntitySpawningInfoProvider());

		if (event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(AetherCore.getResource("PlayerData"), new PlayerAetherProvider(new PlayerAether((EntityPlayer) event.getObject())));
		}
	}

	@SubscribeEvent
	public static void onWorldAttachCapability(final AttachCapabilitiesEvent<World> event)
	{
		final World world = event.getObject();

		event.addCapability(AetherCore.getResource("AetherHooks"), new ChunkAttachmentProvider(new ChunkAttachment()));
		event.addCapability(AetherCore.getResource("WorldObjectManager"),
				new WorldObjectManagerProvider(new WorldObjectManager(event.getObject())));

		// Attach only to worlds of the Aether dimension on the server
		if (!world.isRemote && world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			event.addCapability(AetherCore.getResource("SectorAccess"), new SectorStorageProvider(world));
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(final net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
	{
		final World world = event.player.getEntityWorld();

		if (world.hasCapability(AetherCapabilities.WORLD_OBJECT_MANAGER, null))
		{
			final IWorldObjectManager manager = WorldObjectManager.get(world);

			if (!world.isRemote)
			{
				NetworkingAether.sendPacketToPlayer(new PacketOrbisWorldObjectManager(manager), (EntityPlayerMP) event.player);
			}
		}
	}

	@SubscribeEvent
	public static void onChunkCapabilityAttach(final AttachCapabilitiesEvent<IChunkAttachment> event)
	{
		final IChunkAttachment attachment = event.getObject();

		final int chunkX = attachment.getChunkX();
		final int chunkZ = attachment.getChunkZ();

		event.addCapability(AetherCore.getResource("PlacementFlags"), new PlacementFlagProvider(new PlacementFlagCapability()));
		event.addCapability(AetherCore.getResource("ChunkRenderers"), new ChunkRendererProvider(new ChunkRendererCapability(chunkX, chunkZ)));
	}

	@SubscribeEvent
	public static void onChunkLoad(final ChunkEvent.Load event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			final IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.init(event);
		}
	}

	@SubscribeEvent
	public static void onChunkUnload(final ChunkEvent.Unload event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			final IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.destroy(event);
		}
	}

	@SubscribeEvent
	public static void onChunkDataLoaded(final ChunkDataEvent.Load event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			final IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.load(event);
		}
	}

	@SubscribeEvent
	public static void onChunkDataUnloaded(final ChunkDataEvent.Save event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			final IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.save(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(final PlayerEvent.Clone event)
	{

	}

}
