package com.gildedgames.aether.common.entities.util.flying;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateFlyer extends PathNavigate
{
	public PathNavigateFlyer(EntityLiving entitylivingIn, World worldIn)
	{
		super(entitylivingIn, worldIn);
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
		return new Vec3d(this.theEntity.posX, this.theEntity.posY + (double) this.theEntity.height * 0.5D, this.theEntity.posZ);
	}

	@Override
	protected void pathFollow()
	{
		Vec3d vec3d = this.getEntityPosition();
		float f = this.theEntity.width * this.theEntity.width;
		int i = 6;

		if (vec3d.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex()))
				< (double) f)
		{
			this.currentPath.incrementPathIndex();
		}

		for (int j = Math.min(this.currentPath.getCurrentPathIndex() + 6, this.currentPath.getCurrentPathLength() - 1);
			 j > this.currentPath.getCurrentPathIndex(); --j)
		{
			Vec3d vec3d1 = this.currentPath.getVectorFromIndex(this.theEntity, j);

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
	protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ)
	{
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(posVec31, new Vec3d(posVec32.xCoord,
				posVec32.yCoord + (double) this.theEntity.height * 0.5D, posVec32.zCoord), false, true, false);
		return raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS;
	}

	@Override
	public boolean canEntityStandOnPos(BlockPos pos)
	{
		return !this.world.getBlockState(pos).isFullBlock();
	}
}
