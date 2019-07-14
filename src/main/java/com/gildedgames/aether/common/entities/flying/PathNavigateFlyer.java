package com.gildedgames.aether.common.entities.flying;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

public class PathNavigateFlyer extends PathNavigator
{
	private boolean shouldAvoidSun;

	private boolean shouldAvoidGround;

	public PathNavigateFlyer(final MobEntity entitylivingIn, final World worldIn)
	{
		super(entitylivingIn, worldIn);
	}

	@Override
	protected void trimPath()
	{
		super.trimPath();

		if (this.shouldAvoidSun)
		{
			BlockPos entityPos = new BlockPos(MathHelper.floor(this.entity.posX), (int) (this.entity.getBoundingBox().minY + 0.5D),
					MathHelper.floor(this.entity.posZ));

			if (!(this.world.getHeight(Heightmap.Type.WORLD_SURFACE, entityPos).getY() > entityPos.getY()))
			{
				return;
			}

			for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i)
			{
				PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
				BlockPos pos = new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z);

				if (!(this.world.getHeight(Heightmap.Type.WORLD_SURFACE, pos).getY() > pos.getY()))
				{
					this.currentPath.setCurrentPathIndex(i - 1);
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
	protected PathFinder getPathFinder(int p_179679_1_)
	{
		return new PathFinder(new FlyNodeProcessor(), p_179679_1_);
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
		return new Vec3d(this.entity.posX, this.entity.posY + (double) this.entity.getHeight() * 0.5D, this.entity.posZ);
	}

	@Override
	protected void pathFollow()
	{
		Vec3d vec3d = this.getEntityPosition();
		Vec3d pathVec = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());

		float f = this.entity.getWidth() * this.entity.getWidth();

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
		RayTraceContext context = new RayTraceContext(posVec31, new Vec3d(posVec32.x, posVec32.y + (double) this.entity.getHeight() * 0.5D, posVec32.z), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, this.entity);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(context);
		return raytraceresult.getType() == RayTraceResult.Type.MISS;
	}

	@Override
	public boolean canEntityStandOnPos(final BlockPos pos)
	{
		return !this.world.getBlockState(pos).func_215682_a(this.world, pos, this.entity);
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
