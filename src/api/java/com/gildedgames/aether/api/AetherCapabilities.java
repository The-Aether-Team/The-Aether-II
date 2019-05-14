package com.gildedgames.aether.api;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.world.ISpawnSystem;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class AetherCapabilities
{
	@CapabilityInject(IPlayerAether.class)
	public static final Capability<IPlayerAether> PLAYER_DATA = null;

	@CapabilityInject(ISpawningInfo.class)
	public static final Capability<ISpawningInfo> ENTITY_SPAWNING_INFO = null;

	@CapabilityInject(IPlacementFlagCapability.class)
	public static final Capability<IPlacementFlagCapability> CHUNK_PLACEMENT_FLAG = null;

	@CapabilityInject(ISpawnSystem.class)
	public static final Capability<ISpawnSystem> SPAWN_SYSTEM = null;

	@CapabilityInject(IEntityEyesComponent.class)
	public static final Capability<IEntityEyesComponent> ENTITY_INFO = null;

	@CapabilityInject(IPrecipitationManager.class)
	public static final Capability<IPrecipitationManager> PRECIPITATION_MANAGER = null;

	@CapabilityInject(IAetherStatusEffectPool.class)
	public static final Capability<IAetherStatusEffectPool> STATUS_EFFECT_POOL = null;
}
