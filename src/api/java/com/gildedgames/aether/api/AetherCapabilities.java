package com.gildedgames.aether.api;

import com.gildedgames.aether.api.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.player.IPlayerAether;
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

}
