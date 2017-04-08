package com.gildedgames.aether.common.entities.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityExtendedMob extends EntityMob
{

	private static final DataParameter<Boolean> ATTACKED = EntityDataManager.createKey(EntityExtendedMob.class, DataSerializers.BOOLEAN);

	public EntityExtendedMob(World worldIn)
	{
		super(worldIn);
	}

	protected void handleClientAttack()
	{

	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.hasAttacked() && this.world.isRemote)
		{
			this.handleClientAttack();

			this.setAttacked(false);
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

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
