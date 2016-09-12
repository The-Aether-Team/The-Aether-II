package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class EntityAILongDistanceWander extends EntityAIBase
{

	private final EntityCreature entity;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private final double speed;
	private int executionChance;
	private boolean mustUpdate;

	private Vec3d longDistanceTarget;

	public EntityAILongDistanceWander(EntityCreature creatureIn, double speedIn)
	{
		this(creatureIn, speedIn, 120);
	}

	public EntityAILongDistanceWander(EntityCreature creatureIn, double speedIn, int chance)
	{
		this.entity = creatureIn;
		this.speed = speedIn;
		this.executionChance = chance;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.longDistanceTarget == null)
		{
			this.longDistanceTarget = RandomPositionGenerator.findRandomTarget(this.entity, 100, 7);
		}
		else if (this.entity.getDistanceSq(this.longDistanceTarget.xCoord, this.longDistanceTarget.yCoord, this.longDistanceTarget.zCoord) < 10.0D * 10.0D)
		{
			this.longDistanceTarget = RandomPositionGenerator.findRandomTarget(this.entity, 100, 7);
		}

		Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 10, 2, this.longDistanceTarget);

		if (vec3d == null)
		{
			return false;
		}
		else
		{
			this.xPosition = vec3d.xCoord;
			this.yPosition = vec3d.yCoord;
			this.zPosition = vec3d.zCoord;
			this.mustUpdate = false;
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
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
