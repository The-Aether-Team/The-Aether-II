package com.gildedgames.aether.capabilites;

import com.gildedgames.aether.items.IItemEffectsCapability;
import com.gildedgames.aether.items.IItemPropertiesCapability;
import com.gildedgames.aether.player.IPlayerAetherCapability;
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
}
