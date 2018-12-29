package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityAIHideFromLight extends EntityAIBase
{

	private final float movementSpeed;

	private final EntityCreature entity;

	private double shelterX;

	private double shelterY;

	private double shelterZ;

	private final int lightLevel;

	private boolean enabled;

	public EntityAIHideFromLight(EntityCreature entity, float movementSpeed, int lightLevel)
	{
		this.entity = entity;
		this.movementSpeed = movementSpeed;
		this.lightLevel = lightLevel;
		this.enabled = true;

		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.entity == null || !this.enabled)
		{
			return false;
		}

		BlockPos entityPos = new BlockPos(this.entity.posX, this.entity.getEntityBoundingBox().minY, this.entity.posZ);

		if (this.entity.world.getLightFromNeighbors(entityPos) <= this.lightLevel || (this.entity.world.isDaytime() && this.entity.world.canSeeSky(entityPos)))
		{
			return false;
		}

		Vec3d vec3d = this.findPossibleShelter();

		if (vec3d == null)
		{
			return false;
		}
		else
		{
			this.shelterX = vec3d.x;
			this.shelterY = vec3d.y;
			this.shelterZ = vec3d.z;

			return true;
		}
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	@Override
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
	}

	@Nullable
	private Vec3d findPossibleShelter()
	{
		Random random = this.entity.getRNG();
		BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.getEntityBoundingBox().minY, this.entity.posZ);

		for (int i = 0; i < 10; ++i)
		{
			BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

			if (this.entity.world.getLightFromNeighbors(blockpos1) <= this.lightLevel && !(this.entity.world.isDaytime() && this.entity.world.canSeeSky(blockpos1)))
			{

				return new Vec3d((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
			}
		}

		return null;
	}

	public void setEnabled(boolean b)
	{
		this.enabled = b;
	}
}
