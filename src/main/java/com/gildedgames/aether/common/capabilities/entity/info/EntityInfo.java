package com.gildedgames.aether.common.capabilities.entity.info;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.aether.api.entity.IEntityInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class EntityInfo implements IEntityInfo
{

	private Entity lookingAtEntity;

	private int ticksClosed, ticksUntilClose;

	private EntityLivingBase entity;

	private int ticksLooking, ticksUntilLook;

	public EntityInfo()
	{

	}

	public EntityInfo(EntityLivingBase entity)
	{
		this.entity = entity;
	}

	public static IEntityInfo get(EntityLivingBase entity)
	{
		if (entity.hasCapability(AetherCapabilities.ENTITY_INFO, null))
		{
			return entity.getCapability(AetherCapabilities.ENTITY_INFO, null);
		}

		return null;
	}

	private void closeEyes()
	{
		this.ticksUntilClose = 20 + this.entity.getRNG().nextInt(80);
		this.ticksClosed = 3 + this.entity.getRNG().nextInt(4);
	}

	private void lookAtNearbyEntity()
	{
		this.ticksUntilLook = 40 + this.entity.getRNG().nextInt(160);
		this.ticksLooking = 10 + this.entity.getRNG().nextInt(70);
	}

	@Override
	public Entity lookingAtEntity()
	{
		return this.lookingAtEntity;
	}

	@Override
	public int getTicksEyesClosed()
	{
		return this.ticksClosed;
	}

	@Override
	public void update()
	{
		if (this.ticksUntilClose <= 0)
		{
			this.closeEyes();
		}
		else
		{
			this.ticksUntilClose--;
		}

		if (this.ticksClosed > 0)
		{
			this.ticksClosed--;
		}

		if (this.ticksUntilLook <= 0)
		{
			this.lookAtNearbyEntity();
		}
		else
		{
			this.ticksUntilLook--;
		}

		if (this.ticksLooking > 0)
		{
			this.ticksLooking--;
		}

		if (this.ticksLooking > 0)
		{
			if (this.lookingAtEntity == null)
			{
				this.lookingAtEntity = this.entity.getEntityWorld().getClosestPlayerToEntity(this.entity, 20D);
			}
		}
		else
		{
			this.lookingAtEntity = null;
		}
	}

	public static class Storage implements Capability.IStorage<IEntityInfo>
	{
		@Override
		public NBTBase writeNBT(Capability<IEntityInfo> capability, IEntityInfo instance, EnumFacing side)
		{
			return new NBTTagCompound();
		}

		@Override
		public void readNBT(Capability<IEntityInfo> capability, IEntityInfo instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tag = (NBTTagCompound) nbt;
		}
	}

}
