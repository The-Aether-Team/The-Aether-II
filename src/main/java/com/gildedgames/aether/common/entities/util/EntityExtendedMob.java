package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityExtendedMob extends MonsterEntity
{

	private static final DataParameter<Boolean> ATTACKED = EntityDataManager.createKey(EntityExtendedMob.class, DataSerializers.BOOLEAN);

	private boolean isDayMob;

	public EntityExtendedMob(World worldIn)
	{
		super(worldIn);
	}

	protected void handleClientAttack()
	{

	}

	@Override
	public void onLivingUpdate()
	{
		int idleTime = this.idleTime;

		super.onLivingUpdate();

		if (this.isDayMob)
		{
			this.idleTime = idleTime;
		}
	}

	public void setDayMob(boolean flag)
	{
		this.isDayMob = flag;
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		if (this.hasAttacked() && this.world.isRemote())
		{
			this.handleClientAttack();

			this.setAttacked(false);
		}
	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(EntityExtendedMob.ATTACKED, Boolean.FALSE);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		this.setAttacked(true);

		return super.attackEntityAsMob(entity);
	}

	public void setAttacked(boolean flag)
	{
		this.dataManager.set(EntityExtendedMob.ATTACKED, flag);
	}

	public boolean hasAttacked()
	{
		return this.dataManager.get(EntityExtendedMob.ATTACKED);
	}

}
