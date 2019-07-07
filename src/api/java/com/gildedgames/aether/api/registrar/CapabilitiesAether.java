package com.gildedgames.aether.api.registrar;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISpawnSystem;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilitiesAether extends AbstractRegistrar
{
	@CapabilityInject(IPlayerAether.class)
	public static final Capability<IPlayerAether> PLAYER_DATA = getDefault();

	@CapabilityInject(ISpawningInfo.class)
	public static final Capability<ISpawningInfo> ENTITY_SPAWNING_INFO = getDefault();

	@CapabilityInject(IPlacementFlagCapability.class)
	public static final Capability<IPlacementFlagCapability> CHUNK_PLACEMENT_FLAG = getDefault();

	@CapabilityInject(ISpawnSystem.class)
	public static final Capability<ISpawnSystem> SPAWN_SYSTEM = getDefault();

	@CapabilityInject(IPrecipitationManager.class)
	public static final Capability<IPrecipitationManager> PRECIPITATION_MANAGER = getDefault();

	@CapabilityInject(IAetherStatusEffectPool.class)
	public static final Capability<IAetherStatusEffectPool> STATUS_EFFECT_POOL = getDefault();
}
