package com.gildedgames.aether.common.entities.ai.cockatrice;

import com.gildedgames.aether.common.entities.ai.EntityAIHideFromTarget;
import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class EntityAICockatriceHide extends EntityAIHideFromTarget
{

	EntityCockatrice cockatrice;

	public EntityAICockatriceHide(EntityCockatrice entity, Class<? extends EntityLivingBase> clazz, double movementSpeed)
	{
		super(entity, clazz, movementSpeed);

		this.cockatrice = entity;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		boolean flag = super.shouldExecute();

		if (!flag)
		{
			this.cockatrice.setHidden(true);
		}
		else
		{
			this.cockatrice.setHidden(false);
		}

		return flag && !this.cockatrice.isAttacking();
	}

	@Override
	public void startExecuting()
	{
		super.startExecuting();

		this.cockatrice.setHiding(true);
	}

	@Override
	public void resetTask()
	{
		super.resetTask();

		this.cockatrice.setHiding(false);
	}

}
