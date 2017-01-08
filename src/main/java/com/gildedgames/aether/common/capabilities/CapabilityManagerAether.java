package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IChunkAttachment;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.capabilites.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfo;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfoProvider;
import com.gildedgames.aether.common.capabilities.instances.PlayerInstances;
import com.gildedgames.aether.common.capabilities.instances.PlayerInstancesProvider;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherProvider;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachment;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentProvider;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.PlacementFlagCapability;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.PlacementFlagProvider;
import com.gildedgames.aether.common.world.chunk.hooks.events.AttachCapabilitiesChunkEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
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

		CapabilityManager.INSTANCE.register(IPlayerAether.class, new PlayerAether.Storage(), PlayerAether.class);
		CapabilityManager.INSTANCE.register(ISpawningInfo.class, new EntitySpawningInfo.Storage(), EntitySpawningInfo.class);
		CapabilityManager.INSTANCE.register(IPlacementFlagCapability.class, new PlacementFlagCapability.Storage(), PlacementFlagCapability.class);
		CapabilityManager.INSTANCE.register(IChunkAttachment.class, new ChunkAttachment.Storage(), ChunkAttachment.class);
		CapabilityManager.INSTANCE.register(IPlayerInstances.class, new PlayerInstances.Storage(), PlayerInstances.class);
	}

	@SubscribeEvent
	public static void onEntityLoad(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() == null)
		{
			return;
		}

		event.addCapability(AetherCore.getResource("EntitySpawningInfo"), new EntitySpawningInfoProvider());

		if (event.getEntity() instanceof EntityPlayer)
		{
			event.addCapability(AetherCore.getResource("PlayerData"), new PlayerAetherProvider(new PlayerAether((EntityPlayer) event.getEntity())));
			event.addCapability(AetherCore.getResource("PlayerInstances"), new PlayerInstancesProvider((EntityPlayer) event.getEntity()));
		}
	}

	@SubscribeEvent
	public static void onWorldLoad(AttachCapabilitiesEvent.World event)
	{
		event.addCapability(AetherCore.getResource("AetherHooks"), new ChunkAttachmentProvider(new ChunkAttachment()));
	}

	@SubscribeEvent
	public static void onChunkCapabilityAttach(AttachCapabilitiesChunkEvent event)
	{
		event.addCapability(AetherCore.getResource("PlacementFlags"), new PlacementFlagProvider(new PlacementFlagCapability()));
	}

	@SubscribeEvent
	public static void onChunkLoad(ChunkEvent.Load event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.init(event);
		}
	}

	@SubscribeEvent
	public static void onChunkUnload(ChunkEvent.Unload event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.destroy(event);
		}
	}

	@SubscribeEvent
	public static void onChunkDataLoaded(ChunkDataEvent.Load event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.load(event);
		}
	}

	@SubscribeEvent
	public static void onChunkDataUnloaded(ChunkDataEvent.Save event)
	{
		if (event.getWorld().hasCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null))
		{
			IChunkAttachment pool = event.getWorld().getCapability(AetherCapabilities.CHUNK_ATTACHMENTS, null);
			pool.save(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		IPlayerInstances oldPlayer = AetherAPI.instances().getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			IPlayerInstances newPlayer = AetherAPI.instances().getPlayer((EntityPlayer) event.getEntity());

			Capability.IStorage<IPlayerInstances> storage = AetherCapabilities.PLAYER_INSTANCES.getStorage();

			NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_INSTANCES, oldPlayer, null);

			storage.readNBT(AetherCapabilities.PLAYER_INSTANCES, newPlayer, null, state);
		}
	}

}
