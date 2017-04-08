package com.gildedgames.aether.common.capabilities.entity.stats;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EntityStatProvider implements ICapabilitySerializable<NBTBase>
{
	private final EntityStatContainer.Storage storage = new EntityStatContainer.Storage();

	private final EntityStatContainer container;

	public EntityStatProvider(EntityLivingBase entity)
	{
		this.container = new EntityStatContainer(entity);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ENTITY_STATS;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.container;
		}

		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(AetherCapabilities.ENTITY_STATS, this.container, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.ENTITY_STATS, this.container, null, nbt);
	}
}
