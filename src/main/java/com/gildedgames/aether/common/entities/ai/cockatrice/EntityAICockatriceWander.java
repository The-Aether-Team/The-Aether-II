package com.gildedgames.aether.common.entities.ai.cockatrice;

import com.gildedgames.aether.common.entities.living.enemies.EntityCockatrice;
import net.minecraft.entity.ai.EntityAIWander;

public class EntityAICockatriceWander extends EntityAIWander
{

	EntityCockatrice cockatrice;

	public EntityAICockatriceWander(EntityCockatrice cockatrice, double wanderSpeed)
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

}
