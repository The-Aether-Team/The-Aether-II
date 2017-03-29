package com.gildedgames.aether.common.entities.ai.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.living.dungeon.labyrinth.EntityProductionLine;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.EnumParticleTypes;

import java.util.List;

public class AIRepairProductionLines extends EntityAI<EntityLiving>
{

	private EntityProductionLine chosenToRepair;

	private TickTimer repairTimer = new TickTimer();

	public AIRepairProductionLines(EntityLiving entity)
	{
		super(entity);

		this.setMutexBits(3);
	}

	@Override
	public void startExecuting()
	{
		List<EntityProductionLine> entityList = this.entity().world.getEntitiesWithinAABB(EntityProductionLine.class, this.entity().getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D));

		if (entityList.size() > 0)
		{
			this.chosenToRepair = entityList.get(this.entity().getRNG().nextInt(entityList.size()));

			this.entity().getNavigator().tryMoveToEntityLiving(this.chosenToRepair, 0.4D);
		}
	}

	@Override
	public boolean shouldExecute()
	{
		return this.chosenToRepair == null;
	}

	@Override
	public boolean continueExecuting()
	{
		return this.chosenToRepair != null;
	}

	@Override
	public boolean isInterruptible()
	{
		return false;
	}

	@Override
	public void resetTask()
	{

	}

	@Override
	public void updateTask()
	{
		if (this.chosenToRepair == null)
		{
			return;
		}

		if (this.entity().getDistanceToEntity(this.chosenToRepair) < 3.0F)
		{
			this.repairTimer.tick();

			if (this.chosenToRepair.getHealth() >= this.chosenToRepair.getMaxHealth())
			{
				this.chosenToRepair = null;
			}
			else
			{
				if (this.repairTimer.isMultipleOfTicks(10))
				{
					this.chosenToRepair.heal(1.0F);
					EntityUtil.spawnParticleLineBetween(this.entity(), this.chosenToRepair, 4D, EnumParticleTypes.CRIT_MAGIC);
				}
			}
		}
		else if (this.entity().getNavigator().noPath())
		{
			this.entity().getNavigator().tryMoveToEntityLiving(this.chosenToRepair, 0.4D);
			this.repairTimer.reset();
		}
	}

}
