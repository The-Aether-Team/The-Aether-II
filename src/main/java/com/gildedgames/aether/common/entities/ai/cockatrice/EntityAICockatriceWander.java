package com.gildedgames.aether.common.entities.ai.cockatrice;

import com.gildedgames.aether.common.entities.living.mobs.EntityCockatrice;
import net.minecraft.entity.ai.EntityAIWander;

public class EntityAICockatriceWander extends EntityAIWander
{

	final EntityCockatrice cockatrice;

	public EntityAICockatriceWander(final EntityCockatrice cockatrice, final double wanderSpeed)
	{
		super(cockatrice, wanderSpeed);

		this.cockatrice = cockatrice;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.cockatrice.isHiding() || this.cockatrice.isAttacking())
		{
			return false;
		}

		return super.shouldExecute();
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		if (this.cockatrice.isHiding() || this.cockatrice.isAttacking())
		{
			return false;
		}

		return super.shouldContinueExecuting();
	}

}
