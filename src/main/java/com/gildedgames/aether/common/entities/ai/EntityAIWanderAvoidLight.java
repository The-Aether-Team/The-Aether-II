package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class EntityAIWanderAvoidLight extends Goal
{
	protected final CreatureEntity entity;

	protected double x;

	protected double y;

	protected double z;

	protected final double speed;

	protected int executionChance;

	protected final int lightLevel;

	protected boolean mustUpdate;

	public EntityAIWanderAvoidLight(CreatureEntity creatureIn, double speedIn, int lightLevel)
	{
		this(creatureIn, speedIn, 120, lightLevel);
	}

	public EntityAIWanderAvoidLight(CreatureEntity creatureIn, double speedIn, int chance, int lightLevel)
	{
		this.entity = creatureIn;
		this.speed = speedIn;
		this.executionChance = chance;
		this.lightLevel = lightLevel;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if (!this.mustUpdate)
		{
			if (this.entity.getIdleTime() >= 100)
			{
				return false;
			}

			if (this.entity.getRNG().nextInt(this.executionChance) != 0)
			{
				return false;
			}
		}

		Vec3d vec3d = this.getPosition();

		if (vec3d == null)
		{
			return false;
		}
		else
		{
			this.x = vec3d.x;
			this.y = vec3d.y;
			this.z = vec3d.z;
			this.mustUpdate = false;
			return true;
		}
	}

	@Nullable
	protected Vec3d getPosition()
	{
		for (int i = 0; i < 10; i++)
		{
			Vec3d pos = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

			if (pos != null)
			{
				BlockPos blockPos = new BlockPos(pos);

				if (this.entity.world.getBrightness(blockPos) <= this.lightLevel && !(this.entity.world.isDaytime() && this.entity.world.canSeeSky(blockPos)))
				{
					return pos;
				}
			}
		}

		return null;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);

		if (!this.entity.getNavigator().noPath())
		{
			Path path = this.entity.getNavigator().getPath();

			for (int i = path.getCurrentPathIndex(); i < path.getCurrentPathLength(); i++)
			{
				PathPoint pp = path.getPathPointFromIndex(i);
				BlockPos blockPos = new BlockPos(pp.x, pp.y, pp.z);

				if (this.entity.world.getBrightness(blockPos) > this.lightLevel || (this.entity.world.isDaytime() && this.entity.world.canSeeSky(blockPos)))
				{
					this.entity.getNavigator().clearPath();
				}
			}
		}
	}

	/**
	 * Makes task to bypass chance
	 */
	public void makeUpdate()
	{
		this.mustUpdate = true;
	}

	/**
	 * Changes task random possibility for execution
	 */
	public void setExecutionChance(int newchance)
	{
		this.executionChance = newchance;
	}
}