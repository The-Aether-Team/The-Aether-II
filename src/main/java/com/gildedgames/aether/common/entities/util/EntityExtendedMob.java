package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityExtendedMob extends MonsterEntity
{

	private static final DataParameter<Boolean> ATTACKED = EntityDataManager.createKey(EntityExtendedMob.class, DataSerializers.BOOLEAN);

	private boolean isDayMob;

	protected EntityExtendedMob(EntityType<? extends MonsterEntity> type, World world)
	{
		super(type, world);
	}

	protected void handleClientAttack()
	{

	}

	@Override
	public void livingTick()
	{
		int idleTime = this.idleTime;

		super.livingTick();

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
	public void tick()
	{
		super.tick();

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
