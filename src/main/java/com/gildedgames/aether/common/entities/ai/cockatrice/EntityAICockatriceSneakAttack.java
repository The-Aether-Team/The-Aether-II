package com.gildedgames.aether.common.entities.ai.cockatrice;

import com.gildedgames.aether.common.entities.monsters.EntityCockatrice;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

import java.util.List;

public class EntityAICockatriceSneakAttack extends Goal
{

	private final EntityCockatrice entity;

	protected LivingEntity attack;

	protected int attackTick;

	final Class<? extends LivingEntity> attackClass;

	public EntityAICockatriceSneakAttack(final EntityCockatrice entity, final Class<? extends LivingEntity> clazz)
	{
		this.entity = entity;
		this.attackClass = clazz;
	}

	@Override
	public boolean shouldExecute()
	{
		if (!this.entity.isAttacking() && this.entity.isHidden())
		{
			final List entities = this.entity.world.getEntitiesWithinAABB(this.attackClass, this.entity.getBoundingBox().expand(16.0D, 16.0D, 16.0D));

			if (entities.isEmpty())
			{
				return false;
			}

			LivingEntity attack = null;

			for (final Object o : entities)
			{
				if (o instanceof LivingEntity)
				{
					attack = (LivingEntity) o;
				}
			}

			if (attack == null)
			{
				return false;
			}

			this.attack = attack;

			if (this.attack instanceof PlayerEntity)
			{
				final PlayerEntity player = (PlayerEntity) this.attack;

				return !player.isCreative();
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.entity.isAttacking() && this.attack.isAlive();
	}

	@Override
	public void startExecuting()
	{
		this.entity.setAttacking(true);
		this.entity.getNavigator().tryMoveToEntityLiving(this.attack, 1.2D);
		this.attackTick = 0;
	}

	@Override
	public void resetTask()
	{
		//this.entity.setAttacking(false);
		//this.attackTick = 0;
	}

	@Override
	public void tick()
	{
		this.entity.getLookController().setLookPositionWithEntity(this.attack, 30.0F, 30.0F);
		final double distanceBetweenTarget = this.entity.getDistanceSq(this.attack.posX, this.attack.getBoundingBox().minY, this.attack.posZ);

		this.entity.getNavigator().tryMoveToEntityLiving(this.attack, 1.2D);

		this.attackTick = Math.max(this.attackTick - 1, 0);

		if (distanceBetweenTarget <= this.getAttackReachSqr(this.attack) && this.attackTick <= 0)
		{
			this.attackTick = 20;
			this.entity.swingArm(Hand.MAIN_HAND);
			this.entity.attackEntityAsMob(this.attack);

			this.entity.setAttacking(false);
		}
	}

	protected double getAttackReachSqr(final LivingEntity attackTarget)
	{
		return (double) (this.entity.getWidth() * 2.0F * this.entity.getWidth() * 2.0F + attackTarget.getWidth());
	}

}
