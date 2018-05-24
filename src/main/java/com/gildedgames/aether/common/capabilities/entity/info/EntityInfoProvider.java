package com.gildedgames.aether.common.capabilities.entity.info;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.IEntityInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EntityInfoProvider implements ICapabilitySerializable<NBTBase>
{

	private final EntityInfo.Storage storage = new EntityInfo.Storage();

	private IEntityInfo capability;

	private EntityLivingBase entity;

	public EntityInfoProvider(EntityLivingBase entity)
	{
		this.entity = entity;
	}

	private IEntityInfo fetchCapability()
	{
		if (this.capability == null)
		{
			this.capability = new EntityInfo(this.entity);
		}

		return this.capability;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherCapabilities.ENTITY_INFO;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (this.hasCapability(capability, facing))
		{
			return (T) this.fetchCapability();
		}

		return null;
	}

	@Override
	public NBTBase serializeNBT()
	{
		return this.storage.writeNBT(AetherCapabilities.ENTITY_INFO, this.fetchCapability(), null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		this.storage.readNBT(AetherCapabilities.ENTITY_INFO, this.fetchCapability(), null, nbt);
	}

}
