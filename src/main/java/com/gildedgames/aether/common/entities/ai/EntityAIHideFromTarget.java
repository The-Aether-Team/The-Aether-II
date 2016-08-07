package com.gildedgames.aether.common.entities.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class EntityAIHideFromTarget extends EntityAIBase
{

	EntityCreature entity;

	Class<? extends EntityLivingBase> hideFromClass;

	static final int maxDist = 12, minDist = 4;

	protected EntityLivingBase hideFrom;

	private BlockPos hidingPos;

	private final double movementSpeed;

	public EntityAIHideFromTarget(EntityCreature entity, Class<? extends EntityLivingBase> clazz, double movementSpeed)
	{
		this.entity = entity;
		this.hideFromClass = clazz;
		this.movementSpeed = movementSpeed;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.hidingPos != null)
		{
			return true;
		}

		List entities = this.entity.worldObj.getEntitiesWithinAABB(this.hideFromClass, this.entity.getEntityBoundingBox().expand(maxDist, maxDist, maxDist));

		if (entities.isEmpty())
		{
			return false;
		}

		EntityLivingBase toHideFrom = null;

		for (Object o : entities)
		{
			if (o instanceof EntityLivingBase)
			{
				EntityLivingBase entity = (EntityLivingBase) o;

				toHideFrom = entity;
			}
		}
		if (toHideFrom == null)
		{
			return false;
		}
		else
		{
			this.hideFrom = toHideFrom;

			if (!this.hideFrom.canEntityBeSeen(this.entity))
			{
				return false;
			}

			Vec3d spot = this.findHidingSpot();

			if (spot == null)
			{
				return false;
			}

			this.hidingPos = new BlockPos(spot.xCoord, spot.yCoord, spot.zCoord);

			return true;
		}
	}

	@Override
	public boolean continueExecuting()
	{
		return this.hidingPos != null;
	}

	@Override
	public void startExecuting()
	{
		Path path = this.entity.getNavigator().getPathToPos(this.hidingPos);

		this.entity.getNavigator().setPath(path, this.movementSpeed);
	}

	@Override
	public void updateTask()
	{
		if (this.entity.getNavigator().noPath() && this.hideFrom.canEntityBeSeen(this.entity))
		{
			this.hidingPos = null;
		}
	}

	protected Vec3d findHidingSpot()
	{
		Random random = this.entity.getRNG();
		World world = this.entity.worldObj;

		for (int i = 0; i < 13; ++i)
		{
			int j = MathHelper.floor_double(this.entity.posX + random.nextInt(20) - 10.0D);
			int k = MathHelper.floor_double(this.entity.getEntityBoundingBox().minY + random.nextInt(6) - 3.0D);
			int l = MathHelper.floor_double(this.entity.posZ + random.nextInt(20) - 10.0D);

			RayTraceResult raytrace = world.rayTraceBlocks(new Vec3d(j, k + this.entity.getEyeHeight(), l), new Vec3d(this.hideFrom.posX, this.hideFrom.posY + this.hideFrom.getEyeHeight(), this.hideFrom.posZ));

			if (raytrace != null && raytrace.typeOfHit != null)
			{
				return new Vec3d(j, k, l);
			}
		}

		return null;
	}

}
