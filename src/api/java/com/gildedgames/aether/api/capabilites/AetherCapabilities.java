package com.gildedgames.aether.api.capabilites;

import com.gildedgames.aether.api.capabilites.chunk.IChunkAttachment;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.api.capabilites.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
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

	@CapabilityInject(IChunkAttachment.class)
	public static final Capability<IChunkAttachment> CHUNK_ATTACHMENTS = null;

	@CapabilityInject(IPlayerInstances.class)
	public static final Capability<IPlayerInstances> PLAYER_INSTANCES = null;

}
