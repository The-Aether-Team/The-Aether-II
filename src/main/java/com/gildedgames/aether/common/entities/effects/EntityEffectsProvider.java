package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.effects.IEntityEffectsCapability;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class EntityEffectsProvider implements ICapabilityProvider
{
	private final IEntityEffectsCapability capability;

	public EntityEffectsProvider(IEntityEffectsCapability capability)
	{
		this.capability = capability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ENTITY_EFFECTS && this.capability != null;
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
