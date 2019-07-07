package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISpawnSystem;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.common.capabilities.entity.effects.StatusEffectPool;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.spawning.EntitySpawningInfo;
import com.gildedgames.aether.common.capabilities.world.chunk.PlacementFlagCapability;
import com.gildedgames.aether.common.capabilities.world.precipitation.PrecipitationManagerImpl;
import com.gildedgames.aether.common.world.spawning.SpawnSystem;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;

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
}
