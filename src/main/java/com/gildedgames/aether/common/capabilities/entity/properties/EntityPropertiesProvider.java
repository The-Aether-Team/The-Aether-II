package com.gildedgames.aether.common.capabilities.entity.properties;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class EntityPropertiesProvider implements ICapabilityProvider
{
	private final IEntityPropertiesCapability capability;

	public EntityPropertiesProvider(IEntityPropertiesCapability capability)
	{
		this.capability = capability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ENTITY_PROPERTIES && this.capability != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.capability;
		}

		return null;
	}
}
