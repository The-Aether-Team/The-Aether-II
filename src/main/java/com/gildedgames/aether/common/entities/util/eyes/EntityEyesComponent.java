package com.gildedgames.aether.common.entities.util.eyes;

import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class EntityEyesComponent implements IEntityEyesComponent
{

	private Entity lookingAtEntity;

	private int ticksClosed, ticksUntilClose;

	private LivingEntity entity;

	private int ticksLooking, ticksUntilLook;

	public EntityEyesComponent(LivingEntity entity)
	{
		this.entity = entity;
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
}
