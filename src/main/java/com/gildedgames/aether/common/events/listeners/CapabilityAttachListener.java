package com.gildedgames.aether.common.events.listeners;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.world.preparation.IPrepRegistryEntry;
import com.gildedgames.aether.api.world.spawn.ISpawnSystem;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.effects.StatusEffectPool;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherProvider;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfoProvider;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagCapability;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagProvider;
import com.gildedgames.aether.common.capabilities.world.precipitation.PrecipitationCapabilityProvider;
import com.gildedgames.aether.common.capabilities.world.precipitation.PrecipitationManagerImpl;
import com.gildedgames.aether.common.events.listeners.world.WorldTickListener;
import com.gildedgames.aether.common.world.preparation.capability.PrepManagerStorageProvider;
import com.gildedgames.aether.common.world.spawning.SpawnSystem;
import com.gildedgames.aether.common.world.spawning.SpawnSystemProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityAttachListener
{

	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		IAetherStatusEffectPool statusEffectPool = StatusEffectPool.get(event.getEntityLiving());

		if (statusEffectPool != null)
		{
			statusEffectPool.tick();
		}
	}

	@SubscribeEvent
	public static void onEntityLoad(final AttachCapabilitiesEvent<Entity> event)
	{
		// UNCOMMENT TO ENABLE STATUS EFFECT SYSTEM!!

		/*
		if (event.getObject() instanceof EntityLivingBase)
		{
			event.addCapability(AetherCore.getResource("StatusEffects"), new StatusEffectPoolProvider((EntityLivingBase) event.getObject()));
		}
		*/

		if (event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(AetherCore.getResource("PlayerData"), new PlayerAetherProvider(new PlayerAether((EntityPlayer) event.getObject())));
		}
		else
		{
			// Only attach to non-players
			event.addCapability(AetherCore.getResource("EntityInfo"), new EntitySpawningInfoProvider());
		}
	}

	@SubscribeEvent
	public static void onChunkLoad(final AttachCapabilitiesEvent<Chunk> event)
	{
		event.addCapability(AetherCore.getResource("PlacementFlags"), new PlacementFlagProvider(new PlacementFlagCapability()));
	}

	@SubscribeEvent
	public static void onWorldAttachCapabilities(final AttachCapabilitiesEvent<World> event)
	{
		event.addCapability(AetherCore.getResource("AetherPrecipitation"),
				new PrecipitationCapabilityProvider(new PrecipitationManagerImpl(event.getObject())));

		if (event.getObject().isRemote)
		{
			return;
		}

		final World world = event.getObject();

		final ISpawnSystem spawnSystem = new SpawnSystem(world, WorldTickListener.getSpawnHandlers(world));

		event.addCapability(AetherCore.getResource("SpawnSystem"), new SpawnSystemProvider(spawnSystem));

		for (IPrepRegistryEntry entry : AetherCore.PROXY.content().prep().getEntries())
		{
			if (entry.shouldAttachTo(world))
			{
				event.addCapability(AetherCore.getResource("PrepManagerPool"), new PrepManagerStorageProvider(world, entry));

				break;
			}
		}
	}
}
