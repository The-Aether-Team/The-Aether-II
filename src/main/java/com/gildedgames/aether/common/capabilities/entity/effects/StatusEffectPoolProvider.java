package com.gildedgames.aether.common.capabilities.entity.effects;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StatusEffectPoolProvider implements ICapabilitySerializable<INBT>
{
	private final StatusEffectPool.Storage storage = new StatusEffectPool.Storage();

	private IAetherStatusEffectPool capability;

	private final LivingEntity livingBase;

	public StatusEffectPoolProvider(LivingEntity livingBase)
	{
		this.livingBase = livingBase;
	}

	private IAetherStatusEffectPool fetchCapability()
	{
		if (this.capability == null)
		{
			this.capability = new StatusEffectPool(this.livingBase);
		}

		return this.capability;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable Direction facing)
	{
		return capability == CapabilitiesAether.STATUS_EFFECT_POOL;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.fetchCapability();
		}

		return null;
	}

	@Override
	public INBT serializeNBT()
	{
		return this.storage.writeNBT(CapabilitiesAether.STATUS_EFFECT_POOL, this.fetchCapability(), null);
	}

	@Override
	public void deserializeNBT(INBT nbt)
	{
		this.storage.readNBT(CapabilitiesAether.STATUS_EFFECT_POOL, this.fetchCapability(), null, nbt);
	}
}
