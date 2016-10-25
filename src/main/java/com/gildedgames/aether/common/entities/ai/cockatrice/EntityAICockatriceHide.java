package com.gildedgames.aether.common.entities.ai.cockatrice;

import com.gildedgames.aether.common.entities.ai.EntityAIHideFromTarget;
import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import net.minecraft.entity.EntityLivingBase;

public class EntityAICockatriceHide extends EntityAIHideFromTarget
{

	EntityCockatrice cockatrice;

	public EntityAICockatriceHide(EntityCockatrice entity, Class<? extends EntityLivingBase> clazz, double movementSpeed)
	{
		super(entity, clazz, movementSpeed);

		this.cockatrice = entity;
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
