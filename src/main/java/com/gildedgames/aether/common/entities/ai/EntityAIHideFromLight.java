package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityAIHideFromLight extends Goal
{

	private final float movementSpeed;

	private final CreatureEntity entity;

	private final int lightLevel;

	private double shelterX;

	private double shelterY;

	private double shelterZ;

	private boolean enabled;

	public EntityAIHideFromLight(final CreatureEntity entity, final float movementSpeed, final int lightLevel)
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

		final World world = this.entity.world;

		if (!world.isDaytime())
		{
			return false;
		}
		else if (!world.canSeeSky(new BlockPos(this.entity.posX, this.entity.getBoundingBox().minY, this.entity.posZ)))
		{
			return false;
		}

		final BlockPos entityPos = new BlockPos(this.entity.posX, this.entity.getBoundingBox().minY, this.entity.posZ);

		if (this.entity.world.getBrightness(entityPos) <= this.lightLevel)
		{
			return false;
		}

		final Vec3d vec3d = this.findPossibleShelter();

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
		final Random random = this.entity.getRNG();
		final BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.getBoundingBox().minY, this.entity.posZ);

		for (int i = 0; i < 10; ++i)
		{
			final BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

			if (this.entity.world.getBrightness(blockpos1) <= this.lightLevel)
			{

				return new Vec3d((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
			}
		}

		return null;
	}

	public void setEnabled(final boolean b)
	{
		this.enabled = b;
	}
}
