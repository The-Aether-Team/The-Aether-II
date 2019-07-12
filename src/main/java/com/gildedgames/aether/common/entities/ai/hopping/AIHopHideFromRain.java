package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.common.util.helpers.EntityUtil;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Random;

public class AIHopHideFromRain extends Goal
{

	private final HoppingMoveHelper hoppingMoveHelper;

	private final double movementSpeed;

	private float chosenDegrees;

	private int nextRandomizeTime;

	private final CreatureEntity entity;

	private double shelterX;

	private double shelterY;

	private double shelterZ;

	private int growTieredTimer;

	public AIHopHideFromRain(CreatureEntity entity, final HoppingMoveHelper hoppingMoveHelper, double movementSpeed)
	{
		this.entity = entity;
		this.movementSpeed = movementSpeed;

		this.hoppingMoveHelper = hoppingMoveHelper;

		this.setMutexBits(2);
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.entity == null)
		{
			return false;
		}

		boolean executes = this.entity.world.isRaining() && this.entity.getAttackTarget() == null && (
				this.entity.onGround || this.entity.isInWater() || this.entity.isInLava()
						|| this.entity.isPotionActive(Effects.LEVITATION));

		if (executes)
		{
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

		return false;
	}

	@Override
	public void startExecuting()
	{
		this.growTieredTimer = 300;
		super.startExecuting();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		return this.entity.getDistance(this.shelterX, this.shelterY, this.shelterZ) < 1 && --this.growTieredTimer > 0;
	}

	@Override
	public void updateTask()
	{
		EntityUtil.facePos(this.entity, this.shelterX, this.shelterY, this.shelterZ, 10.0F, 10.0F);
		this.hoppingMoveHelper.setSpeed(this.movementSpeed);
		this.hoppingMoveHelper.setDirection(this.entity.rotationYaw);
	}

	@Nullable
	private Vec3d findPossibleShelter()
	{
		Random random = this.entity.getRNG();
		BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.getBoundingBox().minY, this.entity.posZ);

		for (int i = 0; i < 10; ++i)
		{
			BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

			if (!this.entity.world.canSeeSky(blockpos1) && this.entity.getBlockPathWeight(blockpos1) < 0.0F)
			{
				return new Vec3d((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
			}
		}

		return null;
	}

}
