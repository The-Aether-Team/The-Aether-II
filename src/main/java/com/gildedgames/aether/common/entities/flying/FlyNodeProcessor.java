package com.gildedgames.aether.common.entities.flying;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class FlyNodeProcessor extends NodeProcessor
{

	@Override
	public PathPoint getStart()
	{
		return this.openPoint(MathHelper.floor(this.entity.getEntityBoundingBox().minX), MathHelper.floor(
				this.entity.getEntityBoundingBox().minY + 0.5D), MathHelper.floor(this.entity.getEntityBoundingBox().minZ));
	}

	/**
	 * Returns PathPoint for given coordinates
	 */
	@Override
	public PathPoint getPathPointToCoords(final double x, final double y, final double z)
	{
		return this.openPoint(MathHelper.floor(x - (double) (this.entity.width / 2.0F)), MathHelper.floor(
				y + 0.5D), MathHelper.floor(z - (double) (this.entity.width / 2.0F)));
	}

	@Override
	public int findPathOptions(final PathPoint[] pathOptions, final PathPoint currentPoint, final PathPoint targetPoint, final float maxDistance)
	{
		int i = 0;

		for (EnumFacing enumfacing : EnumFacing.values())
		{
			PathPoint pathpoint = this.getWaterNode(currentPoint.x + enumfacing.getXOffset(), currentPoint.y + enumfacing.getYOffset(),
					currentPoint.z + enumfacing.getZOffset());

			if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
			{
				pathOptions[i++] = pathpoint;
			}
		}

		return i;
	}

	@Override
	public PathNodeType getPathNodeType(final IBlockAccess blockaccessIn, final int x, final int y, final int z, final EntityLiving entitylivingIn,
			final int xSize, final int ySize,
			final int zSize, final boolean canBreakDoorsIn, final boolean canEnterDoorsIn)
	{
		return PathNodeType.OPEN;
	}

	@Override
	public PathNodeType getPathNodeType(final IBlockAccess x, final int y, final int z, final int p_186330_4_)
	{
		return PathNodeType.OPEN;
	}

	@Nullable
	private PathPoint getWaterNode(int p_186328_1_, int p_186328_2_, int p_186328_3_)
	{
		PathNodeType pathnodetype = this.isFree(p_186328_1_, p_186328_2_, p_186328_3_);
		return pathnodetype == PathNodeType.OPEN ? this.openPoint(p_186328_1_, p_186328_2_, p_186328_3_) : null;
	}

	private PathNodeType isFree(int p_186327_1_, int p_186327_2_, int p_186327_3_)
	{
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int i = p_186327_1_; i < p_186327_1_ + this.entitySizeX; ++i)
		{
			for (int j = p_186327_2_; j < p_186327_2_ + this.entitySizeY; ++j)
			{
				for (int k = p_186327_3_; k < p_186327_3_ + this.entitySizeZ; ++k)
				{
					IBlockState iblockstate = this.blockaccess.getBlockState(blockpos$mutableblockpos.setPos(i, j, k));

					if (iblockstate.getMaterial() != Material.AIR)
					{
						return PathNodeType.BLOCKED;
					}
				}
			}
		}

		return PathNodeType.OPEN;
	}
}
