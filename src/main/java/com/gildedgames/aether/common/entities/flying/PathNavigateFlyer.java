package com.gildedgames.aether.common.entities.flying;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateFlyer extends PathNavigator
{
	private boolean shouldAvoidSun;

	private boolean shouldAvoidGround;

	public PathNavigateFlyer(final MobEntity entitylivingIn, final World worldIn)
	{
		super(entitylivingIn, worldIn);
	}

	@Override
	protected void removeSunnyPath()
	{
		super.removeSunnyPath();

		if (this.shouldAvoidSun)
		{
			BlockPos entityPos = new BlockPos(MathHelper.floor(this.entity.posX), (int) (this.entity.getBoundingBox().minY + 0.5D),
					MathHelper.floor(this.entity.posZ));

			if (!(this.world.getTopSolidOrLiquidBlock(entityPos).getY() > entityPos.getY()))
			{
				return;
			}

			for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
			{
				PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
				BlockPos pos = new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z);

				if (!(this.world.getTopSolidOrLiquidBlock(pos).getY() > pos.getY()))
				{
					this.currentPath.setCurrentPathLength(i - 1);
					return;
				}
			}
		}

		if (this.shouldAvoidGround)
		{
			//TODO: Make this work better
			/*BlockPos entityPos = new BlockPos(MathHelper.floor(this.entity.posX), (int) (this.entity.getBoundingBox().minY + 0.5D),
					MathHelper.floor(this.entity.posZ));

			if (Math.abs(this.world.getHeight(entityPos).getY() - entityPos.getY()) <= 10)
			{
				return;
			}

			for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
			{
				PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
				BlockPos pos = new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z);

				if (Math.abs(this.world.getHeight(pos).getY() - pos.getY()) <= 10)
				{
					this.currentPath.setCurrentPathLength(i - 1);
					return;
				}
			}*/
		}
	}

	@Override
	protected PathFinder getPathFinder()
	{
		return new PathFinder(new FlyNodeProcessor());
	}

	/**
	 * If on ground or swimming and can swim
	 */
	@Override
	protected boolean canNavigate()
	{
		return true;
	}

	@Override
	protected Vec3d getEntityPosition()
	{
		return new Vec3d(this.entity.posX, this.entity.posY + (double) this.entity.height * 0.5D, this.entity.posZ);
	}

	@Override
	protected void pathFollow()
	{
		Vec3d vec3d = this.getEntityPosition();
		Vec3d pathVec = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());

		float f = this.entity.width * this.entity.width;

		double sqDistTo = vec3d.squareDistanceTo(pathVec);

		if (sqDistTo < (double) f)
		{
			this.currentPath.incrementPathIndex();
		}

		for (int j = Math.min(this.currentPath.getCurrentPathIndex() + 6, this.currentPath.getCurrentPathLength() - 1);
			 j > this.currentPath.getCurrentPathIndex(); --j)
		{
			Vec3d vec3d1 = this.currentPath.getVectorFromIndex(this.entity, j);

			if (vec3d1.squareDistanceTo(vec3d) <= 36.0D && this.isDirectPathBetweenPoints(vec3d, vec3d1, 0, 0, 0))
			{
				this.currentPath.setCurrentPathIndex(j);
				break;
			}
		}

		this.checkForStuck(vec3d);
	}

	/**
	 * Checks if the specified entity can safely walk to the specified location.
	 */
	@Override
	protected boolean isDirectPathBetweenPoints(final Vec3d posVec31, final Vec3d posVec32, final int sizeX, final int sizeY, final int sizeZ)
	{
		RayTraceResult raytraceresult = this.world
				.rayTraceBlocks(posVec31, new Vec3d(posVec32.x, posVec32.y + (double) this.entity.height * 0.5D, posVec32.z), false, true, false);
		return raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS;
	}

	@Override
	public boolean canEntityStandOnPos(final BlockPos pos)
	{
		return !this.world.getBlockState(pos).isFullBlock();
	}

	public void setAvoidSun(boolean avoidSun)
	{
		this.shouldAvoidSun = avoidSun;
	}

	public void setAvoidGround(boolean avoidGround)
	{
		this.shouldAvoidGround = avoidGround;
	}
}
