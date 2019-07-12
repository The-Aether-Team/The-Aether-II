package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.entities.ai.EntityAI;
import net.minecraft.entity.MobEntity;
import net.minecraft.potion.Effects;

public class AIHopWander extends EntityAI<MobEntity>
{

	private final HoppingMoveHelper hoppingMoveHelper;

	private float chosenDegrees;

	private int nextRandomizeTime;

	public AIHopWander(final MobEntity entity, final HoppingMoveHelper hoppingMoveHelper)
	{
		super(entity);

		this.hoppingMoveHelper = hoppingMoveHelper;

		this.setMutexBits(2);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		return this.entity().getAttackTarget() == null && (this.entity().onGround || this.entity().isInWater() || this.entity().isInLava()
				|| this.entity().isPotionActive(Effects.LEVITATION));
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		if (--this.nextRandomizeTime <= 0)
		{
			this.nextRandomizeTime = 40 + this.entity().getRNG().nextInt(60);
			this.chosenDegrees = (float) this.entity().getRNG().nextInt(360);
		}

		this.hoppingMoveHelper.setSpeed(1.0D);
		this.hoppingMoveHelper.setDirection(this.chosenDegrees);
	}
}
