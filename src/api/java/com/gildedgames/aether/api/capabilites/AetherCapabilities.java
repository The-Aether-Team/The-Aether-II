package com.gildedgames.aether.api.capabilites;

import com.gildedgames.aether.api.capabilites.items.extra_data.IItemExtraDataCapability;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import com.gildedgames.aether.api.capabilites.items.properties.IItemPropertiesCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class AetherCapabilities
{
	@CapabilityInject(IItemEffectsCapability.class)
	public static final Capability<IItemEffectsCapability> ITEM_EFFECTS = null;

	@CapabilityInject(IItemPropertiesCapability.class)
	public static final Capability<IItemPropertiesCapability> ITEM_PROPERTIES = null;

	@CapabilityInject(IPlayerAetherCapability.class)
	public static final Capability<IPlayerAetherCapability> PLAYER_DATA = null;

	@CapabilityInject(IEntityEffectsCapability.class)
	public static final Capability<IEntityEffectsCapability> ENTITY_EFFECTS = null;

	@CapabilityInject(IItemExtraDataCapability.class)
	public static final Capability<IItemExtraDataCapability> ITEM_EXTRA_DATA = null;
}
