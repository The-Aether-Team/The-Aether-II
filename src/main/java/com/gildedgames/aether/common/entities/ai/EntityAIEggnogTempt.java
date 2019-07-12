package com.gildedgames.aether.common.entities.ai;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.GroundPathNavigator;

public class EntityAIEggnogTempt extends Goal
{
	/** The entity using this AI that is tempted by the player. */
	private final CreatureEntity temptedEntity;

	private final double speed;

	/** The player that is tempting the entity that is using this AI. */
	private PlayerEntity temptingPlayer;

	/**
	 * A counter that is decremented each time the shouldExecute method is called. The shouldExecute method will always
	 * return false if delayTemptCounter is greater than 0.
	 */
	private int delayTemptCounter;

	/** True if this EntityAITempt task is running */
	private boolean isRunning;

	public EntityAIEggnogTempt(CreatureEntity temptedEntityIn, double speedIn)
	{
		this.temptedEntity = temptedEntityIn;
		this.speed = speedIn;
		this.setMutexBits(3);

		if (!(temptedEntityIn.getNavigator() instanceof GroundPathNavigator))
		{
			throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if (this.delayTemptCounter > 0)
		{
			--this.delayTemptCounter;
			return false;
		}
		else
		{
			this.temptingPlayer = this.temptedEntity.world.getClosestPlayerToEntity(this.temptedEntity, 10.0D);

			if (this.temptingPlayer == null)
			{
				return false;
			}
			else
			{
				return this.isTempting(this.temptingPlayer);
			}
		}
	}

	protected boolean isTempting(PlayerEntity player)
	{
		PlayerAether playerAether = PlayerAether.getPlayer(player);

		return playerAether.hasEggnogEffect();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		return this.shouldExecute();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.isRunning = true;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask()
	{
		this.temptingPlayer = null;
		this.temptedEntity.getNavigator().clearPath();
		this.delayTemptCounter = 100;
		this.isRunning = false;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask()
	{
		this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, (float) (this.temptedEntity.getHorizontalFaceSpeed() + 20),
				(float) this.temptedEntity.getVerticalFaceSpeed());

		if (this.temptedEntity.getDistanceSq(this.temptingPlayer) < 6.25D)
		{
			this.temptedEntity.getNavigator().clearPath();
		}
		else
		{
			this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
		}
	}

	/**
	 * @see #isRunning
	 */
	public boolean isRunning()
	{
		return this.isRunning;
	}
}