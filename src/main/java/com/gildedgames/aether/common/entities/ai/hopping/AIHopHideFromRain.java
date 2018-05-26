package com.gildedgames.aether.common.entities.ai.hopping;

import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.util.helpers.EntityUtil;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Random;

public class AIHopHideFromRain extends EntityAIBase
{

	private final HoppingMoveHelper hoppingMoveHelper;

	private final double movementSpeed;

	private float chosenDegrees;

	private int nextRandomizeTime;

	private EntityCreature entity;

	private double shelterX;

	private double shelterY;

	private double shelterZ;

	private int growTieredTimer;

	public AIHopHideFromRain(EntityCreature entity, final HoppingMoveHelper hoppingMoveHelper, double movementSpeed)
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

		IIslandDataPartial island = IslandHelper.getPartial(this.entity.world, this.entity.chunkCoordX, this.entity.chunkCoordZ);

		boolean executes = island != null && island.getPrecipitation().getType() == PrecipitationType.RAIN && this.entity.getAttackTarget() == null && (
				this.entity.onGround || this.entity.isInWater() || this.entity.isInLava()
						|| this.entity.isPotionActive(MobEffects.LEVITATION));

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
		BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.getEntityBoundingBox().minY, this.entity.posZ);

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
