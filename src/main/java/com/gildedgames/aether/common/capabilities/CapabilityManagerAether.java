package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISpawnSystem;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.effects.StatusEffectPool;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherProvider;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfo;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfoProvider;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagCapability;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagProvider;
import com.gildedgames.aether.common.capabilities.world.precipitation.PrecipitationManagerImpl;
import com.gildedgames.aether.common.world.spawning.SpawnSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityManagerAether
{
	public static void init()
	{
		CapabilityManager.INSTANCE.register(IPlayerAether.class, new PlayerAether.Storage(), PlayerAether::new);
		CapabilityManager.INSTANCE.register(ISpawningInfo.class, new EntitySpawningInfo.Storage(), EntitySpawningInfo::new);
		CapabilityManager.INSTANCE.register(ISpawnSystem.class, new SpawnSystem.Storage(), SpawnSystem::new);
		CapabilityManager.INSTANCE.register(IPlacementFlagCapability.class, new PlacementFlagCapability.Storage(), PlacementFlagCapability::new);
		CapabilityManager.INSTANCE.register(IPrecipitationManager.class, new PrecipitationManagerImpl.Storage(), PrecipitationManagerImpl::new);
		CapabilityManager.INSTANCE.register(IAetherStatusEffectPool.class, new StatusEffectPool.Storage(), StatusEffectPool::new);
	}

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

}
