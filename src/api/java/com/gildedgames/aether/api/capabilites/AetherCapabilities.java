package com.gildedgames.aether.api.capabilites;

import com.gildedgames.aether.api.capabilites.chunk.IChunkAttachmentCapability;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.api.capabilites.entity.spawning.ISpawningInfo;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.api.capabilites.items.IItemBreakable;
import com.gildedgames.aether.api.capabilites.entity.IPlayerAetherCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class AetherCapabilities
{
	@CapabilityInject(IPlayerAetherCapability.class)
	public static final Capability<IPlayerAetherCapability> PLAYER_DATA = null;

	@CapabilityInject(IItemBreakable.class)
	public static final Capability<IItemBreakable> ITEM_BREAKABLE = null;

	@CapabilityInject(ISpawningInfo.class)
	public static final Capability<ISpawningInfo> ENTITY_SPAWNING_INFO = null;

	@CapabilityInject(IPlacementFlagCapability.class)
	public static final Capability<IPlacementFlagCapability> CHUNK_PLACEMENT_FLAG = null;

	@CapabilityInject(IChunkAttachmentCapability.class)
	public static final Capability<IChunkAttachmentCapability> CHUNK_ATTACHMENTS = null;

	@CapabilityInject(IPlayerInstances.class)
	public static final Capability<IPlayerInstances> PLAYER_INSTANCES = null;

}
