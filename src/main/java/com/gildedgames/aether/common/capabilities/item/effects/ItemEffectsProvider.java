package com.gildedgames.aether.common.capabilities.item.effects;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.items.effects.IItemEffectsCapability;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemEffectsProvider implements ICapabilityProvider
{
	private IItemEffectsCapability effects;

	public ItemEffectsProvider(IItemEffectsCapability effects)
	{
		this.effects = effects;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ITEM_EFFECTS && this.effects != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.effects;
		}

		return null;
	}

}
