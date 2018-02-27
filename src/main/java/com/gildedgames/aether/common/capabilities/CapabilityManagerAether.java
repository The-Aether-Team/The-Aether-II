package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISectorAccess;
import com.gildedgames.aether.api.world.instances.IPlayerInstances;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherProvider;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfo;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfoProvider;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagCapability;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagProvider;
import com.gildedgames.aether.common.capabilities.world.instances.PlayerInstances;
import com.gildedgames.aether.common.capabilities.world.instances.PlayerInstancesProvider;
import com.gildedgames.aether.common.capabilities.world.sectors.IslandSectorAccessFlatFile;
import com.gildedgames.aether.common.capabilities.world.sectors.SectorStorageProvider;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityManagerAether
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(CapabilityManagerAether.class);

		// Register capability implementations
		CapabilityManager.INSTANCE.register(ISectorAccess.class, new IslandSectorAccessFlatFile.Storage(), IslandSectorAccessFlatFile::new);

		CapabilityManager.INSTANCE.register(IPlayerAether.class, new PlayerAether.Storage(), PlayerAether::new);
		CapabilityManager.INSTANCE.register(ISpawningInfo.class, new EntitySpawningInfo.Storage(), EntitySpawningInfo::new);
		CapabilityManager.INSTANCE.register(IPlayerInstances.class, new PlayerInstances.Storage(), PlayerInstances::new);
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
			event.addCapability(AetherCore.getResource("PlayerInstances"), new PlayerInstancesProvider((EntityPlayer) event.getObject()));
		}
	}

	@SubscribeEvent
	public static void onWorldAttachCapability(final AttachCapabilitiesEvent<World> event)
	{
		final World world = event.getObject();

		// Attach only to worlds of the Aether dimension on the server
		if (!world.isRemote && world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			event.addCapability(AetherCore.getResource("SectorAccess"), new SectorStorageProvider(world));
		}
	}

	@SubscribeEvent
	public static void attachChunk(final AttachCapabilitiesEvent<Chunk> event)
	{
		if (event.getObject() == null)
		{
			return;
		}

		if (event.getObject() instanceof Chunk)
		{
			event.addCapability(AetherCore.getResource("PlacementFlags"), new PlacementFlagProvider(new PlacementFlagCapability()));
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(final PlayerEvent.Clone event)
	{
		final IPlayerInstances oldPlayer = AetherAPI.instances().getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			final IPlayerInstances newPlayer = AetherAPI.instances().getPlayer((EntityPlayer) event.getEntity());

			final Capability.IStorage<IPlayerInstances> storage = AetherCapabilities.PLAYER_INSTANCES.getStorage();

			final NBTBase state = storage.writeNBT(AetherCapabilities.PLAYER_INSTANCES, oldPlayer, null);

			storage.readNBT(AetherCapabilities.PLAYER_INSTANCES, newPlayer, null, state);
		}
	}

}
